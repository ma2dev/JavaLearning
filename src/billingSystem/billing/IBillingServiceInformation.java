package billingSystem.billing;

/**
 * 料金取得対象のサービス情報のためのインタフェース
 *
 * @author ma2dev
 *
 */
public interface IBillingServiceInformation {

	/**
	 * 料金取得対象のサービス情報を返却します。
	 *
	 * @param personal
	 *            対象者
	 * @return 対象者のサービス契約情報を返却します。対象者のサービス契約情報が無い場合はnullを返却します。
	 */
	public AbstractBillingService get(IPersonalInformation personal);

}
