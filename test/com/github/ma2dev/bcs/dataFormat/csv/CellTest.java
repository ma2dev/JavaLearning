package com.github.ma2dev.bcs.dataFormat.csv;

import static org.junit.Assert.*;

import org.junit.Test;

public class CellTest {

	@Test
	public final void testCell() {
		Cell c = new Cell();

		assertEquals(c.getData(), null);
		c.setData("abc");
		assertEquals("abc", c.getData());

		c = new Cell("xyz123");
		assertEquals("xyz123", c.getData());

		assertEquals("xyz123".length(), c.length());
		assertEquals("xyz123", c.toString());
	}

}
