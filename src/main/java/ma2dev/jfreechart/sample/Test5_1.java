package ma2dev.jfreechart.sample;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import org.jfree.chart.ChartPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test5_1 extends JFrame {
	private static final long serialVersionUID = 6797093519345702029L;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Test5_1.class);

	public static void main(String[] args) {
		Test5_1 frame = new Test5_1();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(10, 10, 500, 500);
		frame.setTitle("グラフサンプル");
		frame.setVisible(true);
	}

	Test5_1() {
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme()); // 文字化け防止

		DefaultCategoryDataset data = new DefaultCategoryDataset();
		String[] series = { "本社", "大阪支社", "名古屋支社" };
		String[] category = { "4月", "5月", "6月" };

		data.addValue(800, series[0], category[0]);
		data.addValue(600, series[0], category[1]);
		data.addValue(900, series[0], category[2]);
		data.addValue(500, series[1], category[0]);
		data.addValue(300, series[1], category[1]);
		data.addValue(200, series[1], category[2]);
		data.addValue(300, series[2], category[0]);
		data.addValue(900, series[2], category[1]);
		data.addValue(600, series[2], category[2]);

		JFreeChart chart = ChartFactory.createBarChart3D("月別売上", "月", "売上", data, PlotOrientation.VERTICAL, true,
				false, false);

		ChartPanel cpanel = new ChartPanel(chart);
		getContentPane().add(cpanel, BorderLayout.CENTER);
	}
}