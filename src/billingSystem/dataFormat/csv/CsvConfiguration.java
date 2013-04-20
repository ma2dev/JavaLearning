package billingSystem.dataFormat.csv;

public class CsvConfiguration {

	private String delimiter;

	public CsvConfiguration() {
		delimiter = ","; // default
	}

	public CsvConfiguration(String delimiter) {
		setDelimiter(delimiter);
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

}
