package billingSystem.callInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通話情報を管理する。
 *
 * @author ma2dev
 *
 */
public class CallInformationManager {

	private Map<Subscriber, CallInformationCollection> callMap;

	public CallInformationManager() {
		callMap = new HashMap<Subscriber, CallInformationCollection>();
	}

	/**
	 * csvファイルから呼情報を構築する。
	 *
	 * @param csvfile
	 *            csvファイル
	 * @throws FileNotFoundException
	 *             対象のファイルが存在しない場合
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws ParseException
	 *             CSVの解釈に失敗した場合<br>
	 *             主に時刻情報の解釈に失敗した場合
	 */
	public void buildFromCsv(String csvfile) throws FileNotFoundException, IOException, ParseException {
		List<CallInformation> list = CallInformationReader.readFromCsv(new FileReader(csvfile));

		for (CallInformation callInfo : list) {
			this.add(callInfo);
		}
	}

	/**
	 * 呼情報を追加する。
	 *
	 * @param callInformation
	 *            呼情報
	 */
	public void add(CallInformation callInformation) {
		CallInformationCollection targetCollection = callMap.get(callInformation.getSrcSubscriber());

		if (targetCollection == null) {
			// mapに存在しない場合新規追加
			CallInformationCollection collection = new CallInformationCollection(callInformation);
			Subscriber subscriber = callInformation.getSrcSubscriber();
			callMap.put(subscriber, collection);
		} else {
			targetCollection.add(callInformation);
		}
	}

	/**
	 * 特定契約者電話番号の呼情報をリスト形式で取得する。<br>
	 * 対象の呼情報が存在しない場合はnullを返却する。
	 *
	 * @param telNum
	 *            契約者電話番号
	 * @return 呼情報のリスト。<br>
	 *         対象の呼情報が存在しない場合はnullを返却する。
	 */
	public List<CallInformation> get(String telNum) {
		// TODO 未実装
		return null;
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		// TODO 後で消す
		Set<Subscriber> keys = callMap.keySet();
		for (Subscriber subscriber : keys) {
			CallInformationCollection collection = callMap.get(subscriber);
			collection.printOn();
		}
	}
}
