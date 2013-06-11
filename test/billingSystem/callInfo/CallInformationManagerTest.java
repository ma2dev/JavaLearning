package billingSystem.callInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

@SuppressWarnings("unused")
public class CallInformationManagerTest {

	@Test
	public final void testCSVファイル読み込み() {
		String file = new String("dat/callInfo/20130421_callInfor.csv");

		CallInformationManager callManager = new CallInformationManager();
		try {
			callManager.buildFromCsv(file);
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
		callManager.printOn();
	}

}
