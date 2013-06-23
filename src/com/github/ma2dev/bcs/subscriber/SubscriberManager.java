package com.github.ma2dev.bcs.subscriber;

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

import com.github.ma2dev.bcs.call.CallHistory;
import com.github.ma2dev.bcs.call.CallInformationReader;
import com.github.ma2dev.bcs.conf.Configure;
import com.github.ma2dev.bcs.conf.ConfigureServiceFee;
import com.github.ma2dev.bcs.dataFormat.IllegalDataFormatException;
import com.github.ma2dev.bcs.service.Service;
import com.github.ma2dev.bcs.service.ServiceInforamtionReader;

/**
 * 全契約者の情報を管理し、明細ファイルへの出力を行います。
 *
 * @author ma2dev
 *
 */
public class SubscriberManager {

	private List<Subscriber> subscriberList;
	private Map<String, Subscriber> subscriberMap;

	private Writer outputFile;
	private Reader callInfoFile;
	private Reader serviceInfoFile;
	private Reader subscriberInfoFile;

	private Configure configure;

	/**
	 * コンストラクタ<br>
	 * 入力情報から、料金計算に必要な情報の構築を行います。
	 *
	 * @param configFile
	 *            定義ファイル(properties)
	 * @param subscriberInfoFile
	 *            契約者の情報が含まれるファイル
	 * @param callInfoFile
	 *            呼情報ファイル
	 * @param serviceInfoFile
	 *            サービス情報ファイル
	 * @param outputfile
	 *            出力先(明細ファイル)
	 *
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 * @throws ParseException
	 *             文字列解析に失敗した場合
	 * @throws IllegalDataFormatException
	 *             契約者情報ファイル、呼情報ファイル、サービス情報ファイルのデータが妥当で無い場合
	 * @throws IllegalArgumentException
	 *             妥当性検証のための定義ファイルが不正な場合
	 */
	public SubscriberManager(String configFile, String subscriberInfoFile, String callInfoFile, String serviceInfoFile,
			String outputfile) throws IOException, ParseException, IllegalArgumentException, IllegalDataFormatException {
		subscriberList = new ArrayList<Subscriber>();
		subscriberMap = new HashMap<String, Subscriber>();

		this.subscriberInfoFile = new FileReader(subscriberInfoFile);
		this.callInfoFile = new FileReader(callInfoFile);
		this.serviceInfoFile = new FileReader(serviceInfoFile);
		this.outputFile = new FileWriter(outputfile);

		this.configure = new Configure(configFile);

		build();

		this.subscriberInfoFile.close();
		this.callInfoFile.close();
		this.serviceInfoFile.close();
	}

	/**
	 * 料金計算と明細の出力を行います。
	 *
	 * @throws IOException
	 */
	public void execute() throws IOException {
		for (Subscriber subscriber : subscriberList) {
			subscriber.calculate();
			outputFile.write(subscriber.toString() + "\n");
		}
		outputFile.flush();
		outputFile.close();
	}

	/**
	 * 契約情報を構築します。
	 *
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 * @throws ParseException
	 *             データ変換に失敗した場合
	 * @throws IllegalDataFormatException
	 *             契約者情報ファイル、呼情報ファイル、サービス情報ファイルのデータが妥当で無い場合
	 * @throws IllegalArgumentException
	 *             妥当性検証のための定義ファイルが不正な場合
	 */
	private void build() throws IOException, ParseException, IllegalArgumentException, IllegalDataFormatException {
		// 妥当性検証用の定義ファイル準備
		String verificationSubscriberFile = configure.get(Configure.CONFIGURE_VERIFICATION_SUBSCRIBER_FILEPATH);
		String verificationCallInfoFile = configure.get(Configure.CONFIGURE_VERIFICATION_CALLINFO_FILEPATH);
		String verificationServiceInfoFile = configure.get(Configure.CONFIGURE_VERIFICATION_SERVICEINFO_FILEPATH);
		Reader verificationSubscriberReader = new FileReader(verificationSubscriberFile);
		Reader verificationCallInfoReader = new FileReader(verificationCallInfoFile);
		Reader verificationServiceInfoReader = new FileReader(verificationServiceInfoFile);

		// 契約者一覧の作成
		List<Subscriber> subscriberList = SubscriberInformationReader.readFromCsv(subscriberInfoFile,
				verificationSubscriberReader);
		for (Subscriber subscriber : subscriberList) {
			this.subscriberList.add(subscriber);
			this.subscriberMap.put(subscriber.getTelnumber(), subscriber);
		}

		// 通話履歴の設定
		List<CallHistory> callHistoryList = CallInformationReader.readFromCsv(callInfoFile, verificationCallInfoReader);
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
		List<Service> serviceInfoList = ServiceInforamtionReader.readFromCsv(serviceInfoFile, serviceFee,
				verificationServiceInfoReader);
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
