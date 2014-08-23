package com.github.ma2dev.bcs.dataFormat.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import ma2dev.bcs.dataFormat.csv.Csv;
import ma2dev.bcs.dataFormat.csv.CsvVerification;

import org.junit.Test;

public class CsvVerificationTest {

	@Test
	public final void test有効データ保持有無の検証() {

		Csv csv = new Csv();
		assertThat(CsvVerification.isConstructed(csv), is(false));

		int row = 0;
		int column = 0;
		csv.setCell(row, column, "ABC");
		assertThat(CsvVerification.isConstructed(csv), is(true));
	}

	@Test
	public final void test行数の検証_より大きい場合() {
		Csv csv = new Csv();

		assertThat(CsvVerification.isRowSizeMoreThan(csv, -1), is(true));
		assertThat(CsvVerification.isRowSizeMoreThan(csv, 0), is(false));
		csv.setCell(0, 0, "ABC");
		assertThat(CsvVerification.isRowSizeMoreThan(csv, 0), is(true));
		assertThat(CsvVerification.isRowSizeMoreThan(csv, 1), is(false));
		csv.setCell(1, 0, "ABC");
		assertThat(CsvVerification.isRowSizeMoreThan(csv, 1), is(true));
		assertThat(CsvVerification.isRowSizeMoreThan(csv, 2), is(false));
	}

