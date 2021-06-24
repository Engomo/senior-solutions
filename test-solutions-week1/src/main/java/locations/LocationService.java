package locations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    public void writeLocations(Path file, List<Location> locations) {
       try (BufferedWriter writer = Files.newBufferedWriter(file)) {
           for (Location location : locations) {
               writer.write(location.getName() + ",");
               writer.write(location.getLat() + ",");
               writer.write(location.getLon() + "\n");
           }
       }catch (IOException ioe) {
           throw new IllegalStateException("Cannot write file!", ioe);
       }
    }

    public List<Location> readLocationFromCsv(Path file) {
        List<Location> locations = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] temp = line.split(",");
                locations.add(new Location(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2])));
            }
        }catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file!", ioe);
    }
        return locations;
    }
}
