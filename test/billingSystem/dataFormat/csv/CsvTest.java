/**
 *
 */
package billingSystem.dataFormat.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import junitx.framework.FileAssert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * @author ma2dev
 *
 */
public class CsvTest extends Csv {

	/**
	 * {@link billingSystem.dataFormat.csv.Csv#Csv()} のためのテスト・メソッド。
	 */
	@Test
	public final void testCsv() {
		Csv csv = new Csv();

		String file1 = new String("dat/callInfo/20130421_callInfor.csv");
		String file2 = new String("dat/callInfo/out.csv");

		try {
			csv.readFrom(new FileReader(file1));

			Cell cell = csv.getCell(0, 0);
			assertEquals("09076228838", cell.toString());

			csv.writeTo(new FileWriter(file2));

			// commons-ioを使用する例
			// TODO ファイル差分時のExceptionに対するTest
			assertTrue("The files differ!", FileUtils.contentEquals(new File(file1), new File(file2)));

			// JUnit-Addonsを使用する例
			FileAssert.assertEquals(new File(file1), new File(file2));

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO 自動生成された catch ブロック
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
