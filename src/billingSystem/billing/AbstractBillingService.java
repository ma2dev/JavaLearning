package billingSystem.billing;

import java.util.List;

import billingSystem.conf.ConfigureServiceFee;
import billingSystem.info.serviceInfo.AbstractService;

/**
 * サービス契約料金を管理します。
 *
 * @author ma2dev
 *
 */
public abstract class AbstractBillingService implements IBilling {

	protected List<AbstractService> serviceList;
	protected ConfigureServiceFee priceList;

	/**
	 * 価格リストとしてconfを設定します。
	 *
	 * @param priceList
	 *            価格のconf
	 */
	public abstract void setPriceList(ConfigureServiceFee priceList);

	/**
	 * サービス料金のリストを構築します。<br>
	 * List<AbstractService>としてserviceListに設定します。
	 */
	protected abstract void buildServiceList();

	@Override
	public long calculate() {
		buildServiceList();

		long price = 0;

		for (AbstractService service : serviceList) {
			if (service.isSubscribing() == true) {
				// サービス契約状態のもののみ価格を算出
				price += priceList.getPrice(service.getId());
			}
		}

		return price;
	}
}
