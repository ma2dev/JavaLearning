package utils.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogTest {
	// ログインスタンスを取得
	public static Log log = LogFactory.getLog(LogTest.class);

	public static void main(String args[]) {
		// 　致命的なエラーの出力
		log.fatal("fatalメッセージ");

		// 　通常のエラーの出力
		log.error("errorメッセージ");

		// 警告の出力
		log.warn("warnメッセージ");

		// 情報の出力
		log.info("infoメッセージ");

		// デバッグ情報の出力
		log.debug("debugメッセージ");

		// 詳細なデバッグの出力
		log.trace("traceメッセージ");

		try {
			throw new Exception("Error Message");
		} catch (Exception e) {
			// Exceptionのスタックトレースを出力
			log.error("Error Occurs:", e);
		} finally {
			int[] ary = new int[10000];
			if (log.isDebugEnabled()) {
				for (int i = 0; i < 10000; i++) {
					log.debug("array[" + i + "]=" + ary[i]);
				}
			}
		}
	}
}