package unit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;

import com.walmart.SeatHold;

public class SeatHoldTest {

	@Test
	public void constructorShallCreateASeatHoldObject() {
		// ~given
		SeatHold seatHold = null;
		
		// ~when
		seatHold = new SeatHold(1, Collections.emptyList());
		
		// ~then
		assertThat(seatHold, is(notNullValue()));
	}
}