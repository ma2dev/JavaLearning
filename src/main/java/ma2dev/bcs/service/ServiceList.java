package ma2dev.bcs.service;

/**
 * サービスを特定するユニークなIDを提供します。
 *
 * @author ma2dev
 *
 */
public interface ServiceList {
	/**
	 * ナンバーディスプレイ
	 */
	public static final int NUMBERDISPLAY_SERVICE = 0;

	/**
	 * 割り込み通話サービス
	 */
	public static final int CALLINTERRUPT_SERVICE = 1;

	/**
	 * 家族無料通話サービス
	 */
	public static final int FAMILYCALLFREE_SERVICE = 2;
}
