package com.github.ma2dev.bcs.dataFormat.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class CsvVerificationTest {

	@Test
	public final void test有効データ保持有無の検証() {

		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);
		assertThat(verification.isConstructed(), is(false));

		int row = 0;
		int column = 0;
		csv.setCell(row, column, "ABC");
		assertThat(verification.isConstructed(), is(true));
	}

	@Test
	public final void test行数の検証() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isRowSize(-1), is(false));
		assertThat(verification.isRowSize(0), is(true));
		csv.setCell(0, 0, "ABC");
		assertThat(verification.isRowSize(0), is(false));
		assertThat(verification.isRowSize(1), is(true));
		assertThat(verification.isRowSize(2), is(false));
		csv.setCell(1, 0, "ABC");
		assertThat(verification.isRowSize(1), is(false));
		assertThat(verification.isRowSize(2), is(true));
		assertThat(verification.isRowSize(3), is(false));
	}

	@Test
	public final void test行数の検証_より大きい場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isRowSizeMoreThan(-1), is(true));
		assertThat(verification.isRowSizeMoreThan(0), is(false));
		csv.setCell(0, 0, "ABC");
		assertThat(verification.isRowSizeMoreThan(0), is(true));
		assertThat(verification.isRowSizeMoreThan(1), is(false));
		csv.setCell(1, 0, "ABC");
		assertThat(verification.isRowSizeMoreThan(1), is(true));
		assertThat(verification.isRowSizeMoreThan(2), is(false));
	}

	@Test
	public final void test行数の検証_以上の場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isRowSizeMoreThanOrEqual(-1), is(true));
		assertThat(verification.isRowSizeMoreThanOrEqual(0), is(true));
		assertThat(verification.isRowSizeMoreThanOrEqual(1), is(false));
		csv.setCell(0, 0, "ABC");
		assertThat(verification.isRowSizeMoreThanOrEqual(0), is(true));
		assertThat(verification.isRowSizeMoreThanOrEqual(1), is(true));
		assertThat(verification.isRowSizeMoreThanOrEqual(2), is(false));
		csv.setCell(1, 0, "ABC");
		assertThat(verification.isRowSizeMoreThanOrEqual(1), is(true));
		assertThat(verification.isRowSizeMoreThanOrEqual(2), is(true));
		assertThat(verification.isRowSizeMoreThanOrEqual(3), is(false));
	}

	@Test
	public final void test行数の検証_以下の場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isRowSizeLessThanOrEqual(-1), is(false));
		assertThat(verification.isRowSizeLessThanOrEqual(0), is(true)); // 空なので0行以下はOK
		assertThat(verification.isRowSizeLessThanOrEqual(1), is(true)); // 空なので1行以下はOK

		csv.setCell(0, 0, "x");
		assertThat(verification.isRowSizeLessThanOrEqual(0), is(false)); // 1行なので0行以下はNG
		assertThat(verification.isRowSizeLessThanOrEqual(1), is(true)); // 1行なので1行以下はOK

		csv.setCell(9, 9, "x");
		assertThat(verification.isRowSizeLessThanOrEqual(9), is(false)); // 10行なので9行以下はNG
		assertThat(verification.isRowSizeLessThanOrEqual(10), is(true)); // 10行なので10行以下はOK
	}

	@Test
	public final void test列数の検証() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isColumnSize(-1), is(false));
		assertThat(verification.isColumnSize(0), is(false));
		assertThat(verification.isColumnSize(1), is(false));

		csv.setCell(0, 0, "x");
		csv.setCell(0, 1, "y");
		assertThat(verification.isColumnSize(2), is(true));
		csv.setCell(1, 1, "z");
		assertThat(verification.isColumnSize(2), is(true));
		csv.setCell(2, 3, "a");
		assertThat(verification.isColumnSize(2), is(false));

		assertThat(verification.isColumnSize(-1), is(false));
	}

	@Test
	public final void test指定行に対する列数の検証() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isColumnSize(0, -1), is(false));
		assertThat(verification.isColumnSize(1, 0), is(false));
		assertThat(verification.isColumnSize(2, 1), is(false));

		csv.setCell(0, 0, "x");
		csv.setCell(0, 1, "y");
		assertThat(verification.isColumnSize(0, 2), is(true)); // 0行目には2列分あるので
		csv.setCell(1, 1, "z");
		assertThat(verification.isColumnSize(1, 2), is(true)); // 1行目には2列分あるので
		csv.setCell(2, 3, "a");
		assertThat(verification.isColumnSize(2, 3), is(false));// 2行目には4列分あるが検証は3列分のため

		assertThat(verification.isColumnSize(2, 4), is(true)); // 2行目には4列分あり検証は4列分のため
		assertThat(verification.isColumnSize(3, 0), is(false));// 3行目にはデータが無いが3行目を検証しているため
		csv.setCell(3, 0, "b");
		assertThat(verification.isColumnSize(3, 0), is(false));// 3行目には1列分あり検証は0列分のため
		assertThat(verification.isColumnSize(3, 1), is(true)); // 3行目には1列分あり検証は1列分のため
	}

	@Test
	public final void test列数の検証_より大きい場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isColumnSizeMoreThan(-1), is(false));
		assertThat(verification.isColumnSizeMoreThan(0), is(false));
		assertThat(verification.isColumnSizeMoreThan(1), is(false));

		csv.setCell(0, 0, "x");
		csv.setCell(0, 1, "y");
		assertThat(verification.isColumnSizeMoreThan(2), is(false)); // 2列で2列よりも大きくはない
		assertThat(verification.isColumnSizeMoreThan(1), is(true)); // 2列で1列よりも大きい
		csv.setCell(1, 0, "z");
		assertThat(verification.isColumnSizeMoreThan(1), is(false)); // 2行目が1列で1列よりも大きくはない
		csv.setCell(1, 1, "a");
		assertThat(verification.isColumnSizeMoreThan(1), is(true)); // 全てが2列で1列よりも大きい
		csv.setCell(1, 2, "b");
		assertThat(verification.isColumnSizeMoreThan(1), is(true)); // 全てが2列で1列よりも大きい
		assertThat(verification.isColumnSizeMoreThan(2), is(false)); // 1行目が全てが2列で2列よりも大きくない
	}

	@Test
	public final void test列数の検証_以下の場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		// データ未設定はfalse
		assertThat(verification.isColumnSizeLessThanOrEqual(-1), is(false));
		assertThat(verification.isColumnSizeLessThanOrEqual(0), is(false));
		assertThat(verification.isColumnSizeLessThanOrEqual(1), is(false));

		csv.setCell(0, 0, "x"); // 1列
		assertThat(verification.isColumnSizeLessThanOrEqual(0), is(false)); // 0列以下はNG
		assertThat(verification.isColumnSizeLessThanOrEqual(1), is(true)); // 1列以下はOK

		csv.setCell(0, 9, "a"); // 10列
		assertThat(verification.isColumnSizeLessThanOrEqual(9), is(false)); // 9列以下はNG
		assertThat(verification.isColumnSizeLessThanOrEqual(10), is(true)); // 10列以下はOK
		csv.setCell(1, 4, "b"); // 5列
		assertThat(verification.isColumnSizeLessThanOrEqual(4), is(false)); // 4列以下はNG
		assertThat(verification.isColumnSizeLessThanOrEqual(5), is(false)); // 5列以下はNG
		assertThat(verification.isColumnSizeLessThanOrEqual(10), is(true)); // 10列以下はOK
	}

	@Test
	public final void test指定行に対する列数の検証_より大きい場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isColumnSizeMoreThan(0, -1), is(false));
		assertThat(verification.isColumnSizeMoreThan(1, 0), is(false));
		assertThat(verification.isColumnSizeMoreThan(2, 1), is(false));

		csv.setCell(0, 0, "x");
		csv.setCell(0, 1, "y");
		assertThat(verification.isColumnSizeMoreThan(0, 2), is(false)); // 検証対象は2列で2列よりも大きくはない
		csv.setCell(1, 1, "z");
		assertThat(verification.isColumnSizeMoreThan(1, 1), is(true)); // 検証対象は2列で1列よりも大きい
		csv.setCell(2, 3, "a");
		assertThat(verification.isColumnSizeMoreThan(2, 3), is(true));// 検証対象は4列分あり3列よりも大きい

		assertThat(verification.isColumnSizeMoreThan(3, 0), is(false));// 3行目にはデータが無いが3行目を検証しているため
		csv.setCell(3, 0, "b");
		assertThat(verification.isColumnSizeMoreThan(3, 0), is(true));// 検証対象は1列分あり0列よりも大きい
	}

	@Test
	public final void test列数の検証_以上の場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isColumnSizeMoreThanOrEqual(-1), is(false));
		assertThat(verification.isColumnSizeMoreThanOrEqual(0), is(false));
		assertThat(verification.isColumnSizeMoreThanOrEqual(1), is(false));

		csv.setCell(0, 0, "x");
		assertThat(verification.isColumnSizeMoreThanOrEqual(-1), is(true)); // 2列で-1列以上ある

		csv.setCell(0, 1, "y");
		assertThat(verification.isColumnSizeMoreThanOrEqual(1), is(true)); // 2列で1列以上ある
		assertThat(verification.isColumnSizeMoreThanOrEqual(2), is(true)); // 2列で2列以上ある
		assertThat(verification.isColumnSizeMoreThanOrEqual(3), is(false)); // 2列で3列以上はない

		csv.setCell(1, 0, "z");
		csv.setCell(1, 1, "a");
		assertThat(verification.isColumnSizeMoreThanOrEqual(2), is(true)); // 2列で2列以上ある

		csv.setCell(1, 2, "b");
		assertThat(verification.isColumnSizeMoreThanOrEqual(1), is(true)); // 2列で1列以上ある
		assertThat(verification.isColumnSizeMoreThanOrEqual(2), is(true)); // 2列で2列以上ある
		assertThat(verification.isColumnSizeMoreThanOrEqual(3), is(false)); // 2列で3列以上はない
	}

	@Test
	public final void test指定行に対する列数の検証_以上の場合() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		assertThat(verification.isColumnSizeMoreThanOrEqual(0, -1), is(false));
		assertThat(verification.isColumnSizeMoreThanOrEqual(1, 0), is(false));
		assertThat(verification.isColumnSizeMoreThanOrEqual(2, 1), is(false));

		csv.setCell(0, 0, "x");
		csv.setCell(0, 1, "y");
		assertThat(verification.isColumnSizeMoreThanOrEqual(0, 1), is(true)); // 検証対象は2列で1列以上
		assertThat(verification.isColumnSizeMoreThanOrEqual(0, 2), is(true)); // 検証対象は2列で2列以上
		assertThat(verification.isColumnSizeMoreThanOrEqual(0, 3), is(false)); // 検証対象は2列で3列以上ではない

		csv.setCell(2, 3, "a");
		assertThat(verification.isColumnSizeMoreThanOrEqual(2, 3), is(true));// 検証対象は4列分あり3列以上
		assertThat(verification.isColumnSizeMoreThanOrEqual(2, 4), is(true));// 検証対象は4列分あり4列以上
		assertThat(verification.isColumnSizeMoreThanOrEqual(2, 5), is(false));// 検証対象は4列分あり5列以上ではない

		assertThat(verification.isColumnSizeMoreThanOrEqual(3, 0), is(false));// 3行目にはデータが無いが3行目を検証しているため
		csv.setCell(3, 0, "b");
		assertThat(verification.isColumnSizeMoreThanOrEqual(3, 1), is(true));// 検証対象は1列分あり1列以上
	}

	@Test
	public final void test桁数チェック() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		// データ未設定はfalse
		assertThat(verification.isDigit(0, 0, 0), is(false));
		assertThat(verification.isDigit(0, 0, -1), is(false));
		assertThat(verification.isDigit(0, 0, 1), is(false));
		assertThat(verification.isDigit(1, 1, 0), is(false));
		assertThat(verification.isDigit(-1, -1, 0), is(false));
		assertThat(verification.isDigit(1, 1, 1), is(false));

		csv.setCell(1, 1, "0123456789");
		assertThat(verification.isDigit(1, 1, 9), is(false));
		assertThat(verification.isDigit(1, 1, 10), is(true));
		assertThat(verification.isDigit(1, 1, 11), is(false));

		csv.setCell(1, 1, "あ"); // UTF-8で"あ"は、0xe38182 で3byte
		assertThat(verification.isDigit(1, 1, 3), is(true));

		csv.setCell(1, 1, "あa"); // UTF-8で"あ"は、0xe38182 で3byte
		assertThat(verification.isDigit(1, 1, 4), is(true));

		assertThat(verification.isDigit(1, 0, 0), is(true)); // 空文字
	}

	@Test
	public final void test桁数チェック_下限() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		// データ未設定はfalse
		assertThat(verification.isColumnDigitLower(-1, 0, true), is(false));
		assertThat(verification.isColumnDigitLower(0, 0, true), is(false));
		assertThat(verification.isColumnDigitLower(1, 0, true), is(false));
		assertThat(verification.isColumnDigitLower(0, -1, true), is(false));
		assertThat(verification.isColumnDigitLower(-1, 0, false), is(false));
		assertThat(verification.isColumnDigitLower(0, 0, false), is(false));
		assertThat(verification.isColumnDigitLower(1, 0, false), is(false));
		assertThat(verification.isColumnDigitLower(0, -1, false), is(false));

		csv.setCell(0, 0, "01234");
		assertThat(verification.isColumnDigitLower(0, 4, true), is(true)); // 5byteに対して下限値4byteはOK
		assertThat(verification.isColumnDigitLower(0, 5, true), is(true)); // 5byteに対して下限値5byteはOK
		assertThat(verification.isColumnDigitLower(0, 6, true), is(false)); // 5byteに対して下限値6byteはNG

		csv.setCell(1, 0, "0"); // 1byteデータを設定(列の下限は1に)
		assertThat(verification.isColumnDigitLower(0, 5, true), is(false)); // 1byteに対して下限値5byteはNG
		assertThat(verification.isColumnDigitLower(0, 1, true), is(true)); // 1byteに対して下限値1byteはOK
		csv.setCell(2, 0, ""); // 空データを設定(列の下限は0に)
		assertThat(verification.isColumnDigitLower(0, 1, true), is(false)); // 空非許容の場合NG
		assertThat(verification.isColumnDigitLower(0, 1, false), is(true)); // 空許容の場合OK
		csv.setCell(3, 0, "0123456789"); // 10byteデータを設定(列の下限は0のまま)
		assertThat(verification.isColumnDigitLower(0, 1, true), is(false)); // 空非許容の場合NG
		assertThat(verification.isColumnDigitLower(0, 1, false), is(true)); // 空許容の場合OK
	}

	@Test
	public final void test桁数チェック_上限() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		// データ未設定はfalse
		assertThat(verification.isColumnDigitUpper(-1, 0, true), is(false));
		assertThat(verification.isColumnDigitUpper(0, 0, true), is(false));
		assertThat(verification.isColumnDigitUpper(1, 0, true), is(false));
		assertThat(verification.isColumnDigitUpper(0, -1, true), is(false));
		assertThat(verification.isColumnDigitUpper(-1, 0, false), is(false));
		assertThat(verification.isColumnDigitUpper(0, 0, false), is(false));
		assertThat(verification.isColumnDigitUpper(1, 0, false), is(false));
		assertThat(verification.isColumnDigitUpper(0, -1, false), is(false));

		csv.setCell(0, 0, "0123456789");
		assertThat(verification.isColumnDigitUpper(0, 9, true), is(false)); // 10byteに対して上限9byteはNG
		assertThat(verification.isColumnDigitUpper(0, 10, true), is(true)); // 10byteに対して上限10byteはOK

		csv.setCell(1, 0, "012345678901234"); // 15byteデータを設定(列の上限は15に)
		assertThat(verification.isColumnDigitUpper(0, 10, true), is(false)); // 15byteに対して上限10byteはNG
		assertThat(verification.isColumnDigitUpper(0, 15, true), is(true)); // 15byteに対して上限10byteはNG
		csv.setCell(2, 0, ""); // 空データを設定(列の上限は15のまま)
		assertThat(verification.isColumnDigitUpper(0, 15, true), is(false)); // 空非許容の場合NG
		assertThat(verification.isColumnDigitUpper(0, 15, false), is(true)); // 空許容の場合OK
		csv.setCell(3, 0, "0123456789"); // 10byteデータを設定(列の上限は15のまま)
		assertThat(verification.isColumnDigitUpper(0, 15, true), is(false)); // 空非許容の場合NG
		assertThat(verification.isColumnDigitUpper(0, 15, false), is(true)); // 空許容の場合OK
	}

	@Test
	public final void test必須チェック() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		// データ未設定時はfalse
		assertThat(verification.isColumnMust(-1), is(false));
		assertThat(verification.isColumnMust(0), is(false));
		assertThat(verification.isColumnMust(1), is(false));

		csv.setCell(0, 0, "");
		assertThat(verification.isColumnMust(0), is(false)); // 空文字はfalse

		csv.setCell(0, 0, "a");
		assertThat(verification.isColumnMust(0), is(true)); // データ有りでtrue

		// [a]
		// [][d]
		csv.setCell(1, 1, "d");
		assertThat(verification.isColumnMust(0), is(false)); // 2行目1列目が空
		assertThat(verification.isColumnMust(1), is(false)); // 1行目2列目が空

		// [a]
		// [c][d]
		csv.setCell(1, 0, "c");
		assertThat(verification.isColumnMust(0), is(true)); // 2行目1列目はデータ有りになった
		assertThat(verification.isColumnMust(1), is(false)); // 1行目2列目が空

		// [a][b]
		// [c][d]
		csv.setCell(0, 1, "b");
		assertThat(verification.isColumnMust(0), is(true)); // 2行目1列目はデータ有り
		assertThat(verification.isColumnMust(1), is(true)); // 1行目2列目はデータ有りになった
	}

	@Test
	public final void test型チェック() {
		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);

		// データ未設定時はfalse
		assertThat(verification.isColumnType(-1, true, true, true), is(false));
		assertThat(verification.isColumnType(0, false, false, false), is(false));
		assertThat(verification.isColumnType(1, true, true, false), is(false));

		// 英字
		csv.setCell(0, 0, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		assertThat(verification.isColumnType(0, true, false, true), is(true)); // 英字OK
		assertThat(verification.isColumnType(0, false, true, true), is(false)); // 数字NG
		assertThat(verification.isColumnType(0, true, true, true), is(true)); // 英数字OK

		// 数字
		csv.setCell(0, 1, "0123456789");
		assertThat(verification.isColumnType(1, true, false, true), is(false)); // 英字NG
		assertThat(verification.isColumnType(1, false, true, true), is(true)); // 数字OK
		assertThat(verification.isColumnType(1, true, true, true), is(true)); // 英数字OK

		// 英数字
		csv.setCell(0, 2, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		assertThat(verification.isColumnType(2, true, false, true), is(false)); // 英字NG
		assertThat(verification.isColumnType(2, false, true, true), is(false)); // 数字NG
		assertThat(verification.isColumnType(2, true, true, true), is(true)); // 英数字OK
		csv.setCell(1, 2, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		csv.setCell(2, 2, "0123456789");
		assertThat(verification.isColumnType(2, true, false, true), is(false)); // 英字NG
		assertThat(verification.isColumnType(2, false, true, true), is(false)); // 数字NG
		assertThat(verification.isColumnType(2, true, true, true), is(true)); // 英数字OK

		// 英数字以外
		csv.setCell(0, 3, "\"!#$&'()-=^~\\|@`[{;+:*]},<.>/?_");
		assertThat(verification.isColumnType(3, true, false, true), is(false)); // 英字NG
		assertThat(verification.isColumnType(3, false, true, true), is(false)); // 数字NG
		assertThat(verification.isColumnType(3, true, true, true), is(false)); // 英数字NG
		// 英数字以外(全角)
		csv.setCell(0, 4, "あいうえお");
		assertThat(verification.isColumnType(4, true, false, true), is(false)); // 英字NG
		assertThat(verification.isColumnType(4, false, true, true), is(false)); // 数字NG
		assertThat(verification.isColumnType(4, true, true, true), is(false)); // 英数字NG
	}
}
