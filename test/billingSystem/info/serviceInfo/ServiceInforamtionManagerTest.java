package billingSystem.info.serviceInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import billingSystem.billing.IPersonalInformation;
import billingSystem.info.serviceInfo.ServiceInforamtionManager.BillingSystemServiceInformationBuildException;

public class ServiceInforamtionManagerTest {

	@Test
	public final void testサービス明細() {
		String file = new String("dat/billingSystem/serviceInfo/test_oneTelNum_serviceInfo.csv");

		ServiceInforamtionManager serviceManager = new ServiceInforamtionManager();
		try {
			serviceManager.buildFromCsv(file);
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (BillingSystemServiceInformationBuildException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		List<IPersonalInformation> list = serviceManager.getPersonalList();
		IPersonalInformation p = list.get(0);
		assertEquals("09076228838", p.getTelNum());
	}

}
