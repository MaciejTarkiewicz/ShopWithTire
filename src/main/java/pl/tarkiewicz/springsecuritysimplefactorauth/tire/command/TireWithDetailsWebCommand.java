package pl.tarkiewicz.springsecuritysimplefactorauth.tire.command;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;

@Builder
@Data
public class TireWithDetailsWebCommand {

    private Integer wide;
    private Season season;
    private Integer profile;
    private Integer diameter;
    private String mark;
    private BigDecimal price;
    private Integer count;

}
