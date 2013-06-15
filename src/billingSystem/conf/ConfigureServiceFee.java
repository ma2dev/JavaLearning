package billingSystem.conf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import billingSystem.dataFormat.IData;
import billingSystem.dataFormat.csv.Csv;
import billingSystem.info.serviceInfo.services.Services;

/**
 * サービス料金定義ファイルを提供します。
 *
 * @author ma2dev
 *
 */
public class ConfigureServiceFee extends Configure {

	/**
	 * サービス料金の定義ファイルパス
	 */
	public static final String CONFIGURE_SERVICE_FEE_FILENAME = "configure.servicefee.filepath";

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
	 * @param propetiesfile
	 *            confへの情報が記載されたpropatiesファイル
	 * @throws FileNotFoundException
	 *             ファイルが無い場合
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	public ConfigureServiceFee(String propetiesfile) throws FileNotFoundException, IOException {
		super(propetiesfile);

		dataList = readFromCsv(super.get(CONFIGURE_SERVICE_FEE_FILENAME));
	}

	/**
	 * サービス料金を取得します。
	 *
	 * @param serviceKind
	 *            サービス種別
	 * @return 料金
	 */
	public long getPrice(int serviceKind) {
		String data = null;
		long price = 0;

		switch (serviceKind) {
		case Services.NUMBERDISPLAY_SERVICE:
			data = (String) dataList.get(Services.NUMBERDISPLAY_SERVICE).getData();
			price = Long.parseLong(data);
			break;
		case Services.CALLINTERRUPT_SERVICE:
			data = (String) dataList.get(Services.CALLINTERRUPT_SERVICE).getData();
			price = Long.parseLong(data);
			break;
		case Services.FAMILYCALLFREE_SERVICE:
			data = (String) dataList.get(Services.FAMILYCALLFREE_SERVICE).getData();
			price = Long.parseLong(data);
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
	 * @throws FileNotFoundException
	 *             ファイルが無い場合
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	private List<IData> readFromCsv(String filename) throws FileNotFoundException, IOException {
		Csv csv = new Csv();
		csv.read(new FileReader(filename));

		if (csv.getRowSize() >= CONFIGURE_SERVICE_FEE_FILE_ROW_SIZE) {
			return csv.getCells(CONFIGURE_SERVICE_FEE_FILE_TARGET_ROW);
		} else {
			return null;
		}
	}
}
