package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.Exceptions.NotFoundException;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Season;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetails;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireWithDetailsWebCommandConverter;

@Service
@AllArgsConstructor
public class TireGetService {

    private final TireRepo tireRepo;
    private final TireDetailRepo tireDetailRepo;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

    public List<TireDto> getAllTire() {
        return tireRepo.findAll()
                .stream()
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TireDto> getAllBySeason(Season season) {
        return tireDetailRepo.findBySeason(season)
                .stream()
                .map(TireDetails::getTireLists)
                .flatMap(Collection::stream)
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TireDto> getAllNotBoughtTires() {
        return tireDetailRepo.findAll()
                .stream()
                .map(TireDetails::getTireLists)
                .flatMap(Collection::stream)
                .filter(tire -> !tire.isBought())
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public TireDetails getTireDetailsById(Long tireDetailsId) {
        return tireDetailRepo.findById(tireDetailsId).orElseThrow(() -> new NotFoundException(String.format("TireDetail with following id %s not found", tireDetailsId)));
    }

}
