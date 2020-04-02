package pl.tarkiewicz.springsecuritysimplefactorauth.tire.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Service
@AllArgsConstructor
public class TireDeleteService {

    private final TireDetailRepo tireDetailRepo;
    private final TireRepo tireRepo;

    public void deleteTireWithDetails(TireDetails tireDetails) {
        tireDetailRepo.delete(tireDetails);
    }

    public void deleteTire(Tire tire) {
        tireRepo.delete(tire);
    }

}
