package pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.OperationExecutor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OperationManager {

    private List<OperationExecutor> executorList = new ArrayList<>();

    public OperationResult apply(OperationInput operationInput) {
        return executorList.stream()
                .map(action -> action.execute(operationInput))
                .filter(byType(operationInput.getType()))
                .findAny()
                .orElse(buildFailureResult(operationInput));

    }

    private Predicate<OperationResult> byType(Type type) {
        return element -> element.getType().equals(type);
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
