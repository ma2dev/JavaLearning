package billingSystem.info;

import billingSystem.billing.IPersonalInformation;

/**
 * 契約者の情報を提供します。
 *
 * @author ma2dev
 *
 */
public class Subscriber implements IPersonalInformation {

	private String telNum;

	public Subscriber() {
	}

	/**
	 * コンストラクタ<br>
	 * 契約者電話番号を指定できる。
	 *
	 * @param telNum
	 *            契約者電話番号
	 */
	public Subscriber(String telNum) {
		this.telNum = telNum;
	}

	/**
	 * 契約者電話番号を取得します。
	 *
	 * @return telNum 契約者電話番号
	 */
	public String getTelNum() {
		return telNum;
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		// TODO 後で消す
		System.out.println(telNum);
	}
}
