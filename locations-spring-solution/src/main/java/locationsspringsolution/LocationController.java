package locationsspringsolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class LocationController {

    private LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping("/locations")
    public List<LocationDto> getLocations(@RequestParam Optional<String> name) {
        return service.getLocations(name);
    }

    @GetMapping("/locations")
    public LocationDto getLocationById(@RequestParam Long id) {
        return service.getLocationById(id);
    }
}
