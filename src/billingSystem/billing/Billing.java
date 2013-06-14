package billingSystem.billing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import billingSystem.billing.output.PersonalForm;

public class Billing {

	private List<PersonalForm> personalFormList;
	private IBillingCallInformation callInformation;
	private IBillingServiceInformation serviceInformation;

	public Billing(IBillingCallInformation callInformation, IBillingServiceInformation serviceInformation) {
		personalFormList = new ArrayList<PersonalForm>();
		this.callInformation = callInformation;
		this.serviceInformation = serviceInformation;
	}

	public void calculate() {

		// 契約者の一覧を取得
		List<IPersonalInformation> personalList = serviceInformation.getPersonalList();

		PersonalForm personalForm = null;
		for (IPersonalInformation personal : personalList) {
			personalForm = new PersonalForm(personal);

			// 契約者の情報から対象契約者の通話情報の一覧を取得
			ICallCollection callCollection = callInformation.find(personal);

			long callBilling = 0;

			if (callCollection != null) {
				// 通話情報がある場合
				for (IBilling call : callCollection.getList()) {
					// 契約者の通話時間から通話料金を算出
					callBilling += call.calculate();
				}
				System.out.println("-->" + callBilling);
			} else {
				System.out.println("NULL!!!");
			}

			// 算出した通話料金を明細に設定
			personalForm.addCallBilling(callBilling);

			// TODO サービス情報の料金計算処理
			// 契約者のサービス契約情報を取得
			// サービス契約料金を算出
			// 算出したサービス料金を明細に設定

			// 個人明細をリストに追加
			personalFormList.add(personalForm);
		}

	}

	public void write(String filename) throws IOException {

		Writer writer = new FileWriter(filename);

		// 明細を出力する
		for (PersonalForm personalForm : personalFormList) {
			personalForm.write(writer);
		}
		writer.flush();
		writer.close();
	}

}
