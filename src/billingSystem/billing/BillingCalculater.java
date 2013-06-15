package billingSystem.billing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import billingSystem.conf.Configure;
import billingSystem.conf.ConfigurePeriodCount;
import billingSystem.conf.ConfigureServiceFee;

/**
 * 料金計算を提供します。
 *
 * @author ma2dev
 *
 */
public class BillingCalculater {

	private List<PersonalForm> personalFormList;
	private IBillingCallInformation callInformation;
	private IBillingServiceInformation serviceInformation;
	private IBillingPersonalInformation personalInformation;

	private ConfigurePeriodCount configurePeriodCount;
	private ConfigureServiceFee configureServiceFee;

	/**
	 * コンストラクタ
	 *
	 * @param configure
	 *            プロパティ情報
	 * @param personalInformation
	 *            契約者情報
	 * @param callInformation
	 *            通話情報
	 * @param serviceInformation
	 *            サービス情報
	 * @throws IOException
	 * @throws
	 */
	public BillingCalculater(Configure configure, final IBillingPersonalInformation personalInformation,
			final IBillingCallInformation callInformation, final IBillingServiceInformation serviceInformation)
			throws IOException {
		personalFormList = new ArrayList<PersonalForm>();
		this.callInformation = callInformation;
		this.serviceInformation = serviceInformation;
		this.personalInformation = personalInformation;

		configurePeriodCount = new ConfigurePeriodCount(configure.get(Configure.CONFIGURE_PERIOD_COUNT_FILEPATH));
		configureServiceFee = new ConfigureServiceFee(configure.get(Configure.CONFIGURE_SERVICE_FEE_FILEPATH));
	}

	/**
	 * 料金計算を実行します。
	 */
	public final void calculate() {

		// 契約者の一覧を取得
		List<IPersonalInformation> personalList = personalInformation.getPersonalList();

		PersonalForm personalForm = null;
		for (IPersonalInformation personal : personalList) {
			personalForm = new PersonalForm(personal);

			// 契約者の情報から対象契約者の通話情報の一覧を取得
			List<AbstractBillingCall> callList = callInformation.find(personal);

			long callBilling = 0;
			if (callList != null) {
				// 通話情報がある場合
				for (IBilling call : callList) {
					// 契約者の通話時間から通話料金を算出
					callBilling += call.calculate();
				}
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

	/**
	 * 明細情報のリストを返却します。
	 *
	 * @return 明細情報のリスト
	 */
	protected final List<PersonalForm> getPersonalFormList() {
		return personalFormList;
	}
}
