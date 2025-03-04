package guru.springframework.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void testGetBeerJsonNode() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeerJsonNode().subscribe(jsonNode -> {
            System.out.println(jsonNode.toPrettyString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetMapBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getMapBeer().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetListBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getListBeer().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

}