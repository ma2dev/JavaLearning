package com.github.ma2dev.bcs.subscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.ma2dev.bcs.call.CallHistory;
import com.github.ma2dev.bcs.conf.ConfigureServiceFee;
import com.github.ma2dev.bcs.service.Service;

public class SubscriberTest {

	private Subscriber subscriber;

	@Before
	public void setUp() throws Exception {
		subscriber = null;
	}

	@Test
	public final void testオブジェクト生成() {
		subscriber = new Subscriber("09012345678");
		assertThat(subscriber.getTelnumber(), is("09012345678"));
	}

	@Test
	public final void test料金算出と明細出力の正常パターン() {
		String telnumber = "09012345678";
		String failure = "0";

		subscriber = new Subscriber(telnumber);

		CallHistory history = null;
		try {
			history = new CallHistory(telnumber, "0399998888", "20130619202200", "20130619203030", failure);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Service service = new Service("09012345678");
		List<String> family = new ArrayList<String>();
		family.add("01155556666");
		service.setCondition("1", "1", family);

		ConfigureServiceFee serviceFee = null;
		try {
			serviceFee = new ConfigureServiceFee("dat/test/bcs/conf/ServiceFee.conf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		service.setPriceList(serviceFee);

		subscriber.addCallHisotry(history);
		subscriber.setService(service);

		long telPrice = (long) Math.ceil((8.0 * 60.0 + 30.0) * (8.0 / 60.0F));
		long servicePrice = 105 + 315;
		subscriber.calculate();
		assertThat(subscriber.toString(), is(telnumber + "," + "," + telPrice + "," + servicePrice + "," + failure));
	}
}
