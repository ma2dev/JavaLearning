package com.github.ma2dev.bcs.call;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.github.ma2dev.bcs.dataFormat.IllegalDataFormatException;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;
import com.github.ma2dev.bcs.dataFormat.csv.CsvVerification;

/**
 * 呼情報データの妥当性を検証する仕組みを提供します。
 *
 * @author ma2dev
 *
 */
public class CallInformationVerification {

	/** 行と列の妥当性情報のkey */
	private static final String ROW_AND_COLUMN_KEY = "*.*";
	/** 行と列の妥当性情報の要素数 */
	private static final int ROW_AND_COLUMN_ELEMENT_NUM = 4;
	/** 行と列の妥当性情報の最小行数を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_ROMMIN = 0;
	/** 行と列の妥当性情報の最大行数を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_ROMMAX = 1;
	/** 行と列の妥当性情報の最小列数を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_COLUMNMIN = 2;
	/** 行と列の妥当性情報の最大列数を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_COLUMNMAX = 3;

	/** 列の妥当性情報のkeyの接頭辞。この後に列番号が付属する */
	private static final String COLUMN_KEY_PREFIX = "*.";
	/** 列の妥当性情報の要素数 */
	private static final int COLUMN_ELEMENT_NUM = 4;
	/** 列の妥当性情報の最小列数を示すindex */
	private static final int COLUMN_ELEMENT_INDEX_COLUMNMIN = 0;
	/** 列の妥当性情報の最大列数を示すindex */
	private static final int COLUMN_ELEMENT_INDEX_COLUMMAX = 1;
	/** 列の妥当性情報の型を示すindex */
	private static final int COLUMN_ELEMENT_INDEX_TYPE = 2;
	/** 列の妥当性情報の必須/非必須を示すindex */
	private static final int COLUMN_ELEMENT_INDEX_MORO = 3;

	/** 列の妥当性情報の必須を示す文字列 */
	private static final String COLUMN_ELEMENT_MORO_MUST = "MUST";
	/** 列の妥当性情報の非必須を示す文字列 */
	private static final String COLUMN_ELEMENT_MORO_OPTION = "OPTION";

