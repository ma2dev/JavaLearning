package com.github.ma2dev.bcs.conf;

import static org.junit.Assert.*;

import java.io.IOException;

import ma2dev.bcs.conf.Configure;

import org.junit.Test;

public class ConfigureTest {

	@Test
	public final void testプロパティファイル読み込み() {
		String filename = new String("dat/test/bcs/conf/template.properties");

		Configure configure = null;
		try {
			configure = new Configure(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = null;
		// 一致する場合
		result = configure.get("configure.servicefee.filepath");
		assertEquals("dat/test/bcs/conf/ServiceFee.conf", result);

		// 一致しない場合
		result = configure.get("no match");
		assertNull(result);
	}

}
