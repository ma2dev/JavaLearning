package billingSystem.info.serviceInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import billingSystem.info.serviceInfo.services.Services;

public class ServiceInforamtionReaderTest {

	@Test
	public final void testファイル読み込み() {
		String file = new String("dat/test/billingSystem/serviceInfo/template_serviceInfo.csv");

		List<ServiceInformation> list = null;
		try {
			list = ServiceInforamtionReader.readFromCsv(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ServiceInformation s = list.get(0);
		assertEquals("09076228838", s.getSrcSubscriber().getTelNum());
	}

	@Test
	public final void testサービス契約状態確認() {
		String file = new String("dat/test/billingSystem/serviceInfo/test_oneTelNum_serviceInfo.csv");

		List<ServiceInformation> list = null;
		try {
			list = ServiceInforamtionReader.readFromCsv(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ServiceInformation s = list.get(0);
		assertEquals("09076228838", s.getSrcSubscriber().getTelNum());
		assertEquals(false, s.get(Services.NUMBERDISPLAY_SERVICE).isSubscribing());
		assertEquals(true, s.get(Services.CALLINTERRUPT_SERVICE).isSubscribing());
	}

}
