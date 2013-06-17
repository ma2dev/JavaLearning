package billingCalculationSystem.subscriber;

import java.util.ArrayList;
import java.util.List;

import billingCalculationSystem.conf.ConfigureServiceFee;

public class Service {

	private String telnumber;
	private List<String> familyCallTelnumberList;
	private ConfigureServiceFee priceList;
	private long price;

	/**
	 * 番号表示サービスの契約有無
	 */
	private boolean serviceConditionDisplay;
	/**
	 * 割り込み通話サービスの契約有無
	 */
	private boolean serviceConditionInterrupt;
	/**
	 * 家族無料通話サービスの契約有無
	 */
	private boolean serviceConditionFamilyCall;

	private static final String SERVICE_CONDITION_TRUE = "1";
	private static final String SERVICE_CONDITION_FALSE = "0";

	public Service(String telnumber) {
		this.telnumber = telnumber;
		familyCallTelnumberList = new ArrayList<String>();
		priceList = null;
		price = 0;
	}

	public String getTelnumber() {
		return telnumber;
	}

	public void setCondition(String display, String interrupt, List<String> familyCall) {
		serviceConditionDisplay = toBoolean(display);
		serviceConditionInterrupt = toBoolean(interrupt);
		if (familyCall.size() > 0) {
			// 1件以上の登録で契約有りと見なす
			serviceConditionFamilyCall = true;
		}
	}

	public void setPriceList(ConfigureServiceFee serviceFee) {
		this.priceList = serviceFee;
	}

	/**
	 * 無料通話サービスの対象電話番号であるかを確認します。
	 *
	 * @param dstTelnumber
	 * @return
	 */
	public boolean isFamilyCallTelumber(String dstTelnumber) {
		// 無料通話サービスの場合
		for (String targetTelnumber : familyCallTelnumberList) {
			if (targetTelnumber.compareTo(dstTelnumber) == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * サービス契約料金を取得します。
	 *
	 * @return サービス契約料金
	 */
	public long calculateServicePrice() {
		price = 0;

		// 電話番号表示サービス
		price += getServicePrice(ServiceList.NUMBERDISPLAY_SERVICE);

		// 割り込み通話サービス
		price += getServicePrice(ServiceList.CALLINTERRUPT_SERVICE);

		// 家族無料通話サービス
		price += getServicePrice(ServiceList.FAMILYCALLFREE_SERVICE);

		return price;
	}

	/**
	 * サービス契約状態の文字列をbooleanに変換します。
	 *
	 * @param s
	 *            サービス契約状態の文字列
	 * @return boolean
	 */
	private boolean toBoolean(String s) {
		if (s.compareTo(SERVICE_CONDITION_TRUE) == 0) {
			return true;
		} else if (s.compareTo(SERVICE_CONDITION_FALSE) == 0) {
			return false;
		} else {
			// 解釈できない場合はfalseとする
			return false;
		}
	}

	/**
	 * サービスの価格を取得します。
	 *
	 * @param serviceKind
	 *            サービス種別
	 * @return
	 */
	private long getServicePrice(int serviceKind) {
		long price = 0;

		switch (serviceKind) {
		case ServiceList.NUMBERDISPLAY_SERVICE:
			if (serviceConditionDisplay == false) {
				return 0;
			}
			break;
		case ServiceList.CALLINTERRUPT_SERVICE:
			if (serviceConditionInterrupt == false) {
				return 0;
			}
			break;
		case ServiceList.FAMILYCALLFREE_SERVICE:
			if (serviceConditionFamilyCall == false) {
				return 0;
			}
			break;
		default:
			return 0;
		}

		price = priceList.getPrice(serviceKind);
		if (price == Long.MIN_VALUE) {
			price = 0;
		}

		return price;
	}
}
