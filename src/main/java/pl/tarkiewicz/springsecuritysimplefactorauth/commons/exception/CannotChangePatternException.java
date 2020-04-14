package pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception;

public class CannotChangePatternException extends Exception {
	public CannotChangePatternException() {
		super("The template includes the tires you bought, you cannot change the pattern");
	}
}
