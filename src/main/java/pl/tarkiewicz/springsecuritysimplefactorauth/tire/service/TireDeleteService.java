package pl.tarkiewicz.springsecuritysimplefactorauth.tire.service;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.validation.DeleteTireValidation;

@Service
@AllArgsConstructor
public class TireDeleteService {

    private final TireDetailRepo tireDetailRepo;
    private final TireGetService tireGetService;
    private final DeleteTireValidation deleteTireValidation;

    public void deleteTireWithDetails(TireDetails tireDetails) {
        tireDetailRepo.delete(tireDetails);
    }

    public void deleteTires(OperationInput operationInput) throws Exception {
        TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
        deleteTireValidation.deleteTiresValidation(operationInput, tireDetails);
        List<Tire> tires = tireGetService.getAllNotBoughtTiresByTireDetails(tireDetails);
        tires.subList(0, tires.size() - operationInput.getTireAmountCommand().getAmount()).clear();
        tires.forEach(tire -> {
            tireDetails.getTireLists().remove(tire);
            tire.setTireDetails(null);
        });

    }

    public void deleteTireDetailWithAllTire(OperationInput operationInput) throws Exception {
        TireDetails tireDetails = tireGetService.getTireDetailsById(operationInput.getTireDetailsId());
        deleteTireValidation.deleteTireWithDetailsValidation(tireDetails);
        deleteTireWithDetails(tireGetService.getTireDetailsById(operationInput.getTireDetailsId()));
    }

}
