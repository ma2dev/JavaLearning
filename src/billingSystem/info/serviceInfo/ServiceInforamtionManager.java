package billingSystem.info.serviceInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import billingSystem.billing.IBillingServiceInformation;
import billingSystem.billing.IPersonalInformation;
import billingSystem.info.Subscriber;

/**
 * サービス情報を管理します。
 *
 * @author ma2dev
 *
 */
public class ServiceInforamtionManager implements IBillingServiceInformation {

	private Map<Subscriber, ServiceInformation> serviceInfoMap;

	/**
	 * コンストラクタ
	 */
	public ServiceInforamtionManager() {
		serviceInfoMap = new HashMap<Subscriber, ServiceInformation>();
	}

	@Override
	public List<IPersonalInformation> getPersonalList() {
		Set<Subscriber> keys = serviceInfoMap.keySet();
		return new ArrayList<IPersonalInformation>(keys);
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
	 * @throws BillingSystemServiceInformationBuildException
	 *             サービス情報の構築に失敗した場合<br>
	 *             主に入力値のサービス情報の一意性が担保されていなかった場合に発生します。
	 *
	 */
	public void buildFromCsv(String csvfile) throws FileNotFoundException, IOException,
			BillingSystemServiceInformationBuildException {

		Reader reader = new FileReader(csvfile);
		List<ServiceInformation> list = ServiceInforamtionReader.readFromCsv(reader);
		reader.close();

		for (ServiceInformation serviceInfo : list) {
			if (this.add(serviceInfo) == false) {
				throw new BillingSystemServiceInformationBuildException();
			}
		}
	}

	/**
	 * デバッグプリント
	 */
	public void printOn() {
		// TODO 後で削除する
		Set<Subscriber> keys = serviceInfoMap.keySet();
		for (Subscriber subscriber : keys) {
			ServiceInformation serviceInformation = serviceInfoMap.get(subscriber);
			serviceInformation.printOn();
		}
	}

	/**
	 * サービス情報をmapに追加します。<br>
	 * サービス情報の入力情報として契約者情報は一意であることを期待するため、mapに重複があった場合はfalseを返却します。<br>
	 *
	 * @param serviceInfo
	 *            サービス情報
	 * @return 正常にmapに登録できた場合はtrueを、mapにすでに同一契約者のサービス情報が設定されていた場合はfalseを返却します。
	 */
	private boolean add(ServiceInformation serviceInfo) {
		Subscriber subscriber = serviceInfo.getSrcSubscriber();
		ServiceInformation value = serviceInfoMap.put(subscriber, serviceInfo);

		// 設定されるサービス情報は重複していないことを期待する
		if (value != null) {
			// サービス情報が重複している場合
			return false;
		}

		return true;
	}

	/**
	 * サービス情報の構築に失敗した場合<br>
	 * 主に入力値のサービス情報の一意性が担保されていなかった場合に発生します。
	 *
	 * @author ma2dev
	 *
	 */
	public class BillingSystemServiceInformationBuildException extends Exception {

		private static final long serialVersionUID = -7182950861212206707L;
	}

}
