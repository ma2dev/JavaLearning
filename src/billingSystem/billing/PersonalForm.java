package billingSystem.billing;

/**
 * 個人の明細情報を提供します。
 *
 * @author ma2dev
 *
 */
public class PersonalForm {

	private IPersonalInformation personalInformation;
	private long callCalculate;

	/**
	 * コンストラクタ
	 *
	 * @param personal
	 *            帳票対象の個人
	 */
	public PersonalForm(IPersonalInformation personal) {
		personalInformation = personal;
		callCalculate = 0;
	}

	/**
	 * 通話料金を設定します。
	 *
	 * @param callBilling
	 *            対象個人の通話料金
	 */
	public void addCallBilling(long callBilling) {
		callCalculate = callBilling;
	}

	public String toString() {
		return new String(personalInformation.getTelNum() + "," + "," + callCalculate + "," + ",");
	}
}
