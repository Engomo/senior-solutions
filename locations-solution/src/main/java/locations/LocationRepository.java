package locations;

import java.util.List;
import java.util.Optional;

public class LocationRepository {

    private List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void add(Location location){
        locations.add(location);
    }

    public Optional<Location> findByName(String name) {
        return locations.stream()
                .filter(location -> location.getName().equals(name))
                .findFirst();
    }
}
