package ma2dev.jfreechart.sample;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartUtilities;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sample {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sample.class);

	public static void main(String[] args) {
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme()); // 文字化け防止

		DefaultPieDataset data = new DefaultPieDataset();

		data.setValue("アサヒ", 37);
		data.setValue("キリン", 36);
		data.setValue("サントリー", 13);
		data.setValue("サッポロ", 12);
		data.setValue("その他", 2);

		JFreeChart chart = ChartFactory.createPieChart("Sample", data, true, false, false);

		File file = new File("./chart.png");
		try {
			ChartUtilities.saveChartAsPNG(file, chart, 500, 500);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
