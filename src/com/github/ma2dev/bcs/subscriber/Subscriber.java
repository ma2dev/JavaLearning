package com.github.ma2dev.bcs.subscriber;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ma2dev.bcs.call.CallHistory;
import com.github.ma2dev.bcs.service.Service;

/**
 * 契約者の情報を管理します。
 * 
 * @author ma2dev
 * 
 */
public class Subscriber {
	/** logger */
	private static final Logger log = LoggerFactory.getLogger(Subscriber.class);

	private List<CallHistory> callHistoryList;
	private Service serviceInfo;
	private String telnumber;

	private long telPrice;
	private long servicePrice;

	private int failure;

	/**
	 * コンストラクタ
	 * 
	 * @param telnumber
	 *            契約者電話番号
	 */
	public Subscriber(String telnumber) {
		callHistoryList = new ArrayList<CallHistory>();
		serviceInfo = new Service(telnumber);
		this.telnumber = new String(telnumber);

		telPrice = 0;
		servicePrice = 0;
	}

	/**
	 * 通話履歴を追加します。
	 * 
	 * @param history
	 *            通話履歴
	 */
	public void addCallHisotry(CallHistory history) {
		callHistoryList.add(history);

		if (history.getEndReason() == CallHistory.END_REASON_ERROR) {
			failure = CallHistory.END_REASON_ERROR;
		}
	}

	/**
	 * サービス契約情報を設定します。
	 * 
	 * @param service
	 *            サービス契約情報
	 */
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

	/**
	 * 料金を計算します。
	 */
	public void calculate() {
		// 通話料金計算
		for (CallHistory history : callHistoryList) {
			telPrice += calculateCallHistory(history);
		}

		// サービス料金計算
		servicePrice = serviceInfo.calculateServicePrice();
	}

	/**
	 * 契約者の情報を明細の出力形式の文字列として返却します。
	 */
	public String toString() {
		// TODO 集計期間には未対応
		return telnumber + "," + "," + telPrice + "," + servicePrice + "," + failure;
	}

	/**
	 * 通話履歴から通話料金を算出します。
	 * 
	 * @param history
	 *            計算対象の通話履歴
	 * @return 通話料金
	 */
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
	 * 通話時間は秒で返却されます。<br>
	 * 時刻情報のすくなくとも一方がnullの場合、0(秒)を返却します。
	 * 
	 * @param startTime
	 *            開始時刻
	 * @param endTime
	 *            終了時刻
	 * 
	 * @return 通話時間(秒)
	 */
	private long talkTime(Date startTime, Date endTime) {
		if (startTime == null || endTime == null) {
			log.debug("startTime or endTime are null.");
			return 0;
		}

		long start = startTime.getTime();
		long end = endTime.getTime();

		long result = (end - start) / 1000;

		return result;
	}

}
