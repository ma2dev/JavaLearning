package billingSystem.callInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import javax.security.auth.Subject;

import org.junit.Test;

import billingSystem.billing.ICallCollection;
import billingSystem.billing.IPersonalInformation;
import billingSystem.info.Subscriber;
import billingSystem.info.callInfo.CallInformationManager;

@SuppressWarnings("unused")
public class CallInformationManagerTest {

	@Test
	public final void testCSVファイル読み込み() {
		String file = new String("dat/billingSystem/callInfo/20130421_callInfo.csv");

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

		IPersonalInformation p = new Subscriber("09076228838");
		ICallCollection collection = null;
		collection = callManager.find(p);

		assertNotNull(collection);
	}

}
