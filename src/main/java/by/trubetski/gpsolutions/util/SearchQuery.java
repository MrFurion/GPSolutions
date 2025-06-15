package by.trubetski.gpsolutions.util;

public class SearchQuery {
    private SearchQuery() {
    }
    public static String buildQuery(String name, String brand, String city, String country, String amenities) {
        if (city != null) return "city=" + city;
        else if (name != null) return "name=" + name;
        else if (brand != null) return "brand=" + brand;
        else if (country != null) return "country=" + country;
        else if (amenities != null) return "amenities=" + amenities;
        return null;
    }
    public static String buildQuery(String brand, String city, String country, String amenities) {
        if (city != null) return "city=" + city;
        else if (brand != null) return "brand=" + brand;
        else if (country != null) return "country=" + country;
        else if (amenities != null) return "amenities=" + amenities;
        return null;
    }
}
