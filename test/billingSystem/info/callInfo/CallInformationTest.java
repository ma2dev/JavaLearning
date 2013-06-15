package billingSystem.info.callInfo;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import billingSystem.info.callInfo.CallInformation;

public class CallInformationTest {

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

	@Test
	public final void test切断要因の設定と取得() {

		CallInformation callInformation = null;
		try {
			callInformation = new CallInformation(null, null, null, null, "0");
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		assertEquals(CallInformation.END_REASON_NORMAL, callInformation.getEndReason());

		callInformation.setEndReason("0");
		assertEquals(CallInformation.END_REASON_NORMAL, callInformation.getEndReason());
		callInformation.setEndReason("1");
		assertEquals(CallInformation.END_REASON_ERROR, callInformation.getEndReason());
		callInformation.setEndReason("2");
		assertEquals(CallInformation.END_REASON_ERROR, callInformation.getEndReason());
		callInformation.setEndReason(null);
		assertEquals(CallInformation.END_REASON_ERROR, callInformation.getEndReason());
	}
}
