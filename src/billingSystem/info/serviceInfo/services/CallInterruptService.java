package billingSystem.info.serviceInfo.services;

import billingSystem.info.serviceInfo.AbstractService;

/**
 * 割り込み通話サービス
 *
 * @author ma2dev
 *
 */
public class CallInterruptService extends AbstractService {

	private boolean flag;
	private Integer id = new Integer(Services.CALLINTERRUPT_SERVICE);

	/**
	 * コンストラクタ
	 *
	 * @param condition
	 *            サービス契約状態
	 */
	public CallInterruptService(String condition) {
		if (condition.equals("0") == true) {
			// 未契約
			flag = false;
		} else if (condition.equals("1") == true) {
			// 契約
			flag = true;
		} else {
			// その他の文字列の場合は未契約とする
			flag = false;
		}
	}

	@Override
	public boolean isSubscribing() {
		return flag;
	}

	@Override
	public Integer getId() {
		return id;
	}

}
