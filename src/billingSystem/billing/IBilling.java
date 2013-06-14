package billingSystem.billing;

/**
 * 料金計算のためのインタフェース<br>
 * package内のみ
 *
 * @author ma2dev
 *
 */
interface IBilling {

	/**
	 * 料金を計算する。
	 *
	 * @return 料金
	 */
	public long calculate();

}
