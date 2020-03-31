package pl.tarkiewicz.springsecuritysimplefactorauth.TireDto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Season;

@Builder
@Data
public class TireDto {

    private Integer wide;
    private Season season;
    private Integer profile;
    private Integer diameter;
    private String mark;
    private BigDecimal price;

}
