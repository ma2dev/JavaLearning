package com.github.ma2dev.bcs.call;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.GregorianCalendar;

import ma2dev.bcs.call.CallHistory;

import org.junit.Test;

public class CallHistoryTest {

	@Test
	public final void testデータ生成と取得() {
		String srcTelnumber = "08012345678";
		String dstTelnumber = "0398765432";
		String startTime = "20130619120000";
		String endTime = "20130619131234";
		String reason = "0";

		try {
			CallHistory history = new CallHistory(srcTelnumber, dstTelnumber, startTime, endTime, reason);

			assertEquals("08012345678", history.getSrcTelnumber());
			assertEquals("0398765432", history.getDstTelnumber());

			// 2013/06/19 12:00:00
			long time = new GregorianCalendar(2013, 5, 19, 12, 0, 0).getTime().getTime();
			assertEquals(time, history.getStartTime().getTime());

			// 2013/06/19 13:12:34
			time = new GregorianCalendar(2013, 5, 19, 13, 12, 34).getTime().getTime();
			assertEquals(time, history.getEndTime().getTime());

			assertEquals(CallHistory.END_REASON_NORMAL, history.getEndReason());

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public final void test切断様式が異常の場合() {
		String srcTelnumber = "08012345678";
		String dstTelnumber = "0398765432";
		String startTime = "20130619120000";
		String endTime = "20130619131234";
		String reason = "1";

		try {
			CallHistory history = new CallHistory(srcTelnumber, dstTelnumber, startTime, endTime, reason);

			// 切断様式が異常の場合
			history = new CallHistory(srcTelnumber, dstTelnumber, startTime, endTime, reason);
			assertEquals(CallHistory.END_REASON_ERROR, history.getEndReason());

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public final void test切断様式がその他の文字列の場合() {
		String srcTelnumber = "08012345678";
		String dstTelnumber = "0398765432";
		String startTime = "20130619120000";
		String endTime = "20130619131234";
		String reason = "xyz";

		try {
			CallHistory history = new CallHistory(srcTelnumber, dstTelnumber, startTime, endTime, reason);

			// 切断様式が未定義の文字列の場合、切断様式を異常と判定
			history = new CallHistory(srcTelnumber, dstTelnumber, startTime, endTime, reason);
			assertEquals(CallHistory.END_REASON_ERROR, history.getEndReason());

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
