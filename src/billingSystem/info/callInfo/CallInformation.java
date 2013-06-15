package billingSystem.info.callInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import billingSystem.billing.AbstractCall;
import billingSystem.info.Subscriber;

/**
 * 呼情報を提供します。
 *
 * @author ma2dev
 *
 */
public class CallInformation extends AbstractCall {

	private Subscriber srcSubscriber;
	private Subscriber dstSubscriber;
	// private Date startTime;
	// private Date endTime;
	private int reason;

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

	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * コンストラクタ<br>
	 * 引数として、発信者電話番号、着信者電話番号、通話確立時刻、通話切断時刻、切断要因を設定します。
	 *
	 * @param srcTelNum
	 *            発信者電話番号
	 * @param dstTelNum
	 *            着信者電話番号
	 * @param startTime
	 *            通話確立時刻
	 * @param endTime
	 *            通話切断時刻
	 * @param reason
	 *            切断要因
	 * @throws ParseException
	 *             日付文字列の解析に失敗した場合
	 */
	public CallInformation(String srcTelNum, String dstTelNum, String startTime, String endTime, String reason)
			throws ParseException {
		this.srcSubscriber = new Subscriber(srcTelNum);
		this.dstSubscriber = new Subscriber(dstTelNum);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setEndReason(reason);
	}

	/**
	 * 発信者情報を返却します。
	 *
	 * @return 発信者
	 */
	public Subscriber getSrcSubscriber() {
		return srcSubscriber;
	}

	/**
	 * 着信者情報を返却します。
	 *
	 * @return 着信者
	 */
	public Subscriber getDstSubscriber() {
		return dstSubscriber;
	}

	/**
	 * 通話確立時刻を取得します。
	 *
	 * @return startTime 通話確立時刻
	 */
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) throws ParseException {
		this.startTime = stringToDate(startTime);
	}

	/**
	 * 通話切断時刻を取得します。
	 *
	 * @return endTime 通話切断時刻
	 */
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) throws ParseException {
		this.endTime = stringToDate(endTime);
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
	 * 切断要因を設定します。
	 *
	 * @param endReason
	 *            切断要因
	 */
	public void setEndReason(String endReason) {
		this.reason = stringToEndReason(endReason);
	}

	private Date stringToDate(String s) throws ParseException {
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
	private int stringToEndReason(String s) {
		int reason = CallInformation.END_REASON_ERROR;

		if (s == null) {
			// 切断要因が設定されていない場合エラーと判定する
			return reason;
		}

		if (s.compareTo(END_REASON_NOMAL_STRING) == 0) {
			reason = CallInformation.END_REASON_NORMAL;
		} else if (s.compareTo(END_REASON_ERROR_STRING) == 0) {
			reason = CallInformation.END_REASON_ERROR;
		} else {
			// 未定義の値の場合エラーと判定する
			reason = CallInformation.END_REASON_ERROR;
		}

		return reason;
	}
}
