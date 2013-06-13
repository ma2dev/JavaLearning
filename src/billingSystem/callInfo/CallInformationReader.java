package billingSystem.callInfo;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import billingSystem.dataFormat.csv.Cell;
import billingSystem.dataFormat.csv.Csv;
import billingSystem.dataFormat.csv.CsvConfiguration;

/**
 * 呼情報のデータの読み込みを行う。
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
	 * csvファイルから呼情報のListを生成する。
	 *
	 * @param reader
	 *            csvファイル
	 * @return 呼情報のlist
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws ParseException
	 *             csvファイル中の日付情報の変換に失敗した場合
	 */
	public static List<CallInformation> readFromCsv(Reader reader) throws IOException, ParseException {
		Csv csv = new Csv();
		csv.readFrom(reader);

		List<CallInformation> list = new ArrayList<CallInformation>();

		CallInformation callInformation = null;
		List<Cell> cellList = null;
		for (int i = 0; i < csv.getRowSize(); i++) {
			cellList = csv.getCells(i);

			if (checkFormat(cellList) == false) {
				return null;
			}

			String srcTelNum = cellList.get(CALLINFORMATION_COLOUMN_SRC_TEL_NUM).getData();
			String dstTelNum = cellList.get(CALLINFORMATION_COLOUMN_DST_TEL_NUM).getData();
			String startTime = cellList.get(CALLINFORMATION_COLOUMN_START_TIME).getData();
			String endTime = cellList.get(CALLINFORMATION_COLOUMN_END_TIME).getData();
			String reason = cellList.get(CALLINFORMATION_COLOUMN_REASON).getData();

			callInformation = new CallInformation(srcTelNum, dstTelNum, startTime, endTime, reason);
			list.add(callInformation);
		}

		return list;
	}

	/**
	 * 呼情報のパラメータチェック
	 *
	 * @param list
	 * @return
	 */
	private static boolean checkFormat(List<Cell> list) {
		// カラム数
		if (list.size() != CALLINFORMATION_NUM_OF_PARAM) {
			return false;
		}

		return true;
	}
}
