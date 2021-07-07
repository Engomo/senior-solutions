package locationsspringsolution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    LocationService service;

    @InjectMocks
    LocationController controller;

    @Test
    void getLocations() {
        when(service.getLocations()).thenReturn(Arrays.asList(new Location(8L, "Kingston", 17.9983495526, -76.8044524654)));

        String locationsString = controller.getLocations();

        assertThat(locationsString).containsIgnoringCase("Kingston");
    }
}