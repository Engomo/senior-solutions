import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    LocationService ls = new LocationService();

    @TempDir
    Path tempDir;

    @Test
    void writeLocations() throws IOException {
        Path file = tempDir.resolve("test-write.txt");

        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Jeruzsálem", 31.7506409234, 35.2148487102));
        locations.add(new Location("Oslo", 59.9100592208, 10.7586357184));
        locations.add(new Location("KraulShavn", 74.1114253082, -57.0599867781));
        locations.add(new Location("On Equator", 0, -57.0599867781));
        locations.add(new Location("Sydnesdy", -34.0413086234, 151.3396651641));
        locations.add(new Location("Kugluktuk", 67.8029654876, -115.0916122714));
        locations.add(new Location("On Equator1", 0, -115.0916122714));
        locations.add(new Location("Kingston", 17.9983495526, -76.8044524654));
        locations.add(new Location("Puerto Montt", -41.5100063557, -72.9552739634));

        new LocationService().writeLocations(file, locations);

       List<String> content = Files.readAllLines(file);
        assertEquals(content.get(7), "Kingston,17.9983495526,-76.8044524654");
    }

    @Test
    void testReadLocationsFromCsv() throws IOException {
        Path file = Path.of("src/main/resources/locations.csv");

        List<Location> locations = ls.readLocationFromCsv(file);

        Assertions.assertThat(locations).extracting(Location:: getName, Location:: getLat, Location::getLon)
                .contains(tuple("Kingston",17.9983495526,-76.8044524654))
                .hasSize(9);
    }
}