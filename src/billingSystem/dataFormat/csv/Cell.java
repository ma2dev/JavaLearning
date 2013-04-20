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
}
