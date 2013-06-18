package com.github.ma2dev.bcs.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.ma2dev.bcs.conf.ConfigureServiceFee;
import com.github.ma2dev.bcs.dataFormat.IData;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;


/**
 * サービス情報ファイルを読み込みます。
 *
 * @author ma2dev
 *
 */
public class ServiceInforamtionReader {

	private static final int SERVICEINFORMATION_NUM_OF_MINPARAM = 3;
	private static final int SERVICEINFORMATION_NUM_OF_MAXPARAM = 13;

	private static final int SERVICEINFORMATION_TEL_NUM = 0;
	private static final int SERVICEINFORMATION_DISPLAY_CONDITION = 1;
	private static final int SERVICEINFORMATION_INTERRUPT_CONDITION = 2;

	private static final int SERVICEINFORMATION_FAMILYCALL_CONDITION_START = 3;

	/**
	 * csvファイルからサービス情報を構築します。
	 *
	 * @param serviceInfo
	 * @param servicePrice
	 * @return サービス情報のリスト
	 * @throws IOException
	 *             ファイル読み込みに失敗した場合
	 */
	public static List<Service> readFromCsv(final Reader serviceInfo, final ConfigureServiceFee servicePrice)
			throws IOException {
		Csv serviceInfoCsv = new Csv();
		serviceInfoCsv.read(serviceInfo);

		List<Service> list = new ArrayList<Service>();

		Service service = null;
		List<IData> cellList = null;
		for (int i = 0; i < serviceInfoCsv.getRowSize(); i++) {
			cellList = serviceInfoCsv.getCells(i);

			if (checkFormat(cellList) == false) {
				return null;
			}

			String telNum = (String) cellList.get(SERVICEINFORMATION_TEL_NUM).getData();
			String s1 = (String) cellList.get(SERVICEINFORMATION_DISPLAY_CONDITION).getData();
			String s2 = (String) cellList.get(SERVICEINFORMATION_INTERRUPT_CONDITION).getData();

			service = new Service(telNum);
			service.setCondition(s1, s2, buildFamilyCallTelnumberArray(cellList));
			service.setPriceList(servicePrice);

			list.add(service);
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

	/**
	 * 家族無料通話サービスの電話番号配列を組み立てます。
	 *
	 * @param list
	 *            サービス契約情報の文字列のリスト(サービス情報ファイルの1行文のデータ配列)
	 * @return 電話番号を文字列として格納した配列を返却します。
	 */
	private static List<String> buildFamilyCallTelnumberArray(List<IData> list) {
		List<String> telnumberList = new ArrayList<String>();

		for (int i = SERVICEINFORMATION_FAMILYCALL_CONDITION_START; i < list.size(); i++) {
			telnumberList.add((String) list.get(i).getData());
		}

		return telnumberList;
	}
}
