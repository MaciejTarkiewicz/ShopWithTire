package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetails;

@Service
@AllArgsConstructor
public class TireSaveService {

    private final TireDetailRepo tireDetailRepo;
    private final TireRepo tireRepo;

    public void save(TireDetails tireDetails) {
        tireDetailRepo.save(tireDetails);
    }

    public void save(Tire tire) {
        tireRepo.save(tire);
    }

}
