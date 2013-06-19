package billingCalculationSystem.dataFormat.csv;

/**
 * CSVの設定を管理する。
 *
 * @author ma2dev
 *
 */
public class CsvConfiguration {

	private String delimiter;

	/**
	 * コンストラクタ<br>
	 * 設定値はデフォルトが使用される。
	 */
	public CsvConfiguration() {
		delimiter = ","; // default
	}

	/**
	 * コンストラクタ<br>
	 * デリミタを指定できる。
	 *
	 * @param delimiter
	 *            デリミタとなる文字列
	 */
	public CsvConfiguration(final String delimiter) {
		setDelimiter(delimiter);
	}

	/**
	 * 現在のデリミタを取得する。
	 *
	 * @return デリミタ
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * デリミタを設定する。
	 *
	 * @param delimiter
	 *            デリミタ
	 */
	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

}
