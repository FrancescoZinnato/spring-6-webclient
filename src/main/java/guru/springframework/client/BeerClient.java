package guru.springframework.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BeerClient {

    Flux<String> getListBeers();

    Flux<Map> getMapBeers();

    Flux<JsonNode> getBeersJsonNode();

    Flux<BeerDTO> getListBeerDTOs();

    Mono<BeerDTO> getBeerById(String id);

    Flux<BeerDTO> getBeersByBeerStyle(String beerStyle);

    Mono<BeerDTO> createBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(BeerDTO beerDTO);

    Mono<BeerDTO> patchBeerById(String id, BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(BeerDTO beerDTO);

    Mono<Void> deleteBeerById(String id);

}


