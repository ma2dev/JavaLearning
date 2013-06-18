package com.github.ma2dev.bcs.dataFormat.csv;

import static org.junit.Assert.*;

import org.junit.Test;

public class CsvConfigurationTest {

	@Test
	public final void testCsvConfigurationデフォルトコンストラクタ() {
		CsvConfiguration csvConfiguration = new CsvConfiguration();
		assertEquals(",", csvConfiguration.getDelimiter());
	}

	@Test
	public final void testCsvConfigurationStringコンストラクタ() {
		CsvConfiguration csvConfiguration = new CsvConfiguration("XYZ");
		assertEquals("XYZ", csvConfiguration.getDelimiter());
		assertFalse(csvConfiguration.getDelimiter().equals("ABC"));
	}

	@Test
	public final void testデリミタのアクセッサ() {
		CsvConfiguration csvConfiguration = new CsvConfiguration();

		assertEquals(",", csvConfiguration.getDelimiter());

		csvConfiguration.setDelimiter("XYZ");
		assertEquals("XYZ", csvConfiguration.getDelimiter());
	}

}
