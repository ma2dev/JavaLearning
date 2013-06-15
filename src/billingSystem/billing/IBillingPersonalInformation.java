package billingSystem.billing;

import java.util.List;

/**
 * 料金取得対象の個人情報のためのインタフェース
 *
 * @author ma2dev
 *
 */
public interface IBillingPersonalInformation {

	/**
	 * 個人情報一覧を取得します。
	 *
	 * @return 個人情報のList
	 */
	public List<IPersonalInformation> getPersonalList();

}
