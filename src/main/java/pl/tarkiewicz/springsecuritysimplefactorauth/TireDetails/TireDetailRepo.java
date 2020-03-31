package pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Season;

import java.util.List;

@Repository
public interface TireDetailRepo extends JpaRepository<TireDetails, Long> {

    List<TireDetails> findBySeason(Season season);

}
