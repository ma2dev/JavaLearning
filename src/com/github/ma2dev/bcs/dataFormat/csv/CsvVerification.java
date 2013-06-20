package com.github.ma2dev.bcs.dataFormat.csv;

/**
 * CSVデータの妥当性を検証する仕組みを提供します。<br>
 *
 * @author ma2dev
 *
 */
public class CsvVerification {

	private Csv csv;

	/**
	 * コンストラクタ
	 *
	 * @param csv
	 *            検証対象のcsvデータ
	 */
	public CsvVerification(Csv csv) {
		this.csv = csv;
	}

	/**
	 * 検証対象のcsvデータにデータが構築さているかを検証します。<br>
	 * データの有無を確認するため、csvオブジェクトが単純にデータを保持しているかどうかを確認するものです。<br>
	 *
	 * @return データが一つでも設定されている場合はtrueを、データが一つも設定されていない場合はfalseを返却します。
	 */
	public boolean isConstructed() {
		if (csv.getRowSize() != 0) {
			return true;
		}

		return false;
	}

}
