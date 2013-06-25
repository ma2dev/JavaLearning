package com.github.ma2dev.bcs.dataFormat.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.junit.Test;

import com.github.ma2dev.bcs.dataFormat.csv.CsvVerificationProperties;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;

public class CsvVerificationPropertiesTest {

	@Test
	public final void test正常ファイルの確認() {
		Reader verificationFile = null;
		Reader csvReader = null;
		Csv csv = new Csv();
		try {
			verificationFile = new FileReader("dat/test/bcs/verification/callInfo_verification.properties");
			csvReader = new FileReader("dat/test/bcs/callInfo/template_callInfo.csv");
			csv.read(csvReader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean ret = false;
		try {
			ret = CsvVerificationProperties.verificateCsv(csv, verificationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			verificationFile.close();
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertThat(ret, is(true));
	}

}
