package by.trubetski.gpsolutions.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String brand;

    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Contacts> contacts = new ArrayList<>();

    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Amenities amenities;

    @ElementCollection
    @CollectionTable(name = "arrival_times", joinColumns = @JoinColumn(name = "hotel_id"))
    @Builder.Default
    private Set<ArrivalTime> arrivalTimes = new HashSet<>();
}
