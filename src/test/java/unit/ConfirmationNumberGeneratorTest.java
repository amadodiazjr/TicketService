package unit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.walmart.ConfirmationNumber;
import com.walmart.ConfirmationNumberGenerator;

public class ConfirmationNumberGeneratorTest {

	@Test
	public void generateShallGenerateAConfirmationNumber() {
		// ~given
		ConfirmationNumber number = null;
		
		// ~when
		number = ConfirmationNumberGenerator.getInstance().generate();
		
		// ~then
		assertThat(number, is(notNullValue()));	
	}
}