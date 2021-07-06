package locations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class LocationsController {

    private List<Location> locations = Arrays.asList(new Location(1L,"Soltvadkert", 43.2, 18.5), new Location(2L,"Kiskőrös", 42.3,17.5));

    @GetMapping("/")
    @ResponseBody
    public String getLocations() {
        return locations.toString() + LocalDateTime.now();
    }
}