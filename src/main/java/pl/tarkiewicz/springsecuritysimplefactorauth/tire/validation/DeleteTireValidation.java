package pl.tarkiewicz.springsecuritysimplefactorauth.tire.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.AlreadyBoughtException;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.NotPositiveNumberException;
import pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception.PurchaseForTemplateAlreadyExistsException;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Component
@AllArgsConstructor
public class DeleteTireValidation {

    private final TireGetService tireGetService;

    public void deleteTiresValidation(OperationInput operationInput, TireDetails tireDetails) throws AlreadyBoughtException, NotPositiveNumberException {
        if (tireGetService.getAllNotBoughtTiresByTireDetails(tireDetails).size() < operationInput.getTireAmountCommand().getAmount()) {
            throw new AlreadyBoughtException();
        }

        if (operationInput.getTireAmountCommand().getAmount() <= 0) {
            throw new NotPositiveNumberException();
        }
    }

    public void deleteTireWithDetailsValidation(OperationInput operationInput, TireDetails tireDetails) throws PurchaseForTemplateAlreadyExistsException {
        if (tireDetails.getTire().isBought()) {
            throw new PurchaseForTemplateAlreadyExistsException();
        }
    }
}