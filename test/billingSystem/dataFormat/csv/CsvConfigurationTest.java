/**
 *
 */
package billingSystem.dataFormat.csv;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author ma2dev
 *
 */
public class CsvConfigurationTest extends CsvConfiguration {

	/**
	 * {@link billingSystem.dataFormat.csv.CsvConfiguration#CsvConfiguration()}
	 * のためのテスト・メソッド。
	 */
	@Test
	public final void testCsvConfigurationデフォルトコンストラクタ() {
		CsvConfiguration csvConfiguration = new CsvConfiguration();
		assertEquals(csvConfiguration.getDelimiter(), ",");
	}

	/**
	 * {@link billingSystem.dataFormat.csv.CsvConfiguration#CsvConfiguration(java.lang.String)}
	 * のためのテスト・メソッド。
	 */
	@Test
	public final void testCsvConfigurationStringコンストラクタ() {
		CsvConfiguration csvConfiguration = new CsvConfiguration("XYZ");
		assertEquals(csvConfiguration.getDelimiter(), "XYZ");
		assertFalse(csvConfiguration.getDelimiter().equals("ABC"));
	}

	/**
	 * {@link billingSystem.dataFormat.csv.CsvConfiguration#getDelimiter()
	 * /#setDelimiter(String)} のためのテスト・メソッド。
	 */
	@Test
	public final void testデリミタのアクセッサ() {
		CsvConfiguration csvConfiguration = new CsvConfiguration();

		assertEquals(csvConfiguration.getDelimiter(), ",");

		csvConfiguration.setDelimiter("XYZ");
		assertEquals(csvConfiguration.getDelimiter(), "XYZ");
	}

}
