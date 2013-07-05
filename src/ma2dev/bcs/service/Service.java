package ma2dev.bcs.service;

import java.util.ArrayList;
import java.util.List;

import ma2dev.bcs.conf.ConfigureServiceFee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * サービスを管理します。
 *
 * @author ma2dev
 *
 */
public class Service {
	/** logger */
	private static final Logger log = LoggerFactory.getLogger(Service.class);

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

	/**
	 * コンストラクタ
	 *
	 * @param telnumber
	 *            契約者電話番号
	 */
	public Service(String telnumber) {
		this.telnumber = telnumber;
		familyCallTelnumberList = new ArrayList<String>();
		priceList = null;
		price = 0;
	}

	/**
	 * 契約者電話番号を取得します。
	 *
	 * @return 契約者電話番号
	 */
	public String getTelnumber() {
		return telnumber;
	}

	/**
	 * 各種サービス契約状態を設定します。
	 *
	 * @param display
	 *            番号表示サービスの契約状態を文字列で指定します。<br>
	 *            指定は、契約の場合、文字列"1"を、未契約の場合、文字列"0"を設定します。
	 * @param interrupt
	 *            割り込み通話サービスの契約状態を文字列で指定します。<br>
	 *            指定は、契約の場合、文字列"1"を、未契約の場合、文字列"0"を設定します。
	 * @param familyCall
	 *            家族無料通話サービスの契約状態を文字列で指定します。<br>
	 *            指定は、家族の電話番号をString型の配列で設定します。
	 */
	public void setCondition(String display, String interrupt, List<String> familyCall) {
		serviceConditionDisplay = toBoolean(display);
		serviceConditionInterrupt = toBoolean(interrupt);

		for (String number : familyCall) {
			familyCallTelnumberList.add(number);
		}

		if (familyCall.size() > 0) {
			// 1件以上の登録で契約有りと見なす
			serviceConditionFamilyCall = true;
		}
	}

	/**
	 * サービス料金を設定します。
	 *
	 * @param serviceFee
	 *            サービス料金のオブジェクト
	 */
	public void setPriceList(ConfigureServiceFee serviceFee) {
		this.priceList = serviceFee;
	}

	/**
	 * 無料通話サービスの対象電話番号であるかを確認します。
	 *
	 * @param dstTelnumber
	 *            確認したい電話番号
	 * @return 対象電話番号の場合trueを、対象外の場合falseを返却します。
	 */
	public boolean isFamilyCallTelumber(String dstTelnumber) {
		// 無料通話サービスの場合
		for (String targetTelnumber : familyCallTelnumberList) {
			if (targetTelnumber.compareTo(dstTelnumber) == 0) {
				log.debug("Family free - srcTelNum: {}, dstTelNum: {}", targetTelnumber, dstTelnumber);
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
	 * @return boolean "1"に対してtrueを、"0"に対してfalseを返却します。
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
	 * @return 価格
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
