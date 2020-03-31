package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.OperationActions;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.TireInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service.TireDeleteService;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service.TireSaveService;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDetails.TireDetails;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeleteExecutor implements Executor {

    private final TireDeleteService tireDeleteService;
    private final TireGetService tireGetService;

    @Transactional
    @Override
    public void execute(TireInput tireInput) throws Exception {
        if (isDelete(tireInput)) {
            if (tireInput.getTireAmountCommand() != null) {
                deleteTires(tireInput);
            } else {
                deleteTireDetailWithAllTire(tireInput);
            }

        }
    }

    private void deleteTires(TireInput tireInput) throws Exception {
        TireDetails tireDetails = tireGetService.getTireDetailsById(tireInput.getTireDetailsId());
        if (tireDeleteService.TiresToDelete(tireDetails).size() < tireInput.getTireAmountCommand().getAmount()) {
            throw new Exception("Cannot be removed because this tires has been bought");
        } else {
            if (tireInput.getTireAmountCommand().getAmount() > 0) {
                List<Tire> tires = tireDeleteService.TiresToDelete(tireDetails);
                tires.subList(0, tires.size() - tireInput.getTireAmountCommand().getAmount()).clear();
                tires.forEach(tire -> {
                    tireDetails.getTireLists().remove(tire);
                    tire.setTireDetails(null);
                });
                // tires.forEach(tire -> tire.setTireDetails(null));

            } else {
                log.warn("Liczba opon do usuniecia nie jest liczba dodtatnia");
            }

        }

    }

    private void deleteTireDetailWithAllTire(TireInput tireInput) throws Exception {
        TireDetails tireDetails = tireGetService.getTireDetailsById(tireInput.getTireDetailsId());
        if (tireDetails.getTireLists().stream().map(Tire::isBought).anyMatch(e -> e.equals(true))) {
            throw new Exception("Cannot delete because there is a purchase for this tire template");
        }
        tireDeleteService.deleteTireWithDetails(tireGetService.getTireDetailsById(tireInput.getTireDetailsId()));
    }

    private boolean isDelete(TireInput tireInput) {
        return tireInput.getOperationActions() == OperationActions.DELETE;
    }
}
