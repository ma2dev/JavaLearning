package billingSystem.info.serviceInfo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import billingSystem.billing.IBillingServiceInformation;
import billingSystem.billing.IPersonalInformation;

public class ServiceInforamtionManagerTest {

	@Test
	public final void testサービス明細() {
		String file = new String("dat/billingSystem/serviceInfo/test_oneTelNum_serviceInfo.csv");

		IBillingServiceInformation serviceManager = null;
		try {
			serviceManager = ServiceInformationManagerFactory.create(ServiceInformationManagerFactory.FACTORY_KIND_CSV,
					file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (BillingSystemServiceInformationBuildException e) {
			e.printStackTrace();
		}

		List<IPersonalInformation> list = serviceManager.getPersonalList();
		IPersonalInformation p = list.get(0);
		assertEquals("09076228838", p.getTelNum());
	}

}
