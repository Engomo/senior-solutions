package locationsspringsolution;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Long id;
    private String name;
    private double lat;
    private double lon;

    protected boolean canEqual(final Object other) {
        return other instanceof Location;
    }

}
