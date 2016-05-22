package com.walmart;

import org.apache.commons.lang3.Validate;
import java.lang.StringBuilder;

public class ConfirmationNumber {
	private final String prefix;
	private final Integer number;
	private final String postfix;
	
	public ConfirmationNumber(final String prefix, final Integer number, final String postfix) {
		Validate.notBlank(prefix, "prefix is not provided.");
		Validate.notNull(number, "number is not provided.");
		Validate.notBlank(postfix, "postfix is not provided");
		
		this.prefix = prefix;
		this.number = number;
		this.postfix = postfix;
	}

	@Override
	public String toString() {
		return new StringBuilder(prefix).append(number).append(postfix).toString();
	}
}
