package guru.springframework.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.model.BeerDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Service
public class BeerClientImpl implements BeerClient {

    public static final String BEER_PATH = "/api/v3/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final WebClient webClient;

    public BeerClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<Void> deleteBeerById(String id) {
        return webClient.delete().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id))
                .retrieve().toBodilessEntity().then();
    }

    @Override
    public Mono<BeerDTO> patchBeer(BeerDTO beerDTO) {
        return webClient.patch().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(beerDTO.getId()))
                .body(Mono.just(beerDTO), BeerDTO.class)
                .retrieve().toBodilessEntity()
                .flatMap(voidResponseEntity -> getBeerById(beerDTO.getId()));
    }

    @Override
    public Mono<BeerDTO> patchBeerById(String id, BeerDTO beerDTO) {
        return webClient.patch().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id))
                .body(Mono.just(beerDTO), BeerDTO.class)
                .retrieve().toBodilessEntity()
                .flatMap(voidResponseEntity -> getBeerById(id));
    }

    @Override
    public Mono<BeerDTO> updateBeer(BeerDTO beerDTO) {
        return webClient.put().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(beerDTO.getId()))
                .body(Mono.just(beerDTO), BeerDTO.class)
                .retrieve().toBodilessEntity()
                .flatMap(voidResponseEntity -> getBeerById(beerDTO.getId()));
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDTO) {
        /* 1. Inizia una richiesta POST al percorso definito da BEER_PATH.
           2. Imposta il corpo della richiesta con l'oggetto beerDTO, convertendolo in un Mono<BeerDTO>.
           3. Esegue la richiesta e recupera la risposta sotto forma di ResponseEntity<Void> perché ci interessano l`header e lo status della risposta, ma non il body.
           4. Utilizza flatMap per estrarre il primo valore dall`header "Location" dalla risposta. L`header non può essere null.
           5. Estrae l'ID della birra dal percorso "Location" dividendo l`URL dall'ultimo "/".
           6. Utilizza flatMap per recuperare la birra completa (Mono<BeerDTO>) tramite l'ID estratto, chiamando il metodo getBeerById.
           7. Gestisce la NullPointerException che si potrebbe generare in caso di header "Location" mancante.*/ // Spiegazione
        return webClient.post().uri(BEER_PATH)
                .body(Mono.just(beerDTO), BeerDTO.class)
                .retrieve().toBodilessEntity()
                .flatMap(voidResponseEntity -> Mono.just(Objects.requireNonNull(voidResponseEntity.getHeaders().get("Location")).getFirst()))
                .map(path -> path.split("/")[path.split("/").length - 1])
                .flatMap(this::getBeerById)
                .onErrorResume(NullPointerException.class, throwable -> Mono.error(new RuntimeException("Header 'Location' not found")));
    }

    @Override
    public Flux<BeerDTO> getBeersByBeerStyle(String beerStyle) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path(BEER_PATH).queryParam("beerStyle", beerStyle).build()).retrieve().bodyToFlux(BeerDTO.class);
    }

    @Override
    public Mono<BeerDTO> getBeerById(String id) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id)).retrieve().bodyToMono(BeerDTO.class);
    }

    @Override
    public Flux<BeerDTO> getListBeerDTOs() {
        return webClient.get().uri(BEER_PATH, BeerDTO.class).retrieve().bodyToFlux(BeerDTO.class);
    }

    @Override
    public Flux<JsonNode> getBeersJsonNode() {
        return webClient.get().uri(BEER_PATH, JsonNode.class).retrieve().bodyToFlux(JsonNode.class);
    }

    @Override
    public Flux<Map> getMapBeers() {
        return webClient.get().uri(BEER_PATH, Map.class).retrieve().bodyToFlux(Map.class);
    }

    @Override
    public Flux<String> getListBeers() {
        return webClient.get().uri(BEER_PATH, String.class).retrieve().bodyToFlux(String.class);
    }

}
