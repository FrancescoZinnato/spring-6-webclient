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
    void testGetBeersByBeerStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersByBeerStyle("Pale Ale").subscribe(beerDTO -> {
            System.out.println(beerDTO);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerById() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getListBeerDTOs().flatMap(fluxDTO -> beerClient.getBeerById(fluxDTO.getId()))
                .subscribe(dtoById -> {
                    System.out.println(dtoById);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetListBeerDTOs() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getListBeerDTOs().subscribe(beerDTO -> {
            System.out.println(beerDTO.getBeerName());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeersJsonNode() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersJsonNode().subscribe(jsonNode -> {
            System.out.println(jsonNode.toPrettyString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetMapBeers() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getMapBeers().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetListBeers() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getListBeers().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

}