package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Status;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireDeleteService;

@Service
@AllArgsConstructor
@Slf4j
public class DeleteTireExecutor implements OperationExecutor {

    private final TireDeleteService tireDeleteService;

    @Transactional
    @Override
    public OperationResult execute(OperationInput operationInput) {
        try {
            if (operationInput.getTireAmountCommand() != null) {
                tireDeleteService.deleteTires(operationInput);
            } else {
                tireDeleteService.deleteTireDetailWithAllTire(operationInput);
            }
            return new OperationResult(Type.DELETE, Status.SUCCESS, List.of());
        } catch (Exception e) {
            return new OperationResult(Type.DELETE, Status.FAILURE, List.of(e.getMessage()));
        }
    }

    @Override
    public boolean support(Type type) {
        return type == Type.DELETE;
    }

}
