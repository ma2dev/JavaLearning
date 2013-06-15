package billingSystem.conf;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import billingSystem.info.serviceInfo.services.Services;

public class ConfigureServiceFeeTest {

	@Test
	public final void testサービス料金定義ファイル読み込み() {
		String filename = new String("dat/billingSystem/conf/test.properties");
		ConfigureServiceFee config = null;

		try {
			config = new ConfigureServiceFee(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 105,315,0
		long price = 0;
		price = config.getPrice(Services.NUMBERDISPLAY_SERVICE);
		assertEquals(105, price);
		price = config.getPrice(Services.CALLINTERRUPT_SERVICE);
		assertEquals(315, price);
		price = config.getPrice(Services.FAMILYCALLFREE_SERVICE);
		assertEquals(0, price);
	}

}
