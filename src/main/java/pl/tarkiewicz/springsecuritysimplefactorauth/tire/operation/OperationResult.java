package pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OperationResult {

    private final Type type;
    private final Status status;
    private final List<String> errors;

}
