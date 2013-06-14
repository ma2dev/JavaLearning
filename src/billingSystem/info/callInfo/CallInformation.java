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
	private Enum<EndReason> reason;

	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	public CallInformation() {

	}

	/**
	 * コンストラクタ<br>
	 * 引数として、発信者電話番号、着信者電話番号、通話確立時刻、通話切断時刻、切断要因を設定します。
	 *
	 * @param srcSubscriber
	 *            発信者電話番号
	 * @param dstSubscriber
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
	public CallInformation(String srcSubscriber, String dstSubscriber, String startTime, String endTime, String reason)
			throws ParseException {
		this.srcSubscriber = new Subscriber(srcSubscriber); // TODO 生成器の作成が必要
		this.dstSubscriber = new Subscriber(dstSubscriber); // TODO 生成器の作成が必要
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setEndReason(reason);
	}

	/**
	 * 発信者情報を返却する。
	 *
	 * @return 発信者
	 */
	public Subscriber getSrcSubscriber() {
		return srcSubscriber;
	}

	/**
	 * 着信者情報を返却する。
	 *
	 * @return 着信者
	 */
	public Subscriber getDstSubscriber() {
		return dstSubscriber;
	}

	/**
	 * 通話確立時刻を取得する。
	 *
	 * @return startTime 通話確立時刻
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 通話確立時刻を設定する。
	 *
	 * @param startTime
	 *            通話確立時刻
	 * @throws ParseException
	 *             日付文字列の解析に失敗した場合
	 */
	public void setStartTime(String startTime) throws ParseException {
		this.startTime = stringToDate(startTime);
	}

	/**
	 * 通話切断時刻を取得する。
	 *
	 * @return endTime 通話切断時刻
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 通話切断時刻を設定する。
	 *
	 * @param endTime
	 *            通話切断時刻
	 * @throws ParseException
	 *             日付文字列の解析に失敗した場合
	 */
	public void setEndTime(String endTime) throws ParseException {
		this.endTime = stringToDate(endTime);
	}

	/**
	 * 切断要因を取得する。
	 *
	 * @return endReason 切断要因
	 */
	public Enum<EndReason> getEndReason() {
		return reason;
	}

	/**
	 * 切断要因を設定する。
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
	 * 切断要因を文字列から列挙型に変換する。
	 *
	 * @param s
	 *            切断要因の文字列
	 * @return 切断要因
	 */
	private Enum<EndReason> stringToEndReason(String s) {

		if (s == null) {
			return null;
		}

		Enum<EndReason> e;

		if (s.compareTo("0") == 0) {
			e = EndReason.NORMAL;
		} else {
			e = EndReason.ERROR;
		}

		return e;
	}
}
