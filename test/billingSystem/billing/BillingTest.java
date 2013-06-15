package billingSystem.billing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import billingSystem.conf.Configure;
import billingSystem.info.callInfo.CallInformationManagerFactory;
import billingSystem.info.serviceInfo.ServiceInformationBuildException;
import billingSystem.info.serviceInfo.ServiceInformationManagerFactory;

public class BillingTest {

	@Test
	public final void test料金計算() {
		// Propaties
		Configure configure = null;
		try {
			configure = new Configure("dat/test/billingSystem/conf/template.properties");
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		// CallInfo
		IBillingCallInformation callInformationManager = null;
		try {
			callInformationManager = CallInformationManagerFactory.create(
					CallInformationManagerFactory.FACTORY_KIND_CSV,
					"dat/test/billingSystem/callInfo/template_callInfo.csv");
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
					"dat/test/billingSystem/serviceInfo/template_serviceInfo.csv");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ServiceInformationBuildException e) {
			e.printStackTrace();
		}
		IBillingPersonalInformation personalInformation = (IBillingPersonalInformation) serviceInforamtionManager;

		Billing billing = null;
		try {
			billing = new Billing(configure, personalInformation, callInformationManager, serviceInforamtionManager);
			billing.calculate();
			assertNotNull(billing.getPersonalFormList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
