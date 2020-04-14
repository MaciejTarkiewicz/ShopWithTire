package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;

public interface OperationExecutor {

	OperationResult execute(OperationInput operationInput);

	boolean support(Type type);

}
