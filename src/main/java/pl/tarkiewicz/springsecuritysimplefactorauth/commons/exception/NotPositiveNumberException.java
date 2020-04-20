package pl.tarkiewicz.springsecuritysimplefactorauth.commons.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotPositiveNumberException extends Exception {
	public NotPositiveNumberException() {
		super("The number is not a positive number");
	}
}
