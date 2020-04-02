package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireAmountCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TirePriceCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationManager;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.CreateTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.DeleteTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.UpdateTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.utils.ResultCreator;

@RestController
public class TireController {

    private OperationManager operationManager;
    private TireGetService tireGetService;

    public TireController(CreateTireExecutor createTireExecutor, DeleteTireExecutor deleteTireExecutor, UpdateTireExecutor updateTireExecutor, TireGetService tireGetService) {
        this.operationManager = OperationManager.builder()
                .withExecutor(createTireExecutor)
                .withExecutor(deleteTireExecutor)
                .withExecutor(updateTireExecutor)
                .build();
        this.tireGetService = tireGetService;
    }

    @GetMapping(value = "/tire/details/all")
    public List<TireDto> getAllTireWithDetails() {
        return tireGetService.getAllTire();
    }

    @GetMapping(value = "/tire/details/all/{season}")
    public List<TireDto> getAllTireWithDetailsBySeason(@PathVariable Season season) {
        return tireGetService.getAllBySeason(season);
    }

    @PostMapping(value = "/tire/details/add")
    public ResponseEntity<List<OperationResult>> addTiresWithDetails(@RequestBody TireWithDetailsWebCommand tireWithDetailsWebCommand) {
        OperationInput operationInput = OperationInput.builder()
                .tireWithDetailsWebCommand(tireWithDetailsWebCommand)
                .type(Type.ADD)
                .build();
        operationManager.apply(operationInput);
        return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

    }

    @DeleteMapping(value = "/tire/delete/{tireDetailsId}")
    public ResponseEntity<List<OperationResult>> deletedTires(@PathVariable Long tireDetailsId, @RequestBody TireAmountCommand tireAmountCommand) {
        OperationInput operationInput = OperationInput.builder()
                .tireDetailsId(tireDetailsId)
                .tireAmountCommand(tireAmountCommand)
                .type(Type.DELETE)
                .build();
        return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

    }

    @DeleteMapping(value = "/tire/details/delete/{tireDetailsId}")
    public ResponseEntity<List<OperationResult>> deletedAllTiresWithDetails(@PathVariable Long tireDetailsId) {
        OperationInput operationInput = OperationInput.builder()
                .tireDetailsId(tireDetailsId)
                .type(Type.DELETE)
                .build();
        operationManager.apply(operationInput);
        return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

    }

    @PatchMapping(value = "/tire/update/price/{tireDetailsId}")
    public ResponseEntity<List<OperationResult>> updateTirePrice(@RequestBody TirePriceCommand tirePriceCommand, @PathVariable Long tireDetailsId) {
        OperationInput operationInput = OperationInput.builder()
                .tireDetailsId(tireDetailsId)
                .tirePriceCommand(tirePriceCommand)
                .type(Type.UPDATE)
                .build();
        operationManager.apply(operationInput);
        return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

    }

    @PatchMapping(value = "/tire/details/update/{tireDetailsId}")
    public ResponseEntity<List<OperationResult>> updateAllTiresWithDetails(@RequestBody TireWithDetailsWebCommand tireWithDetailsWebCommand, @PathVariable Long tireDetailsId) {
        OperationInput operationInput = OperationInput.builder()
                .tireDetailsId(tireDetailsId)
                .tireWithDetailsWebCommand(tireWithDetailsWebCommand)
                .type(Type.UPDATE)
                .build();
        operationManager.apply(operationInput);
        return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

    }

}
