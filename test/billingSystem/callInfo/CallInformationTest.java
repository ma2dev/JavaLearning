package billingSystem.callInfo;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class CallInformationTest extends CallInformation {

	@Test
	public final void test通話時間計算() {
		CallInformation callInformation = new CallInformation();

		try {

			// 通話時間1分
			callInformation.setStartTime("20130520100000");
			callInformation.setEndTime("20130520100100");
			long time = callInformation.computeTalkTime();
			assertEquals(60, time);

			// 通話時間1時間
			callInformation.setStartTime("20130520100000");
			callInformation.setEndTime("20130520110000");
			time = callInformation.computeTalkTime();
			assertEquals(60 * 60, time);

			// 通話時間1日
			callInformation.setStartTime("20130520100000");
			callInformation.setEndTime("20130521100000");
			time = callInformation.computeTalkTime();
			assertEquals(60 * 60 * 24, time);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
