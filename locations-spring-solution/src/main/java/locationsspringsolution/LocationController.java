package locationsspringsolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<LocationDto> getLocations(@RequestParam Optional<String> name) {
        return service.getLocations(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity getLocationById(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(service.getLocationById(id));
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@RequestBody CreateLocationCommand command) {
        return service.createLocation(command);
    }

    @PutMapping("/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id,@RequestBody UpdateLocationCommand command) {
        return service.updateLocation(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") long id) {
        service.deleteLocation(id);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> locationNotFound(LocationNotFoundException lne) {
        Problem problem =
                Problem.builder()
                .withType(URI.create("locations/not-found"))
                .withTitle("Not found!")
                .withStatus(Status.NOT_FOUND)
                .withDetail(lne.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
