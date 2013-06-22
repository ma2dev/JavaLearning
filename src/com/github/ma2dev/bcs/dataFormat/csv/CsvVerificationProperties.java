package com.github.ma2dev.bcs.dataFormat.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.ma2dev.bcs.dataFormat.IllegalDataFormatException;

/**
 * CSVデータの妥当性を検証する仕組みを提供します。<br>
 * 妥当性の検証にはpropertiesファイルに示された情報に従います。<br>
 * <br>
 * CSVデータの行数および列数の下限値、上限値は以下のkey(*.*)によって指定します。<br>
 * *.*=[行数下限値],[行数上限値],[列数下限値],[列数上限値]<br>
 * [行数下限値]: 行数の下限値を半角数字で指定します。省略可能です。<br>
 * [行数上限値]: 行数の上限値を半角数字で指定します。省略可能です。<br>
 * [列数下限値]: 列数の下限値を半角数字で指定します。省略可能です。<br>
 * [列数上限値]: 列数の上限値を半角数字で指定します。列数の上限値は32767です。省略可能です。省略した場合は最大で列数の上限値まで走査します。<br>
 * <br>
 * CSVは列の情報に従ったデータを複数行持つことでデータを表現していることを期待し、各列のデータフォーマットを以下のように規定します。
 * この規定に対しての妥当性を検証します。<br>
 * 列のデータフォーマットは、対象列を以下のkey(*.[列]:列は0..nの半角数字)によって指定します。[列]番号は必ず連番であることを期待します。
 * 欠番があった場合の動作は保証されません。<br>
 * *.[列]=[桁数下限値],[桁数上限値],[型],[必須/非必須]<br>
 * [桁数下限値]: 対象列のデータ桁数下限値を半角数字で指定します。<br>
 * [桁数下限値]: 対象列のデータ桁数上限値を半角数字で指定します。<br>
 * [型]: 対象データの型を指定します。指定は、半角英字を ALPHABET 、半角数字を NUMERIC とします。それらの条件を複数指定する場合は |
 * で区切ります。すなわち、半角英数字の場合は ALPHABET|NUMERIC と指定できます。条件の順番は問いません。<br>
 * [必須/非必須]: 対象列のデータが必須であるか、省略可能かを指定します。必須の場合 MUST を、省略可能な場合 OPTION を指定します。
 *
 * @author ma2dev
 *
 */
public class CsvVerificationProperties {

	/** 行と列の妥当性情報のkey */
	private static final String ROW_AND_COLUMN_KEY = "*.*";
	/** 行と列の妥当性情報の要素数 */
	private static final int ROW_AND_COLUMN_ELEMENT_NUM = 4;
	/** 行と列の妥当性情報の行数下限値を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_ROMLOWER = 0;
	/** 行と列の妥当性情報の行数上限値を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_ROMUPPER = 1;
	/** 行と列の妥当性情報の列数下限値を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_COLUMNLOWER = 2;
	/** 行と列の妥当性情報の列数上限値を示すindex */
	private static final int ROW_AND_COLUMN_INDEX_COLUMNUPPER = 3;

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

	/** 空文字だった場合や文字列変換に失敗した場合に返却する値 */
	private static final int STRING_TO_INT_ERROR_VALUE = -1;

	/** 列数のシステムとしての最大値 */
	private static final int COLUMN_SYSTEM_UPPER = Short.MAX_VALUE;

