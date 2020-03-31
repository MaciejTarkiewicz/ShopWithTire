package pl.tarkiewicz.springsecuritysimplefactorauth.TireDto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TirePriceCommand {

    private BigDecimal price;
}
