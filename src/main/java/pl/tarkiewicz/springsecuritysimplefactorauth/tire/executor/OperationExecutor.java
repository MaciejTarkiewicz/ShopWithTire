package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;

public interface OperationExecutor {

    OperationResult execute(OperationInput operationInput);

}
