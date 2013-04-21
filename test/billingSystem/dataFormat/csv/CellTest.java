/**
 *
 */
package billingSystem.dataFormat.csv;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author ma2dev
 *
 */
public class CellTest extends Cell {

	/**
	 * {@link billingSystem.dataFormat.csv.Cell#Cell()} のためのテスト・メソッド。
	 */
	@Test
	public final void testCell() {
		Cell c = new Cell();

		assertEquals(c.getData(), null);
		c.setData("abc");
		assertEquals(c.getData(), "abc");

		c = new Cell("xyz123");
		assertEquals(c.getData(), "xyz123");

		assertEquals(c.length(), "xyz123".length());
		assertEquals(c.toString(), "xyz123");
	}

}
