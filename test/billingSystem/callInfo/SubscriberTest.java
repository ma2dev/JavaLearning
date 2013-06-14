package billingSystem.callInfo;

import static org.junit.Assert.*;

import org.junit.Test;

import billingSystem.info.Subscriber;

public class SubscriberTest extends Subscriber {

	@Test
	public final void test電話番号の設定と取得() {
		Subscriber s = new Subscriber("09076228838");

		assertEquals("09076228838", s.getTelNum());
	}

}
