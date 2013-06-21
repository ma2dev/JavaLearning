package com.github.ma2dev.bcs.dataFormat.csv;

import java.util.List;
import com.github.ma2dev.bcs.dataFormat.IData;

/**
 * CSVデータの妥当性を検証する仕組みを提供します。<br>
 *
 * @author ma2dev
 *
 */
public class CsvVerification {

	private static final String VERIFICATION_MATCH_ALPHABET = "^[A-Za-z]+$";
	private static final String VERIFICATION_MATCH_NUMBER = "^[0-9]+$";
	private static final String VERIFICATION_MATCH_ALPHABET_AND_NUMBER = "^[0-9A-Za-z]+$";

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

	// nullチェック --------------------------------------------------------------
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

	// 行数チェック --------------------------------------------------------------
	/**
	 * 行数の検証をします。
	 *
	 * @param size
	 *            行数
	 * @return 指定された行数ある場合はtrueを、無い場合はfalseを返却します。
	 */
	public boolean isRowSize(int size) {
		if (csv.getRowSize() == size) {
			return true;
		}
		return false;
	}

	/**
	 * 行数の検証をします。<br>
	 * 指定された行数より大きいことを検証します。
	 *
	 * @param size
	 *            行数
	 * @return 指定された行数*より大きい*場合はtrueを、以下の場合はfalseを返却します。
	 */
	public boolean isRowSizeMoreThan(int size) {
		if (csv.getRowSize() > size) {
			return true;
		}
		return false;
	}

	/**
	 * 行数の検証をします。<br>
	 * 指定された行数*以上*であることを検証します。
	 *
	 * @param size
	 *            行数
	 * @return 指定された行数*以上*の場合はtrueを、より小さい場合はfalseを返却します。
	 */
	public boolean isRowSizeMoreThanOrEqual(int size) {
		if (csv.getRowSize() >= size) {
			return true;
		}
		return false;
	}

	/**
	 * 行数の検証をします。<br>
	 * 指定された行数*以下*であることを検証します。
	 *
	 * @param size
	 *            行数
	 * @return 指定された行数*以下*の場合はtrueを、より大きい場合はfalseを返却します。
	 */
	public boolean isRowSizeLessThanOrEqual(int size) {
		// TODO UT未実施
		return !(isRowSizeMoreThan(size));
	}

