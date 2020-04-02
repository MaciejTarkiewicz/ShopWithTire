package pl.tarkiewicz.springsecuritysimplefactorauth.tire;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.CreateTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.user.User;
import pl.tarkiewicz.springsecuritysimplefactorauth.user.UserRepo;
//import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireBought.TireBoughtRepo;

@Component
public class Start {

    private UserRepo userRepo;
    private final CreateTireExecutor createTireExecutor;
    //private final TireBoughtService tireBoughtService;
    private final TireRepo tireRepo;

    public Start(UserRepo userRepo, PasswordEncoder passwordEncoder, CreateTireExecutor createTireExecutor, TireRepo tireRepo) {
        this.userRepo = userRepo;
        this.createTireExecutor = createTireExecutor;
        //this.tireBoughtService = tireBoughtService;
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

        OperationInput operationInput = OperationInput.builder().tireWithDetailsWebCommand(tireWithDetailsWebCommand)
                .type(Type.ADD).build();
        createTireExecutor.execute(operationInput);

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
