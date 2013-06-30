package com.github.ma2dev.bcs.call;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通話履歴を管理します。
 * 
 * @author ma2dev
 * 
 */
public class CallHistory {
	/** logger */
	private static final Logger log = LoggerFactory.getLogger(CallHistory.class);

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

	/** 切断要因 正常 を表す値 */
	public static final int END_REASON_NORMAL = 0;
	/** 切断要因 正常 を表す文字列 */
	private static final String END_REASON_NOMAL_STRING = "0"; // 文字列定義

	/** 切断要因 異常 を表す値 */
	public static final int END_REASON_ERROR = 1;
	/** 切断要因 異常 を表す文字列 */
	private static final String END_REASON_ERROR_STRING = "1"; // 文字列定義

	/**
	 * コンストラクタ
	 * 
	 * @param srcTelnumber
	 *            発信者電話番号
	 * @param dstTelnumber
	 *            着信者電話番号
	 * @param startTime
	 *            通話確立時刻
	 * @param endTime
	 *            通話切断時刻
	 * @param reason
	 *            切断様式
	 * @throws ParseException
	 *             文字列の解析に失敗した場合
	 */
	public CallHistory(String srcTelnumber, String dstTelnumber, String startTime, String endTime, String reason)
			throws ParseException {
		this.srcTelnumber = new String(Objects.requireNonNull(srcTelnumber, "srcTelnumber must not be null."));
		this.dstTelnumber = new String(Objects.requireNonNull(dstTelnumber, "dstTelnumber must not be null."));
		this.startTime = stringToDate(Objects.requireNonNull(startTime, "startTime must not be null."));
		this.endTime = stringToDate(Objects.requireNonNull(endTime, "endTime must not be null."));
		this.reason = stringToEndReason(Objects.requireNonNull(reason, "reason must not be null."));
	}

	/**
	 * 発信者電話番号を取得します。
	 * 
	 * @return 発信者電話番号
	 */
	public String getSrcTelnumber() {
		return srcTelnumber;
	}

	/**
	 * 着信者電話番号を取得します。
	 * 
	 * @return 着信者電話番号
	 */
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
	 * @return endReason 切断要因<br>
	 *         正常時は、{@link CallHistory#END_REASON_NORMAL}が取得されます。<br>
	 *         異常時は、{@link CallHistory#END_REASON_ERROR}が取得されます。
	 */
	public int getEndReason() {
		return reason;
	}

	/**
	 * 文字列からDataオブジェクトを生成します。
	 * 
	 * @param s
	 *            文字列
	 * @return Dateオブジェクト。s がnullもしくは空の場合はnullを返却します。
	 * @throws ParseException
	 *             変換に失敗した場合
	 */
	private Date stringToDate(String s) throws ParseException {
		if (s == null) {
			log.debug("stringToDate is null.");
			return null;
		} else if (s.equals("")) {
			log.debug("stringToDate is empty string.");
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
	private int stringToEndReason(String s) {
		int reason = END_REASON_ERROR;

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
