package pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

@Component
public class TireWithDetailsWebCommandConverter {

    public TireDetails toTireDetails(TireWithDetailsWebCommand tireWithDetailsWebCommand) {
        TireDetails tireDetails = new TireDetails();
        tireDetails.setDiameter(tireWithDetailsWebCommand.getDiameter());
        tireDetails.setMark(tireWithDetailsWebCommand.getMark());
        tireDetails.setProfile(tireWithDetailsWebCommand.getProfile());
        tireDetails.setSeason(tireWithDetailsWebCommand.getSeason());
        tireDetails.setWide(tireWithDetailsWebCommand.getWide());
        tireDetails.setTireLists(createTire(tireWithDetailsWebCommand));
        return tireDetails;
    }

    public TireDetails toTireDetails(TireWithDetailsWebCommand tireWithDetailsWebCommand, TireDetails tireDetails) {
        tireDetails.setDiameter(tireWithDetailsWebCommand.getDiameter());
        tireDetails.setMark(tireWithDetailsWebCommand.getMark());
        tireDetails.setProfile(tireWithDetailsWebCommand.getProfile());
        tireDetails.setSeason(tireWithDetailsWebCommand.getSeason());
        tireDetails.setWide(tireWithDetailsWebCommand.getWide());
        tireDetails.setTireLists(createTire(tireWithDetailsWebCommand));
        tireDetails.getTireLists().forEach(tire -> tire.setPrice(tireWithDetailsWebCommand.getPrice()));
        return tireDetails;
    }

    private List<Tire> createTire(TireWithDetailsWebCommand tireWithDetailsWebCommand) {
        List<Tire> tireLists = new ArrayList<>();
        for (int i = 0; i < tireWithDetailsWebCommand.getCount(); i++) {
            Tire tire = new Tire();
            tire.setPrice(tireWithDetailsWebCommand.getPrice());
            tireLists.add(tire);
        }
        return tireLists;
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
