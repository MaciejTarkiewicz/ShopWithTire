package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TireRepo extends JpaRepository<Tire, Long> {

//    @Query(value = "SELECT TIRE.* FROM TIRE\n" +
//            "JOIN TIRE_DETAILS  ON TIRE_DETAILS.ID  = TIRE.TIRE_DETAILS_ID \n" +
//            "WHERE TIRE_DETAILS.SEASON = 'SUMMER'", nativeQuery = true)
//    List<Tire> findBySeason(Season season);

}
