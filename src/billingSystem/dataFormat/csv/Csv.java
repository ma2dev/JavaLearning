package billingSystem.dataFormat.csv;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Csv {

	private CsvConfiguration config;
	private List<List<Cell>> cellArray;

	public Csv() {
		this(new CsvConfiguration());
	}

	public Csv(CsvConfiguration config) {
		this.config = config;
		cellArray = new ArrayList<List<Cell>>();
	}

	public CsvConfiguration getConfiguration() {
		return config;
	}

	/**
	 * 行と列を指定して情報を取得する。
	 *
	 * @param row
	 *            行
	 * @param column
	 *            列
	 * @return 情報
	 */
	public Cell getCell(int row, int column) {
		List<Cell> line = cellArray.get(row);
		Cell cell = line.get(column);
		return cell;
	}

	/**
	 * 行を指定して情報を取得する。
	 *
	 * @param row
	 *            行番号
	 * @return 行の情報のcellのList
	 */
	public List<Cell> getCells(int row) {
		return cellArray.get(row);
	}

	/**
	 * 行数を取得する。
	 *
	 * @return 行数
	 */
	public int getRowSize() {
		return cellArray.size();
	}

	public void setCell(Cell cell, int row, int column) {
		// TODO 未実装
	}

	public void readFrom(Reader reader) throws IOException {
		String line;
		LineNumberReader lineNumberReader = new LineNumberReader(reader);
		while ((line = lineNumberReader.readLine()) != null) {
			cellArray.add(getCellOfLine(line));
		}
	}

	public void writeTo(Writer writer) throws IOException {
		for (int i = 0; i < cellArray.size(); i++) {
			writer.write(toStringOfLine(i));
			writer.write('\n');
		}
		writer.flush();
	}

	private List<Cell> getCellOfLine(String line) {
		List<Cell> list = new ArrayList<Cell>();
		StringTokenizer st = new StringTokenizer(line, config.getDelimiter());

		Cell c;
		while (st.hasMoreElements()) {
			c = new Cell();
			c.setData(st.nextToken());
			list.add(c);
		}

		return list;
	}

	private String toStringOfLine(int rowNumber) {
		StringBuffer sb = new StringBuffer();

		List<Cell> line = cellArray.get(rowNumber);
		for (int i = 0; i < line.size(); i++) {
			sb.append(line.get(i).getData());

			if (i != line.size() - 1) {
				sb.append(config.getDelimiter());
			}
		}

		return sb.toString();
	}
}
