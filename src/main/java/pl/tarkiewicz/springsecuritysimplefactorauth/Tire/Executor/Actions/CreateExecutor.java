package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.OperationActions;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.TireInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service.TireSaveService;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetails;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireWithDetailsWebCommandConverter;

@Service
@AllArgsConstructor
public class CreateExecutor implements Executor {

    private final TireSaveService tireSaveService;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

    @Transactional
    @Override
    public void execute(TireInput tireInput) {
        if (isCreate(tireInput)) {
            TireDetails tireDetails = tireWithDetailsWebCommandConverter.toTireDetails(tireInput.getTireWithDetailsWebCommand());
            //tireDetails.getTireLists().forEach(tireSaveService::save);
            tireSaveService.save(tireDetails);

        }

    }

    private boolean isCreate(TireInput tireInput) {
        return tireInput.getOperationActions() == OperationActions.CREATE;
    }
}
