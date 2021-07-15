package locationsspringsolution;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private ModelMapper modelMapper;

    private AtomicLong idGenerator = new AtomicLong();

    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(List.of(
            (new Location(idGenerator.incrementAndGet(), "Jeruzsálem", 31.7506409234, 35.2148487102)),
            (new Location(idGenerator.incrementAndGet(), "Oslo", 59.9100592208, 10.7586357184)),
            (new Location(idGenerator.incrementAndGet(), "KraulShavn", 74.1114253082, -57.0599867781)),
            (new Location(idGenerator.incrementAndGet(), "On Equator", 0, -57.0599867781)),
            (new Location(idGenerator.incrementAndGet(), "Sydney", -34.0413086234, 151.3396651641)),
            (new Location(idGenerator.incrementAndGet(), "Kugluktuk", 67.8029654876, -115.0916122714)),
            (new Location(idGenerator.incrementAndGet(), "On Equator1", 0, -115.0916122714)),
            (new Location(idGenerator.incrementAndGet(), "Kingston", 17.9983495526, -76.8044524654)),
            (new Location(idGenerator.incrementAndGet(), "Puerto Montt", -41.5100063557, -72.9552739634)))));

    public LocationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> getLocations(Optional<String> name) {
        Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
        List<Location> filtered = locations.stream()
                .filter(l ->  name.isEmpty() || l.getName().equals(name.get()))
                .collect(Collectors.toList());
        return modelMapper.map(filtered, targetListType);
    }

    public LocationDto getLocationById(long id) {
       Location result = locations.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElseThrow(()-> new LocationNotFoundException("Not Found!"));

       return modelMapper.map(result, LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(idGenerator.incrementAndGet(), command.getName(), command.getLat(), command.getLon());
        locations.add(location);
        return modelMapper.map(location, LocationDto.class);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location location = locations.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElseThrow(()-> new LocationNotFoundException(("Location not found : " + id)));

        location.setName(command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());

        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(long id) {
       Location location = locations.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElseThrow(()-> new LocationNotFoundException(("Location not found : " + id)));
        locations.remove(location);
    }
}