	/**
	 * csvデータの妥当性を検証します。
	 *
	 * @param csv
	 *            csvデータ
	 * @param verificationReader
	 *            妥当性検証のための定義ファイル
	 * @return csvデータが妥当な場合trueを、そうで無い場合はIllegalDataFormatExceptionの例外をthrowします。<br>
	 *         また、妥当性検証のための定義ファイルが不正な場合はIllegalArgumentExceptionをthrowします。
	 *
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 * @throws IllegalArgumentException
	 *             妥当性検証のための定義ファイルが不正な場合
	 */
	public static boolean verificateCsv(Csv csv, Reader verificationReader) throws IllegalDataFormatException,
			IllegalArgumentException, IOException {
		CsvVerification verification = new CsvVerification(csv);
		Properties properties = new Properties();
		properties.load(verificationReader);

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
		int rowLower = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_ROMLOWER), STRING_TO_INT_ERROR_VALUE);
		int rowUpper = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_ROMUPPER), STRING_TO_INT_ERROR_VALUE);
		int columnLower = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_COLUMNLOWER), STRING_TO_INT_ERROR_VALUE);
		int columnUpper = stringToInt(rowAndColumn.get(ROW_AND_COLUMN_INDEX_COLUMNUPPER), STRING_TO_INT_ERROR_VALUE);
		if (columnUpper > COLUMN_SYSTEM_UPPER) {
			throw new IllegalArgumentException("列数上限値違反[expected:" + COLUMN_SYSTEM_UPPER + ", actual:" + columnUpper
					+ "]");
		}
		if (verificateRowAndColumn(verification, rowLower, rowUpper, columnLower, columnUpper) == false) {
			throw new IllegalDataFormatException("列数と桁数の妥当性チェック違反");
		}

		// 列数上限値が設定されていない場合、許容最大数を設定
		if (columnUpper == STRING_TO_INT_ERROR_VALUE) {
			columnUpper = COLUMN_SYSTEM_UPPER;
		}

		// 列情報の妥当性チェック
		// 列情報の妥当性チェック情報は最大列数分あることを期待する
		for (int i = 0; i < columnUpper; i++) {
			String key = COLUMN_KEY_PREFIX + i;
			targetStr = properties.getProperty(key);
			if (targetStr == null) {
				// 列の走査が終わった場合
				// columnUpperがCOLUMN_SYSTEM_UPPERの場合にのみ通ると期待される
				// それ以外の場合、列情報として情報の欠落がある場合
				break;
			}

			List<String> columnList = getSplitString(targetStr, csv.getConfiguration().getDelimiter(),
					COLUMN_ELEMENT_NUM);
			if (columnList.size() != COLUMN_ELEMENT_NUM) {
				// 列の妥当性情報が不足している場合
				throw new IllegalArgumentException("列の妥当性情報の要素数が想定値でない。[expected:" + COLUMN_ELEMENT_NUM + ", actual:"
						+ columnList.size() + "][properties.key:" + key + ", prpperties.value:" + targetStr + "]");
			}

			// 列情報の妥当性チェック
			int targetColumn = i;
			// [桁数下限値],[桁数上限値],[型],[必須/非必須]
			int digitLower = stringToInt(columnList.get(COLUMN_ELEMENT_INDEX_DIGITLOWER), STRING_TO_INT_ERROR_VALUE);
			int digitUpper = stringToInt(columnList.get(COLUMN_ELEMENT_INDEX_DIGITUPPER), STRING_TO_INT_ERROR_VALUE);
			String type = columnList.get(COLUMN_ELEMENT_INDEX_TYPE);
			String mOrO = columnList.get(COLUMN_ELEMENT_INDEX_MORO);
			verificateColumn(verification, targetColumn, digitLower, digitUpper, type, mOrO);
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
	private static List<String> getSplitString(String str, String regex, int limit) {
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
	private static int stringToInt(String target, int returunThenError) {
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
	 * @param rowLower
	 *            最小行数
	 * @param rowUpper
	 *            最大行数
	 * @param columnLower
	 *            最小列数
	 * @param columnUpper
	 *            最大列数
	 * @return 妥当な場合はtrueを返却します。妥当で無い場合はIllegalDataFormatExceptionの例外をthrowします。
	 * @throws IllegalDataFormatException
	 *             データが妥当で無い場合
	 */
	private static boolean verificateRowAndColumn(CsvVerification target, int rowLower, int rowUpper, int columnLower,
			int columnUpper) throws IllegalDataFormatException {
		if (rowLower >= 0) {
			if (target.isRowSizeMoreThanOrEqual(rowLower) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:行数下限値違反");
			}
		}
		if (rowUpper >= 0) {
			if (target.isRowSizeLessThanOrEqual(rowUpper) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:行数上限値違反");
			}
		}
		if (columnLower >= 0) {
			if (target.isColumnSizeMoreThanOrEqual(columnLower) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:列数下限値違反");
			}
		}
		if (columnUpper >= 0) {
			if (target.isColumnSizeLessThanOrEqual(columnLower) == false) {
				throw new IllegalDataFormatException("行数と列数の妥当性:列数上限値違反");
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
	private static boolean verificateColumn(CsvVerification target, int targetColumn, int digitLower, int digitUpper,
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
	private static boolean analyzeTypeAlphabet(String type) {
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
	private static boolean analyzeTypeNumeric(String type) {
		for (String st : type.split(COLUMN_ELEMENT_TYPE_DELIMITER)) {
			if (st.equals(COLUMN_ELEMENT_TYPE_NUMERIC)) {
				return true;
			}
		}

		return false;
	}

}
