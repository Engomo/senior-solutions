package locations;

public class LocationParser {

    public Location parse(String text){
        String[] temp = text.split(",");
        return new Location(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
    }

    public boolean isOnEquator(Location location){
        if(location.getLat() == 0) {
            return true;
        }
        return false;
    }

    public boolean isOnPrimeMeridian(Location location){
        if(location.getLon() == 0) {
            return true;
        }
        return false;
    }
}
