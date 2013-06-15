package billingSystem.billing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import billingSystem.info.callInfo.CallInformationManagerFactory;
import billingSystem.info.serviceInfo.BillingSystemServiceInformationBuildException;
import billingSystem.info.serviceInfo.ServiceInformationManagerFactory;

public class BillingTest {

	@Test
	public final void test料金計算() {
		// CallInfo
		IBillingCallInformation callInformationManager = null;
		try {
			callInformationManager = CallInformationManagerFactory.create(
					CallInformationManagerFactory.FACTORY_KIND_CSV, "dat/billingSystem/callInfo/20130421_callInfo.csv");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// ServiceInfo
		IBillingServiceInformation serviceInforamtionManager = null;
		try {
			serviceInforamtionManager = ServiceInformationManagerFactory.create(
					ServiceInformationManagerFactory.FACTORY_KIND_CSV,
					"dat/billingSystem/serviceInfo/20130614_serviceInfo.csv");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (BillingSystemServiceInformationBuildException e) {
			e.printStackTrace();
		}
		IBillingPersonalInformation personalInformation = (IBillingPersonalInformation) serviceInforamtionManager;

		Billing billing = new Billing(personalInformation, callInformationManager, serviceInforamtionManager);

		billing.calculate();
		assertNotNull(billing.getPersonalFormList());

	}
}
