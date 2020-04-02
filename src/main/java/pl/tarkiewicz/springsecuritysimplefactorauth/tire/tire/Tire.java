package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireBought.TireBought;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private boolean bought;

    @ManyToOne
    private TireDetails tireDetails;

    @OneToOne(mappedBy = "tire", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private TireBought tireBought;

}
