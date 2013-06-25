package com.github.ma2dev.bcs.dataFormat.csv;

import com.github.ma2dev.bcs.dataFormat.IData;

/**
 * CSVのセル情報を表現します。
 *
 * @author ma2dev
 *
 */
public class Cell implements IData {

	private String data;

	/**
	 * コンストラクタ<br>
	 * 空文字が設定されます。
	 *
	 */
	public Cell() {
		data = new String("");
	}

	/**
	 * コンストラクタ<br>
	 * セルに設定するデータを文字列として指定できます。
	 *
	 * @param data
	 *            データ
	 */
	public Cell(String data) {
		setData(data);
	}

	/**
	 * セルに設定されているデータを文字列として取得します。
	 *
	 * @return データ
	 */
	public String getData() {
		return data;
	}

	/**
	 * セルにデータをセットします。
	 *
	 * @param data
	 *            データ
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * データの文字列長を取得します。
	 *
	 * @return 文字列長
	 */
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
