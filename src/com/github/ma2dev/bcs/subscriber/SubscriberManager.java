package com.github.ma2dev.bcs.subscriber;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	/** logger */
	private static final Logger log = LoggerFactory.getLogger(SubscriberManager.class);

	private List<Subscriber> subscriberList;
	private Map<String, Subscriber> subscriberMap;

	private BufferedReader callInfoFile;
	private BufferedReader serviceInfoFile;
	private BufferedReader subscriberInfoFile;

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
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 * @throws ParseException
	 *             文字列解析に失敗した場合
	 * @throws IllegalDataFormatException
	 *             契約者情報ファイル、呼情報ファイル、サービス情報ファイルのデータが妥当で無い場合
	 * @throws IllegalArgumentException
	 *             妥当性検証のための定義ファイルが不正な場合
	 */
	public SubscriberManager(String configFile, String subscriberInfoFile, String callInfoFile, String serviceInfoFile)
			throws IOException, ParseException, IllegalArgumentException, IllegalDataFormatException {
		subscriberList = new ArrayList<Subscriber>();
		subscriberMap = new HashMap<String, Subscriber>();

		this.subscriberInfoFile = new BufferedReader(new FileReader(subscriberInfoFile));
		this.callInfoFile = new BufferedReader(new FileReader(callInfoFile));
		this.serviceInfoFile = new BufferedReader(new FileReader(serviceInfoFile));

		this.configure = new Configure(configFile);

		build();

		this.subscriberInfoFile.close();
		this.callInfoFile.close();
		this.serviceInfoFile.close();
	}

	/**
	 * 料金計算と明細の出力を行います。
	 *
	 * @param outputFile
	 *            明細出力先
	 * @throws IOException
	 *             ファイル出力に失敗した場合
	 */
	public void execute(String outputfile) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile));
		for (Subscriber subscriber : subscriberList) {
			subscriber.calculate();
			writer.write(subscriber.toString() + "\n");
		}
		writer.flush();
		writer.close();
	}

	/**
	 * 契約情報を構築します。
	 *
	 * @return 正常時はtrueを、異常時はfalseを返却します。
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
	private boolean build() throws IOException, ParseException, IllegalArgumentException, IllegalDataFormatException {
		// 妥当性検証用の定義ファイル準備
		String verificationSubscriberFile = configure.get(Configure.CONFIGURE_VERIFICATION_SUBSCRIBER_FILEPATH);
		String verificationCallInfoFile = configure.get(Configure.CONFIGURE_VERIFICATION_CALLINFO_FILEPATH);
		String verificationServiceInfoFile = configure.get(Configure.CONFIGURE_VERIFICATION_SERVICEINFO_FILEPATH);
		BufferedReader verificationSubscriberReader = new BufferedReader(new FileReader(verificationSubscriberFile));
		BufferedReader verificationCallInfoReader = new BufferedReader(new FileReader(verificationCallInfoFile));
		BufferedReader verificationServiceInfoReader = new BufferedReader(new FileReader(verificationServiceInfoFile));

		// 契約者一覧の作成
		List<Subscriber> subscriberList = SubscriberInformationReader.readFromCsv(subscriberInfoFile,
				verificationSubscriberReader);
		if (subscriberList == null) {
			log.error("契約者リストの生成に失敗しました");
			verificationSubscriberReader.close();
			verificationServiceInfoReader.close();
			verificationCallInfoReader.close();
			return false;
		}
		for (Subscriber subscriber : subscriberList) {
			this.subscriberList.add(subscriber);
			this.subscriberMap.put(subscriber.getTelnumber(), subscriber);
		}

		// 通話履歴の設定
		List<CallHistory> callHistoryList = CallInformationReader.readFromCsv(callInfoFile, verificationCallInfoReader);
		if (callHistoryList == null) {
			log.error("呼情報リストの生成に失敗しました");
			verificationSubscriberReader.close();
			verificationServiceInfoReader.close();
			verificationCallInfoReader.close();
			return false;
		}
		for (CallHistory history : callHistoryList) {
			String srcTelnumber = history.getSrcTelnumber();
			Subscriber targetSubscriber = subscriberMap.get(srcTelnumber);
			if (targetSubscriber != null) {
				targetSubscriber.addCallHisotry(history); // 通話履歴の追加
			} else {
				// 契約者一覧にない発信電話番号の場合
				log.warn("契約者一覧にない通話履歴上の発信電話番号: {}", srcTelnumber);
			}
		}

		// サービス契約情報の設定
		// 価格情報の取得
		ConfigureServiceFee serviceFee = new ConfigureServiceFee(
				configure.get(Configure.CONFIGURE_SERVICE_FEE_FILEPATH));
		List<Service> serviceInfoList = ServiceInforamtionReader.readFromCsv(serviceInfoFile, serviceFee,
				verificationServiceInfoReader);
		if (serviceInfoList == null) {
			log.error("サービス契約情報リストの生成に失敗しました");
			verificationSubscriberReader.close();
			verificationServiceInfoReader.close();
			verificationCallInfoReader.close();
			return false;
		}
		for (Service service : serviceInfoList) {
			String telnumber = service.getTelnumber();
			Subscriber targetSubscriber = subscriberMap.get(telnumber);
			if (targetSubscriber != null) {
				targetSubscriber.setService(service);
			} else {
				// 契約者一覧にないサービス情報の場合
				log.warn("契約者一覧にないサービス契約情報上の発信者電話番号: {}", telnumber);
			}
		}

		// リソース解放
		verificationSubscriberReader.close();
		verificationServiceInfoReader.close();
		verificationCallInfoReader.close();

		return true;
	}
}
