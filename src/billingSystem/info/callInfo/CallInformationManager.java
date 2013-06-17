package billingSystem.info.callInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import billingSystem.billing.AbstractBillingCall;
import billingSystem.billing.IBillingCallInformation;
import billingSystem.billing.IPersonalInformation;
import billingSystem.info.Subscriber;

/**
 * 通話情報を管理します。
 *
 * @author ma2dev
 *
 */
public class CallInformationManager implements IBillingCallInformation {

	private Map<IPersonalInformation, CallInformationCollection> callMap;

	/**
	 * コンストラクタ
	 */
	public CallInformationManager() {
		callMap = new HashMap<IPersonalInformation, CallInformationCollection>();
	}

	@Override
	public List<AbstractBillingCall> find(final IPersonalInformation personal) {
		CallInformationCollection collection = callMap.get(personal);

		List<AbstractBillingCall> list = null; // 通話情報が取得できなかった場合はこのままnullが返る
		if (collection != null) {
			// 通話情報が取得できた場合
			// listの再構築(型変換のため)
			list = new ArrayList<AbstractBillingCall>();
			for (AbstractBillingCall call : collection.getList()) {
				list.add(call);
			}
		}

		return list;
	}

	/**
	 * 呼情報を追加します。<br>
	 * 公開範囲はpackage内です。
	 *
	 * @param callInformation
	 *            呼情報
	 */
	void add(final CallInformation callInformation) {
		CallInformationCollection targetCollection = callMap.get(callInformation.getSrcSubscriber());

		if (targetCollection == null) {
			// mapに存在しない場合新規追加
			CallInformationCollection collection = new CallInformationCollection(callInformation);
			Subscriber subscriber = callInformation.getSrcSubscriber();
			callMap.put(subscriber, collection);
		} else {
			targetCollection.add(callInformation);
		}
	}
}
