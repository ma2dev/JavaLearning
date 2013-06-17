package billingSystem.info.serviceInfo.services;

import billingSystem.info.serviceInfo.AbstractService;

/**
 * ナンバーディスプレイサービス
 *
 * @author ma2dev
 *
 */
public class NumberDisplayService extends AbstractService {

	/**
	 * コンストラクタ
	 *
	 * @param condition
	 *            サービス契約状態
	 */
	public NumberDisplayService(final String condition) {
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

		serviceId = Services.NUMBERDISPLAY_SERVICE;
	}
}
