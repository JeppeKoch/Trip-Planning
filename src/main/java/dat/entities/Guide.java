package dat.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guide")
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "years_of_experience", nullable = false)
    private double yearsOfExperience;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Trip> trips;

    public Guide(dat.dtos.GuideDto guideDto) {
        this.firstname = guideDto.getFirstname();
        this.lastname = guideDto.getLastname();
        this.email = guideDto.getEmail();
        this.phone = guideDto.getPhone();
        this.yearsOfExperience = guideDto.getYearsOfExperience();
    }


    public void addTrip(Trip trip) {
        trips.add(trip);
        trip.setGuide(this);
    }

    public void removeTrip(Trip trip) {
        trips.remove(trip);
        trip.setGuide(null);
    }
}
