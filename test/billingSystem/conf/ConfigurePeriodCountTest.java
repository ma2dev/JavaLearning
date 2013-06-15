package billingSystem.conf;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class ConfigurePeriodCountTest {

	@Test
	public final void test集計期間定義ファイル読み込み() {
		String propetiesfile = new String("dat/billingSystem/conf/test.properties");
		Configure properties = null;
		ConfigurePeriodCount periodCount = null;

		try {
			properties = new Configure(propetiesfile);
			periodCount = new ConfigurePeriodCount(properties.get(Configure.CONFIGURE_PERIOD_COUNT_FILEPATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int day = 0;

		// 21,20,16,15
		day = periodCount.getPreviousStartDay();
		assertEquals(21, day);

		day = periodCount.getPreviousEndDay();
		assertEquals(20, day);

		day = periodCount.getSucceedingStartDay();
		assertEquals(16, day);

		day = periodCount.getSucceedingEndDay();
		assertEquals(15, day);
	}

}