	// 列数チェック --------------------------------------------------------------
	/**
	 * 列数の検証をします。<br>
	 * 全ての行について指定された列数であることを検証します。<br>
	 * なお、検証対象のデータが1行も無い場合はfalseを返却します。
	 *
	 * @param size
	 *            列数
	 * @return 指定された行数ある場合はtrueを、無い場合はfalseを返却します。
	 */
	public boolean isColumnSize(int size) {
		if (this.isConstructed() == false) {
			// 1行もデータが無い場合
			return false;
		}

		for (int i = 0; i < csv.getRowSize(); i++) {
			List<IData> line = csv.getCells(i);
			if (line.size() != size) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 列数の検証をします。<br>
	 * 指定した行について指定された列数であることを検証します。<br>
	 * なお、指定した行が存在しない場合はfalseを返却します。
	 *
	 * @param row
	 *            行指定
	 * @param size
	 *            列数
	 * @return 指定された行数ある場合はtrueを、無い場合はfalseを返却します。
	 */
	public boolean isColumnSize(int row, int size) {
		if (csv.getRowSize() < row + 1) {
			return false;
		}

		List<IData> line = csv.getCells(row);
		if (line.size() != size) {
			return false;
		}
		return true;
	}

	/**
	 * 列数の検証をします。<br>
	 * 全ての行について指定された列数*より大きい*ことを検証します。<br>
	 * なお、検証対象のデータが1行も無い場合はfalseを返却します。
	 *
	 * @param size
	 *            列数
	 * @return 指定された列数*より大きい*場合はtrueを、以下の場合はfalseを返却します。
	 */
	public boolean isColumnSizeMoreThan(int size) {
		if (this.isConstructed() == false) {
			// 1行もデータが無い場合
			return false;
		}

		for (int i = 0; i < csv.getRowSize(); i++) {
			List<IData> line = csv.getCells(i);
			if (line.size() <= size) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 列数の検証をします。<br>
	 * 指定した行について指定された列数*より大きい*ことを検証します。<br>
	 * なお、指定した行が存在しない場合はfalseを返却します。
	 *
	 * @param row
	 *            行指定
	 * @param size
	 *            列数
	 * @return 指定された列数*より大きい*場合はtrueを、以下の場合はfalseを返却します。
	 */
	public boolean isColumnSizeMoreThan(int row, int size) {
		if (csv.getRowSize() < row + 1) {
			return false;
		}

		List<IData> line = csv.getCells(row);
		if (line.size() <= size) {
			return false;
		}
		return true;
	}

	/**
	 * 列数の検証をします。<br>
	 * 全ての行について指定された列数*以上*であることを検証します。<br>
	 * なお、検証対象のデータが1行も無い場合はfalseを返却します。
	 *
	 * @param size
	 *            列数
	 * @return 指定された列数*以上*の場合はtrueを、より小さい場合はfalseを返却します。
	 */
	public boolean isColumnSizeMoreThanOrEqual(int size) {
		if (this.isConstructed() == false) {
			// 1行もデータが無い場合
			return false;
		}

		for (int i = 0; i < csv.getRowSize(); i++) {
			List<IData> line = csv.getCells(i);
			if (line.size() < size) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 列数の検証をします。<br>
	 * 指定した行について指定された列数*以上*であることを検証します。<br>
	 * なお、指定した行が存在しない場合はfalseを返却します。
	 *
	 * @param row
	 *            行指定
	 * @param size
	 *            列数
	 * @return 指定された列数*以上*の場合はtrueを、より小さい場合はfalseを返却します。
	 */
	public boolean isColumnSizeMoreThanOrEqual(int row, int size) {
		if (csv.getRowSize() < row + 1) {
			return false;
		}

		List<IData> line = csv.getCells(row);
		if (line.size() < size) {
			return false;
		}
		return true;
	}

	/**
	 * 列数の検証をします。<br>
	 * 全ての行について指定された列数*以下*であることを検証します。<br>
	 * なお、検証対象のデータが1行も無い場合はfalseを返却します。
	 *
	 * @param size
	 *            列数
	 * @return 指定された列数*以下*の場合はtrueを、より大きい場合はfalseを返却します。
	 */
	public boolean isColumnSizeLessThanOrEqual(int size) {
		// TODO UT未実施
		return !(isColumnSizeMoreThan(size));
	}

	// 桁数チェック --------------------------------------------------------------
	/**
	 * 指定された特定情報の桁数をチェックします。<br>
	 * 桁数は半角数字(バイト数)でのチェックとします。<br>
	 * なお、指定した行列のデータが存在しない場合はfalseを返却します。
	 *
	 * @param row
	 *            行
	 * @param column
	 *            列
	 * @param number
	 *            桁数
	 * @return 指定された桁数の場合はtrueを、そうでない場合はfalseを返却します。
	 */
	public boolean isDigit(int row, int column, int byteNumber) {
		// 行チェック
		IData data = csv.getCell(row, column);
		if (data == null) {
			return false;
		}

		String str = (String) data.getData();
		int length = str.getBytes().length; // バイト数取得
		if (byteNumber != length) {
			return false;
		}

		return true;
	}

	// 必須/非必須チェック -------------------------------------------------------
	/**
	 * 列要素のデータ有無の検証をします。<br>
	 * 全ての行について指定された列にデータが存在していることを検証します。<br>
	 * 指定された列がnullもしくは空文字の場合はfalseを返却し、それ以外はtrueを返却します。<br>
	 * なお、検証対象のデータが1行も無い場合はfalseを返却します。
	 *
	 * @param column
	 *            列
	 * @return 指定された列がnullもしくは空文字の場合はfalseを返却し、それ以外はtrueを返却します。
	 */
	public boolean isColumnMust(int column) {
		if (this.isConstructed() == false) {
			// 1行もデータが無い場合
			return false;
		}

		for (int i = 0; i < csv.getRowSize(); i++) {
			IData data = csv.getCell(i, column);
			if (data == null) {
				// null
				return false;
			}
			String s = (String) data.getData();
			if (s.equals("")) {
				// 空文字
				return false;
			}
		}
		return true;
	}

	/**
	 * 指定列のデータ桁数最小値を検証します。
	 *
	 * @param column
	 *            列
	 * @param placeMin
	 *            最小桁数
	 * @param must
	 *            必須(true)/非必須(false)を指定
	 * @return
	 */
	public boolean isColumnPlaceMin(int column, int placeMin, boolean must) {
		// TODO 未実装
		return false;
	}

	/**
	 * 指定列のデータ桁数最大値を検証します。
	 *
	 * @param column
	 *            列
	 * @param placeMax
	 *            最大桁数
	 * @param must
	 *            必須(true)/非必須(false)を指定
	 * @return
	 */
	public boolean isColumnPlaceMax(int column, int placeMin, boolean must) {
		// TODO 未実装
		return false;
	}

	/**
	 *
	 * @param column
	 * @param typeAlphabet
	 * @param typeNumber
	 * @param must
	 * @return
	 */
	public boolean isColumnType(int column, boolean typeAlphabet, boolean typeNumber, boolean must) {
		// TODO UT未実施
		if (this.isConstructed() == false) {
			// 1行もデータが無い場合
			return false;
		}
		if (must == true) {
			if (isColumnMust(column) == false) {
				// 必須チェック
				return false;
			}
		}

		for (int i = 0; i < csv.getRowSize(); i++) {
			IData data = csv.getCell(i, column);
			String s = (String) data.getData();
			if (typeAlphabet == true && typeNumber == false) {
				if (isAlphabet(s) == false) {
					return false;
				}
			} else if (typeAlphabet == false && typeNumber == true) {
				if (isNumber(s) == false) {
					return false;
				}
			} else if (typeAlphabet == true && typeNumber == true) {
				if (isAlphabetAndNumber(s) == false) {
					return false;
				}
			} else {
				if (isAlphabetAndNumber(s)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 文字列が半角英数字かどうかを検証します。
	 *
	 * @param s
	 *            検証対象文字列
	 * @return 半角英数字のみで構成されている場合はtrueを、そうで無い場合はfalseを返却します。
	 */
	private boolean isAlphabetAndNumber(String s) {
		return s.matches(VERIFICATION_MATCH_ALPHABET_AND_NUMBER);
	}

	/**
	 * 文字列が半角英字かどうかを検証します。
	 *
	 * @param s
	 *            検証対象文字列
	 * @return 半角英字のみで構成されている場合はtrueを、そうで無い場合はfalseを返却します。
	 */
	private boolean isAlphabet(String s) {
		return s.matches(VERIFICATION_MATCH_ALPHABET);
	}

	/**
	 * 文字列が半角数字かどうかを検証します。
	 *
	 * @param s
	 *            検証対象文字列
	 * @return 半角数字のみで構成されている場合はtrueを、そうで無い場合はfalseを返却します。
	 */
	private boolean isNumber(String s) {
		return s.matches(VERIFICATION_MATCH_NUMBER);
	}

}
