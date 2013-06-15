package billingSystem.info.callInfo;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.List;

import billingSystem.billing.IBillingCallInformation;

/**
 * CallInformationManagerの生成器
 *
 * @author ma2dev
 *
 */
public class CallInformationManagerFactory {

	/**
	 * 生成元のデータ種別がCSVファイルの場合
	 */
	public static final int FACTORY_KIND_CSV = 0;

	private CallInformationManagerFactory() {
	}

	/**
	 * CallInformationManagerの生成を行います。<br>
	 * 引数に元となるデータ(obj)とデータの種別(kind)を指定します。
	 *
	 * @param kind
	 *            種別
	 * @param obj
	 *            データ元
	 * @return CallInformationManagerをIBillingCallInformationで返却します。
	 * @throws IOException
	 *             IOに失敗した場合
	 * @throws ParseException
	 *             時刻情報の解釈に失敗した場合
	 */
	public static IBillingCallInformation create(int kind, Object obj) throws IOException, ParseException {
		IBillingCallInformation manager = null;

		switch (kind) {
		case FACTORY_KIND_CSV:
			manager = fromCsv((String) obj);
			break;
		default:
			manager = null;
			break;
		}

		return manager;
	}

	/**
	 * CSVからのManager生成
	 *
	 * @param file
	 *            入力ファイル
	 * @return CallInformationManagerをIBillingCallInformationで返却します。
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws ParseException
	 *             時刻情報の解釈に失敗した場合
	 */
	private static final IBillingCallInformation fromCsv(String file) throws IOException, ParseException {
		CallInformationManager manager = new CallInformationManager();

		Reader reader = new FileReader(file);
		List<CallInformation> list = CallInformationReader.readFromCsv(reader);
		reader.close();

		for (CallInformation callInfo : list) {
			manager.add(callInfo);
		}

		return manager;
	}
}
