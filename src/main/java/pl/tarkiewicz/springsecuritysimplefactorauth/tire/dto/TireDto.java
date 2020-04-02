package pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;

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
