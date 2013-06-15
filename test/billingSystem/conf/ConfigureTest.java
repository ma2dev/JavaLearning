package billingSystem.conf;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class ConfigureTest {

	@Test
	public final void testプロパティファイル読み込み() {
		String filename = new String("dat/test/billingSystem/conf/template.properties");

		Configure configure = null;
		try {
			configure = new Configure(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = null;
		// 一致する場合
		result = configure.get("configure.servicefee.filepath");
		assertEquals("dat/test/billingSystem/conf/ServiceFee.conf", result);

		// 一致しない場合
		result = configure.get("no match");
		assertNull(result);
	}

}
