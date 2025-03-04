package guru.springframework.client;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface BeerClient {

    Flux<String> getListBeer();

    Flux<Map> getMapBeer();

}


