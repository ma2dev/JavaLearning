package com.github.ma2dev.bcs.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ma2dev.bcs.conf.ConfigureServiceFee;
import ma2dev.bcs.service.Service;

import org.junit.Before;
import org.junit.Test;

public class ServiceTest {

	private Service service;

	@Before
	public void setUp() throws Exception {
		service = null;
	}

	@Test
	public void testオブジェクト生成() {
		String number = "09012345678";
		service = new Service(number);
		assertEquals("09012345678", service.getTelnumber());
	}

	@Test
	public void test家族無料通話対象番号の確認() {
		String number = "09012345678";
		String display = "1";
		String interrupt = "0";
		List<String> familyCall = new ArrayList<String>();
		familyCall.add("0123456789");
		familyCall.add("08011112222");
		familyCall.add("09055556666");

		service = new Service(number);
		service.setCondition(display, interrupt, familyCall);

		// 対象電話番号
		assertTrue(service.isFamilyCallTelumber("0123456789"));
		// 対象電話番号
		assertTrue(service.isFamilyCallTelumber("09055556666"));
		// 対象電話番号外
		assertFalse(service.isFamilyCallTelumber("09099999999"));
	}

	@Test
	public void testサービス契約料金の計算() {
		String number = "09012345678";
		String display = "1";
		String interrupt = "0";
		List<String> familyCall = new ArrayList<String>();
		familyCall.add("0123456789");
		familyCall.add("08011112222");
		familyCall.add("09055556666");
		String serviceFeePath = "dat/test/bcs/conf/ServiceFee.conf";
		ConfigureServiceFee serviceFee = null;
		try {
			serviceFee = new ConfigureServiceFee(serviceFeePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		service = new Service(number);
		service.setCondition(display, interrupt, familyCall);
		service.setPriceList(serviceFee);
		assertThat(service.calculateServicePrice(), is(105L));
	}
}
