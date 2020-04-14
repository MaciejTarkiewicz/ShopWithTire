package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.AlreadyBoughtException;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.NotPositiveNumberException;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.PurchaseForTemplateAlreadyExistsException;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Status;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireDeleteService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeleteTireExecutor implements OperationExecutor {

	private final TireDeleteService tireDeleteService;
	private final TireGetService tireGetService;

//	TODO: nie podoba mi się, że masz walidacje pomieszaną z logiką, powoduje to dużą ifologię.

	@Transactional
	@Override
	public OperationResult execute(OperationInput operationInput) {
		try {
			if (operationInput.getTireAmountCommand() != null) {
				deleteTires(operationInput);
			} else {
				deleteTireDetailWithAllTire(operationInput);
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

	private void deleteTires(OperationInput operationInput) throws Exception {
		TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
		if (tireGetService.getAllNotBoughtTiresByTireDetails(tireDetails).size() < operationInput.getTireAmountCommand().getAmount()) {
			throw new AlreadyBoughtException();
		} else {
			if (operationInput.getTireAmountCommand().getAmount() > 0) {
				List<Tire> tires = tireGetService.getAllNotBoughtTiresByTireDetails(tireDetails);
				tires.subList(0, tires.size() - operationInput.getTireAmountCommand().getAmount()).clear();
				tires.forEach(tire -> {
					tireDetails.getTireLists().remove(tire);
					tire.setTireDetails(null);
				});
			} else {
				throw new NotPositiveNumberException();
			}

		}

	}

	private void deleteTireDetailWithAllTire(OperationInput operationInput) throws Exception {
		TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
		if (tireDetails.getTireLists().stream().anyMatch(Tire::isBought)) {
			throw new PurchaseForTemplateAlreadyExistsException();
		}
		tireDeleteService.deleteTireWithDetails(tireGetService.getTireDetailsById(operationInput.getTireDetailsId()));
	}

}
