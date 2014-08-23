package com.github.ma2dev.bcs.subscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;

import ma2dev.bcs.dataFormat.csv.Csv;
import ma2dev.bcs.subscriber.SubscriberManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

	@Test
	public final void test通話無し正常切断パターン() {
		Csv serviceInfoCsv = new Csv();
		serviceInfoCsv.setCell(0, 0, "0123456789");
		serviceInfoCsv.setCell(0, 1, "0");
		serviceInfoCsv.setCell(0, 2, "0");

		Csv callInfoCsv = new Csv();
		callInfoCsv.setCell(0, 0, "0123456789");
		callInfoCsv.setCell(0, 1, "0987654321");
		callInfoCsv.setCell(0, 2, "");
		callInfoCsv.setCell(0, 3, "");
		callInfoCsv.setCell(0, 4, "0");

		String configFile = "dat/test/bcs/conf/template.properties";

		try {
			File serviceInfoFile = File.createTempFile("test_serviceInfo", ".csv");
			serviceInfoFile.deleteOnExit();
			Writer serviceInfoWriter = new FileWriter(serviceInfoFile);
			serviceInfoCsv.write(serviceInfoWriter);
			serviceInfoWriter.close();

			File callInfoFile = File.createTempFile("test_callInfo", ".csv");
			callInfoFile.deleteOnExit();
			Writer callInfoWriter = new FileWriter(callInfoFile);
			callInfoCsv.write(callInfoWriter);
			callInfoWriter.close();

			SubscriberManager manager = new SubscriberManager(configFile, serviceInfoFile.getPath(),
					callInfoFile.getPath(), serviceInfoFile.getPath());

			File outputFile = File.createTempFile("test_noncallnormal", ".csv");
			outputFile.deleteOnExit();
			manager.execute(outputFile.getPath());

			Csv resultCsv = new Csv();
			resultCsv.read(new FileReader(outputFile.getPath()));

			assertThat((String) resultCsv.getCell(0, 0).getData(), is("0123456789"));
			assertThat((String) resultCsv.getCell(0, 2).getData(), is("0"));
			assertThat((String) resultCsv.getCell(0, 3).getData(), is("0"));
			assertThat((String) resultCsv.getCell(0, 4).getData(), is("0"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void test無料通話パターン() {
		Csv serviceInfoCsv = new Csv();
		serviceInfoCsv.setCell(0, 0, "0123456789");
		serviceInfoCsv.setCell(0, 1, "0");
		serviceInfoCsv.setCell(0, 2, "0");
		serviceInfoCsv.setCell(0, 3, "0987654321");

		Csv callInfoCsv = new Csv();
		callInfoCsv.setCell(0, 0, "0123456789");
		callInfoCsv.setCell(0, 1, "0987654321");
		callInfoCsv.setCell(0, 2, "20130705000000");
		callInfoCsv.setCell(0, 3, "20130705000100");
		callInfoCsv.setCell(0, 4, "0");

		String configFile = "dat/test/bcs/conf/template.properties";

		try {
			File serviceInfoFile = File.createTempFile("test_serviceInfo", ".csv");
			serviceInfoFile.deleteOnExit();
			Writer serviceInfoWriter = new FileWriter(serviceInfoFile);
			serviceInfoCsv.write(serviceInfoWriter);
			serviceInfoWriter.close();

			File callInfoFile = File.createTempFile("test_callInfo", ".csv");
			callInfoFile.deleteOnExit();
			Writer callInfoWriter = new FileWriter(callInfoFile);
			callInfoCsv.write(callInfoWriter);
			callInfoWriter.close();

			SubscriberManager manager = new SubscriberManager(configFile, serviceInfoFile.getPath(),
					callInfoFile.getPath(), serviceInfoFile.getPath());

			File outputFile = File.createTempFile("test_noncallnormal", ".csv");
			outputFile.deleteOnExit();
			manager.execute(outputFile.getPath());

			Csv resultCsv = new Csv();
			resultCsv.read(new FileReader(outputFile.getPath()));

			assertThat((String) resultCsv.getCell(0, 0).getData(), is("0123456789"));
			assertThat((String) resultCsv.getCell(0, 2).getData(), is("0"));
			assertThat((String) resultCsv.getCell(0, 3).getData(), is("0"));
			assertThat((String) resultCsv.getCell(0, 4).getData(), is("0"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
