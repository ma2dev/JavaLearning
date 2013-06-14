package billingSystem.info.callInfo;

import java.util.ArrayList;
import java.util.List;

import billingSystem.info.Subscriber;

/**
 * 特定契約者の呼情報集合を提供します。
 *
 * @author ma2dev
 *
 */
public class CallInformationCollection {

	private Subscriber subscriber;
	private List<CallInformation> callInforList;

	/**
	 * コンストラクタ。
	 */
	public CallInformationCollection() {
		subscriber = null;
		callInforList = new ArrayList<CallInformation>();
	}

	/**
	 * コンストラクタ。<br>
	 * 引数として呼情報を設定します。
	 *
	 * @param callInformation
	 *            呼情報
	 */
	public CallInformationCollection(CallInformation callInformation) {
		this();
		this.add(callInformation);
	}

	/**
	 * 呼情報を追加します。<br>
	 * 正しく呼情報が追加できた場合はtrueを返却します。<br>
	 * 引数に渡された呼情報の契約者電話番号が呼情報集合の対象と異なる場合はfalseを返却します。<br>
	 *
	 * @param callInformation
	 *            呼情報
	 * @return 正常時はtrueを、呼情報の契約者電話番号が呼情報集合の対象と異なる場合はfalseを返却します。
	 */
	public boolean add(CallInformation callInformation) {
		if (subscriber == null) {
			subscriber = callInformation.getSrcSubscriber();
		}

		Subscriber targetSubscriber = callInformation.getSrcSubscriber();

		if (subscriber.getTelNum().compareTo(targetSubscriber.getTelNum()) != 0) {
			// 発信者情報が収集対象ではない呼情報(設定誤り)の場合
			return false;
		}

		callInforList.add(callInformation);

		return true;
	}

	/**
	 * 呼情報集合が保持する呼情報の数を返却します。
	 *
	 * @return 呼情報数
	 */
	public int getNumberOfCallInformation() {
		return callInforList.size();
	}

	/**
	 * 契約者情報を返却する。
	 *
	 * @return 契約者情報
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		//TODO 後で消す
		subscriber.printOn();
		for (CallInformation info : callInforList) {
			info.printOn();
		}
	}
}
