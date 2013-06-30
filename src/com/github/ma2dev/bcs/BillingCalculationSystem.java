package com.github.ma2dev.bcs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ma2dev.bcs.subscriber.SubscriberManager;

/**
 * 通話履歴およびサービス契約情報から契約者の通話料金及びサービス契約料金を算出し、明細に出力します。
 * 
 * @author ma2dev
 * 
 */
public class BillingCalculationSystem {

	/** logger */
	private static final Logger log = LoggerFactory.getLogger(BillingCalculationSystem.class);

	public static final String CALLINFO_OPTION_CHAR = "c";
	public static final String SERVICEINFO_OPTION_CHAR = "s";
	public static final String OUTPUT_OPTION_CHAR = "o";
	public static final String PROPERTIES_OPTION_CHAR = "p";

	/**
	 * @param args
	 *            オプションについては実行時に表示されるUsage参照。
	 */
	public static void main(String[] args) {
		log.info("プログラムの実行を開始します。");

		// オプションチェック ---------------------------------------------------------
		Options options = new Options();

		// 呼情報ファイルのオプション
		@SuppressWarnings("static-access")
		Option optCallInfo = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("呼情報ファイルを指定します。") // Usage出力用の説明
				.withArgName("callInfo") // パラメータ名
				.withLongOpt("call") // オプションの別名
				.create(CALLINFO_OPTION_CHAR); // オプション作成
		optCallInfo.setRequired(true);
		options.addOption(optCallInfo);

		// サービス情報ファイルのオプション
		@SuppressWarnings("static-access")
		Option optServiceInfo = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("サービス情報ファイルを指定します。") // Usage出力用の説明
				.withArgName("serviceInfo") // パラメータ名
				.withLongOpt("service") // オプションの別名
				.create(SERVICEINFO_OPTION_CHAR); // オプション作成
		optServiceInfo.setRequired(true);
		options.addOption(optServiceInfo);

		// 明細一覧ファイルのオプション
		@SuppressWarnings("static-access")
		Option outputfileOpt = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("明細一覧ファイルを指定します。") // Usage出力用の説明
				.withArgName("output") // パラメータ名
				.withLongOpt("output") // オプションの別名
				.create(OUTPUT_OPTION_CHAR); // オプション作成
		outputfileOpt.setRequired(true);
		options.addOption(outputfileOpt);

		// プロパティファイルのオプション
		@SuppressWarnings("static-access")
		Option propertiesfileOpt = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("プロパティファイルを指定します。") // Usage出力用の説明
				.withArgName("properties") // パラメータ名
				.withLongOpt("prop") // オプションの別名
				.create(PROPERTIES_OPTION_CHAR); // オプション作成
		propertiesfileOpt.setRequired(true);
		options.addOption(propertiesfileOpt);

		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = null;

		try {
			commandLine = parser.parse(options, args);

		} catch (org.apache.commons.cli.ParseException e) {
			// オプションの指定が誤っている場合
			showUsage(options);
			return;
		}

		// 「-c」の場合
		String callInfoFile = null;
		if (commandLine.hasOption(CALLINFO_OPTION_CHAR)) {
			// 引数を取得
			callInfoFile = commandLine.getOptionValue(CALLINFO_OPTION_CHAR);

			File file = new File(callInfoFile);
			String result = "指定された呼情報ファイルは存在しません。";
			if (file.exists() == false || file.isFile() == false) {
				// ファイル無し
				System.err.println(result);
				return;
			}
		}

		// 「-s」の場合
		String serviceInfoFile = null;
		if (commandLine.hasOption(SERVICEINFO_OPTION_CHAR)) {
			// 引数を取得
			serviceInfoFile = commandLine.getOptionValue(SERVICEINFO_OPTION_CHAR);

			File file = new File(serviceInfoFile);
			String result = "指定されたサービス情報ファイルは存在しません。";
			if (file.exists() == false || file.isFile() == false) {
				// ファイル無し
				System.err.println(result);
				return;
			}
		}

		// 「-o」の場合
		String outputFile = null;
		if (commandLine.hasOption(OUTPUT_OPTION_CHAR)) {
			// 引数を取得
			outputFile = commandLine.getOptionValue(OUTPUT_OPTION_CHAR);
		}

		// 「-p」の場合
		String propertiesFile = null;
		if (commandLine.hasOption(PROPERTIES_OPTION_CHAR)) {
			// 引数を取得
			propertiesFile = commandLine.getOptionValue(PROPERTIES_OPTION_CHAR);

			File file = new File(propertiesFile);
			String result = "指定されたプロパティファイルは存在しません。";
			if (file.exists() == false || file.isFile() == false) {
				// ファイル無し
				System.err.println(result);
				return;
			}
		}

		// main --------------------------------------------------------------
		SubscriberManager manager;
		boolean result = true;
		try {
			manager = new SubscriberManager(propertiesFile, serviceInfoFile, callInfoFile, serviceInfoFile);
			manager.execute(outputFile);
		} catch (IOException e) {
			// e.printStackTrace();
			System.err.println("ファイル入出力に異常がありました。");
			result = false;
		} catch (java.text.ParseException e) {
			// e.printStackTrace();
			System.err.println("呼情報ファイル入力でエラーになりました。");
			System.err.println("呼情報ファイルの日付情報がIF規定に違反している可能性があります。");
			result = false;
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			System.err.println("入力値に異常があります。");
			result = false;
		}
		if (result == true) {
			System.out.println("プログラムは正常終了しました。");
			log.info("プログラムは正常終了しました。");
		} else {
			System.out.println("プログラムは異常終了しました。");
			log.error("プログラムは異常終了しました。");
		}
	}

	/**
	 * Usage出力
	 * 
	 * @param options
	 *            オプション情報
	 */
	private static void showUsage(Options options) {
		HelpFormatter help = new HelpFormatter();
		// ヘルプを出力
		help.printHelp("BillingCalculationSystem", options, true);
	}
}
