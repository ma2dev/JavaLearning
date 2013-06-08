package billingSystem.callInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

public class CallInformationReaderTest {

	@Test
	public final void testCSVファイル読み込み() {
		String file = new String("dat/callInfo/20130421_callInfor.csv");

		try {
			List<CallInformation> list = CallInformationReader.readFromCsv(new FileReader(file));

			assertNotNull(list);
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
