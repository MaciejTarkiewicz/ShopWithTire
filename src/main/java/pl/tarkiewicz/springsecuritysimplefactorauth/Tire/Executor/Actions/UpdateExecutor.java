package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.OperationActions;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.TireInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service.TireSaveService;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetails;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireWithDetailsWebCommandConverter;

@Service
@AllArgsConstructor
public class UpdateExecutor implements Executor {

    private final TireSaveService tireSaveService;
    private final TireGetService tireGetService;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

    @Transactional
    @Override
    public void execute(TireInput tireInput) {
        if (isUpdate(tireInput)) {
            if (tireInput.getTirePriceCommand() != null) {
                updateTirePrice(tireInput);
            } else {
                updateTireDetailsWithAllTire(tireInput);
            }
        }
    }

    private boolean isUpdate(TireInput tireInput) {
        return tireInput.getOperationActions() == OperationActions.UPDATE;
    }

    private void updateTirePrice(TireInput tireInput) {
        TireDetails tireDetails = tireGetService.getTireDetailsById(tireInput.getTireDetailsId());
        tireGetService.getTireDetailsById(tireInput.getTireDetailsId()).getTireLists().forEach(tire -> tire.setPrice(tireInput.getTirePriceCommand().getPrice()));
        tireSaveService.save(tireDetails);
    }

    private void updateTireDetailsWithAllTire(TireInput tireInput) {
        TireDetails tireDetails = tireWithDetailsWebCommandConverter.toTireDetails(tireInput.getTireWithDetailsWebCommand(), tireGetService.getTireDetailsById(tireInput.getTireDetailsId()));
        //tireDetails.getTireLists().forEach(tireSaveService::saveTire);
        tireSaveService.save(tireDetails);
    }
}
