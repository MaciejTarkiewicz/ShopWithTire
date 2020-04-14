package pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.OperationExecutor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OperationManager {

	private final List<OperationExecutor> executorList;

	public OperationResult apply(OperationInput operationInput) {
		return executorList.stream()
			.filter(operationExecutor -> operationExecutor.support(operationInput.getType()))
			.map(action -> action.execute(operationInput))
			.findAny()
			.orElse(buildFailureResult(operationInput));
	}

	private OperationResult buildFailureResult(OperationInput operationInput) {
		return new OperationResult(
			operationInput.getType(),
			Status.FAILURE,
			List.of("Operation result for: " + operationInput.getType() + " not found."));
	}

	public static OperationManagerBuilder builder() {
		return OperationManagerBuilder.builder();
	}

	public static final class OperationManagerBuilder {

		List<OperationExecutor> executorList = new ArrayList<>();

		private OperationManagerBuilder() {
		}

		public static OperationManagerBuilder builder() {
			return new OperationManagerBuilder();
		}

		public OperationManagerBuilder withExecutor(OperationExecutor operationExecutor) {
			executorList.add(operationExecutor);
			return this;
		}

		public OperationManager build() {
			return new OperationManager(this.executorList);
		}
	}
}
