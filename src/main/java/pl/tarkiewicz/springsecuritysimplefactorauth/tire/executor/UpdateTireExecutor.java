package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter.TireWithDetailsWebCommandConverter;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Status;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireSaveService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Service
@AllArgsConstructor
public class UpdateTireExecutor implements OperationExecutor {

    private final TireSaveService tireSaveService;
    private final TireGetService tireGetService;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

    @Transactional
    @Override
    public OperationResult execute(OperationInput operationInput) {
        if (isUpdate(operationInput)) {
            try {
                if (operationInput.getTirePriceCommand() != null) {
                    updateTirePrice(operationInput);
                } else {
                    updateTireDetailsWithAllTire(operationInput);
                }
                return new OperationResult(Type.DELETE, Status.SUCCESS, List.of());
            } catch (Exception e) {
                return new OperationResult(Type.ADD, Status.FAILURE, List.of());
            }
        }
        return new OperationResult(Type.ADD, Status.SKIPPED, List.of());
    }

    private boolean isUpdate(OperationInput operationInput) {
        return operationInput.getType() == Type.UPDATE;
    }

    private void updateTirePrice(OperationInput operationInput) {
        TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
        tireGetService.getTireDetailsById(operationInput.getTireDetailsId()).getTireLists().forEach(tire -> tire.setPrice(operationInput.getTirePriceCommand().getPrice()));
        tireSaveService.save(tireDetails);
    }

    private void updateTireDetailsWithAllTire(OperationInput operationInput) {
        TireDetails tireDetails = tireWithDetailsWebCommandConverter.toTireDetails(operationInput.getTireWithDetailsWebCommand(), tireGetService.getTireDetailsById(operationInput.getTireDetailsId()));
        tireSaveService.save(tireDetails);
    }
}
