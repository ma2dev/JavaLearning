package billingSystem.conf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 設定値を管理します。
 *
 * @author ma2dev
 *
 */
public class Configure {
	/**
	 * 集計期間の定義ファイルパス
	 */
	public static final String CONFIGURE_PERIOD_COUNT_FILEPATH = "configure.periodcount.filepath";
	/**
	 * サービス料金の定義ファイルパス
	 */
	public static final String CONFIGURE_SERVICE_FEE_FILEPATH = "configure.servicefee.filepath";

	private Properties properties;

	/**
	 * コンストラクタ
	 *
	 * @param propetiesfile
	 *            propatiesファイル名
	 * @throws FileNotFoundException
	 *             ファイルが無かった場合
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	public Configure(final String propetiesfile) throws FileNotFoundException, IOException {
		properties = new Properties();
		properties.load(new FileReader(propetiesfile));
	}

	/**
	 * keyに対応する設定値を取得します。<br>
	 * 該当する設定値が無い場合はnullを返却します。
	 *
	 * @param key
	 *            key
	 * @return 設定値
	 */
	public String get(final String key) {
		return properties.getProperty(key);
	}
}
