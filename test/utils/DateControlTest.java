package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateControlTest {

	private static final int COL = 11;

	@Test
	public final void test通常演算() {
		String[] d = {
				"2013","06","07","01","30","2013","06","07","01","30","0",
				"2013","06","07","01","31","2013","06","07","01","30","60000",
				"2013","06","07","02","30","2013","06","07","01","30","3600000",
				"2013","06","08","01","30","2013","06","07","01","30","86400000",
				"2013","02","07","01","30","2013","01","07","01","30","2678400000",
				"2013","07","07","01","30","2013","06","07","01","30","2592000000",
				"2016","03","07","01","30","2016","02","07","01","30","2505600000",
				"2013","03","07","01","30","2013","02","07","01","30","2419200000",
				"2014","06","07","01","30","2013","06","07","01","30","31536000000",
				"2016","06","07","01","30","2015","06","07","01","30","31622400000"};

		calc(d, COL);
	}

	@Test
	public final void test跨がり() {
		String[] d = {
				"2013","06","07","01","00","2013","06","07","00","59","60000",
				"2013","06","08","00","00","2013","06","07","23","59","60000",
				"2013","02","01","00","00","2013","01","31","23","59","60000",
				"2013","05","01","00","00","2013","04","30","23","59","60000",
				"2016","03","01","00","00","2016","02","29","23","59","60000",
				"2013","04","01","00","00","2013","03","31","23","59","60000",
				"2014","01","01","00","00","2013","12","31","23","59","60000"};

		calc(d, COL);
	}

	@Test
	public final void test跨がり2() {
		String[] d = {
				"2013","06","07","02","00","2013","06","07","00","59","3660000",
				"2013","06","09","00","00","2013","06","07","23","59","86460000",
				"2013","02","02","00","00","2013","01","31","23","59","86460000",
				"2013","05","02","00","00","2013","04","30","23","59","86460000",
				"2016","03","02","00","00","2016","02","29","23","59","86460000",
				"2013","04","02","00","00","2013","03","31","23","59","86460000",
				"2014","02","01","00","00","2013","12","31","23","59","2678460000"};

		calc(d, COL);
	}

	@Test
	public final void test入力大小誤り() {
		String[] d = {
				"2013","06","07","01","30","2013","06","07","01","31","-60000",
				"2013","06","07","01","30","2013","06","07","02","30","-3600000",
				"2013","06","07","01","30","2013","06","08","01","30","-86400000",
				"2013","01","07","01","30","2013","02","07","01","30","-2678400000",
				"2013","06","07","01","30","2014","06","07","01","30","-31536000000"};

		calc(d, COL);
	}

	@Test
	public final void testパターン時刻小大() {
		String[] d = {
				"2013","01","01","01","10","2012","12","31","12","30","45600000",
				"2013","02","01","01","10","2013","01","31","12","30","45600000",
				"2013","03","01","01","10","2013","02","28","12","30","45600000",
				"2016","03","01","01","10","2016","02","29","12","30","45600000",
				"2013","04","01","01","10","2013","03","31","12","30","45600000",
				"2013","05","01","01","10","2013","04","30","12","30","45600000",
				"2013","06","01","01","10","2013","05","31","12","30","45600000",
				"2013","07","01","01","10","2013","06","30","12","30","45600000",
				"2013","08","01","01","10","2013","07","31","12","30","45600000",
				"2013","09","01","01","10","2013","08","31","12","30","45600000",
				"2013","10","01","01","10","2013","09","30","12","30","45600000",
				"2013","11","01","01","10","2013","10","31","12","30","45600000",
				"2013","12","01","01","10","2013","11","30","12","30","45600000"};

		calc(d, COL);
	}

	@Test
	public final void testパターン時刻大小() {
		String[] d = {
				"2013","01","01","12","30","2012","12","31","01","10","127200000",
				"2013","02","01","12","30","2013","01","31","01","10","127200000",
				"2013","03","01","12","30","2013","02","28","01","10","127200000",
				"2016","03","01","12","30","2016","02","29","01","10","127200000",
				"2013","04","01","12","30","2013","03","31","01","10","127200000",
				"2013","05","01","12","30","2013","04","30","01","10","127200000",
				"2013","06","01","12","30","2013","05","31","01","10","127200000",
				"2013","07","01","12","30","2013","06","30","01","10","127200000",
				"2013","08","01","12","30","2013","07","31","01","10","127200000",
				"2013","09","01","12","30","2013","08","31","01","10","127200000",
				"2013","10","01","12","30","2013","09","30","01","10","127200000",
				"2013","11","01","12","30","2013","10","31","01","10","127200000",
				"2013","12","01","12","30","2013","11","30","01","10","127200000"};

		calc(d, COL);
	}

	@Test
	public final void testパターン時刻同一() {
		String[] d = {
				"2013","01","01","01","10","2012","12","31","01","10","86400000",
				"2013","02","01","01","10","2013","01","31","01","10","86400000",
				"2013","03","01","01","10","2013","02","28","01","10","86400000",
				"2016","03","01","01","10","2016","02","29","01","10","86400000",
				"2013","04","01","01","10","2013","03","31","01","10","86400000",
				"2013","05","01","01","10","2013","04","30","01","10","86400000",
				"2013","06","01","01","10","2013","05","31","01","10","86400000",
				"2013","07","01","01","10","2013","06","30","01","10","86400000",
				"2013","08","01","01","10","2013","07","31","01","10","86400000",
				"2013","09","01","01","10","2013","08","31","01","10","86400000",
				"2013","10","01","01","10","2013","09","30","01","10","86400000",
				"2013","11","01","01","10","2013","10","31","01","10","86400000",
				"2013","12","01","01","10","2013","11","30","01","10","86400000"};

		calc(d, COL);
	}

	private void calc(String[] d, int col) {
		int size = d.length / col;
		for (int i = 0; i < size; i++) {
			String year1 = d[0 + i * col];
			String month1 = d[1 + i * col];
			String day1 = d[2 + i * col];
			String hour1 = d[3 + i * col];
			String min1 = d[4 + i * col];
			String year2 = d[5 + i * col];
			String month2 = d[6 + i * col];
			String day2 = d[7 + i * col];
			String hour2 = d[8 + i * col];
			String min2 = d[9 + i * col];
			long expected = Long.parseLong(d[10 + i * col]);
			long result = DateControl.getDiffTime(year1, month1, day1, hour1, min1, year2, month2, day2, hour2, min2);

			assertEquals(expected, result);
		}
	}
}
