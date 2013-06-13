package billingSystem.serviceInfo;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import billingSystem.serviceInfo.ServiceInforamtionManager.BillingSystemServiceInformationBuildException;

public class ServiceInforamtionManagerTest {

	@Test
	public final void testサービス情報構築() {
		ServiceInforamtionManager manager = new ServiceInforamtionManager();
		try {
			manager.buildFromCsv("dat/billingSystem/serviceInfo/20130614_serviceInfo.csv");
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

		manager.printOn();
	}

}
