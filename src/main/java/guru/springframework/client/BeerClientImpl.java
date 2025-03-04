package guru.springframework.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.model.BeerDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class BeerClientImpl implements BeerClient {

    public static final String BEER_PATH = "/api/v3/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final WebClient webClient;

    public BeerClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
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
