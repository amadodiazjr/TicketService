package unit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.walmart.ConfirmationNumber;

public class ConfirmationNumberTest {

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenPrefixIsNotProvided() {
		final String code = "1";
		final String postfix = "bar";
		
		// ~given
		final String prefix = null;
		
		// ~when
		new ConfirmationNumber(prefix, code, postfix);
		
		// ~then
		// exception is thrown
	}
	
	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenNumberIsNotProvided() {
		final String prefix = "foo";
		final String postfix = "bar";

		// ~given
		final String code = null;
		
		// ~when
		new ConfirmationNumber(prefix, code, postfix);
		
		// ~then
		// exception is thrown
	}

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenPostfixIsNotProvided() {
		final String prefix = "foo";
		final String code = "1";

		// ~given
		final String postfix = null;
		
		// ~when
		new ConfirmationNumber(prefix, code, postfix);
		
		// ~then
		// exception is thrown
	}

	@Test
	public void constructorShallCreateAConfirmationNumberObjectWhenAllRequirementsAreMet() {		
		// ~given
		final String prefix = "foo";
		final String code = "1";
		final String postfix = "bar";
		
		// ~when
		final ConfirmationNumber confirmationNumber = new ConfirmationNumber(prefix, code, postfix);
		
		// ~then
		assertThat(confirmationNumber, is(notNullValue()));
	}

	@Test
	public void toStringShallReturnAString() {		
		final String prefix = "foo";
		final String code = "1";
		final String postfix = "bar";

		// ~given
		final ConfirmationNumber confirmationNumber = new ConfirmationNumber(prefix, code, postfix);
		
		// ~when
		final String string = confirmationNumber.toString();
		
		// ~then
		assertThat(string, is(notNullValue()));
	}

}
