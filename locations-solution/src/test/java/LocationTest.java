
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    LocationParser lp;

    @BeforeEach
    void init() {
        lp = new LocationParser();
    }

    @Test
    @DisplayName("Test the parse method")
    void testParse() {
        Location budapest = lp.parse("Budapest,47.497912,19.040235");
        assertEquals("Budapest", budapest.getName());
        assertEquals(47.497912, budapest.getLat(), 0.000005);
        assertEquals(19.040235, budapest.getLon(), 0.000005);
    }

    @Test
    void testAllInOne() {
        Location soltvadkert = lp.parse("Soltvadkert,46.5809,19.3937");
        assertAll(
                ()->     assertEquals("Soltvadkert", soltvadkert.getName()),
                ()-> assertEquals(46.5809, soltvadkert.getLat(), 0.000005),
                ()->  assertEquals(19.3937, soltvadkert.getLon(), 0.000005));
    }

    @Test
    @DisplayName("Test the isOnEquator method")
    void isOnEquatorTest() {
        Location equator = lp.parse("Equator,0.0,50.0");
        Location primeMeridian = lp.parse("Prime Meridian,50.0,0.0");
        assertTrue(lp.isOnEquator(equator));
        assertFalse(lp.isOnEquator(primeMeridian));
    }

    @Test
    @DisplayName("Test the isOnPrimeMeridian method")
    void isOnPrimeMeridianTest() {
        Location equator = lp.parse("Equator,0.0,50.0");
        Location primeMeridian = lp.parse("Prime Meridian,50.0,0.0");
        assertTrue(lp.isOnPrimeMeridian(primeMeridian));
        assertFalse(lp.isOnPrimeMeridian(equator));
    }

    @Test
    void testDistanceFrom() {
    Location budapest = lp.parse("Budapest,47.497912,19.040235");
    Location soltvadkert = lp.parse("Soltvadkert,46.5809,19.3937");
    assertEquals(105426, budapest.distanceFrom(soltvadkert), 0.5);
    }

    @Test
    void validatorTest() {
        assertThrows(IllegalArgumentException.class, ()-> lp.parse("Wrong1,-100,80"));
        assertThrows(IllegalArgumentException.class, ()-> lp.parse("Wrong2,100,80"));
        assertThrows(IllegalArgumentException.class, ()-> lp.parse("Wrong3,-55,-280"));
        assertThrows(IllegalArgumentException.class, ()-> lp.parse("Wrong4,55,280"));
    }
}
