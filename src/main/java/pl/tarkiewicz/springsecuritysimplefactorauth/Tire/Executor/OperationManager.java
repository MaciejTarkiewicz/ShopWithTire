package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor;

import java.util.ArrayList;
import java.util.List;

import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions.Executor;

public class OperationManager {

    private List<Executor> list = new ArrayList<>();

    private OperationManager() {
    }

    public void apply(TireInput tireInput) {
        list.forEach(actions -> {
            try {
                actions.execute(tireInput);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public static OperationManagerBuilder builder() {
        return OperationManagerBuilder.builder();

    }

    public static final class OperationManagerBuilder {

        List<Executor> list = new ArrayList<>();

        private OperationManagerBuilder() {
        }

        public static OperationManagerBuilder builder() {
            return new OperationManagerBuilder();
        }

        public OperationManagerBuilder withExecutor(Executor executor) {
            list.add(executor);
            return this;
        }

        public OperationManager build() {
            OperationManager operationManager = new OperationManager();
            operationManager.list = this.list;
            return operationManager;
        }
    }
}
