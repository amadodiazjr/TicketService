package com.walmart;

import java.util.UUID;

public class ConfirmationNumberGenerator {
	private static ConfirmationNumberGenerator INSTANCE = new ConfirmationNumberGenerator();

	private ConfirmationNumberGenerator() { }
	
	public static ConfirmationNumberGenerator getInstance() {
		return INSTANCE;
	}

	public ConfirmationNumber generate() {
		return new ConfirmationNumber("foo", UUID.randomUUID().toString(), "bar");
	}
}