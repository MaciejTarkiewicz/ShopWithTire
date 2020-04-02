package pl.tarkiewicz.springsecuritysimplefactorauth.tire.utils;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Status;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultCreator {

    public static ResponseEntity<List<OperationResult>> create(List<OperationResult> operationsResults) {
        return operationsResults.stream()
                .anyMatch(result -> result.getStatus() == Status.FAILURE)
                ? failure(operationsResults)
                : success(operationsResults);
    }

    private static ResponseEntity<List<OperationResult>> failure(List<OperationResult> operationsResults) {
        log.info("One or more operations ended with status FAILURE, response with status INTERNAL SERVER ERROR will be returned.");
        return new ResponseEntity<>(operationsResults, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<List<OperationResult>> success(List<OperationResult> operationsResults) {
        log.info("All operations ended with status SUCCESS, response with status OK will be returned.");
        return new ResponseEntity<>(operationsResults, HttpStatus.OK);
    }

}
