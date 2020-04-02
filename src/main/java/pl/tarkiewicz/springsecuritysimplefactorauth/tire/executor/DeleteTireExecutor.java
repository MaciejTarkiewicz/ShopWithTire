package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import java.util.ArrayList;
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
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Service
@AllArgsConstructor
@Slf4j
public class DeleteTireExecutor implements OperationExecutor {

    private final TireDeleteService tireDeleteService;
    private final TireGetService tireGetService;

    @Transactional
    @Override
    public OperationResult execute(OperationInput operationInput) {
        if (isDelete(operationInput)) {
            try {
                if (operationInput.getTireAmountCommand() != null) {
                    deleteTires(operationInput);
                } else {
                    deleteTireDetailWithAllTire(operationInput);
                }
                return new OperationResult(Type.DELETE, Status.SUCCESS, List.of());
            } catch (Exception e) {
                return new OperationResult(Type.DELETE, Status.FAILURE, List.of());
            }
        }
        return new OperationResult(Type.DELETE, Status.SKIPPED, List.of());
    }

    private void deleteTires(OperationInput operationInput) throws Exception {
        TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
        if (tireDeleteService.TiresToDelete(tireDetails).size() < operationInput.getTireAmountCommand().getAmount()) {
            throw new Exception("Cannot be removed because this tires has been bought");
        } else {
            if (operationInput.getTireAmountCommand().getAmount() > 0) {
                List<Tire> tires = tireDeleteService.TiresToDelete(tireDetails);
                tires.subList(0, tires.size() - operationInput.getTireAmountCommand().getAmount()).clear();
                tires.forEach(tire -> {
                    tireDetails.getTireLists().remove(tire);
                    tire.setTireDetails(null);
                });

            } else {
                throw new Exception("The number of tires to be removed is not a positive number");
            }

        }

    }

    private void deleteTireDetailWithAllTire(OperationInput operationInput) throws Exception {
        TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
        if (tireDetails.getTireLists().stream().map(Tire::isBought).anyMatch(e -> e.equals(true))) {
            throw new Exception("Cannot delete because there is a purchase for this tire template");
        }
        tireDeleteService.deleteTireWithDetails(tireGetService.getTireDetailsById(operationInput.getTireDetailsId()));
    }

    private boolean isDelete(OperationInput operationInput) {
        return operationInput.getType() == Type.DELETE;
    }
}
