package pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.Actions;

import pl.tarkiewicz.springsecuritysimplefactorauth.Tire.Executor.TireInput;

public interface Executor {

    void execute(TireInput tireInput) throws Exception;

}
