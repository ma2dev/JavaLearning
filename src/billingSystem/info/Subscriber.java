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

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Subscriber) {
			Subscriber o = (Subscriber) obj;

			if (this.telNum == null) {
				return o.telNum == null;
			}

			return this.telNum.equals(o.telNum);
		}
		return super.equals(obj);
	}

	public int hashCode() {
		int h = (telNum == null) ? 0 : telNum.hashCode();
		return h;
	}
}
