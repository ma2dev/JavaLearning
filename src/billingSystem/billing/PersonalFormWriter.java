package billingSystem.billing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * 明細情報の出力を提供します。
 *
 * @author ma2dev
 *
 */
public class PersonalFormWriter {

	/**
	 * 明細情報の出力
	 *
	 * @param filename
	 *            出力先ファイル名
	 * @param billing
	 *            出力対象の料金情報
	 * @throws IOException
	 *             ファイル出力に失敗した場合
	 */
	public static void writeTo(final String filename, final BillingCalculater billing) throws IOException {

		Writer writer = new FileWriter(filename);

		// 明細を出力する
		List<PersonalForm> list = billing.getPersonalFormList();
		for (PersonalForm personalForm : list) {
			writer.write(personalForm.toString() + "\n");
		}
		writer.flush();
		writer.close();

	}
}
