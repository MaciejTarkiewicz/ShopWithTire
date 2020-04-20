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
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireUpdateService;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateTireExecutor implements OperationExecutor {

    private TireUpdateService tireUpdateService;

    @Transactional
    @Override
    public OperationResult execute(OperationInput operationInput) {
        try {
            if (operationInput.getTirePriceCommand() != null) {
                tireUpdateService.updateTirePrice(operationInput);
            } else {
                tireUpdateService.updateTireDetailsWithAllTire(operationInput);
            }
            return new OperationResult(Type.UPDATE, Status.SUCCESS, List.of());
        } catch (Exception e) {
            return new OperationResult(Type.UPDATE, Status.FAILURE, List.of(e.getMessage()));
        }
    }

    @Override
    public boolean support(Type type) {
        return type == Type.UPDATE;
    }

}
