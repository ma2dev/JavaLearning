package ma2dev.bcs.call;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ma2dev.bcs.dataFormat.IData;
import ma2dev.bcs.dataFormat.csv.Csv;
import ma2dev.bcs.dataFormat.csv.CsvVerificationProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 呼情報のデータの読み込みを行います。
 *
 * @author ma2dev
 *
 */
public class CallInformationReader {

	/** logger */
	private static final Logger log = LoggerFactory.getLogger(CallInformationReader.class);

	/** 発信者電話番号のカラム位置 */
	private static final int CALLINFORMATION_COLOUMN_SRC_TEL_NUM = 0;
	/** 着信者電話番号のカラム位置 */
	private static final int CALLINFORMATION_COLOUMN_DST_TEL_NUM = 1;
	/** 通話開始時刻のカラム位置 */
	private static final int CALLINFORMATION_COLOUMN_START_TIME = 2;
	/** 通話終了時刻のカラム位置 */
	private static final int CALLINFORMATION_COLOUMN_END_TIME = 3;
	/** 切断要因のカラム位置 */
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
	 * @deprecated この関数はデータの妥当性を検証しないため推奨されません。
	 *             {@link CallInformationReader#readFromCsv(Reader, Reader)}
	 *             を使用してください。
	 */
	@Deprecated
	public static List<CallHistory> readFromCsv(Reader reader) throws IOException, ParseException {
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
	 * @return 呼情報のリストを返却します。妥当性の検証で問題が検出された場合はnullを返却します。
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws ParseException
	 *             csvファイル中の日付情報の変換に失敗した場合
	 */
	public static List<CallHistory> readFromCsv(Reader csvReader, Reader verificationReader) throws IOException,
			ParseException {
		// nullチェック
		Objects.requireNonNull(csvReader, "csvReader must not be null.");
		Objects.requireNonNull(verificationReader, "verificationReader must not be null.");

		Csv csv = new Csv();
		csv.read(csvReader);
		
		List<CallHistory> list = null;
		if (CsvVerificationProperties.verificateCsv(csv, verificationReader)) {
			list = new ArrayList<CallHistory>();

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
		} else {
			log.error("呼情報ファイルの妥当性判定で問題が検出されました");
		}

		return list;
	}
}
