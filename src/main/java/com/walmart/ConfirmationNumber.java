package com.walmart;

import org.apache.commons.lang3.Validate;
import java.lang.StringBuilder;

public class ConfirmationNumber {
	private final String prefix;
	private final String code;
	private final String postfix;
	
	public ConfirmationNumber(final String prefix, final String code, final String postfix) {
		Validate.notBlank(prefix, "prefix is not provided.");
		Validate.notNull(code, "code is not provided.");
		Validate.notBlank(postfix, "postfix is not provided");
		
		this.prefix = prefix;
		this.code = code;
		this.postfix = postfix;
	}

	@Override
	public String toString() {
		return new StringBuilder(prefix).append(code).append(postfix).toString();
	}
}
