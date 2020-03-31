package pl.tarkiewicz.springsecuritysimplefactorauth.Exceptions;


public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

}

