package pl.tarkiewicz.springsecuritysimplefactorauth.tire.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.NotFoundException;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter.TireWithDetailsWebCommandConverter;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Service
@AllArgsConstructor
public class TireGetService {

    private final TireRepo tireRepo;
    private final TireDetailRepo tireDetailRepo;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

    public List<TireDto> getAllTiresByDtoParameters(TireDto tireDto) {
        TireDetails filterBy = new TireDetails();
        filterBy.setDiameter(tireDto.getDiameter());
        filterBy.setWide((tireDto.getWide()));
        filterBy.setSeason(tireDto.getSeason());
        filterBy.setMark(tireDto.getMark());
        filterBy.setProfile(tireDto.getProfile());

        Example<TireDetails> example = Example.of(filterBy);
        return tireDetailRepo.findAll(example).stream()
                .map(TireDetails::getTire)
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TireDto> getAllTire() {
        return tireRepo.findAll()
                .stream()
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TireDto> getAllBySeason(Season season) {
        return tireDetailRepo.findBySeason(season)
                .stream()
                .map(TireDetails::getTire)
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TireDto> getAllNotBoughtTires() {
        return tireDetailRepo.findAll()
                .stream()
                .map(TireDetails::getTire)
                .filter(tire -> !tire.isBought())
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<Tire> getAllNotBoughtTiresByTireDetails(TireDetails tireDetails) {
        return tireDetailRepo.findById(tireDetails.getId())
                .stream()
                .map(TireDetails::getTire)
                .filter(tire -> !tire.isBought())
                .collect(Collectors.toList());
    }

    public TireDetails getTireDetailsById(Long tireDetailsId) throws NotFoundException {
        return tireDetailRepo.findById(tireDetailsId).orElseThrow(() -> new NotFoundException(String.format("TireDetail with following id %s not found", tireDetailsId)));
    }
}