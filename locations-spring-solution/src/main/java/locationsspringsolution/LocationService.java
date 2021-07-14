package locationsspringsolution;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private ModelMapper modelMapper;

    private List<Location> locations = Arrays.asList(
            (new Location(1L, "Jeruzsálem", 31.7506409234, 35.2148487102)),
            (new Location(2L, "Oslo", 59.9100592208, 10.7586357184)),
            (new Location(3L, "KraulShavn", 74.1114253082, -57.0599867781)),
            (new Location(4L, "On Equator", 0, -57.0599867781)),
            (new Location(5L, "Sydney", -34.0413086234, 151.3396651641)),
            (new Location(6L, "Kugluktuk", 67.8029654876, -115.0916122714)),
            (new Location(7L, "On Equator1", 0, -115.0916122714)),
            (new Location(8L, "Kingston", 17.9983495526, -76.8044524654)),
            (new Location(9L, "Puerto Montt", -41.5100063557, -72.9552739634)));

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

    public LocationDto getLocationById(Long id) {
       Location result = locations.stream()
                .filter(l -> id.longValue() == l.getId())
                .findFirst().get();

       return modelMapper.map(result, LocationDto.class);
    }
}

