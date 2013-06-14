package billingSystem.info;

/**
 * 契約者の情報を提供します。
 *
 * @author ma2dev
 *
 */
public class Subscriber {

	private String telNum;

	/**
	 * コンストラクタ
	 */
	public Subscriber() {
		telNum = null;
	}

	/**
	 * コンストラクタ<br>
	 * 契約者電話番号を指定できる。
	 *
	 * @param telNum
	 *            契約者電話番号
	 */
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
	protected void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		// TODO 後で消す
		System.out.println(telNum);
	}

}
