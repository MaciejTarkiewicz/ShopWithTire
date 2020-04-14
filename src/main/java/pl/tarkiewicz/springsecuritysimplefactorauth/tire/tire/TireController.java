package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireAmountCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TirePriceCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.CreateTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.DeleteTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.UpdateTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationManager;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.utils.ResultCreator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	@GetMapping("/example")
	public List<TireDto> example(TireDto tireDto) {
		return tireGetService.getAllTiresByDtoParameters(tireDto);
	}

	@GetMapping(value = "/tire/details/all")
	public List<TireDto> getAllTiresWithDetails() {

		return tireGetService.getAllTire();
	}

	@GetMapping(value = "/tire/details/all/{season}")
	public List<TireDto> getAllTiresWithDetailsBySeason(@PathVariable Season season) {
		return tireGetService.getAllBySeason(season);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/tire/details/add")
	public ResponseEntity<List<OperationResult>> addTiresWithDetails(@RequestBody TireWithDetailsWebCommand tireWithDetailsWebCommand) {
		OperationInput operationInput = OperationInput.builder()
			.tireWithDetailsWebCommand(tireWithDetailsWebCommand)
			.type(Type.ADD)
			.build();
		return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
		return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

	}

	@PatchMapping(value = "/tire/update/price/{tireDetailsId}")
	public ResponseEntity<List<OperationResult>> updateTirePrice(@RequestBody TirePriceCommand tirePriceCommand, @PathVariable Long tireDetailsId) {
		OperationInput operationInput = OperationInput.builder()
			.tireDetailsId(tireDetailsId)
			.tirePriceCommand(tirePriceCommand)
			.type(Type.UPDATE)
			.build();
		return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

	}

	@PatchMapping(value = "/tire/details/update/{tireDetailsId}")
	public ResponseEntity<List<OperationResult>> updateAllTiresWithDetails(@RequestBody TireWithDetailsWebCommand tireWithDetailsWebCommand, @PathVariable Long tireDetailsId) {
		OperationInput operationInput = OperationInput.builder()
			.tireDetailsId(tireDetailsId)
			.tireWithDetailsWebCommand(tireWithDetailsWebCommand)
			.type(Type.UPDATE)
			.build();
		return ResultCreator.create(Stream.of(operationInput).map(operationManager::apply).collect(Collectors.toList()));

	}

}