	/**
	 * csvデータの妥当性を検証します。
	 *
	 * @param csv
	 *            csvデータ
	 * @param verificationFile
	 *            妥当性検証のための定義ファイル
	 * @return csvデータが妥当な場合trueを、そうで無い場合はIllegalDataFormatExceptionの例外をthrowします。<br>
	 *         また、妥当性検証のための定義ファイルが不正な場合はIllegalArgumentExceptionをthrowします。
	 *
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 */
	public boolean verificateCsv(Csv csv, Reader verificationFile) throws IOException, IllegalDataFormatException {
		CsvVerification verification = new CsvVerification(csv);
		Properties properties = new Properties();
		properties.load(verificationFile);

		// 列数と桁数の制限取得
		String targetStr = properties.getProperty(ROW_AND_COLUMN_KEY);
		List<String> rowAndColumn = getSplitString(targetStr, csv.getConfiguration().getDelimiter());
		if (rowAndColumn.size() != ROW_AND_COLUMN_ELEMENT_NUM) {
			// 行列の妥当性情報が不足している場合
			throw new IllegalArgumentException("行列の妥当性情報の要素数が想定値でない。" + "[expected:" + ROW_AND_COLUMN_ELEMENT_NUM
					+ ", actual:" + rowAndColumn.size() + "]");
		}
		// 列数と桁数の妥当性チェック
		int rowMin = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_ROMMIN), -1);
		int rowMax = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_ROMMAX), -1);
		int columnMin = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_COLUMNMIN), -1);
		int columnMax = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_COLUMNMAX), -1);
		if (verificateRowAndColumn(verification, rowMin, rowMax, columnMin, columnMax) == false) {
			throw new IllegalDataFormatException("列数と桁数の妥当性チェック違反");
		}

		// 列情報の妥当性チェック
		// 列情報の妥当性チェック情報は最大列数分あることを期待する
		for (int i = 0; i < columnMax; i++) {
			String key = COLUMN_KEY_PREFIX + i;
			targetStr = properties.getProperty(ROW_AND_COLUMN_KEY);
			List<String> columnList = getSplitString(key, csv.getConfiguration().getDelimiter());
			if (columnList.size() != COLUMN_ELEMENT_NUM) {
				// 列の妥当性情報が不足している場合
				throw new IllegalArgumentException("列の妥当性情報の要素数が想定値でない。" + "[expected:" + COLUMN_ELEMENT_NUM
						+ ", actual:" + columnList.size() + "]");
			}

			// 列情報の妥当性チェック
			int targetColumn = i;
			// [最小桁数],[最大桁数],[型],[必須/非必須]
			int placeMin = stringToInt(columnList.get(COLUMN_ELEMENT_INDEX_COLUMNMIN), -1);
			int placeMax = stringToInt(columnList.get(COLUMN_ELEMENT_INDEX_COLUMMAX), -1);
			String type = columnList.get(COLUMN_ELEMENT_INDEX_TYPE);
			String mOrO = columnList.get(COLUMN_ELEMENT_INDEX_MORO);
			verificateColumn(verification, targetColumn, placeMin, placeMax, type, mOrO);
		}

		return true;
	}

	/**
	 * 文字列を分割します。
	 *
	 * @param str
	 *            分割対象の文字列
	 * @param delimiter
	 *            分割文字
	 * @return 分割した文字列の配列
	 */
	private List<String> getSplitString(String str, String delimiter) {
		List<String> list = new ArrayList<String>();

		StringTokenizer st = new StringTokenizer(str, delimiter);

		String token = null;
		while (st.hasMoreElements()) {
			token = st.nextToken();
			list.add(token);
		}

		return list;
	}

	/**
	 * 文字列をintに変換します。
	 *
	 * @param target
	 *            変換対象文字列
	 * @param returunThenError
	 *            空文字だった場合や文字列変換に失敗した場合に返却する値
	 * @return int値
	 */
	private int stringToInt(String target, int returunThenError) {
		int toInt = 0;
		try {
			toInt = Integer.parseInt(target);
		} catch (NumberFormatException e) {
			toInt = returunThenError;
		}

		return toInt;
	}

	/**
	 * 行数と列数の妥当性を検証します。
	 *
	 * @param target
	 *            妥当性検証対象データ
	 * @param rowMin
	 *            最小行数
	 * @param rowMax
	 *            最大行数
	 * @param columnMin
	 *            最小列数
	 * @param columnMax
	 *            最大列数
	 * @return 妥当な場合はtrueを返却します。妥当で無い場合はIllegalDataFormatExceptionの例外をthrowします。
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 */
	private boolean verificateRowAndColumn(CsvVerification target, int rowMin, int rowMax, int columnMin, int columnMax)
			throws IllegalDataFormatException {
		if (rowMin >= 0) {
			if (target.isRowSizeMoreThanOrEqual(rowMin) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:最小行数違反");
			}
		}
		if (rowMax >= 0) {
			if (target.isRowSizeLessThanOrEqual(rowMax) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:最大行数違反");
			}
		}
		if (columnMin >= 0) {
			if (target.isColumnSizeMoreThanOrEqual(columnMin) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:最小列数違反");
			}
		}
		if (columnMax >= 0) {
			if (target.isColumnSizeLessThanOrEqual(columnMin) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:最大列数違反");
			}
		}

		return true;
	}

	/**
	 * 列の妥当性を検証します。
	 *
	 * @param target
	 *            妥当性検証対象データ
	 * @param targetColumn
	 *            対象列番号
	 * @param placeMin
	 *            最小桁数
	 * @param placeMax
	 *            最大桁数
	 * @param type
	 *            型
	 * @param mOrO
	 *            必須(MUST)/非必須(OPTION)
	 * @return 妥当な場合はtrueを返却します。妥当で無い場合はIllegalDataFormatExceptionの例外をthrowします。<br>
	 *         また、妥当性検証のための定義ファイルが不正な場合はIllegalArgumentExceptionをthrowします。
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 */
	private boolean verificateColumn(CsvVerification target, int targetColumn, int placeMin, int placeMax, String type,
			String mOrO) throws IllegalDataFormatException, IllegalArgumentException {
		if (mOrO.equals(COLUMN_ELEMENT_MORO_MUST)) {
			// 必須
			// 要素の有無を確認
			if (target.isColumnMust(targetColumn) == false) {
				// 要素が無い列がある場合にエラーとする
				throw new IllegalDataFormatException("列の妥当性:必須違反");
			}
		} else if (mOrO.equals(COLUMN_ELEMENT_MORO_OPTION)) {
			// 非必須
			// 必須要素の有無の確認は不要
		} else {
			// 行列の妥当性情報が不足している場合
			throw new IllegalArgumentException("列の妥当性情報の要素[必須/非必須]の設定値誤り[" + mOrO + "]");
		}

		if (target.isColumnPlaceMin(targetColumn, placeMin, false) == false) {
			// 最小桁数の確認
		}
		if (target.isColumnPlaceMax(targetColumn, placeMin, false) == false) {
			// 最大桁数の確認
		}
		if (target.isColumnType(targetColumn, false, true, false) == false) {
			// 最大桁数の確認
		}

		// ここには来ない
		return true;
	}
}
