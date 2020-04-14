package pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception;

public class NotPositiveNumberException extends Exception {
	public NotPositiveNumberException() {
		super("The number is not a positive number");
	}
}
