package com.github.ma2dev.bcs.subscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.ma2dev.bcs.dataFormat.IllegalDataFormatException;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;

public class SubscriberManagerTest {

	private String outputfile = "dat/test/bcs/output/SubscriberManagerTest_out.csv";

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
	public final void testファイル読み込み() {
		String subscriberInfoFile = "dat/test/bcs/serviceInfo/template_serviceInfo.csv";
		String callInfoFile = "dat/test/bcs/callInfo/template_callInfo.csv";
		String serviceInfoFile = "dat/test/bcs/serviceInfo/template_serviceInfo.csv";
		String configFile = "dat/test/bcs/conf/template.properties";

		SubscriberManager manager = null;
		try {
			manager = new SubscriberManager(configFile, subscriberInfoFile, callInfoFile, serviceInfoFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalDataFormatException e) {
			e.printStackTrace();
		}

		assertNotNull(manager);
	}

	@Test
	public final void test明細出力() {
		String subscriberInfoFile = "dat/test/bcs/serviceInfo/test_oneTelNum_serviceInfo.csv";
		String callInfoFile = "dat/test/bcs/callInfo/tset_oneTelNum_callInfo.csv";
		String serviceInfoFile = "dat/test/bcs/serviceInfo/test_oneTelNum_serviceInfo.csv";
		String configFile = "dat/test/bcs/conf/template.properties";

		SubscriberManager manager = null;
		try {
			manager = new SubscriberManager(configFile, subscriberInfoFile, callInfoFile, serviceInfoFile);
			manager.execute(outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalDataFormatException e) {
			e.printStackTrace();
		}

		Csv csv = new Csv();
		try {
			Reader csvReader = new FileReader(outputfile);
			csv.read(csvReader);
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertThat((String) csv.getCell(0, 0).getData(), is("09011112222"));
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
