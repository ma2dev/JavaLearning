package billingSystem.callInfo;

/**
 * 契約者の情報を提供します。
 *
 * @author ma2dev
 *
 */
public class Subscriber {

	private String telNum;

	public Subscriber() {
		telNum = null;
	}

	public Subscriber(String telNum) {
		this.setTelNum(telNum);
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
	 * 契約者電話番号を設定します。
	 *
	 * @param telNum
	 *            契約者電話番号
	 */
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		System.out.println(telNum);
	}

}
