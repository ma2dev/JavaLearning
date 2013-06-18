package com.github.ma2dev.bcs.call;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.github.ma2dev.bcs.dataFormat.IData;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;


/**
 * 呼情報のデータの読み込みを行います。
 *
 * @author ma2dev
 *
 */
public class CallInformationReader {

	private static final int CALLINFORMATION_NUM_OF_PARAM = 5;
	private static final int CALLINFORMATION_COLOUMN_SRC_TEL_NUM = 0;
	private static final int CALLINFORMATION_COLOUMN_DST_TEL_NUM = 1;
	private static final int CALLINFORMATION_COLOUMN_START_TIME = 2;
	private static final int CALLINFORMATION_COLOUMN_END_TIME = 3;
	private static final int CALLINFORMATION_COLOUMN_REASON = 4;

	/**
	 * csvファイルから呼情報のListを生成します。
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

			if (checkFormat(cellList) == false) {
				return null;
			}

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
	 * 呼情報のパラメータチェック
	 *
	 * @param list
	 *            呼情報のリスト
	 * @return 問題が無ければtrueを、問題があればfalseを返却します。
	 */
	private static boolean checkFormat(final List<IData> list) {
		// カラム数
		if (list.size() != CALLINFORMATION_NUM_OF_PARAM) {
			return false;
		}

		return true;
	}
}
