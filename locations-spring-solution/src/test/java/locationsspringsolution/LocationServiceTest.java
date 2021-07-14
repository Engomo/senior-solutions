package locationsspringsolution;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    @Test
    void testGetLocations() {
        LocationService service = new LocationService(new ModelMapper());

        assertEquals(9, service.getLocations(Optional.empty()).size());
        assertEquals(1, service.getLocations(Optional.of("Kingston")).size());
    }

    @Test
    void testGetLocationById() {
        LocationService service = new LocationService(new ModelMapper());

        assertThat(service.getLocationById(1L).getName().equals("Jeruzsálem"));
        assertThat(service.getLocationById(6L).getName().equals("Kugluktuk"));
    }
}