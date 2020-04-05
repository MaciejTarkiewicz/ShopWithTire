package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;

@Repository
public interface TireDetailRepo extends JpaRepository<TireDetails, Long> {

    List<TireDetails> findBySeason(Season season);

}
