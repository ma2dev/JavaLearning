package billingSystem.serviceInfo;

import billingSystem.subscriber.Subscriber;

/**
 * サービス情報を提供します。
 *
 * @author ma2dev
 *
 */
public class ServiceInformation {

	private Subscriber subscriber;

	/**
	 * コンストラクタ。<br>
	 * 引数として、契約者情報を設定します。
	 *
	 * @param subscriber
	 *            契約者情報
	 */
	public ServiceInformation(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	/**
	 * 契約者情報を取得します。
	 *
	 * @return 契約者情報
	 */
	public Subscriber getSrcSubscriber() {
		return this.subscriber;
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		// TODO あとで削除する
		this.subscriber.printOn();
	}
}
