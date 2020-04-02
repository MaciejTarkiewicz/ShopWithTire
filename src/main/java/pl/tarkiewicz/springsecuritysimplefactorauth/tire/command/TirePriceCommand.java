package pl.tarkiewicz.springsecuritysimplefactorauth.tire.command;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TirePriceCommand {

    private BigDecimal price;
}
