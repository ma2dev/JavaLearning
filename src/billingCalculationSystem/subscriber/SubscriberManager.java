package billingCalculationSystem.subscriber;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import billingCalculationSystem.conf.Configure;
import billingCalculationSystem.conf.ConfigureServiceFee;

public class SubscriberManager {

	private List<Subscriber> subscriberList;
	private Map<String, Subscriber> subscriberMap;

	private Writer outputFile;
	private Reader callInfoFile;
	private Reader serviceInfoFile;
	private Reader subscriberInfoFile;

	private Configure configure;

	public SubscriberManager(String subscriberInfoFile, String callInfoFile, String serviceInfoFile, String outputfile,
			String configFile) throws IOException, ParseException {
		subscriberList = new ArrayList<Subscriber>();
		subscriberMap = new HashMap<String, Subscriber>();

		this.subscriberInfoFile = new FileReader(subscriberInfoFile);
		this.callInfoFile = new FileReader(callInfoFile);
		this.serviceInfoFile = new FileReader(serviceInfoFile);
		this.outputFile = new FileWriter(outputfile);

		this.configure = new Configure(configFile);

		build();
	}

	public void execute() throws IOException {
		for (Subscriber subscriber : subscriberList) {
			subscriber.calculate();
			outputFile.write(subscriber.toString());
		}
		outputFile.flush();
	}

	/**
	 * 契約情報を構築します。
	 *
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 * @throws ParseException
	 *             データ変換に失敗した場合
	 */
	private void build() throws IOException, ParseException {
		// 契約者一覧の作成
		List<Subscriber> subscriberList = SubscriberInformationReader.readFromCsv(subscriberInfoFile);
		for (Subscriber subscriber : subscriberList) {
			this.subscriberList.add(subscriber);
			this.subscriberMap.put(subscriber.getTelnumber(), subscriber);
		}

		// 通話履歴の設定
		List<CallHistory> callHistoryList = CallInformationReader.readFromCsv(callInfoFile);
		for (CallHistory history : callHistoryList) {
			String srcTelnumber = history.getSrcTelnumber();
			Subscriber targetSubscriber = subscriberMap.get(srcTelnumber);
			if (targetSubscriber != null) {
				targetSubscriber.addCallHisotry(history); // 通話履歴の追加
			} else {
				// 契約者一覧にない発信電話番号の場合
			}
		}

		// サービス契約情報の設定
		// 価格情報の取得
		ConfigureServiceFee serviceFee = new ConfigureServiceFee(
				configure.get(Configure.CONFIGURE_SERVICE_FEE_FILEPATH));
		List<Service> serviceInfoList = ServiceInforamtionReader.readFromCsv(serviceInfoFile, serviceFee);
		for (Service service : serviceInfoList) {
			String telnumber = service.getTelnumber();
			Subscriber targetSubscriber = subscriberMap.get(telnumber);
			if (targetSubscriber != null) {
				targetSubscriber.setService(service);
			} else {
				// 契約者一覧にないサービス情報の場合
			}
		}
	}
}
