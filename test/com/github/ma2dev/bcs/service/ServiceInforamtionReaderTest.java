package com.github.ma2dev.bcs.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.junit.Test;

import com.github.ma2dev.bcs.conf.ConfigureServiceFee;

public class ServiceInforamtionReaderTest {

	@Test
	public final void testファイル読み込み() {
		Reader serviceInfo = null;
		ConfigureServiceFee servicePrice = null;
		try {
			serviceInfo = new FileReader("dat/test/bcs/serviceInfo/template_serviceInfo.csv");
			servicePrice = new ConfigureServiceFee("dat/test/bcs/conf/ServiceFee.conf");
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Service> list = null;
		try {
			list = ServiceInforamtionReader.readFromCsv(serviceInfo, servicePrice);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(list.size() > 0);
		assertThat(list.get(0).getTelnumber(), is("09011112222"));
		assertEquals(105, list.get(0).calculateServicePrice());
	}
}
