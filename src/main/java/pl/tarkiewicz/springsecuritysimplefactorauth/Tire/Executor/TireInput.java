package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor;

import lombok.Builder;
import lombok.Data;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireAmountCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TirePriceCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireWithDetailsWebCommand;

@Builder
@Data
public class TireInput {

    private Long tireDetailsId;
    private OperationActions operationActions;
    private TireWithDetailsWebCommand tireWithDetailsWebCommand;
    private TirePriceCommand tirePriceCommand;
    private TireAmountCommand tireAmountCommand;


}
