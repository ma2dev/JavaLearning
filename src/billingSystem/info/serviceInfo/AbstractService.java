package billingSystem.info.serviceInfo;

/**
 * サービスを表現します。
 *
 * @author ma2dev
 *
 */
public abstract class AbstractService {

	/**
	 * 契約有無を取得します。
	 *
	 * @return サービス契約がある場合は true を、契約が無い場合は false を返却します。
	 */
	public abstract boolean isSubscribing();

	/**
	 * サービスを示すユニークなIDを取得します。
	 *
	 * @return サービスを示すID
	 */
	public abstract Integer getId();

}
