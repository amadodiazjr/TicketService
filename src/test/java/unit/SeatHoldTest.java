package unit;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.walmart.SeatHold;

public class SeatHoldTest {

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenSeatIdsAreEmptyCreateASeatHoldObject() {
		// ~given
		final List<Integer> seatIds = Collections.emptyList(); 
		
		// ~when
		new SeatHold(1, seatIds);
		
		// ~then
		// an exception is thrown;
	}
}