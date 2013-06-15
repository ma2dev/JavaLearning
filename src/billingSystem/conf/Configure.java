package billingSystem.conf;

import java.util.HashMap;
import java.util.Map;

/**
 * 設定値を管理します。
 *
 * @author ma2dev
 *
 */
public class Configure {

	/**
	 * 集計期間を示すID
	 */
	public static final int CONFIGURE_PERIOD_COUNT_FILENAME = 1;

	private Map<Integer, String> configMap = new HashMap<Integer, String>();

	/**
	 * IDに対応する設定値を文字列として設定します。<br>
	 * IDに対応する設定値がすでに設定されている場合、新たな値として置き換えられます。<br>
	 * <br>
	 * 本メソッドの公開範囲はpackage内です。
	 *
	 * @param elementID
	 *            ID
	 * @param elementValue
	 *            設定値
	 */
	void add(int elementID, String elementValue) {
		configMap.put(elementID, elementValue);
	}

	/**
	 * IDに対応する設定値を取得します。<br>
	 * 該当する設定値が無い場合はnullを返却します。
	 *
	 * @param elementID
	 *            ID
	 * @return 設定値
	 */
	public String get(int elementID) {
		return configMap.get(elementID);
	}
}
