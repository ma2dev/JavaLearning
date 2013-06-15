package billingSystem.conf;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import billingSystem.dataFormat.IData;
import billingSystem.dataFormat.csv.Csv;
import billingSystem.info.serviceInfo.services.Services;

/**
 * サービス料金定義を提供します。<br>
 * 定義値として、小数点を含む場合、整数を表現していない文字列、負値についてはエラーと判定します。
 *
 * @author ma2dev
 *
 */
public class ConfigureServiceFee {

	private List<IData> dataList;

	/**
	 * confファイルの行数
	 */
	private static final int CONFIGURE_SERVICE_FEE_FILE_ROW_SIZE = 1;
	/**
	 * confファイル中の対象行番号
	 */
	private static final int CONFIGURE_SERVICE_FEE_FILE_TARGET_ROW = 0;

	/**
	 * コンストラクタ
	 *
	 * @param filename
	 *            confファイル
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	public ConfigureServiceFee(final String filename) throws IOException {
		dataList = readFromCsv(filename);
	}

	/**
	 * サービス料金を取得します。
	 *
	 * @param serviceKind
	 *            サービス種別
	 * @return 料金
	 */
	public long getPrice(final int serviceKind) {
		long price = 0;

		switch (serviceKind) {
		case Services.NUMBERDISPLAY_SERVICE:
			price = getLongValueFromData(Services.NUMBERDISPLAY_SERVICE);
			break;
		case Services.CALLINTERRUPT_SERVICE:
			price = getLongValueFromData(Services.CALLINTERRUPT_SERVICE);
			break;
		case Services.FAMILYCALLFREE_SERVICE:
			price = getLongValueFromData(Services.FAMILYCALLFREE_SERVICE);
			break;
		default:
			break;
		}

		return price;
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
	private List<IData> readFromCsv(final String filename) throws IOException {
		Csv csv = new Csv();
		csv.read(new FileReader(filename));

		if (csv.getRowSize() >= CONFIGURE_SERVICE_FEE_FILE_ROW_SIZE) {
			return csv.getCells(CONFIGURE_SERVICE_FEE_FILE_TARGET_ROW);
		} else {
			return null;
		}
	}

	/**
	 * 値をlong値で取得します。
	 *
	 * @param index
	 *            index
	 * @return 値を返却します。正しく値が取得できない場合は-1を返却します。
	 */
	private long getLongValueFromData(final int index) {
		String data = null;
		long l = 0;

		data = (String) dataList.get(index).getData();
		if (data == null) {
			return -1;
		}

		try {
			l = Long.parseLong(data);
		} catch (NumberFormatException e) {
			return -1;
		}

		if (l < 0) {
			return -1;
		}

		return l;
	}
}
