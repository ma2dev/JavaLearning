package sha;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SHA {

	private static final Log LOG = LogFactory.getLog(SHA.class);

	private static final String ALG = "SHA-256";

	/**
	 * 引数で与えた文字列のハッシュ値を取得
	 *
	 * @param target
	 * @return
	 */
	public static String getHash(String target) {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance(ALG);
			md.update(target.getBytes());
			byte[] digest = md.digest();
			// byte[] -> String
			hash = new String(digest, "UTF-8");

		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getLocalizedMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
		return hash;
	}
}
