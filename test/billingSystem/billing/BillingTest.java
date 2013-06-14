package billingSystem.billing;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import billingSystem.info.callInfo.CallInformationManager;
import billingSystem.info.serviceInfo.ServiceInforamtionManager;
import billingSystem.info.serviceInfo.ServiceInforamtionManager.BillingSystemServiceInformationBuildException;

public class BillingTest {

	@Test
	public final void test料金計算() {
		// CallInfo
		CallInformationManager callInformationManager = new CallInformationManager();
		try {
			callInformationManager.buildFromCsv("dat/billingSystem/callInfo/20130421_callInfo.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		// ServiceInfo
		ServiceInforamtionManager serviceInforamtionManager = new ServiceInforamtionManager();
		try {
			serviceInforamtionManager.buildFromCsv("dat/billingSystem/serviceInfo/20130614_serviceInfo.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BillingSystemServiceInformationBuildException e) {
			e.printStackTrace();
		}

		Billing billing = new Billing(callInformationManager, serviceInforamtionManager);

		billing.calculate();

	}

}
