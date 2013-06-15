package billingSystem.info.callInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.security.auth.Subject;

import org.junit.Test;

import billingSystem.billing.AbstractCall;
import billingSystem.billing.IPersonalInformation;
import billingSystem.info.Subscriber;
import billingSystem.info.callInfo.CallInformationManager;

@SuppressWarnings("unused")
public class CallInformationManagerTest {

	@Test
	public final void testCSVファイル読み込み() {
		String file = new String("dat/billingSystem/callInfo/tset_oneTelNum_callInfo.csv");

		CallInformationManager callManager = new CallInformationManager();
		try {
			callManager.buildFromCsv(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		IPersonalInformation p = new Subscriber("09076228838");
		List<AbstractCall> list = callManager.find(p);
		assertNotNull(list);
	}

}
