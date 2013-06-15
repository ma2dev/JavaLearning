package billingSystem.info.serviceInfo;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.List;

import billingSystem.billing.IBillingServiceInformation;

/**
 * ServiceInformationManagerの生成器
 *
 * @author ma2dev
 *
 */
public class ServiceInformationManagerFactory {

	/**
	 * 生成元のデータ種別がCSVファイルの場合
	 */
	public static final int FACTORY_KIND_CSV = 0;

	private ServiceInformationManagerFactory() {

	}

	/**
	 * ServiceInformationManagerの生成を行います。<br>
	 * 引数に元となるデータ(obj)とデータの種別(kind)を指定します。
	 *
	 * @param kind
	 *            種別
	 * @param obj
	 *            データ元
	 * @return ServiceInformationManagerをIBillingServiceInformationで返却します。
	 * @throws IOException
	 *             IOに失敗した場合
	 * @throws ParseException
	 *             時刻情報の解釈に失敗した場合
	 * @throws BillingSystemServiceInformationBuildException
	 *             サービス情報の構築に失敗した場合
	 */
	public static IBillingServiceInformation create(int kind, Object obj) throws IOException, ParseException,
			BillingSystemServiceInformationBuildException {
		IBillingServiceInformation manager = null;

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
	 * @return ServiceInforamtionManagerをIBillingServiceInformationで返却します。
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws ParseException
	 *             時刻情報の解釈に失敗した場合
	 * @throws BillingSystemServiceInformationBuildException
	 *             サービス情報の構築に失敗した場合
	 */
	private static final IBillingServiceInformation fromCsv(String file) throws IOException, ParseException,
			BillingSystemServiceInformationBuildException {
		ServiceInformationManager manager = new ServiceInformationManager();

		Reader reader = new FileReader(file);
		List<ServiceInformation> list = ServiceInforamtionReader.readFromCsv(reader);
		reader.close();

		for (ServiceInformation serviceInfo : list) {
			if (manager.add(serviceInfo) == false) {
				throw new BillingSystemServiceInformationBuildException();
			}
		}

		return manager;
	}

}
