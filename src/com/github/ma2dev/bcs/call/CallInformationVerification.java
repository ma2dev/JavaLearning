package com.github.ma2dev.bcs.call;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
	private static final int COLUMN_ELEMENT_INDEX_DIGITLOWER = 0;
	/** 列の妥当性情報の最大列数を示すindex */
	private static final int COLUMN_ELEMENT_INDEX_DIGITUPPER = 1;
	/** 列の妥当性情報の型を示すindex */
	private static final int COLUMN_ELEMENT_INDEX_TYPE = 2;
	/** 列の妥当性情報の必須/非必須を示すindex */
	private static final int COLUMN_ELEMENT_INDEX_MORO = 3;

	/** 列の妥当性情報の型を表現する文字列の区切り文字 */
	private static final String COLUMN_ELEMENT_TYPE_DELIMITER = "\\|";
	/** 列の妥当性情報の型として半角英字を示すキーワード */
	private static final String COLUMN_ELEMENT_TYPE_ALPHABET = "ALPHABET";
	/** 列の妥当性情報の型として半角数字を示すキーワード */
	private static final String COLUMN_ELEMENT_TYPE_NUMERIC = "NUMERIC";

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
		List<String> rowAndColumn = getSplitString(targetStr, csv.getConfiguration().getDelimiter(),
				ROW_AND_COLUMN_ELEMENT_NUM);
		if (rowAndColumn.size() != ROW_AND_COLUMN_ELEMENT_NUM) {
			// 行列の妥当性情報が不足している場合
			throw new IllegalArgumentException("行列の妥当性情報の要素数が想定値でない。" + "[expected:" + ROW_AND_COLUMN_ELEMENT_NUM
					+ ", actual:" + rowAndColumn.size() + "][properties.key:" + ROW_AND_COLUMN_KEY
					+ ", prpperties.value:" + targetStr + "]");
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
			targetStr = properties.getProperty(key);
			List<String> columnList = getSplitString(targetStr, csv.getConfiguration().getDelimiter(),
					COLUMN_ELEMENT_NUM);
			if (columnList.size() != COLUMN_ELEMENT_NUM) {
				// 列の妥当性情報が不足している場合
				throw new IllegalArgumentException("列の妥当性情報の要素数が想定値でない。" + "[expected:" + COLUMN_ELEMENT_NUM
						+ ", actual:" + columnList.size() + "][properties.key:" + key + ", prpperties.value:"
						+ targetStr + "]");
			}

			// 列情報の妥当性チェック
			int targetColumn = i;
			// [桁数下限値],[桁数上限値],[型],[必須/非必須]
			int digitLower = stringToInt(columnList.get(COLUMN_ELEMENT_INDEX_DIGITLOWER), -1);
			int digitUpper = stringToInt(columnList.get(COLUMN_ELEMENT_INDEX_DIGITUPPER), -1);
			String type = columnList.get(COLUMN_ELEMENT_INDEX_TYPE);
			String mOrO = columnList.get(COLUMN_ELEMENT_INDEX_MORO);
			try {
				verificateColumn(verification, targetColumn, digitLower, digitUpper, type, mOrO);
			} catch (IllegalDataFormatException e) {
				// IllegalDataFormatExceptionのみcatch
				// TODO Exception内容のlogging
				return false;
			}
		}

		return true;
	}

	/**
	 * 文字列を分割します。
	 *
	 * @param str
	 *            分割対象の文字列
	 * @param regex
	 *            分割文字
	 * @param limit
	 *            分割結果のしきい値<br>
	 *            期待する配列の要素数を設定します。これにより、<i>str.split(regex, limit)</i>
	 *            と同じ効果を得ます。
	 * @return 分割した文字列の配列
	 */
	private List<String> getSplitString(String str, String regex, int limit) {
		List<String> list = new ArrayList<String>();

		for (String st : str.split(regex)) {
			list.add(st);
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
	 * @param digitLower
	 *            桁数下限値
	 * @param digitUpper
	 *            桁数上限値
	 * @param type
	 *            型
	 * @param mOrO
	 *            必須(MUST)/非必須(OPTION)
	 * @return 妥当な場合はtrueを返却します。妥当で無い場合はIllegalDataFormatExceptionの例外をthrowします。<br>
	 *         また、妥当性検証のための定義ファイルが不正な場合はIllegalArgumentExceptionをthrowします。
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 */
	private boolean verificateColumn(CsvVerification target, int targetColumn, int digitLower, int digitUpper,
			String type, String mOrO) throws IllegalDataFormatException, IllegalArgumentException {
		boolean mustFlag = false;
		if (mOrO.equals(COLUMN_ELEMENT_MORO_MUST)) {
			// 必須
			// 要素の有無を確認
			if (target.isColumnMust(targetColumn) == false) {
				// 要素が無い列がある場合にエラーとする
				throw new IllegalDataFormatException("列の妥当性:要素必須違反 [column:" + targetColumn + "]");
			}

			mustFlag = true;
		} else if (mOrO.equals(COLUMN_ELEMENT_MORO_OPTION)) {
			// 非必須
			// 必須要素の有無の確認は不要
			mustFlag = false;
		} else {
			// 行列の妥当性情報が不足している場合
			throw new IllegalArgumentException("列の妥当性情報の要素[必須/非必須]の設定値誤り[" + mOrO + "]");
		}

		// 桁数の確認
		if (target.isColumnDigitLower(targetColumn, digitLower, mustFlag) == false) {
			// 桁数下限値の確認
			throw new IllegalDataFormatException("列の妥当性:桁数下限値違反 [column:" + targetColumn + ", lower:" + digitLower
					+ ", must:" + mustFlag + "]");
		}
		if (target.isColumnDigitUpper(targetColumn, digitUpper, mustFlag) == false) {
			// 桁数上限値の確認
			throw new IllegalDataFormatException("列の妥当性:桁数上限値違反 [column:" + targetColumn + ", lower:" + digitUpper
					+ ", must:" + mustFlag + "]");
		}

		// 型の確認
		boolean typeAlphabet = false;
		boolean typeNumeric = false;
		typeAlphabet = analyzeTypeAlphabet(type);
		typeNumeric = analyzeTypeNumeric(type);
		if (target.isColumnType(targetColumn, typeAlphabet, typeNumeric, mustFlag) == false) {
			// 型の確認
			throw new IllegalDataFormatException("列の妥当性:型違反 [column:" + targetColumn + ", alphabet:" + typeAlphabet
					+ ", numeric:" + typeNumeric + ", must:" + mustFlag + "]");
		}

		return true;
	}

	/**
	 * 解析対象文字列に"ALPHABET"が含まれるかどうかを解析します。<br>
	 * 解析対象文字列は COLUMN_ELEMENT_TYPE_DELIMITER で単語が区切られていることを期待します。
	 *
	 * @param type
	 *            解析対象文字列
	 * @return 解析対象文字列に"ALPHABET"があればtrueを返却し、なければfalseを返却します。
	 */
	private boolean analyzeTypeAlphabet(String type) {
		for (String st : type.split(COLUMN_ELEMENT_TYPE_DELIMITER)) {
			if (st.equals(COLUMN_ELEMENT_TYPE_ALPHABET)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 解析対象文字列に"NUMERIC"が含まれるかどうかを解析します。<br>
	 * 解析対象文字列は COLUMN_ELEMENT_TYPE_DELIMITER で単語が区切られていることを期待します。
	 *
	 * @param type
	 *            解析対象文字列
	 * @return 解析対象文字列に"NUMERIC"があればtrueを返却し、なければfalseを返却します。
	 */
	private boolean analyzeTypeNumeric(String type) {
		for (String st : type.split(COLUMN_ELEMENT_TYPE_DELIMITER)) {
			if (st.equals(COLUMN_ELEMENT_TYPE_NUMERIC)) {
				return true;
			}
		}

		return false;
	}

}
