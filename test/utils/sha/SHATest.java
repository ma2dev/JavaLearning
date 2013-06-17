/**
 *
 */
package utils.sha;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.sha.SHA;

/**
 * @author ma2dev
 *
 */
public class SHATest extends SHA {

	@Test
	public void test同一ハッシュ値の比較() {
		String pass1 = "password";
		String pass2 = "password";

		assertEquals(SHA.getHash(pass1), SHA.getHash(pass2));

	}
}
