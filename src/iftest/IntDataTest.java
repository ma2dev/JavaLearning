package iftest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IntDataTest {

	private IData data;

	@Before
	public void setUp() throws Exception {
		data = new IntDataCreator().create();
	}

	@Test
	public void test() {
		data.set(10);
		assertEquals(data.get(), 10);
	}

}
