package com.github.ma2dev.bcs.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.ma2dev.bcs.conf.ConfigureServiceFee;
import com.github.ma2dev.bcs.dataFormat.IData;
import com.github.ma2dev.bcs.dataFormat.IllegalDataFormatException;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;
import com.github.ma2dev.bcs.dataFormat.csv.CsvVerificationProperties;

/**
 * サービス情報ファイルを読み込みます。
 *
 * @author ma2dev
 *
 */
public class ServiceInforamtionReader {

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
	 * @return サービス情報のリスト
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 * @throws IllegalArgumentException
	 *             妥当性検証のための定義ファイルが不正な場合
	 */
	public static List<Service> readFromCsv(Reader csvReader, ConfigureServiceFee servicePrice,
			Reader verificationReader) throws IOException, IllegalArgumentException, IllegalDataFormatException {
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
