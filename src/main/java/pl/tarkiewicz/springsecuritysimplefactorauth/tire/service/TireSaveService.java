package pl.tarkiewicz.springsecuritysimplefactorauth.tire.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

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
