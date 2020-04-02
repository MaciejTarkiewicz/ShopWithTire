package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;

import java.util.List;

@Repository
public interface TireDetailRepo extends JpaRepository<TireDetails, Long> {

    List<TireDetails> findBySeason(Season season);

}
