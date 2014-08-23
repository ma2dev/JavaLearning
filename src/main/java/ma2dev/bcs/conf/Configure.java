package ma2dev.bcs.conf;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * 設定値を管理します。
 *
 * @author ma2dev
 *
 */
public class Configure {
	/** 集計期間の定義ファイルパス */
	public static final String CONFIGURE_PERIOD_COUNT_FILEPATH = "configure.periodcount.filepath";
	/** サービス料金の定義ファイルパス */
	public static final String CONFIGURE_SERVICE_FEE_FILEPATH = "configure.servicefee.filepath";

	/** 契約者情報ファイルの妥当性検証定義ファイルパス */
	public static final String CONFIGURE_VERIFICATION_SUBSCRIBER_FILEPATH = "configure.verification.properties.subscriber.filepath";
	/** 呼情報ファイルの妥当性検証定義ファイルパス */
	public static final String CONFIGURE_VERIFICATION_CALLINFO_FILEPATH = "configure.verification.properties.callinfo.filepath";
	/** サービス情報ファイルの妥当性検証定義ファイルパス */
	public static final String CONFIGURE_VERIFICATION_SERVICEINFO_FILEPATH = "configure.verification.properties.serviceinfo.filepath";

	private Properties properties;

	/**
	 * コンストラクタ
	 *
	 * @param propetiesfile
	 *            propatiesファイル名
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	public Configure(String propetiesfile) throws IOException {
		Reader file = new FileReader(propetiesfile);
		properties = new Properties();
		properties.load(file);
		file.close();
	}

	/**
	 * keyに対応する設定値を取得します。<br>
	 * 該当する設定値が無い場合はnullを返却します。
	 *
	 * @param key
	 *            key
	 * @return 設定値
	 */
	public String get(String key) {
		return properties.getProperty(key);
	}
}
