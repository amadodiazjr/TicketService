package com.walmart;

public class ConfirmationNumberGenerator {

	public static ConfirmationNumber generate() {
		return new ConfirmationNumber("foo", 1, "bar");
	}
}