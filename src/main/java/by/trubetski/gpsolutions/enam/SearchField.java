package by.trubetski.gpsolutions.enam;

import java.util.HashMap;
import java.util.Map;

public enum SearchField {
    NAME("name"),
    BRAND("brand"),
    CITY("city"),
    COUNTRY("country"),
    AMENITIES("amenities");

    private static final Map<String, SearchField> DISPLAY_NAME_TO_FIELD;

    static {
        DISPLAY_NAME_TO_FIELD = new HashMap<>();
        for (SearchField field : SearchField.values()) {
            DISPLAY_NAME_TO_FIELD.put(field.displayName, field);
        }
    }

    private final String displayName;

    SearchField(String displayName) {
        this.displayName = displayName;
    }

    public static SearchField fromDisplayName(String displayName) {
        return DISPLAY_NAME_TO_FIELD.get(displayName.toLowerCase());
    }
}
