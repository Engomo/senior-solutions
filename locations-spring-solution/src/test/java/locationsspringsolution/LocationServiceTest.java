package locationsspringsolution;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    @Test
    void testGetLocations() {
        LocationService service = new LocationService(new ModelMapper());

        assertEquals(9, service.getLocations().size());
    }
}