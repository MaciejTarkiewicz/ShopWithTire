package pl.tarkiewicz.springsecuritysimplefactorauth;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions.CreateExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.OperationActions;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.TireInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Season;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Tire;
import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireBought.TireBought;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireBought.TireBoughtService;
import pl.tarkiewicz.springsecuritysimplefactorauth.TireDto.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.User.User;
import pl.tarkiewicz.springsecuritysimplefactorauth.User.UserRepo;

@Component
public class Start {

    private UserRepo userRepo;
    private final CreateExecutor createExecutor;
    private final TireBoughtService tireBoughtService;
    private final TireRepo tireRepo;

    public Start(UserRepo userRepo, PasswordEncoder passwordEncoder, CreateExecutor createExecutor, TireBoughtService tireBoughtService, TireRepo tireRepo) {
        this.userRepo = userRepo;
        this.createExecutor = createExecutor;
        this.tireBoughtService = tireBoughtService;
        this.tireRepo = tireRepo;

        User user = new User();
        user.setUsername("Janusz");
        user.setPassword(passwordEncoder.encode("Janusz123"));
        user.setRole("ROLE_ADMIN");

        User user1 = new User();
        user1.setUsername("Bartek");
        user1.setPassword(passwordEncoder.encode("Bartek123"));
        user1.setRole("ROLE_USER");
        userRepo.save(user);
        userRepo.save(user1);

        TireWithDetailsWebCommand tireWithDetailsWebCommand = TireWithDetailsWebCommand.builder()
                .count(6)
                .diameter(55)
                .mark("cos")
                .profile(3)
                .price(new BigDecimal(23))
                .season(Season.SUMMER)
                .wide(3)
                .build();

        TireInput tireInput = TireInput.builder().tireWithDetailsWebCommand(tireWithDetailsWebCommand)
                .operationActions(OperationActions.CREATE).build();
        createExecutor.execute(tireInput);

//        TireBought tireBought = new TireBought();
//        tireBought.setUser(user);
//        Tire tire = tireRepo.findById(3L).get();
//        tireBought.setTire(tire);
//        tireBought.setBoughtDate(LocalDate.now());
//        tireBought.getTire().setBought(true);
//        tireRepo.save(tireBought.getTire());
//        tireBoughtService.buyTire(tireBought);

    }

}
