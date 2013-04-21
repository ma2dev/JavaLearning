package billingSystem.dataFormat.csv;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Csv {

	private CsvConfiguration config;
	private List<List<Cell>> cellArray;

	public Csv() {
		config = new CsvConfiguration();
		cellArray = new ArrayList<List<Cell>>();
	}

	public Csv(CsvConfiguration config) {
		this.config = config;
	}

	public CsvConfiguration getConfiguration() {
		return config;
	}

	public Cell getCell(int row, int column) {
		List<Cell> line = cellArray.get(row);
		Cell cell = line.get(column);
		return cell;
	}

	public void readFrom(Reader reader, CsvConfiguration config) {

	}
}
