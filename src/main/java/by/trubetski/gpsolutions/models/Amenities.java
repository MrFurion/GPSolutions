package by.trubetski.gpsolutions.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "amenities")
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "free_parking")
    private boolean freeParking;
    @Column(name = "free_wifi")
    private boolean freeWifi;
    @Column(name = "non_smoking_rooms")
    private boolean nonSmokingRooms;
    @Column(name = "concierge")
    private boolean concierge;
    @Column(name = "on_site_restaurant")
    private boolean onSiteRestaurant;
    @Column(name = "fitness_center")
    private boolean fitnessCenter;
    @Column(name = "pet_friendly_rooms")
    private boolean petFriendlyRooms;
    @Column(name = "room_service")
    private boolean roomService;
    @Column(name = "business_center")
    private boolean businessCenter;
    @Column(name = "meeting_rooms")
    private boolean meetingRooms;

    @OneToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
