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
	 * 指定した行について指定された行数より大きいことを検証します。
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
	 * 指定した行について指定された行数*以上*であることを検証します。
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
	 * 列数の検証をします。<br>
	 * 全ての行について指定された列数であることを検証します。<br>
	 * なお、データが1行も無い場合に、0列あることを検証した場合にはtrueを返却します。
	 *
	 * @param size
	 *            列数
	 * @return 指定された行数ある場合はtrueを、無い場合はfalseを返却します。
	 */
	public boolean isColumnSize(int size) {
		if (this.isRowSizeMoreThan(0) == true) {
			for (int i = 0; i < csv.getRowSize(); i++) {
				List<IData> line = csv.getCells(i);
				if (line.size() != size) {
					return false;
				}
			}
			return true;
		} else if (this.isRowSize(0) && size == 0) {
			// データがないとき、列数0は真のためtrueを返却する。
			return true;
		}

		// 行が無い場合
		return false;
	}

	/**
	 * 列数の検証をします。<br>
	 * 指定した行について指定された列数であることを検証します。<br>
	 * なお、指定した行が存在しない場合はfalseを返却します。<br>
	 * このため、指定した行が存在しないとき、該当行に0列あることを検証した場合にはfalseを返却します。
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
	 * 全ての行について指定された列数*より大きい*ことを検証します。
	 *
	 * @param size
	 *            列数
	 * @return 指定された列数*より大きい*場合はtrueを、以下の場合はfalseを返却します。
	 */
	public boolean isColumnSizeMoreThan(int size) {
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
	 * 全ての行について指定された列数*以上*であることを検証します。
	 *
	 * @param size
	 *            列数
	 * @return 指定された列数*以上*の場合はtrueを、より小さい場合はfalseを返却します。
	 */
	public boolean isColumnSizeMoreThanOrEqual(int size) {
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
}
