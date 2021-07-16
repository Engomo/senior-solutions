package locationsspringsolution;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LocationController.class)
public class LocationsControllerWebMvcIT {

    @MockBean
    LocationService locationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testListLocations() throws Exception{
        when(locationService.getLocations((any())))
                .thenReturn(List.of(
                        new LocationDto("Jeruzsálem", 31.7506409234, 35.2148487102),
                        new LocationDto("Oslo", 59.9100592208, 10.7586357184)));

        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name", equalTo("Oslo")));
    }
}
