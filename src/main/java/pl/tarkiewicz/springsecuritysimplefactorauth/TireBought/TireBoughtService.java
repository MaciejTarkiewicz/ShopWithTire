package pl.tarkiewicz.springsecuritysimplefactorauth.TireBought;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TireBoughtService {

    private final TireBoughtRepo tireBoughtRepo;

    public void buyTire(TireBought tireBought) {
        tireBoughtRepo.save(tireBought);
    }

}
