package locationsspringsolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    LocationService service;

    @BeforeEach
    void deleteAllLocations() {
        service.deleteAllLocations();
    }

    @Test
    void testCreateLocations() {
        LocationDto locationDto =
        template.postForObject("/locations", new CreateLocationCommand("Soltvadkert", 18.654654, 24.6546546), LocationDto.class);

        assertEquals("Soltvadkert", locationDto.getName());
    }

    @Test
    void testGetLocations() {

        template.postForObject("/locations", new CreateLocationCommand("Kiskőrös", 18.654654, 24.6546546), LocationDto.class);
        template.postForObject("/locations", new CreateLocationCommand("Soltvadkert", 18.654654, 24.6546546), LocationDto.class);

       List<LocationDto> locationDtos = template.exchange("/locations", HttpMethod.GET, null, new ParameterizedTypeReference<List<LocationDto>>() {}).getBody();

       assertThat(locationDtos)
               .extracting(LocationDto :: getName)
               .containsExactly("Kiskőrös", "Soltvadkert");
    }
}
