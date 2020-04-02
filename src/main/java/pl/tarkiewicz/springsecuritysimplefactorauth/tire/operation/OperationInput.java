package pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation;

import lombok.Builder;
import lombok.Data;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireAmountCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TirePriceCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;

@Builder
@Data
public class OperationInput {

    private Long tireDetailsId;
    private Type type;
    private TireWithDetailsWebCommand tireWithDetailsWebCommand;
    private TirePriceCommand tirePriceCommand;
    private TireAmountCommand tireAmountCommand;


}
