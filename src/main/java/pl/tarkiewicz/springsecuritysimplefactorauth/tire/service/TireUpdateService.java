package pl.tarkiewicz.springsecuritysimplefactorauth.tire.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.CannotChangePatternException;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.NotFoundException;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter.TireWithDetailsWebCommandConverter;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Service
@AllArgsConstructor
@Slf4j
public class TireUpdateService {

    private final TireGetService tireGetService;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;
    private final TireSaveService tireSaveService;

    public void updateTirePrice(OperationInput operationInput) throws NotFoundException {
        TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
        tireGetService.getTireDetailsById(operationInput.getTireDetailsId()).getTireLists().stream()
                .filter(tire -> !tire.isBought())
                .forEach(tire -> tire.setPrice(operationInput.getTirePriceCommand().getPrice()));
        tireSaveService.save(tireDetails);
        log.info(String.format("Tire price changed to %s", operationInput.getTirePriceCommand().getPrice()));
    }

    public void updateTireDetailsWithAllTire(OperationInput operationInput) throws CannotChangePatternException, NotFoundException {
        if (!isAnyBoughtTire(operationInput)) {
            TireDetails tireDetails = toTireDetails(operationInput.getTireWithDetailsWebCommand(), tireGetService.getTireDetailsById(operationInput.getTireDetailsId()));
            tireSaveService.save(tireDetails);
            log.info(String.format("Tire price changed to %s", operationInput.getTirePriceCommand().getPrice()));
        } else {
            throw new CannotChangePatternException();
        }
    }

    private boolean isAnyBoughtTire(OperationInput operationInput) throws NotFoundException {
        return tireGetService.getTireDetailsById(operationInput.getTireDetailsId())
                .getTireLists()
                .stream()
                .anyMatch(Tire::isBought);
    }

    private TireDetails toTireDetails(TireWithDetailsWebCommand tireWithDetailsWebCommand, TireDetails tireDetails) {
        tireDetails.setDiameter(tireWithDetailsWebCommand.getDiameter());
        tireDetails.setMark(tireWithDetailsWebCommand.getMark());
        tireDetails.setProfile(tireWithDetailsWebCommand.getProfile());
        tireDetails.setSeason(tireWithDetailsWebCommand.getSeason());
        tireDetails.setWide(tireWithDetailsWebCommand.getWide());
        tireDetails.setTireLists(tireWithDetailsWebCommandConverter.createTires(tireWithDetailsWebCommand));
        tireDetails.getTireLists().forEach(tire -> tire.setPrice(tireWithDetailsWebCommand.getPrice()));
        return tireDetails;
    }

}
