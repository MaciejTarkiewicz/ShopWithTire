package pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter;

import org.springframework.stereotype.Component;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

import java.util.ArrayList;
import java.util.List;

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

	// TODO: tu sie odbywa update encji, czemu wiec to jest w converterze, do ktorego bym nie zajrzal po logike biznesowa?
	public TireDetails toTireDetails(TireWithDetailsWebCommand tireWithDetailsWebCommand, TireDetails tireDetails) {
		tireDetails.setDiameter(tireWithDetailsWebCommand.getDiameter());
		tireDetails.setMark(tireWithDetailsWebCommand.getMark());
		tireDetails.setProfile(tireWithDetailsWebCommand.getProfile());
		tireDetails.setSeason(tireWithDetailsWebCommand.getSeason());
		tireDetails.setWide(tireWithDetailsWebCommand.getWide());
		tireDetails.setTireLists(createTires(tireWithDetailsWebCommand));
		tireDetails.getTireLists().forEach(tire -> tire.setPrice(tireWithDetailsWebCommand.getPrice()));
		return tireDetails;
	}

	private List<Tire> createTires(TireWithDetailsWebCommand tireWithDetailsWebCommand) {
		List<Tire> tireLists = new ArrayList<>();
		for (int i = 0; i < tireWithDetailsWebCommand.getCount(); i++) {
			addTire(tireWithDetailsWebCommand, tireLists);
		}
		return tireLists;
	}

	private void addTire(TireWithDetailsWebCommand tireWithDetailsWebCommand, List<Tire> tireLists) {
		Tire tire = new Tire();
		// TODO: dwa razy cene ustawiasz (patrz: 34 linia)
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
