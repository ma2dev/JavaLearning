package com.github.ma2dev.bcs.conf;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.github.ma2dev.bcs.service.ServiceList;

public class ConfigureServiceFeeTest {

	@Test
	public final void testサービス料金定義ファイル読み込み() {
		String filename = new String("dat/test/bcs/conf/template.properties");
		Configure properties = null;
		ConfigureServiceFee config = null;

		try {
			properties = new Configure(filename);
			config = new ConfigureServiceFee(properties.get(Configure.CONFIGURE_SERVICE_FEE_FILEPATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 105,315,0
		long price = 0;
		price = config.getPrice(ServiceList.NUMBERDISPLAY_SERVICE);
		assertEquals(105, price);
		price = config.getPrice(ServiceList.CALLINTERRUPT_SERVICE);
		assertEquals(315, price);
		price = config.getPrice(ServiceList.FAMILYCALLFREE_SERVICE);
		assertEquals(0, price);
	}

	@Test
	public final void testエラーパターン() {
		String filename = new String("dat/test/bcs/conf/error.properties");
		Configure properties = null;
		ConfigureServiceFee config = null;

		try {
			properties = new Configure(filename);
			config = new ConfigureServiceFee(properties.get(Configure.CONFIGURE_SERVICE_FEE_FILEPATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ABC,-3000,12.3
		long price = 0;
		price = config.getPrice(ServiceList.NUMBERDISPLAY_SERVICE);
		assertEquals(Long.MIN_VALUE, price);
		price = config.getPrice(ServiceList.CALLINTERRUPT_SERVICE);
		assertEquals(-3000, price); // マイナス値はエラーとしない
		price = config.getPrice(ServiceList.FAMILYCALLFREE_SERVICE);
		assertEquals(Long.MIN_VALUE, price);
	}
}
