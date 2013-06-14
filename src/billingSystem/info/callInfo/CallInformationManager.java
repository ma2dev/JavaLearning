package billingSystem.info.callInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import billingSystem.billing.IBillingCallInformation;
import billingSystem.billing.ICallCollection;
import billingSystem.billing.IPersonalInformation;
import billingSystem.info.Subscriber;

/**
 * 通話情報を管理します。
 *
 * @author ma2dev
 *
 */
public class CallInformationManager implements IBillingCallInformation {

	private Map<IPersonalInformation, CallInformationCollection> callMap;

	public CallInformationManager() {
		callMap = new HashMap<IPersonalInformation, CallInformationCollection>();
	}

	@Override
	public ICallCollection find(IPersonalInformation personal) {
		System.out.print("2-->");
		personal.printOn();
		CallInformationCollection collection = callMap.get(personal);
		if (collection == null) {
			System.out.println("CallInformationManager --> NULL");
		} else {
			System.out.println("CallInformationManager --> NOT NULL");
		}

		Subscriber tmpS = new Subscriber(personal.getTelNum());
		tmpS.printOn();
		collection = callMap.get(tmpS);
		if (collection == null) {
			System.out.println("CallInformationManager2 --> NULL");
		} else {
			System.out.println("CallInformationManager2 --> NOT NULL");
		}

		return collection;
	}

	/**
	 * csvファイルから呼情報を構築します。
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
		Reader reader = new FileReader(csvfile);
		List<CallInformation> list = CallInformationReader.readFromCsv(reader);
		reader.close();

		for (CallInformation callInfo : list) {
			this.add(callInfo);
		}
	}

	/**
	 * 呼情報を追加します。
	 *
	 * @param callInformation
	 *            呼情報
	 */
	private void add(CallInformation callInformation) {
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
	 * デバッグプリント
	 */
	public void printOn() {
		// TODO 後で消す
		Set<IPersonalInformation> keys = callMap.keySet();
		for (IPersonalInformation subscriber : keys) {
			CallInformationCollection collection = callMap.get(subscriber);
			collection.printOn();
		}
	}

}
