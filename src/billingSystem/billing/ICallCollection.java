package billingSystem.billing;

import java.util.List;

/**
 * 通話情報をリストとして表現するインタフェース<br>
 * 料金計算を行う対象の契約者の通話情報集合は、本インタフェースを実装する必要があります。
 *
 * @author ma2dev
 *
 */
public interface ICallCollection {

	/**
	 * 通話情報をListとして取得します。
	 *
	 * @return 通話情報のリスト
	 */
	List<AbstractCall> getList();

}
