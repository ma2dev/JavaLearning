package billingSystem.billing;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * 通話料金を管理します。
 *
 * @author ma2dev
 *
 */
public abstract class AbstractCall implements IBilling {

	// 1秒あたりの料金(円)
	private static final BigDecimal RatePerSec = new BigDecimal(8.0 / 60.0);

	protected Date startTime;
	protected Date endTime;

	protected abstract void setStartTime(String startTime) throws ParseException;

	protected abstract void setEndTime(String endTime) throws ParseException;

	@Override
	public final long calculate() {
		BigDecimal billing = null;

		// 通話時間を計算
		BigDecimal talkTime = new BigDecimal(this.talkTime());

		// 通話時間を金額に変換
		billing = RatePerSec.multiply(talkTime); // 通話料金

		return billing.setScale(0, BigDecimal.ROUND_UP).longValue(); // 小数点第一位で切り上げ
	}

	/**
	 * 通話時間を求めます。<br>
	 * 通話時間は秒で返却されます。
	 *
	 * @return 通話時間(秒)
	 */
	private long talkTime() {
		long start = startTime.getTime();
		long end = endTime.getTime();

		long result = (end - start) / 1000;

		return result;
	}

}
