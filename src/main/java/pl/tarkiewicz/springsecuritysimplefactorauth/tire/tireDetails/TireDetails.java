package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails;

import lombok.*;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
