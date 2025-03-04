package guru.springframework.client;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;

import java.util.Map;

public interface BeerClient {

    Flux<String> getListBeer();

    Flux<Map> getMapBeer();

    Flux<JsonNode> getBeerJsonNode();

}


