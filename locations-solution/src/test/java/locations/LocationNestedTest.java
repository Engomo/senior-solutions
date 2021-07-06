package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LocationNestedTest {

    LocationParser lp;

    @BeforeEach
    void init() {
        lp = new LocationParser();
    }

    @Nested
    class WithZeroCoordinates {
        Location zeroCoordinates;
        @BeforeEach
                void init() {
            zeroCoordinates = new Location("Zero", 0.0, 0.0);
        }
        @Test
        void zeroIsOnEquator() {
            assertTrue(lp.isOnEquator(zeroCoordinates));
        }
        @Test
        void zeroIsOnPrimeMeridian() {
            assertTrue(lp.isOnPrimeMeridian(zeroCoordinates));
        }
    }

    @Nested
    class WithRandomCoordinates {
        Location randomCoordinates;
        @BeforeEach
        void init() {
            randomCoordinates = new Location("Random", 47.497912, 19.040235);
        }
        @Test
        void zeroIsOnEquator() {
            assertFalse(lp.isOnEquator(randomCoordinates));
        }
        @Test
        void zeroIsOnPrimeMeridian() {
            assertFalse(lp.isOnPrimeMeridian(randomCoordinates));
        }
    }
}
