package locationsspringsolution;

import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private long id;
    private String name;
    private double lat;
    private double lon;

    protected boolean canEqual(final Object other) {
        return other instanceof Location;
    }

}
