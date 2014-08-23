package com.github.ma2dev.bcs.dataFormat.csv;

import static org.junit.Assert.*;
import ma2dev.bcs.dataFormat.csv.Cell;

import org.junit.Test;

public class CellTest {

	@Test
	public final void testコンストラクタ() {
		Cell c = new Cell();

		assertEquals(c.getData(), "");
	}

	@Test(expected = NullPointerException.class)
	public final void testコンストラクタでのNull値設定() {
		// NullPointerException
		new Cell(null);
	}

	@Test(expected = NullPointerException.class)
	public final void testSetterでのNull値設定() {
		// NullPointerException
		Cell c = new Cell();
		c.setData(null);
	}

	@Test
	public final void testコンストラクタでのデータ設定() {
		Cell c = new Cell("xyz123");
		assertEquals("xyz123", c.getData());
	}

	@Test
	public final void testSetterでのデータ設定() {
		Cell c = new Cell();

		c.setData("abc");
		assertEquals("abc", c.getData());

		assertEquals("abc".length(), c.length());
		assertEquals("abc", c.toString());
	}

	@Test
	public final void testデータ取得() {
		Cell c = new Cell("abcdefg1234567890");

		assertEquals("abcdefg1234567890".length(), c.length());
		assertEquals("abcdefg1234567890", c.toString());
	}
}
