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

	public static final String VERSION = "0.1";

	/**
	 * @param args オプションについては実行時に表示されるUsage参照。
	 */
	public static void main(String[] args) {

		Options options = new Options();
		options.addOption("h", "help", false, "各オプションの説明です。");
		options.addOption("v", "version", false, "バージョン情報を出力します。");
		@SuppressWarnings("static-access")
		Option callInfoOpt = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("呼情報ファイルを指定します。") // Usage出力用の説明
				.withArgName("callInfo file") // パラメータ名
				.withLongOpt("callinfo") // オプションの別名
				.create("c"); // オプション作成
		options.addOption(callInfoOpt);
		@SuppressWarnings("static-access")
		Option outputfileOpt = OptionBuilder // OptionBuilder
				.hasArg(true) // オプションの後にパラメータが必須か
				.withDescription("明細情報ファイルを指定します。") // Usage出力用の説明
				.withArgName("output file") // パラメータ名
				.withLongOpt("output") // オプションの別名
				.create("o"); // オプション作成
		options.addOption(outputfileOpt);

		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = null;

		try {
			commandLine = parser.parse(options, args);

		} catch (ParseException e) {
			System.out.println("error");
			// オプションの指定が誤っている場合
			showUsage(options);
			return;
		}

		// オプションが無い場合
		if (commandLine.getArgs().length == 0) {
			showUsage(options);
			return;
		}

		// 「-h」の場合
		if (commandLine.hasOption("h")) {
			showUsage(options);
			return;
		}

		// 「-v」の場合
		if (commandLine.hasOption("v")) {
			System.out.println(VERSION);
			return;
		}

		// 「-c」の場合
		String callInfoFile = null;
		if (commandLine.hasOption("c")) {
			// 引数を取得
			callInfoFile = commandLine.getOptionValue("c");

			File file = new File(callInfoFile);
			String result = "指定された呼情報ファイルは存在しません。";
			if (file.exists() == false) {
				// ファイル無し
				System.err.println(result);
				return;
			}
		}

		CallInformationManager callInformationManager = new CallInformationManager();
		try {
			callInformationManager.buildFromCsv(callInfoFile);
			callInformationManager.printOn(); // TODO 料金計算処理の作成
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
