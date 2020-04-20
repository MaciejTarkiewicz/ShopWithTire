package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class TireDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer wide;

    @Enumerated(value = EnumType.STRING)
    private Season season;

    private Integer profile;

    private Integer diameter;

    private String mark;

    // IMO: powinno byc one to one
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "tire_details_id")
    @Builder.Default
    private List<Tire> tireLists = new ArrayList<>();

}
