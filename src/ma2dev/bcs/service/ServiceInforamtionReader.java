package ma2dev.bcs.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import ma2dev.bcs.conf.ConfigureServiceFee;
import ma2dev.bcs.dataFormat.IData;
import ma2dev.bcs.dataFormat.csv.Csv;
import ma2dev.bcs.dataFormat.csv.CsvVerificationProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * サービス情報ファイルを読み込みます。
 *
 * @author ma2dev
 *
 */
public class ServiceInforamtionReader {

	/** logger */
	private static final Logger log = LoggerFactory.getLogger(ServiceInforamtionReader.class);

	private static final int SERVICEINFORMATION_TEL_NUM = 0;
	private static final int SERVICEINFORMATION_DISPLAY_CONDITION = 1;
	private static final int SERVICEINFORMATION_INTERRUPT_CONDITION = 2;

	private static final int SERVICEINFORMATION_FAMILYCALL_CONDITION_START = 3;

	/**
	 * csvファイルからサービス情報を構築します。<br>
	 * csvファイルの妥当性の検証は実施されません。
	 *
	 * @param csvReader
	 *            csvファイル
	 * @param servicePrice
	 *            サービス料金定義情報
	 * @return サービス情報のリスト
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @deprecated この関数はデータの妥当性を検証しないため推奨されません。
	 *             {@link ServiceInforamtionReader#readFromCsv(Reader, ConfigureServiceFee, Reader)}
	 *             を使用してください。
	 */
	@Deprecated
	public static List<Service> readFromCsv(Reader csvReader, ConfigureServiceFee servicePrice) throws IOException {
		Csv csv = new Csv();
		csv.read(csvReader);

		List<Service> list = new ArrayList<Service>();

		Service service = null;
		List<IData> cellList = null;
		for (int i = 0; i < csv.getRowSize(); i++) {
			cellList = csv.getCells(i);

			String telNum = (String) cellList.get(SERVICEINFORMATION_TEL_NUM).getData();
			String s1 = (String) cellList.get(SERVICEINFORMATION_DISPLAY_CONDITION).getData();
			String s2 = (String) cellList.get(SERVICEINFORMATION_INTERRUPT_CONDITION).getData();

			service = new Service(telNum);
			service.setCondition(s1, s2, buildFamilyCallTelnumberArray(cellList));
			service.setPriceList(servicePrice);

			list.add(service);
		}

		return list;
	}

	/**
	 * csvファイルからサービス情報を構築します。<br>
	 * csvファイルの妥当性の検証を行います。
	 *
	 * @param csvReader
	 *            csvファイル
	 * @param servicePrice
	 *            サービス料金定義情報
	 * @param verificationReader
	 *            妥当性検証のための定義ファイル
	 * @return サービス情報のリストを返却します。妥当性の検証で問題が検出された場合はnullを返却します。
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 */
	public static List<Service> readFromCsv(Reader csvReader, ConfigureServiceFee servicePrice,
			Reader verificationReader) throws IOException {
		Csv csv = new Csv();
		csv.read(csvReader);

		List<Service> list = null;
		if (CsvVerificationProperties.verificateCsv(csv, verificationReader)) {
			list = new ArrayList<Service>();

			Service service = null;
			List<IData> cellList = null;
			for (int i = 0; i < csv.getRowSize(); i++) {
				cellList = csv.getCells(i);

				String telNum = (String) cellList.get(SERVICEINFORMATION_TEL_NUM).getData();
				String s1 = (String) cellList.get(SERVICEINFORMATION_DISPLAY_CONDITION).getData();
				String s2 = (String) cellList.get(SERVICEINFORMATION_INTERRUPT_CONDITION).getData();

				service = new Service(telNum);
				service.setCondition(s1, s2, buildFamilyCallTelnumberArray(cellList));
				service.setPriceList(servicePrice);

				list.add(service);
			}
		} else {
			log.error("サービス情報ファイルの妥当性判定で問題が検出されました");
		}

		return list;
	}

	/**
	 * 家族無料通話サービスの電話番号配列を組み立てます。
	 *
	 * @param list
	 *            サービス契約情報の文字列のリスト(サービス情報ファイルの1行文のデータ配列)
	 * @return 電話番号を文字列として格納した配列を返却します。
	 */
	private static List<String> buildFamilyCallTelnumberArray(List<IData> list) {
		List<String> telnumberList = new ArrayList<String>();

		for (int i = SERVICEINFORMATION_FAMILYCALL_CONDITION_START; i < list.size(); i++) {
			telnumberList.add((String) list.get(i).getData());
		}

		return telnumberList;
	}
}
