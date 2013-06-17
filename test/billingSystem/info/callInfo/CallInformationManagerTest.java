package billingSystem.info.callInfo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import billingSystem.billing.AbstractBillingCall;
import billingSystem.billing.IBillingCallInformation;
import billingSystem.billing.IPersonalInformation;
import billingSystem.info.Subscriber;

public class CallInformationManagerTest {

	@Test
	public final void testCSVファイル読み込み() {
		String file = new String("dat/test/billingSystem/callInfo/tset_oneTelNum_callInfo.csv");

		IBillingCallInformation callManager = null;
		try {
			callManager = CallInformationManagerFactory.create(CallInformationManagerFactory.FACTORY_KIND_CSV, file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		IPersonalInformation p = new Subscriber("09076228838");
		List<AbstractBillingCall> list = callManager.find(p);
		assertNotNull(list);
	}

}
