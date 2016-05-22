package unit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.walmart.ConfirmationNumber;

public class ConfirmationNumberTest {

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenPrefixIsNotProvided() {
		final Integer number = 1;
		final String postfix = "bar";
		
		// ~given
		final String prefix = null;
		
		// ~when
		new ConfirmationNumber(prefix, number, postfix);
		
		// ~then
		// exception is thrown
	}
	
	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenNumberIsNotProvided() {
		final String prefix = "foo";
		final String postfix = "bar";

		// ~given
		final Integer number = null;
		
		// ~when
		new ConfirmationNumber(prefix, number, postfix);
		
		// ~then
		// exception is thrown
	}

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenPostfixIsNotProvided() {
		final String prefix = "foo";
		final Integer number = 1;

		// ~given
		final String postfix = null;
		
		// ~when
		new ConfirmationNumber(prefix, number, postfix);
		
		// ~then
		// exception is thrown
	}

	@Test
	public void constructorShallCreateAConfirmationNumberObjectWhenAllRequirementsAreMet() {		
		// ~given
		final String prefix = "foo";
		final Integer number = 1;
		final String postfix = "bar";
		
		// ~when
		final ConfirmationNumber confirmationNumber = new ConfirmationNumber(prefix, number, postfix);
		
		// ~then
		assertThat(confirmationNumber, is(notNullValue()));
	}

}
