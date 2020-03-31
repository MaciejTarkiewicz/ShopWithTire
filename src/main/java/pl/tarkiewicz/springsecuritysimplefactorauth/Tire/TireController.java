package pl.tarkiewicz.springsecuritysimplefactorauth.Tire;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions.CreateExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions.DeleteExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions.UpdateExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.OperationActions;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.OperationManager;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.TireInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireAmountCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TirePriceCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireWithDetailsWebCommand;

@RestController
public class TireController {

    private OperationManager operationManager;
    private TireGetService tireGetService;

    public TireController(CreateExecutor createExecutor, DeleteExecutor deleteExecutor, UpdateExecutor updateExecutor, TireGetService tireGetService) {
        this.operationManager = OperationManager.builder()
                .withExecutor(createExecutor)
                .withExecutor(deleteExecutor)
                .withExecutor(updateExecutor)
                .build();
        this.tireGetService = tireGetService;
    }

    @GetMapping(value = "/tire/details/all")
    public List<TireDto> getAllTireWithDetails() {
        return tireGetService.getAllTire();
    }

    @GetMapping(value = "/tire/details/all/open")
    public List<TireDto> getAllTireWithDetailsWhichNotBought() {
        return tireGetService.getAllNotBoughtTires();
    }

    @GetMapping(value = "/tire/details/all/{season}")
    public List<TireDto> getAllTireWithDetailsBySeason(@PathVariable Season season) {
        return tireGetService.getAllBySeason(season);
    }

    @PostMapping(value = "/tire/details/add")
    public ResponseEntity<?> addTiresWithDetails(@RequestBody TireWithDetailsWebCommand tireWithDetailsWebCommand) {
        TireInput tireInput = TireInput.builder()
                .tireWithDetailsWebCommand(tireWithDetailsWebCommand)
                .operationActions(OperationActions.CREATE)
                .build();
        operationManager.apply(tireInput);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/tire/delete/{tireDetailsId}")
    public ResponseEntity<?> deletedTires(@PathVariable Long tireDetailsId, @RequestBody TireAmountCommand tireAmountCommand) {
        TireInput tireInput = TireInput.builder()
                .tireDetailsId(tireDetailsId)
                .tireAmountCommand(tireAmountCommand)
                .operationActions(OperationActions.DELETE)
                .build();
        operationManager.apply(tireInput);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/tire/details/delete/{tireDetailsId}")
    public ResponseEntity<?> deletedAllTiresWithDetails(@PathVariable Long tireDetailsId) {
        TireInput tireInput = TireInput.builder()
                .tireDetailsId(tireDetailsId)
                .operationActions(OperationActions.DELETE)
                .build();
        operationManager.apply(tireInput);
        return ResponseEntity.ok().build();

    }

    @PatchMapping(value = "/tire/update/price/{tireDetailsId}")
    public ResponseEntity<?> updateTirePrice(@RequestBody TirePriceCommand tirePriceCommand, @PathVariable Long tireDetailsId) {
        TireInput tireInput = TireInput.builder()
                .tireDetailsId(tireDetailsId)
                .tirePriceCommand(tirePriceCommand)
                .operationActions(OperationActions.UPDATE)
                .build();
        operationManager.apply(tireInput);
        return ResponseEntity.ok().build();

    }

    @PatchMapping(value = "/tire/details/update/{tireDetailsId}")
    public ResponseEntity<?> updateAllTiresWithDetails(@RequestBody TireWithDetailsWebCommand tireWithDetailsWebCommand, @PathVariable Long tireDetailsId) {
        TireInput tireInput = TireInput.builder()
                .tireDetailsId(tireDetailsId)
                .tireWithDetailsWebCommand(tireWithDetailsWebCommand)
                .operationActions(OperationActions.UPDATE)
                .build();
        operationManager.apply(tireInput);
        return ResponseEntity.ok().build();

    }

}
