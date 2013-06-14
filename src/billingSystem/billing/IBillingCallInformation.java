package billingSystem.billing;

/**
 * 料金取得対象の通話情報のためのインタフェース
 *
 * @author ma2dev
 *
 */
public interface IBillingCallInformation {

	/**
	 * 料金取得対象の通話情報集合を検索します。
	 *
	 * @param personal
	 *            対象者
	 * @return 対象者の通話情報集合を返却します。対象者の通話情報が無い場合はnullを返却します。
	 */
	public ICallCollection find(IPersonalInformation personal);

}
