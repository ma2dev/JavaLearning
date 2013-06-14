package billingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import billingSystem.callInfo.CallInformationManager;

/**
 * 料金計算システム<br>
 * 呼情報ファイルを入力値として、通話料金の計算を行う。<br>
 * Javaの勉強を目的としたプログラム課題。
 *
 * @author ma2dev
 *
 */
public class BillingSystem {

	public static final String CALLINFO_OPTION_C = "c";
	public static final String SERVICEINFO_OPTION_C = "s";
	public static final String OUTPUT_OPTION_C = "o";

	/**
	 * @param args
	 *            オプションについては実行時に表示されるUsage参照。
	 */
	public static void main(String[] args) {

		// オプションチェック ---------------------------------------------------------
		Options options = new Options();

		// 呼情報ファイルのオプション
		@SuppressWarnings("static-access")
		Option optCallInfo = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("呼情報ファイルを指定します。") // Usage出力用の説明
				.withArgName("callInfo") // パラメータ名
				.withLongOpt("call") // オプションの別名
				.create(CALLINFO_OPTION_C); // オプション作成
		optCallInfo.setRequired(true);
		options.addOption(optCallInfo);

		// サービス情報ファイルのオプション
		@SuppressWarnings("static-access")
		Option optServiceInfo = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("サービス情報ファイルを指定します。") // Usage出力用の説明
				.withArgName("serviceInfo") // パラメータ名
				.withLongOpt("service") // オプションの別名
				.create(SERVICEINFO_OPTION_C); // オプション作成
		optServiceInfo.setRequired(true);
		options.addOption(optServiceInfo);

		// 明細一覧ファイルのオプション
		@SuppressWarnings("static-access")
		Option outputfileOpt = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("明細一覧ファイルを指定します。") // Usage出力用の説明
				.withArgName("output") // パラメータ名
				.withLongOpt("output") // オプションの別名
				.create(OUTPUT_OPTION_C); // オプション作成
		outputfileOpt.setRequired(true);
		options.addOption(outputfileOpt);

		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = null;

		try {
			commandLine = parser.parse(options, args);

		} catch (ParseException e) {
			// オプションの指定が誤っている場合
			showUsage(options);
			return;
		}

		// 「-c」の場合
		String callInfoFile = null;
		if (commandLine.hasOption(CALLINFO_OPTION_C)) {
			// 引数を取得
			callInfoFile = commandLine.getOptionValue(CALLINFO_OPTION_C);

			File file = new File(callInfoFile);
			String result = "指定された呼情報ファイルは存在しません。";
			if (file.exists() == false) {
				// ファイル無し
				System.err.println(result);
				return;
			}
		}

		String serviceInfoFile = null;
		if (commandLine.hasOption(SERVICEINFO_OPTION_C)) {
			// 引数を取得
			serviceInfoFile = commandLine.getOptionValue(SERVICEINFO_OPTION_C);

			File file = new File(serviceInfoFile);
			String result = "指定されたサービス情報ファイルは存在しません。";
			if (file.exists() == false) {
				// ファイル無し
				System.err.println(result);
				return;
			}
		}

		String outputFile = null;
		if (commandLine.hasOption(SERVICEINFO_OPTION_C)) {
			// 引数を取得
			outputFile = commandLine.getOptionValue(SERVICEINFO_OPTION_C);
		}

		// main --------------------------------------------------------------
		CallInformationManager callInformationManager = new CallInformationManager();
		try {
			callInformationManager.buildFromCsv(callInfoFile);
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}



		System.out.println("callInfo---");
		callInformationManager.printOn(); // TODO 料金計算処理の作成
	}

	/**
	 * Usage出力
	 *
	 * @param options
	 */
	private static void showUsage(Options options) {
		HelpFormatter help = new HelpFormatter();
		// ヘルプを出力
		help.printHelp("BillingSystem", options, true);
	}
}
