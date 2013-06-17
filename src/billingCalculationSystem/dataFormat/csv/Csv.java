package billingCalculationSystem.dataFormat.csv;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import billingCalculationSystem.dataFormat.IData;

/**
 * CSVファイルを表現します。<br>
 * コンストラクタでコンフィグを設定可能です。
 *
 * @author ma2dev
 *
 */
public class Csv {

	private CsvConfiguration config;
	private List<List<IData>> cellArray;

	/**
	 * コンストラクタ<br>
	 * 設定されるコンフィグはデフォルトです。
	 *
	 */
	public Csv() {
		this(new CsvConfiguration());
	}

	/**
	 * コンストラクタ<br>
	 * コンフィグを設定できます。
	 *
	 * @param config
	 *            コンフィグ
	 */
	public Csv(final CsvConfiguration config) {
		this.config = config;
		cellArray = new ArrayList<List<IData>>();
	}

	/**
	 * コンフィグを取得します。
	 *
	 * @return コンフィグ
	 */
	public CsvConfiguration getConfiguration() {
		return config;
	}

	/**
	 * 行と列を指定して情報を取得します。
	 *
	 * @param row
	 *            行
	 * @param column
	 *            列
	 * @return 情報
	 */
	public IData getCell(final int row, final int column) {
		List<IData> line = cellArray.get(row);
		IData cell = line.get(column);
		return cell;
	}

	/**
	 * 行を指定して情報をListとして取得します。
	 *
	 * @param row
	 *            行番号
	 * @return 行の情報のcellのList
	 */
	public List<IData> getCells(final int row) {
		return cellArray.get(row);
	}

	/**
	 * 行数を取得します。
	 *
	 * @return 行数
	 */
	public int getRowSize() {
		return cellArray.size();
	}

	/**
	 * Readerから読み込みます。
	 *
	 * @param reader
	 *            Readerオブジェクト
	 * @throws IOException
	 *             読み込みに失敗した場合
	 */
	public void read(final Reader reader) throws IOException {
		String line;
		LineNumberReader lineNumberReader = new LineNumberReader(reader);
		while ((line = lineNumberReader.readLine()) != null) {
			cellArray.add(getCellOfLine(line));
		}
	}

	/**
	 * Writerに書き込みます。
	 *
	 * @param writer
	 *            Writerオブジェクト
	 * @throws IOException
	 *             書き込みに失敗した場合
	 */
	public void write(final Writer writer) throws IOException {
		for (int i = 0; i < cellArray.size(); i++) {
			writer.write(toStringOfLine(i));
			writer.write('\n');
		}
		writer.flush();
	}

	/**
	 * 1行のcsv形式の文字列を分解し、Cellのリストとして返却します。
	 *
	 * @param line
	 *            1行のcsv形式の文字列
	 * @return Cellのリスト
	 */
	private List<IData> getCellOfLine(final String line) {
		List<IData> list = new ArrayList<IData>();
		StringTokenizer st = new StringTokenizer(line, config.getDelimiter());

		Cell c;
		while (st.hasMoreElements()) {
			c = new Cell(st.nextToken());
			list.add(c);
		}

		return list;
	}

	/**
	 * 指定した行をcsv形式の文字列として返却します。
	 *
	 * @param rowNumber
	 *            指定行
	 * @return csv形式の文字列
	 */
	private String toStringOfLine(final int rowNumber) {
		StringBuffer sb = new StringBuffer();

		List<IData> line = cellArray.get(rowNumber);
		for (int i = 0; i < line.size(); i++) {
			sb.append(line.get(i).getData());

			if (i != line.size() - 1) {
				sb.append(config.getDelimiter());
			}
		}

		return sb.toString();
	}
}
