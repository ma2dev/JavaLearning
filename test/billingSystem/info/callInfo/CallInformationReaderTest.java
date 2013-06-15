package billingSystem.info.callInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import billingSystem.info.callInfo.CallInformation;
import billingSystem.info.callInfo.CallInformationReader;

public class CallInformationReaderTest {

	@Test
	public final void testCSVファイル読み込み() {
		String file = new String("dat/billingSystem/callInfo/20130421_callInfo.csv");

		List<CallInformation> list = null;
		try {
			list = CallInformationReader.readFromCsv(new FileReader(file));

			assertNotNull(list);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		CallInformation c = list.get(0);
		assertEquals("09076228838", c.getSrcSubscriber().getTelNum());
	}

}
