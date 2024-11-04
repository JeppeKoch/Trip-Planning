package dat.entities;


import dat.dtos.TripDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "starttime", nullable = false)
    private LocalTime starttime;
    @Column(name = "endtime", nullable = false)
    private LocalTime endtime;
    @Column(name = "startposition", nullable = false)
    private String startposition;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = true)
    private Guide guide;

    public Trip(TripDto tripDto) {
        this.starttime = tripDto.getStarttime();
        this.endtime = tripDto.getEndtime();
        this.startposition = tripDto.getStartposition();
        this.name = tripDto.getName();
        this.price = tripDto.getPrice();
        this.category = tripDto.getCategory();
        if (tripDto.getGuideId() != null) {
            this.guide = new Guide();
            this.guide.setId(tripDto.getGuideId());
        }
    }
    public Trip(LocalTime starttime, LocalTime endtime, String startposition, String name, double price, Category category) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
