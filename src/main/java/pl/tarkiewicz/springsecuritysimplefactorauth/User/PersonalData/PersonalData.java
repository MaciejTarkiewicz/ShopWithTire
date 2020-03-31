package pl.tarkiewicz.springsecuritysimplefactorauth.User.PersonalData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tarkiewicz.springsecuritysimplefactorauth.User.User;

@Entity
@NoArgsConstructor
@Data
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "personalData")
    private User user;

    private String name;
    private String LastName;
    private String email;

}
