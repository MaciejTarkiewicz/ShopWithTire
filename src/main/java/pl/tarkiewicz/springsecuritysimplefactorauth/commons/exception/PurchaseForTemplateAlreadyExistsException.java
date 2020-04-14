package pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception;

public class PurchaseForTemplateAlreadyExistsException extends Exception {
	public PurchaseForTemplateAlreadyExistsException() {
		super("there is a purchase for this tire template");
	}
}
