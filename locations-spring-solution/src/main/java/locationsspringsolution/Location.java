package locationsspringsolution;

import lombok.*;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name")
    private String name;

    @Column(name = "location_lat")
    private double lat;

    @Column(name = "location_lon")
    private double lon;

    protected boolean canEqual(final Object other) {
        return other instanceof Location;
    }

}
