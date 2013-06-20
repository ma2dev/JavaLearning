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
}