	@Test
	public final void test行数の検証_以上の場合() {
		Csv csv = new Csv();

		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, -1), is(true));
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 0), is(true));
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 1), is(false));
		csv.setCell(0, 0, "ABC");
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 0), is(true));
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 1), is(true));
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 2), is(false));
		csv.setCell(1, 0, "ABC");
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 1), is(true));
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 2), is(true));
		assertThat(CsvVerification.isRowSizeMoreThanOrEqual(csv, 3), is(false));
	}

	@Test
	public final void test行数の検証_以下の場合() {
		Csv csv = new Csv();

		assertThat(CsvVerification.isRowSizeLessThanOrEqual(csv, -1), is(false));
		assertThat(CsvVerification.isRowSizeLessThanOrEqual(csv, 0), is(true)); // 空なので0行以下はOK
		assertThat(CsvVerification.isRowSizeLessThanOrEqual(csv, 1), is(true)); // 空なので1行以下はOK

		csv.setCell(0, 0, "x");
		assertThat(CsvVerification.isRowSizeLessThanOrEqual(csv, 0), is(false)); // 1行なので0行以下はNG
		assertThat(CsvVerification.isRowSizeLessThanOrEqual(csv, 1), is(true)); // 1行なので1行以下はOK

		csv.setCell(9, 9, "x");
		assertThat(CsvVerification.isRowSizeLessThanOrEqual(csv, 9), is(false)); // 10行なので9行以下はNG
		assertThat(CsvVerification.isRowSizeLessThanOrEqual(csv, 10), is(true)); // 10行なので10行以下はOK
	}

	@Test
	public final void test列数の検証_以下の場合() {
		Csv csv = new Csv();

		// データ未設定はfalse
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, -1), is(false));
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 0), is(false));
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 1), is(false));

		csv.setCell(0, 0, "x"); // 1列
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 0), is(false)); // 0列以下はNG
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 1), is(true)); // 1列以下はOK

		csv.setCell(0, 9, "a"); // 10列
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 9), is(false)); // 9列以下はNG
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 10), is(true)); // 10列以下はOK
		csv.setCell(1, 4, "b"); // 5列
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 4), is(false)); // 4列以下はNG
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 5), is(false)); // 5列以下はNG
		assertThat(CsvVerification.isColumnSizeLessThanOrEqual(csv, 10), is(true)); // 10列以下はOK
	}

	@Test
	public final void test列数の検証_以上の場合() {
		Csv csv = new Csv();

		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, -1), is(false));
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 0), is(false));
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 1), is(false));

		csv.setCell(0, 0, "x");
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, -1), is(true)); // 2列で-1列以上ある

		csv.setCell(0, 1, "y");
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 1), is(true)); // 2列で1列以上ある
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 2), is(true)); // 2列で2列以上ある
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 3), is(false)); // 2列で3列以上はない

		csv.setCell(1, 0, "z");
		csv.setCell(1, 1, "a");
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 2), is(true)); // 2列で2列以上ある

		csv.setCell(1, 2, "b");
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 1), is(true)); // 2列で1列以上ある
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 2), is(true)); // 2列で2列以上ある
		assertThat(CsvVerification.isColumnSizeMoreThanOrEqual(csv, 3), is(false)); // 2列で3列以上はない
	}

	@Test
	public final void test桁数チェック_下限() {
		Csv csv = new Csv();

		// データ未設定はfalse
		assertThat(CsvVerification.isColumnDigitLower(csv, -1, 0, true), is(false));
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 0, true), is(false));
		assertThat(CsvVerification.isColumnDigitLower(csv, 1, 0, true), is(false));
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, -1, true), is(false));
		assertThat(CsvVerification.isColumnDigitLower(csv, -1, 0, false), is(false));
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 0, false), is(false));
		assertThat(CsvVerification.isColumnDigitLower(csv, 1, 0, false), is(false));
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, -1, false), is(false));

		csv.setCell(0, 0, "01234");
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 4, true), is(true)); // 5byteに対して下限値4byteはOK
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 5, true), is(true)); // 5byteに対して下限値5byteはOK
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 6, true), is(false)); // 5byteに対して下限値6byteはNG

		csv.setCell(1, 0, "0"); // 1byteデータを設定(列の下限は1に)
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 5, true), is(false)); // 1byteに対して下限値5byteはNG
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 1, true), is(true)); // 1byteに対して下限値1byteはOK
		csv.setCell(2, 0, ""); // 空データを設定(列の下限は0に)
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 1, true), is(false)); // 空非許容の場合NG
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 1, false), is(true)); // 空許容の場合OK
		csv.setCell(3, 0, "0123456789"); // 10byteデータを設定(列の下限は0のまま)
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 1, true), is(false)); // 空非許容の場合NG
		assertThat(CsvVerification.isColumnDigitLower(csv, 0, 1, false), is(true)); // 空許容の場合OK
	}

	@Test
	public final void test桁数チェック_上限() {
		Csv csv = new Csv();

		// データ未設定はfalse
		assertThat(CsvVerification.isColumnDigitUpper(csv, -1, 0, true), is(false));
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 0, true), is(false));
		assertThat(CsvVerification.isColumnDigitUpper(csv, 1, 0, true), is(false));
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, -1, true), is(false));
		assertThat(CsvVerification.isColumnDigitUpper(csv, -1, 0, false), is(false));
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 0, false), is(false));
		assertThat(CsvVerification.isColumnDigitUpper(csv, 1, 0, false), is(false));
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, -1, false), is(false));

		csv.setCell(0, 0, "0123456789");
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 9, true), is(false)); // 10byteに対して上限9byteはNG
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 10, true), is(true)); // 10byteに対して上限10byteはOK

		csv.setCell(1, 0, "012345678901234"); // 15byteデータを設定(列の上限は15に)
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 10, true), is(false)); // 15byteに対して上限10byteはNG
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 15, true), is(true)); // 15byteに対して上限10byteはNG
		csv.setCell(2, 0, ""); // 空データを設定(列の上限は15のまま)
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 15, true), is(false)); // 空非許容の場合NG
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 15, false), is(true)); // 空許容の場合OK
		csv.setCell(3, 0, "0123456789"); // 10byteデータを設定(列の上限は15のまま)
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 15, true), is(false)); // 空非許容の場合NG
		assertThat(CsvVerification.isColumnDigitUpper(csv, 0, 15, false), is(true)); // 空許容の場合OK
	}

	@Test
	public final void test必須チェック() {
		Csv csv = new Csv();

		// データ未設定時はfalse
		assertThat(CsvVerification.isColumnMust(csv, -1), is(false));
		assertThat(CsvVerification.isColumnMust(csv, 0), is(false));
		assertThat(CsvVerification.isColumnMust(csv, 1), is(false));

		csv.setCell(0, 0, "");
		assertThat(CsvVerification.isColumnMust(csv, 0), is(false)); // 空文字はfalse

		csv.setCell(0, 0, "a");
		assertThat(CsvVerification.isColumnMust(csv, 0), is(true)); // データ有りでtrue

		// [a]
		// [][d]
		csv.setCell(1, 1, "d");
		assertThat(CsvVerification.isColumnMust(csv, 0), is(false)); // 2行目1列目が空
		assertThat(CsvVerification.isColumnMust(csv, 1), is(false)); // 1行目2列目が空

		// [a]
		// [c][d]
		csv.setCell(1, 0, "c");
		assertThat(CsvVerification.isColumnMust(csv, 0), is(true)); // 2行目1列目はデータ有りになった
		assertThat(CsvVerification.isColumnMust(csv, 1), is(false)); // 1行目2列目が空

		// [a][b]
		// [c][d]
		csv.setCell(0, 1, "b");
		assertThat(CsvVerification.isColumnMust(csv, 0), is(true)); // 2行目1列目はデータ有り
		assertThat(CsvVerification.isColumnMust(csv, 1), is(true)); // 1行目2列目はデータ有りになった
	}

	@Test
	public final void test型チェック() {
		Csv csv = new Csv();

		// データ未設定時はfalse
		assertThat(CsvVerification.isColumnType(csv, -1, true, true, true), is(false));
		assertThat(CsvVerification.isColumnType(csv, 0, false, false, false), is(false));
		assertThat(CsvVerification.isColumnType(csv, 1, true, true, false), is(false));

		// 英字
		csv.setCell(0, 0, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		assertThat(CsvVerification.isColumnType(csv, 0, true, false, true), is(true)); // 英字OK
		assertThat(CsvVerification.isColumnType(csv, 0, false, true, true), is(false)); // 数字NG
		assertThat(CsvVerification.isColumnType(csv, 0, true, true, true), is(true)); // 英数字OK

		// 数字
		csv.setCell(0, 1, "0123456789");
		assertThat(CsvVerification.isColumnType(csv, 1, true, false, true), is(false)); // 英字NG
		assertThat(CsvVerification.isColumnType(csv, 1, false, true, true), is(true)); // 数字OK
		assertThat(CsvVerification.isColumnType(csv, 1, true, true, true), is(true)); // 英数字OK

		// 英数字
		csv.setCell(0, 2, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		assertThat(CsvVerification.isColumnType(csv, 2, true, false, true), is(false)); // 英字NG
		assertThat(CsvVerification.isColumnType(csv, 2, false, true, true), is(false)); // 数字NG
		assertThat(CsvVerification.isColumnType(csv, 2, true, true, true), is(true)); // 英数字OK
		csv.setCell(1, 2, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		csv.setCell(2, 2, "0123456789");
		assertThat(CsvVerification.isColumnType(csv, 2, true, false, true), is(false)); // 英字NG
		assertThat(CsvVerification.isColumnType(csv, 2, false, true, true), is(false)); // 数字NG
		assertThat(CsvVerification.isColumnType(csv, 2, true, true, true), is(true)); // 英数字OK

		// 英数字以外
		csv.setCell(0, 3, "\"!#$&'()-=^~\\|@`[{;+:*]},<.>/?_");
		assertThat(CsvVerification.isColumnType(csv, 3, true, false, true), is(false)); // 英字NG
		assertThat(CsvVerification.isColumnType(csv, 3, false, true, true), is(false)); // 数字NG
		assertThat(CsvVerification.isColumnType(csv, 3, true, true, true), is(false)); // 英数字NG
		// 英数字以外(全角)
		csv.setCell(0, 4, "あいうえお");
		assertThat(CsvVerification.isColumnType(csv, 4, true, false, true), is(false)); // 英字NG
		assertThat(CsvVerification.isColumnType(csv, 4, false, true, true), is(false)); // 数字NG
		assertThat(CsvVerification.isColumnType(csv, 4, true, true, true), is(false)); // 英数字NG
	}
}
