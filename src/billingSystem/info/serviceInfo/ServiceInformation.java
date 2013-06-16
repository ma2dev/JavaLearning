package billingSystem.info.serviceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import billingSystem.billing.AbstractBillingService;
import billingSystem.conf.ConfigureServiceFee;
import billingSystem.info.Subscriber;

/**
 * サービス情報を提供します。
 *
 * @author ma2dev
 *
 */
public class ServiceInformation extends AbstractBillingService {

	private Subscriber subscriber;
	private Map<Integer, AbstractService> serviceMap;

	/**
	 * コンストラクタ。<br>
	 * 引数として、契約者情報を設定します。
	 *
	 * @param subscriber
	 *            契約者情報
	 */
	public ServiceInformation(final Subscriber subscriber) {
		this.subscriber = subscriber;
		serviceMap = new HashMap<Integer, AbstractService>();
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
	 * サービスを取得します。
	 *
	 * @param serviceId
	 *            サービスID
	 * @return サービス
	 */
	public AbstractService get(final int serviceId) {
		return serviceMap.get(serviceId);
	}

	/**
	 * サービス契約の追加<br>
	 * 可視範囲はパッケージ内
	 *
	 * @param service
	 *            サービス
	 */
	void add(final AbstractService service) {
		serviceMap.put(service.getId(), service); // IneterはAutoboxingで処理
	}

	@Override
	public void setPriceList(ConfigureServiceFee priceList) {
		super.priceList = priceList;
	}

	@Override
	public void buildServiceList() {
		super.serviceList = new ArrayList<AbstractService>();
		Set<Integer> keys = serviceMap.keySet();
		for (Integer key : keys) {
			AbstractService service = serviceMap.get(key);
			serviceList.add(service); // 契約状態にかかわらず設定
		}
	}
}
