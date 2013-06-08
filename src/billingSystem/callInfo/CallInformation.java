package billingSystem.callInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 呼情報を提供します。
 *
 * @author ma2dev
 *
 */
public class CallInformation {

	private Subscriber srcSubscriber;
	private Subscriber dstSubscriber;
	private Date startTime;
	private Date endTime;
	private Enum<EndReason> reason;

	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	public CallInformation() {
		this.clear();
	}

	public CallInformation(String srcSubscriber, String dstSubscriber, String startTime, String endTime, String reason)
			throws ParseException {
		this();

		this.srcSubscriber = new Subscriber(srcSubscriber); // TODO 生成器の作成が必要
		this.dstSubscriber = new Subscriber(dstSubscriber); // TODO 生成器の作成が必要
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setEndReason(reason);
	}

	/**
	 * 通話時間を求めます。<br>
	 * 通話時間は秒で返却されます。
	 *
	 * @return 通話時間(秒)
	 */
	public long computeTalkTime() {
		long start = startTime.getTime();
		long end = endTime.getTime();

		long result = (end - start) / 1000;

		return result;
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
	 * 通話開始時刻を取得する。
	 *
	 * @return startTime 通話開始時刻
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 通話開始時刻を設定する。
	 *
	 * @param startTime
	 *            通話開始時刻
	 * @throws ParseException
	 *             日付文字列の解析に失敗した場合
	 */
	public void setStartTime(String startTime) throws ParseException {
		this.startTime = stringToDate(startTime);
	}

	/**
	 * 通話終了時刻を取得する。
	 *
	 * @return endTime 通話終了時刻
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 通話終了時刻を設定する。
	 *
	 * @param endTime
	 *            通話終了時刻 セットする endTime
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

		Enum<EndReason> e;

		if (s.compareTo("0") == 0) {
			e = EndReason.NORMAL;
		} else {
			e = EndReason.ERROR;
		}

		return e;
	}

	/**
	 * clear
	 */
	private void clear() {
		srcSubscriber = new Subscriber();
		dstSubscriber = new Subscriber();
		startTime = new Date();
		endTime = new Date();
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		System.out.println(srcSubscriber.getTelNum() + ", " + dstSubscriber.getTelNum() + ", " + startTime.toString() + ", "
				+ endTime.toString() + ", " + reason);
	}

}
