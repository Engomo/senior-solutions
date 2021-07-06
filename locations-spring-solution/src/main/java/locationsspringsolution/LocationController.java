package locationsspringsolution;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class LocationController {

    List<Location> locations = Arrays.asList(new Location(1L,"Sotvadkert", 43.2, 18.5), new Location(2L,"Kiskőrös", 42.3,17.5));

    @GetMapping("/locations")
    public String getLocations() {
        return locations.toString() + LocalDateTime.now();
    }
}
