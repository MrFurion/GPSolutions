package by.trubetski.gpsolutions.dto.constants;

public class RequestConstants {
    private RequestConstants() {
    }
    public static final String SHOULD_BETWEEN_5_AND_255_CHARACTER = " should between 5 and 255 character ";
    public static final String SHOULD_NOT_BE_EMPTY = " should not be empty";
    public static final String NAME = "Name";
    public static final String BRAND = "Brand";
    public static final String HOUSE_NUMBER = "House number";
    public static final String SHOULD_BETWEEN_1_AND_255_CHARACTER = " should between 1 and 255 character";
    public static final String STREET = "Street";
    public static final String CITY = "City";
    public static final String COUNTRY = "Country";
    public static final String POSTAL_CODE = "Postal code";
    public static final String PHONE = "Phone";
    public static final String EMAIL = "Email";
    public static final String MESSAGE_PHONE_FORMAT = "Phone number must be in the format +375 XX XXX-XX-XX (e.g., +375 17 309-80-00)";
    public static final String PHONE_FORMAT = "^\\+375\\s\\d{2}\\s\\d{3}-\\d{2}-\\d{2}$";
    public static final String SHOULD_BE_YOUR_NAME_EMAIL_HILTON_COM_FORMAT = " Should be yourNameEmail@hilton.com format";
    public static final String CHECK_IN_TIME_MUST_BE_IN_24_HOUR_FORMAT_HH_MM_E_G_14_00 = "Check-in time must be in 24-hour format HH:mm (e.g., 14:00)";
    public static final String CHECK_OUT_TIME_MUST_BE_IN_24_HOUR_FORMAT_HH_MM_E_G_12_00 = "Check-out time must be in 24-hour format HH:mm (e.g., 12:00)";
    public static final String TIME = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
    public static final String MESSAGE_FOR_AMENITIES_FIELD = "Amenities must be one of: Free parking, Free WiFi, Non-smoking rooms, " +
                                                             "Concierge, On-site restaurant, Fitness center, Pet-friendly rooms, " +
                                                             "Room service, Business center, Meeting rooms";
    public static final String PATTERN_FOR_AMENITIES_FIELD = "^(Free parking|Free WiFi|Non-smoking rooms|Concierge|On-site restaurant|Fitness center" +
                                                             "|Pet-friendly rooms|Room service|Business center|Meeting rooms)?$";
}
