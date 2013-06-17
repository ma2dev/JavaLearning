/**
 *
 */
package billingSystem.dataFormat.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import junitx.framework.FileAssert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import billingSystem.dataFormat.IData;

/**
 * @author ma2dev
 *
 */
public class CsvTest {

	/**
	 * {@link billingSystem.dataFormat.csv.Csv#Csv()} のためのテスト・メソッド。
	 */
	@Test
	public final void testCsv() {
		Csv csv = new Csv();

		String file1 = new String("dat/test/billingSystem/callInfo/template_callInfo.csv");
		String file2 = new String("dat/test/billingSystem/callInfo/testCsv_out.csv");

		try {
			csv.read(new FileReader(file1));

			IData cell = csv.getCell(0, 0);
			assertEquals("09076228838", cell.toString());

			csv.write(new FileWriter(file2));

			// commons-ioを使用する例
			// TODO ファイル差分時のExceptionに対するTest
			assertTrue("The files differ!", FileUtils.contentEquals(new File(file1), new File(file2)));

			// JUnit-Addonsを使用する例
			FileAssert.assertEquals(new File(file1), new File(file2));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testコンストラクタ() {
		Csv csv = new Csv();

		assertNotNull(csv);
		assertNotNull(csv.getConfiguration());
		assertEquals(0, csv.getRowSize());

		csv = new Csv(new CsvConfiguration());
		assertNotNull(csv);
		assertNotNull(csv.getConfiguration());
		assertEquals(0, csv.getRowSize());

	}
}
