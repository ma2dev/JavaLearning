package billingSystem.dataFormat.csv;

public class Cell {

	private String data;

	public Cell() {
		data = null;
	}

	public Cell(String data) {
		setData(data);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int length() {
		return data.length();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return data;
	}

}
