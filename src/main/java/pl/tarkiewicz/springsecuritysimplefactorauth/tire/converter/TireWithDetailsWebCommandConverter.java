package pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Component
public class TireWithDetailsWebCommandConverter {

    public TireDetails toTireDetails(TireWithDetailsWebCommand tireWithDetailsWebCommand) {
        return TireDetails.builder()
                .diameter(tireWithDetailsWebCommand.getDiameter())
                .mark(tireWithDetailsWebCommand.getMark())
                .profile(tireWithDetailsWebCommand.getProfile())
                .season(tireWithDetailsWebCommand.getSeason())
                .wide(tireWithDetailsWebCommand.getWide())
                .tireLists(createTires(tireWithDetailsWebCommand))
                .build();
    }

    public List<Tire> createTires(TireWithDetailsWebCommand tireWithDetailsWebCommand) {
        List<Tire> tireLists = new ArrayList<>();
        for (int i = 0; i < tireWithDetailsWebCommand.getCount(); i++) {
            addTire(tireWithDetailsWebCommand, tireLists);
        }
        return tireLists;
    }

    private void addTire(TireWithDetailsWebCommand tireWithDetailsWebCommand, List<Tire> tireLists) {
        Tire tire = new Tire();
        tire.setPrice(tireWithDetailsWebCommand.getPrice());
        tireLists.add(tire);
    }

    public TireDto toDto(Tire tire) {
        TireDetails tireDetails = tire.getTireDetails();
        return TireDto.builder()
                .diameter(tireDetails.getDiameter())
                .mark(tireDetails.getMark())
                .season(tireDetails.getSeason())
                .profile(tireDetails.getProfile())
                .wide(tireDetails.getWide())
                .price(tire.getPrice())
                .build();
    }

}
