package com.github.ma2dev.bcs.call;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.github.ma2dev.bcs.dataFormat.IData;
import com.github.ma2dev.bcs.dataFormat.IllegalDataFormatException;
import com.github.ma2dev.bcs.dataFormat.csv.CsvVerificationProperties;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;

/**
 * 呼情報のデータの読み込みを行います。
 *
 * @author ma2dev
 *
 */
public class CallInformationReader {

	private static final int CALLINFORMATION_COLOUMN_SRC_TEL_NUM = 0;
	private static final int CALLINFORMATION_COLOUMN_DST_TEL_NUM = 1;
	private static final int CALLINFORMATION_COLOUMN_START_TIME = 2;
	private static final int CALLINFORMATION_COLOUMN_END_TIME = 3;
	private static final int CALLINFORMATION_COLOUMN_REASON = 4;

	/**
	 * csvファイルから呼情報のListを生成します。<br>
	 * csvファイルの妥当性の検証は実施されません。
	 *
	 * @param reader
	 *            csvファイル
	 * @return 呼情報のlist
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws ParseException
	 *             csvファイル中の日付情報の変換に失敗した場合
	 */
	public static List<CallHistory> readFromCsv(final Reader reader) throws IOException, ParseException {
		Csv csv = new Csv();
		csv.read(reader);

		List<CallHistory> list = new ArrayList<CallHistory>();

		CallHistory callHistory = null;
		List<IData> cellList = null;
		for (int i = 0; i < csv.getRowSize(); i++) {
			cellList = csv.getCells(i);

			String srcTelNum = (String) cellList.get(CALLINFORMATION_COLOUMN_SRC_TEL_NUM).getData();
			String dstTelNum = (String) cellList.get(CALLINFORMATION_COLOUMN_DST_TEL_NUM).getData();
			String startTime = (String) cellList.get(CALLINFORMATION_COLOUMN_START_TIME).getData();
			String endTime = (String) cellList.get(CALLINFORMATION_COLOUMN_END_TIME).getData();
			String reason = (String) cellList.get(CALLINFORMATION_COLOUMN_REASON).getData();

			callHistory = new CallHistory(srcTelNum, dstTelNum, startTime, endTime, reason);
			list.add(callHistory);
		}

		return list;
	}

	/**
	 * csvファイルから呼情報のListを生成します。<br>
	 * csvファイルの妥当性の検証を行います。
	 *
	 * @param csvReader
	 *            csvファイル
	 * @param verificationReader
	 *            妥当性検証のための定義ファイル
	 * @return 呼情報のlistを返却します。妥当性の検証に失敗した場合もしくはNGとなった場合はnullを返却します。
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws ParseException
	 *             csvファイル中の日付情報の変換に失敗した場合
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 * @throws IllegalArgumentException
	 *             妥当性検証のための定義ファイルが不正な場合
	 */
	public static List<CallHistory> readFromCsv(final Reader csvReader, Reader verificationReader) throws IOException,
			ParseException, IllegalDataFormatException, IllegalArgumentException {
		Csv csv = new Csv();
		csv.read(csvReader);

		List<CallHistory> list = null;
		if (CsvVerificationProperties.verificateCsv(csv, verificationReader)) {
			list = readFromCsv(csvReader);
		}

		return list;
	}
}
