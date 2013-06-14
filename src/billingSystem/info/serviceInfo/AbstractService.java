package billingSystem.info.serviceInfo;

/**
 * サービスを表現します。
 *
 * @author ma2dev
 *
 */
public abstract class AbstractService {

	/**
	 * サービス契約状態を表すフラグ(true:契約、false:未契約)
	 */
	protected boolean flag;

	/**
	 * サービスを表すID<br>
	 * サービス毎にユニークな整数型オブジェクトを設定スル必要があります。
	 */
	protected Integer serviceId;

	/**
	 * 契約有無を取得します。
	 *
	 * @return サービス契約がある場合は true を、契約が無い場合は false を返却します。
	 */
	public boolean isSubscribing() {
		return flag;
	}

	/**
	 * サービスを示すユニークなIDを取得します。
	 *
	 * @return サービスを示すID
	 */
	public Integer getId() {
		return serviceId;
	}

}
