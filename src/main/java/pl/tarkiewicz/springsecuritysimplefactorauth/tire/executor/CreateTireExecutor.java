package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter.TireWithDetailsWebCommandConverter;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Status;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireSaveService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateTireExecutor implements OperationExecutor {

    private final TireSaveService tireSaveService;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

    @Transactional
    @Override
    public OperationResult execute(OperationInput operationInput) {
        if (isCreate(operationInput)) {
            try {
                TireDetails tireDetails = tireWithDetailsWebCommandConverter.toTireDetails(operationInput.getTireWithDetailsWebCommand());
                tireSaveService.save(tireDetails);
                return new OperationResult(Type.ADD, Status.SUCCESS, List.of());

            } catch (Exception e) {
                return new OperationResult(Type.ADD, Status.FAILURE, List.of());
            }
        }
        return new OperationResult(Type.ADD, Status.SKIPPED, List.of());
    }

    private boolean isCreate(OperationInput operationInput) {
        return operationInput.getType() == Type.ADD;
    }
}
