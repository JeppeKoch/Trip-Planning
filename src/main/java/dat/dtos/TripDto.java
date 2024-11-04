package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Category;
import dat.entities.Trip;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("starttime")
    private LocalTime starttime;
    @JsonProperty("endtime")
    private LocalTime endtime;
    @JsonProperty("startposition")
    private String startposition;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private double price;
    @JsonProperty("category")
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Category category;
    @JsonProperty("guide_id")
    private Long guideId;
    @JsonProperty("guide")
    private GuideDto guide;

    public TripDto(Trip trip) {
        this.id = trip.getId();
        this.starttime = trip.getStarttime();
        this.endtime = trip.getEndtime();
        this.startposition = trip.getStartposition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.guideId = trip.getGuide() != null ? trip.getGuide().getId() : null;

    }
    public TripDto(String name, LocalTime starttime, LocalTime endtime, String startposition, double price, Category category) {
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.price = price;
        this.category = category;
    }

}
