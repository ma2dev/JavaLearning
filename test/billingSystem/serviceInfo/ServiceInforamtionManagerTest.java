package billingSystem.serviceInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import billingSystem.billing.IPersonalInformation;
import billingSystem.info.serviceInfo.ServiceInforamtionManager;
import billingSystem.info.serviceInfo.ServiceInforamtionManager.BillingSystemServiceInformationBuildException;

public class ServiceInforamtionManagerTest {

	@Test
	public final void testサービス情報構築() {
		ServiceInforamtionManager manager = new ServiceInforamtionManager();
		try {
			manager.buildFromCsv("dat/billingSystem/serviceInfo/test_oneTelNum_serviceInfo.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BillingSystemServiceInformationBuildException e) {
			e.printStackTrace();
		}

		List<IPersonalInformation> list = manager.getPersonalList();
		IPersonalInformation p = list.get(0);
		assertEquals("09076228838", p.getTelNum());
	}

}
