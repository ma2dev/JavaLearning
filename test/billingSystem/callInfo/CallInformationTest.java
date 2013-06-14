package billingSystem.callInfo;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import billingSystem.info.callInfo.CallInformation;

public class CallInformationTest extends CallInformation {

	@Test
	public final void test通話料金計算() {
		try {
			CallInformation callInformation = new CallInformation(null, null, null, null, null);

			// 通話時間1分
			callInformation.setStartTime("20130520100000");
			callInformation.setEndTime("20130520100100");
			long time = callInformation.calculate();
			assertEquals(8, time);

			// 通話時間1時間
			callInformation.setStartTime("20130520100000");
			callInformation.setEndTime("20130520110000");
			time = callInformation.calculate();
			assertEquals(8 * 60, time);

			// 通話時間1日
			callInformation.setStartTime("20130520100000");
			callInformation.setEndTime("20130521100000");
			time = callInformation.calculate();
			assertEquals(8 * 60 * 24, time);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
