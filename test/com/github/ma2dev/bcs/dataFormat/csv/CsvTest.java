package com.github.ma2dev.bcs.dataFormat.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import junitx.framework.FileAssert;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.ma2dev.bcs.dataFormat.IData;

public class CsvTest {
	private String outputfile = "dat/test/bcs/callInfo/testCsv_out.csv";

	@Before
	public void setUp() throws Exception {
		// outputファイルがある場合に削除
		File file = new File(outputfile);
		if (file.exists()) {
			if (file.delete()) {
				;
			} else {
				System.out.println("ファイル削除に失敗しました");
			}
		}
	}

	@Test
	public final void testCsv() {
		Csv csv = new Csv();

		String file1 = new String("dat/test/bcs/callInfo/template_callInfo.csv");
		String file2 = new String(outputfile);

		try {
			Reader f1 = new FileReader(file1);
			csv.read(f1);
			f1.close();

			IData cell = csv.getCell(0, 0);
			assertEquals("09011112222", cell.toString());

			Writer w2 = new FileWriter(file2);
			csv.write(w2);
			w2.write("\n"); // 改行が入らないので追加
			w2.close();

			// commons-ioを使用する例
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

	@Test
	public final void testデータが無い場合() {
		Csv csv = new Csv();

		// nullが返却されることを期待
		assertNull(csv.getCell(0, 0));
	}

	@Test
	public final void testデータ設定() {
		Csv csv = new Csv();

		// []
		// ↓
		// [hoge]
		csv.setCell(0, 0, "hoge");
		assertThat((String) csv.getCell(0, 0).getData(), is("hoge"));

		// [hoge]
		// ↓
		// [hoge]
		// []
		// [][][piyo]
		csv.setCell(2, 2, "piyo");
		assertThat((String) csv.getCell(2, 2).getData(), is("piyo"));

		// [hoge]
		// []
		// [][][piyo]
		assertNull(csv.getCell(1, 1));

		// [hoge]
		// []
		// [][][piyo]
		// ↓
		// [hoge]
		// [][][xxx]
		// [][][piyo]
		csv.setCell(1, 2, "xxx");
		assertThat((String) csv.getCell(1, 2).getData(), is("xxx"));
		assertThat(csv.toString(), is("hoge\n,,xxx\n,,piyo"));

		// [hoge]
		// [][][xxx]
		// [][][piyo]
		// ↓
		// [hoge]
		// [][][yyy]
		// [][][piyo]
		csv.setCell(1, 2, "yyy");
		assertThat((String) csv.getCell(1, 2).getData(), is("yyy"));
		assertThat(csv.toString(), is("hoge\n,,yyy\n,,piyo"));
	}

	@After
	public void tearDown() throws Exception {
		// outputファイルがある場合に削除
		File file = new File(outputfile);
		if (file.exists()) {
			if (file.exists()) {
				if (file.delete()) {
					;
				} else {
					System.out.println("ファイル削除に失敗しました");
				}
			}
		}
	}
}
