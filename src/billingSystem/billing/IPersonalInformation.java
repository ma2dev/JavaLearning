package billingSystem.billing;

/**
 * 個人情報を表現するインタフェース
 *
 * @author ma2dev
 *
 */
public interface IPersonalInformation {

	/**
	 * 電話番号を取得します。
	 *
	 * @return 電話番号
	 */
	public String getTelNum();

	/**
	 * equals
	 *
	 * @param obj 比較するオブジェクト
	 * @return 指定されたオブジェクト等しい場合は true
	 */
	public boolean equals(Object obj);

	/**
	 * クラス Object 内の hashCode
	 *
	 * @return ハッシュコード値
	 */
	public int hashCode();

}
