package billingCalculationSystem.subscriber;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallHistory {

	private String srcTelnumber;
	private String dstTelnumber;
	private Date startTime;
	private Date endTime;
	private int reason;

	// スレッド毎にインスタンスを保持
	private final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	/*
	 * private static final ThreadLocal<DateFormat> df = new
	 * ThreadLocal<DateFormat>() {
	 *
	 * @Override protected DateFormat initialValue() { return new
	 * SimpleDateFormat("yyyyMMddHHmmss"); } };
	 */

	/**
	 * 切断要因 正常
	 */
	public static final int END_REASON_NORMAL = 0;
	private static final String END_REASON_NOMAL_STRING = "0"; // 文字列定義
	/**
	 * 切断要因 異常
	 */
	public static final int END_REASON_ERROR = 1;
	private static final String END_REASON_ERROR_STRING = "1"; // 文字列定義

	public CallHistory(String srcTelnumber, String dstTelnumber, String startTime, String endTime, String reason)
			throws ParseException {
		this.srcTelnumber = new String(srcTelnumber);
		this.dstTelnumber = new String(dstTelnumber);
		this.startTime = stringToDate(startTime);
		this.endTime = stringToDate(endTime);
		this.reason = stringToEndReason(reason);
	}

	public String getSrcTelnumber() {
		return srcTelnumber;
	}

	public String getDstTelnumber() {
		return dstTelnumber;
	}

	/**
	 * 通話確立時刻を取得します。
	 *
	 * @return startTime 通話確立時刻
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 通話切断時刻を取得します。
	 *
	 * @return endTime 通話切断時刻
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 切断要因を取得します。
	 *
	 * @return endReason 切断要因
	 */
	public int getEndReason() {
		return reason;
	}

	/**
	 * 文字列からDataオブジェクトを生成します。
	 *
	 * @param s
	 *            文字列
	 * @return Dateオブジェクト
	 * @throws ParseException
	 *             変換に失敗した場合
	 */
	private Date stringToDate(final String s) throws ParseException {
		if (s == null) {
			return null;
		}
		return df.parse(s);
	}

	/**
	 * 切断要因を文字列から列挙型に変換します。
	 *
	 * @param s
	 *            切断要因の文字列
	 * @return 切断要因
	 */
	private int stringToEndReason(final String s) {
		int reason = END_REASON_ERROR;

		if (s == null) {
			// 切断要因が設定されていない場合エラーと判定する
			return reason;
		}

		if (s.compareTo(END_REASON_NOMAL_STRING) == 0) {
			reason = END_REASON_NORMAL;
		} else if (s.compareTo(END_REASON_ERROR_STRING) == 0) {
			reason = END_REASON_ERROR;
		} else {
			// 未定義の値の場合エラーと判定する
			reason = END_REASON_ERROR;
		}

		return reason;
	}

}
