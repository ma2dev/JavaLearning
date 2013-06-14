package billingSystem.info.serviceInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class ServiceInforamtionReaderTest {

	@Test
	public final void testファイル読み込み() {
		String file = new String("dat/billingSystem/serviceInfo/20130614_serviceInfo.csv");

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

}
