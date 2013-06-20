package com.github.ma2dev.bcs.dataFormat.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class CsvVerificationTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public final void test有効データ保持有無の確認() {

		Csv csv = new Csv();
		CsvVerification verification = new CsvVerification(csv);
		assertThat(verification.isConstructed(), is(false));

		int row = 0;
		int column = 0;
		csv.setCell(row, column, "ABC");
		assertThat(verification.isConstructed(), is(true));
	}

}
