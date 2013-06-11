package billingSystem.dataFormat.csv;

/**
 * CSVのセル情報を表現する。
 *
 * @author ma2dev
 *
 */
public class Cell {

	private String data;

	/**
	 * コンストラクタ
	 */
	public Cell() {
		data = null;
	}

	/**
	 * コンストラクタ<br>
	 * セルに設定する文字列を指定できる。
	 *
	 * @param data
	 */
	public Cell(String data) {
		setData(data);
	}

	/**
	 * セルに設定されている情報を文字列として取得する。
	 *
	 * @return 情報
	 */
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int length() {
		return data.length();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return data;
	}

}
