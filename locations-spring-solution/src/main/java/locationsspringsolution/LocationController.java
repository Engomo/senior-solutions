package locationsspringsolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class LocationController {

    private LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping("/locations")
    public List<LocationDto> getLocations() {
        return service.getLocations();
    }
}
