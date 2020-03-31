package pl.tarkiewicz.springsecuritysimplefactorauth.TireBought;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.User.User;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TireBought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Tire tire;

    private LocalDate boughtDate;

}
