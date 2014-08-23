package ma2dev.bcs.conf;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ma2dev.bcs.dataFormat.IData;
import ma2dev.bcs.dataFormat.csv.Csv;

/**
 * 集計期間定義を提供します。<br>
 * 定義値として、小数点を含む場合、整数を表現していない文字列、負値についてはエラーと判定します。
 *
 * @author ma2dev
 *
 */
public class ConfigurePeriodCount {

	private List<IData> dataList;

	/**
	 * confファイルの行数
	 */
	private static final int CONFIGURE_PERIOD_COUNT_FILE_ROW_SIZE = 1;
	/**
	 * confファイル中の対象行番号
	 */
	private static final int CONFIGURE_PERIOD_COUNT_FILE_TARGET_ROW = 0;

	private static final int CONFIGURE_PERIOD_COUNT_FILE_PREVIOUS_START_COLUMN = 0;
	private static final int CONFIGURE_PERIOD_COUNT_FILE_PREVIOUS_END_COLUMN = 1;
	private static final int CONFIGURE_PERIOD_COUNT_FILE_SUCCEEDING_START_COLUMN = 2;
	private static final int CONFIGURE_PERIOD_COUNT_FILE_SUCCEEDING_END_COLUMN = 3;

	/**
	 * コンストラクタ
	 *
	 * @param filename
	 *            confファイル
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	public ConfigurePeriodCount(String filename) throws IOException {
		dataList = readFromCsv(filename);
	}

	/**
	 * 変更前集計開始日を取得します。
	 *
	 * @return 変更前集計開始日を返却します。正しく値が取得できない場合は0を返却します。
	 */
	public int getPreviousStartDay() {
		return getDay(CONFIGURE_PERIOD_COUNT_FILE_PREVIOUS_START_COLUMN);
	}

	/**
	 * 変更前集計終了日を取得します。
	 *
	 * @return 変更前集計開始日を返却します。正しく値が取得できない場合は0を返却します。
	 */
	public int getPreviousEndDay() {
		return getDay(CONFIGURE_PERIOD_COUNT_FILE_PREVIOUS_END_COLUMN);
	}

	/**
	 * 変更後集計開始日を取得します。
	 *
	 * @return 変更後集計開始日を返却します。正しく値が取得できない場合は0を返却します。
	 */
	public int getSucceedingStartDay() {
		return getDay(CONFIGURE_PERIOD_COUNT_FILE_SUCCEEDING_START_COLUMN);

	}

	/**
	 * 変更前集計開始日を取得します。
	 *
	 * @return 変更前集計開始日を返却します。正しく値が取得できない場合は0を返却します。
	 */
	public int getSucceedingEndDay() {
		return getDay(CONFIGURE_PERIOD_COUNT_FILE_SUCCEEDING_END_COLUMN);
	}

	/**
	 * confファイル(*.csv)の読み込み
	 *
	 * @param filename
	 *            confファイル
	 * @return データのListを返却します。データが無かった場合はnullを返却します。
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	private List<IData> readFromCsv(String filename) throws IOException {
		Csv csv = new Csv();
		csv.read(new FileReader(filename));

		if (csv.getRowSize() >= CONFIGURE_PERIOD_COUNT_FILE_ROW_SIZE) {
			return csv.getCells(CONFIGURE_PERIOD_COUNT_FILE_TARGET_ROW);
		} else {
			return null;
		}
	}

	/**
	 * 値を取得します。
	 *
	 * @param index
	 *            index
	 * @return 値を取得します。値が正しく取得できなかった場合は0を返却します。
	 */
	private int getDay(int index) {
		int day = 0;
		String data = null;

		data = (String) dataList.get(index).getData();
		if (data == null) {
			return 0;
		}

		try {
			day = Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return 0;
		}

		// 日付情報であるため1より小さい場合、もしくは31より大きな数値の場合エラーとする
		if (day < 1 || 31 < day) {
			return 0;
		}

		return day;
	}

}
