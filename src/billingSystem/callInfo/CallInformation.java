package billingSystem.callInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallInformation {

	private String srcTelNum;
	private String dstTelNum;
	private Date startTime;
	private Date endTime;
	private Enum<EndReason> endReason;

	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	public CallInformation() {
		this.clear();
	}

	/**
	 * 通話時間を求める。<br>
	 * 通話時間は秒で返却される。
	 *
	 * @return
	 */
	public long computeTalkTime() {
		long start = startTime.getTime();
		long end = endTime.getTime();

		long result = (end - start) * 1000;

		return result;
	}

	/**
	 * @return srcTelNum
	 */
	public String getSrcTelNum() {
		return srcTelNum;
	}

	/**
	 * @param srcTelNum
	 *            セットする srcTelNum
	 */
	public void setSrcTelNum(String srcTelNum) {
		this.srcTelNum = srcTelNum;
	}

	/**
	 * @return dstTelNum
	 */
	public String getDstTelNum() {
		return dstTelNum;
	}

	/**
	 * @param dstTelNum
	 *            セットする dstTelNum
	 */
	public void setDstTelNum(String dstTelNum) {
		this.dstTelNum = dstTelNum;
	}

	/**
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            セットする startTime
	 * @throws ParseException
	 */
	public void setStartTime(String startTime) throws ParseException {
		this.startTime = stringToDate(startTime);
	}

	/**
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            セットする endTime
	 * @throws ParseException
	 */
	public void setEndTime(String endTime) throws ParseException {
		this.endTime = stringToDate(endTime);
	}

	/**
	 * @return endReason
	 */
	public Enum<EndReason> getEndReason() {
		return endReason;
	}

	/**
	 * @param endReason
	 *            セットする endReason
	 */
	public void setEndReason(String endReason) {
		this.endReason = stringToEndReason(endReason);
	}

	private Date stringToDate(String s) throws ParseException {
		return df.parse(s);
	}

	private Enum<EndReason> stringToEndReason(String s) {

		Enum<EndReason> e;

		if (s.compareTo("0") == 0) {
			e = EndReason.NORMAL;
		} else {
			e = EndReason.ERROR;
		}

		return e;
	}

	private void clear() {
		srcTelNum = null;
		dstTelNum = null;
		startTime = new Date();
		endTime = new Date();
	}

}
