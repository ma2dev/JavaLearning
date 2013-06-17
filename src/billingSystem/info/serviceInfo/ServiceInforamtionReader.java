package billingSystem.info.serviceInfo;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import billingSystem.dataFormat.IData;
import billingSystem.dataFormat.csv.Csv;
import billingSystem.info.Subscriber;
import billingSystem.info.serviceInfo.services.NumberDisplayService;
import billingSystem.info.serviceInfo.services.CallInterruptService;

public class ServiceInforamtionReader {

	private static final int SERVICEINFORMATION_NUM_OF_MINPARAM = 3;
	private static final int SERVICEINFORMATION_NUM_OF_MAXPARAM = 13;

	private static final int SERVICEINFORMATION_TEL_NUM = 0;
	private static final int SERVICEINFORMATION_DISPLAY_CONDITION = 1;
	private static final int SERVICEINFORMATION_INTERRUPT_CONDITION = 2;

	/**
	 * csvファイルからサービス情報を構築します。
	 *
	 * @param reader
	 *            csvファイルのReaderオブジェクト
	 * @return サービス情報のリスト
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 */
	public static List<ServiceInformation> readFromCsv(final Reader reader) throws IOException {
		Csv csv = new Csv();
		csv.read(reader);

		List<ServiceInformation> list = new ArrayList<ServiceInformation>();

		ServiceInformation serviceInformation = null;
		List<IData> cellList = null;
		for (int i = 0; i < csv.getRowSize(); i++) {
			cellList = csv.getCells(i);

			if (checkFormat(cellList) == false) {
				return null;
			}

			String telNum = (String) cellList.get(SERVICEINFORMATION_TEL_NUM).getData();
			String s1 = (String) cellList.get(SERVICEINFORMATION_DISPLAY_CONDITION).getData();
			String s2 = (String) cellList.get(SERVICEINFORMATION_INTERRUPT_CONDITION).getData();

			serviceInformation = new ServiceInformation(new Subscriber(telNum));
			serviceInformation.add(new NumberDisplayService(s1));
			serviceInformation.add(new CallInterruptService(s2));

			list.add(serviceInformation);
		}

		return list;
	}

	/**
	 * サービス情報のパラメータチェック
	 *
	 * @param list
	 *            呼情報のリスト
	 * @return 問題が無ければtrueを、問題があればfalseを返却します。
	 */
	private static boolean checkFormat(final List<IData> list) {
		// 最小パラメータ数(必須のみ)
		if (list.size() < SERVICEINFORMATION_NUM_OF_MINPARAM) {
			return false;
		}

		// 最大パラメータ数(必須+オプション)
		if (list.size() > SERVICEINFORMATION_NUM_OF_MAXPARAM) {
			return false;
		}

		return true;
	}
}
