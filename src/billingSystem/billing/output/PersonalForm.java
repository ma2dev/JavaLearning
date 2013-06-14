package billingSystem.billing.output;

import java.io.IOException;
import java.io.Writer;

import billingSystem.billing.IPersonalInformation;

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

	/**
	 * 帳票出力
	 *
	 * @param writer
	 *            出力先
	 * @throws IOException
	 *             書き込みに失敗した場合
	 */
	public void write(Writer writer) throws IOException {
		writer.write(personalInformation.getTelNum() + "," + "," + callCalculate + "," + ",");
		writer.write("\n");
	}

}
