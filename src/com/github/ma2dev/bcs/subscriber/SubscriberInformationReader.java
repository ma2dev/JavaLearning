package com.github.ma2dev.bcs.subscriber;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.ma2dev.bcs.dataFormat.IData;
import com.github.ma2dev.bcs.dataFormat.IllegalDataFormatException;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;
import com.github.ma2dev.bcs.dataFormat.csv.CsvVerificationProperties;

/**
 * 契約者情報の読み込みを行います。
 *
 * @author ma2dev
 *
 */
public class SubscriberInformationReader {

	private static final int SUBSCRIBERINFORMATION_TEL_NUM = 0;

	/**
	 * csvファイルから契約者情報を構築します。<br>
	 * csvファイルの妥当性の検証は実施されません。
	 *
	 * @param reader
	 *            csvファイル
	 * @return 契約者情報のリスト
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 * @deprecated この関数はデータの妥当性を検証しないため推奨されません。
	 *             {@link SubscriberInformationReader#readFromCsv(Reader, Reader)}
	 *             を使用してください。
	 */
	@Deprecated
	public static List<Subscriber> readFromCsv(Reader reader) throws IOException {
		Csv serviceInfoCsv = new Csv();
		serviceInfoCsv.read(reader);

		List<Subscriber> list = new ArrayList<Subscriber>();

		Subscriber subscriber = null;
		List<IData> cellList = null;
		for (int i = 0; i < serviceInfoCsv.getRowSize(); i++) {
			cellList = serviceInfoCsv.getCells(i);

			String telnumber = (String) cellList.get(SUBSCRIBERINFORMATION_TEL_NUM).getData();

			subscriber = new Subscriber(telnumber);

			list.add(subscriber);
		}

		return list;

	}

	/**
	 * csvファイルから契約者情報を構築します。<br>
	 * csvファイルの妥当性の検証を行います。
	 *
	 * @param csvReader
	 *            csvファイル
	 * @param verificationReader
	 *            妥当性検証のための定義ファイル
	 * @return 契約者情報のリスト
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 * @throws IllegalArgumentException
	 *             妥当性検証のための定義ファイルが不正な場合
	 */
	public static List<Subscriber> readFromCsv(Reader csvReader, Reader verificationReader) throws IOException,
			IllegalArgumentException, IllegalDataFormatException {
		Csv csv = new Csv();
		csv.read(csvReader);

		List<Subscriber> list = null;
		if (CsvVerificationProperties.verificateCsv(csv, verificationReader)) {
			list = new ArrayList<Subscriber>();
			Subscriber subscriber = null;
			List<IData> cellList = null;
			for (int i = 0; i < csv.getRowSize(); i++) {
				cellList = csv.getCells(i);
				String telnumber = (String) cellList.get(SUBSCRIBERINFORMATION_TEL_NUM).getData();
				subscriber = new Subscriber(telnumber);
				list.add(subscriber);
			}
		}

		return list;
	}
}
