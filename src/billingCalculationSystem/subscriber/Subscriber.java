package billingCalculationSystem.subscriber;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 契約者の状態を管理します。
 *
 * @author ma2dev
 *
 */
public class Subscriber {

	private List<CallHistory> callHistoryList;
	private Service serviceInfo;
	private String telnumber;

	private long telPrice;
	private long servicePrice;

	private int failure;

	public Subscriber(String telnumber) {
		callHistoryList = new ArrayList<CallHistory>();
		serviceInfo = new Service(telnumber);
		this.telnumber = new String(telnumber);

		telPrice = 0;
		servicePrice = 0;
	}

	public void addCallHisotry(CallHistory history) {
		callHistoryList.add(history);

		if (history.getEndReason() == CallHistory.END_REASON_ERROR) {
			failure = CallHistory.END_REASON_ERROR;
		}
	}

	public void setService(Service service) {
		serviceInfo = service;
	}

	/**
	 * 契約者電話番号を取得します。<br>
	 *
	 * @return 電話番号
	 */
	public String getTelnumber() {
		return telnumber;
	}

	public void calculate() {
		// 通話料金計算
		for (CallHistory history : callHistoryList) {
			telPrice += calculateCallHistory(history);
		}

		// サービス料金計算
		servicePrice = serviceInfo.calculateServicePrice();
	}

	public String toString() {
		// TODO 集計期間を入れられるようにする
		return telnumber + "," + "," + telPrice + "," + servicePrice + "," + failure;
	}

	private long calculateCallHistory(CallHistory history) {
		// 家族無料通話対象の確認
		boolean familyCallFlag = serviceInfo.isFamilyCallTelumber(history.getDstTelnumber());
		if (familyCallFlag == true) {
			// 家族無料通話に該当する場合は無料。
			return 0;
		}

		long time = talkTime(history.getStartTime(), history.getEndTime());

		BigDecimal billing = null;
		// 通話時間を計算
		BigDecimal talkTime = new BigDecimal(time);
		// 通話時間を金額に変換
		BigDecimal RatePerSec = new BigDecimal(8.0 / 60.0);
		billing = RatePerSec.multiply(talkTime); // 通話料金

		long price = billing.setScale(0, BigDecimal.ROUND_UP).longValue(); // 小数点第一位で切り上げ

		return price;
	}

	/**
	 * 通話時間を求めます。<br>
	 * 通話時間は秒で返却されます。
	 *
	 * @return 通話時間(秒)
	 */
	private long talkTime(Date startTime, Date endTime) {
		long start = startTime.getTime();
		long end = endTime.getTime();

		long result = (end - start) / 1000;

		return result;
	}

}
