package billingSystem.info.serviceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import billingSystem.billing.AbstractBillingService;
import billingSystem.billing.IBillingPersonalInformation;
import billingSystem.billing.IBillingServiceInformation;
import billingSystem.billing.IPersonalInformation;
import billingSystem.info.Subscriber;

/**
 * サービス情報を管理します。
 *
 * @author ma2dev
 *
 */
public class ServiceInformationManager implements IBillingServiceInformation, IBillingPersonalInformation {

	private Map<IPersonalInformation, ServiceInformation> serviceInfoMap;

	/**
	 * コンストラクタ
	 */
	public ServiceInformationManager() {
		serviceInfoMap = new HashMap<IPersonalInformation, ServiceInformation>();
	}

	@Override
	public List<IPersonalInformation> getPersonalList() {
		Set<IPersonalInformation> keys = serviceInfoMap.keySet();
		return new ArrayList<IPersonalInformation>(keys);
	}

	/**
	 * サービス情報をmapに追加します。<br>
	 * サービス情報の入力情報として契約者情報は一意であることを期待するため、mapに重複があった場合はfalseを返却します。<br>
	 * mapに重複があった場合は、値の置き換えは行われません。<br>
	 * 公開範囲はpackage内です。
	 *
	 * @param serviceInfo
	 *            サービス情報
	 * @return 正常にmapに登録できた場合はtrueを、mapにすでに同一契約者のサービス情報が設定されていた場合はfalseを返却します。
	 */
	boolean add(final ServiceInformation serviceInfo) {
		Subscriber subscriber = serviceInfo.getSrcSubscriber();

		boolean contain = serviceInfoMap.containsKey(subscriber);
		// 設定されるサービス情報は重複していないことを期待する
		if (contain == true) {
			// サービス情報が重複している場合
			return false;
		}

		serviceInfoMap.put(subscriber, serviceInfo);

		return true;
	}

	@Override
	public AbstractBillingService get(final IPersonalInformation personal) {
		return serviceInfoMap.get(personal);
	}

}
