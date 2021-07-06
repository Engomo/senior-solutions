package locations;

import java.util.Optional;

public class DistanceService {

    private LocationRepository locationRepository;

    public DistanceService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Location> first = locationRepository.findByName(name1);
        Optional<Location> second = locationRepository.findByName(name2);

        if (first.equals(Optional.empty()) || second.equals(Optional.empty())) {
            return Optional.empty();
        }else {
            double result = first.get().distanceFrom(second.get());
            return Optional.of(result);
        }
    }
}
