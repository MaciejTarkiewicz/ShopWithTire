package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetails;

@Service
@AllArgsConstructor
public class TireDeleteService {

    private final TireDetailRepo tireDetailRepo;
    private final TireRepo tireRepo;

    public List<Tire> TiresToDelete(TireDetails tireDetails) {
        return tireDetailRepo.findById(tireDetails.getId())
                .stream()
                .map(TireDetails::getTireLists)
                .flatMap(Collection::stream)
                .filter(tire -> !tire.isBought())
                .collect(Collectors.toList());
    }

    public void deleteTireWithDetails(TireDetails tireDetails) {
        tireDetailRepo.delete(tireDetails);
    }

    public void deleteTire(Tire tire) {
        tireRepo.delete(tire);
    }

}
