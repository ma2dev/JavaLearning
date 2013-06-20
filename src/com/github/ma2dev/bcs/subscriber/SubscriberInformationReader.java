package com.github.ma2dev.bcs.subscriber;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.ma2dev.bcs.dataFormat.IData;
import com.github.ma2dev.bcs.dataFormat.csv.Csv;


/**
 * 契約者情報の読み込みを行います。
 *
 * @author ma2dev
 *
 */
public class SubscriberInformationReader {

	private static final int SUBSCRIBERINFORMATION_TEL_NUM = 0;
	private static final int SUBSCRIBERINFORMATION_NUM_OF_MINPARAM = 1;

	/**
	 * csvファイルから契約者情報を構築します。
	 *
	 * @param subscriberInfoFile
	 *            契約者情報のcsvデータファイル
	 * @return 契約者情報のリスト
	 * @throws IOException
	 *             ファイル入力に失敗した場合
	 */
	public static List<Subscriber> readFromCsv(final Reader subscriberInfoFile) throws IOException {
		Csv serviceInfoCsv = new Csv();
		serviceInfoCsv.read(subscriberInfoFile);

		List<Subscriber> list = new ArrayList<Subscriber>();

		Subscriber subscriber = null;
		List<IData> cellList = null;
		for (int i = 0; i < serviceInfoCsv.getRowSize(); i++) {
			cellList = serviceInfoCsv.getCells(i);

			if (checkFormat(cellList) == false) {
				return null;
			}

			String telnumber = (String) cellList.get(SUBSCRIBERINFORMATION_TEL_NUM).getData();

			subscriber = new Subscriber(telnumber);

			list.add(subscriber);
		}

		return list;

	}

	/**
	 * 契約者情報のパラメータチェック
	 *
	 * @param list
	 *            情報のリスト
	 * @return 問題が無ければtrueを、問題があればfalseを返却します。
	 */
	private static boolean checkFormat(final List<IData> list) {
		// 最小パラメータ数
		if (list.size() < SUBSCRIBERINFORMATION_NUM_OF_MINPARAM) {
			return false;
		}

		return true;
	}
}
