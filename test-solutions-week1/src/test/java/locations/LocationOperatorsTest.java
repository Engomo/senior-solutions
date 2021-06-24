package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationOperatorsTest {

    List<Location> locations;
    LocationOperators lo;
    LocationParser lp;

    @BeforeEach
    void init() {
        lo = new LocationOperators();
        lp = new LocationParser();

        locations = new ArrayList<>();
        locations.add(new Location("Jeruzsálem", 31.7506409234, 35.2148487102));
        locations.add(new Location("Oslo", 59.9100592208, 10.7586357184));
        locations.add(new Location("KraulShavn", 74.1114253082, -57.0599867781));
        locations.add(new Location("On Equator", 0, -57.0599867781));
        locations.add(new Location("Sydney", -34.0413086234, 151.3396651641));
        locations.add(new Location("Kugluktuk", 67.8029654876, -115.0916122714));
        locations.add(new Location("On Equator1", 0, -115.0916122714));
        locations.add(new Location("Kingston", 17.9983495526, -76.8044524654));
        locations.add(new Location("Puerto Montt", -41.5100063557, -72.9552739634));
    }

    @Test
    void filterOnNorthTest() {
        List<Location> result = new ArrayList<>(lo.filterOnNorth(locations));
        assertEquals(5, result.size());
        assertEquals("Jeruzsálem", result.get(0).getName());
        assertEquals("Oslo", result.get(1).getName());
        assertEquals("KraulShavn", result.get(2).getName());
        assertEquals("Kugluktuk", result.get(3).getName());
        assertEquals("Kingston", result.get(4).getName());
    }

    @RepeatedTest(5)
    void testIsOnEquatorRepeated(RepetitionInfo repetitionInfo) {
        Object[][] values = {{locations.get(0), false}, {locations.get(1), false}, {locations.get(2), false}, {locations.get(3), true}, {locations.get(4), false},
                            {locations.get(5), false}, {locations.get(6), true}, {locations.get(7), false}, {locations.get(8), false}};

        assertEquals(values[repetitionInfo.getCurrentRepetition() - 1][1], lp.isOnEquator(locations.get(repetitionInfo.getCurrentRepetition() - 1)));
    }


}