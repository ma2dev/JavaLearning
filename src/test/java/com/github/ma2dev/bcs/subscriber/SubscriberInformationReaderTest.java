package com.github.ma2dev.bcs.subscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import ma2dev.bcs.subscriber.Subscriber;
import ma2dev.bcs.subscriber.SubscriberInformationReader;

import org.junit.Test;

public class SubscriberInformationReaderTest {

	@Test
	public final void testファイル読み込み() {
		Reader reader = null;
		try {
			reader = new FileReader("dat/test/bcs/serviceInfo/template_serviceInfo.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<Subscriber> list = null;
		try {
			list = SubscriberInformationReader.readFromCsv(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertThat(list.get(0).getTelnumber(), is("09011112222"));
	}

}
