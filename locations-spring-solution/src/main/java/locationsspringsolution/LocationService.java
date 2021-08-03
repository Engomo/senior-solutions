package locationsspringsolution;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service

public class LocationService {

    private ModelMapper modelMapper;

    private AtomicLong idGenerator = new AtomicLong();

    private LocationRepository repository;

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

    public LocationService(ModelMapper modelMapper, LocationRepository repository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    public List<LocationDto> getLocations(Optional<String> name) {
        return repository.findAll().stream()
                .map(l -> modelMapper.map(l, LocationDto.class))
                .collect(Collectors.toList());
    }

    public LocationDto getLocationById(long id) {
//       Location result = locations.stream()
//                .filter(l -> l.getId() == id)
//                .findFirst().orElseThrow(()-> new LocationNotFoundException(id));
//
//        return modelMapper.map(result, LocationDto.class);

        return modelMapper.map(repository.findById(id).orElseThrow(()-> new LocationNotFoundException(id)),LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(idGenerator.incrementAndGet(), command.getName(), command.getLat(), command.getLon());
//        locations.add(location);
        repository.save(location);
        return modelMapper.map(location, LocationDto.class);


    }

    @Transactional
    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location location = repository.findById(id).orElseThrow(()-> new LocationNotFoundException(id));

        location.setName(command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());

        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(long id) {
       repository.deleteById(id);

    }

    public void deleteAllLocations() {
        repository.deleteAll();
    }
}

