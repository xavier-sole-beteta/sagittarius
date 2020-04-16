package sagittarius.vista.general;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * <b>GraficDeBarresPercentual</b> genera panells amb gràfics de barres percentuals.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GraficDeBarresPercentual  {

	private String titolGrafic;
	private CategoryDataset dataset;
	private JFreeChart chart;
    private ChartPanel chartPanel;
    private String titolEixY;
    private String titolLlengenda;
    private boolean llegenda;
	
	public GraficDeBarresPercentual(String titolGrafic, String titolEixY, String titolLlegenda, boolean llegenda) {
        this.titolGrafic = titolGrafic;
        this.titolEixY = titolEixY;
        this.llegenda = llegenda;
        this.titolLlengenda = titolLlegenda;

        dataset = new DefaultCategoryDataset();
        chart = createChart();
    }

    public void afegeixDades(Double valor, String serie, String categoria) {
    	((DefaultCategoryDataset) dataset).addValue(valor, serie, categoria); 
    }

    private JFreeChart createChart() {

        JFreeChart chart = ChartFactory.createStackedBarChart3D(
                titolGrafic, titolLlengenda, titolEixY,
                dataset, PlotOrientation.VERTICAL, llegenda, true, false);

    	JPanel aux = new JPanel();
    	chart.setBackgroundPaint(aux.getBackground());

        chart.getLegend().setBackgroundPaint(aux.getBackground());
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setForegroundAlpha(0.5f);
        plot.setBackgroundPaint(aux.getBackground());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator( "{3}", NumberFormat.getNumberInstance(), new DecimalFormat( "0.0%")));
       
        renderer.setBaseItemLabelsVisible(true);
        
        return chart;
    }
    
    public JPanel generaPanell() {
    	JPanel aux = new JPanel();
    	chart = createChart();
    	chartPanel = new ChartPanel(chart);
    	aux.setBackground(Color.WHITE);
    	aux.add(chartPanel);
    	
    	return chartPanel;
    }
}
