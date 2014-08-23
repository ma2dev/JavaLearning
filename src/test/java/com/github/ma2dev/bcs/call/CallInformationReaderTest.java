package com.github.ma2dev.bcs.call;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.List;

import ma2dev.bcs.call.CallHistory;
import ma2dev.bcs.call.CallInformationReader;

import org.junit.Test;

public class CallInformationReaderTest {

	@Test
	public final void testファイル読み込み() {
		Reader reader = null;
		try {
			reader = new FileReader("dat/test/bcs/callInfo/template_callInfo.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<CallHistory> list = null;
		try {
			list = CallInformationReader.readFromCsv(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertTrue(list.size() > 0);
		assertThat(list.get(0).getSrcTelnumber(), is("09011112222"));
	}

}
