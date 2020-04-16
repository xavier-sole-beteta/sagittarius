package sagittarius.vista.general;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

/**
 * <b>GraficRadial</b> genera panells amb gràfics radials.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GraficRadial {
	
    private CategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private String titolGrafic;
    
    public GraficRadial(String titolGrafic, boolean llegenda) {
    	this.titolGrafic = titolGrafic;
    	dataset = new DefaultCategoryDataset();
    }

    public ChartPanel generaPanell() {
    	JPanel aux = new JPanel();
    	chart = createChart();
    	chartPanel = new ChartPanel(chart);
    	chart.setBackgroundPaint(aux.getBackground());   
    	aux.add(chartPanel);
    	
    	return chartPanel;
    }
    
    public void afegeixDades(String barra, String categoria, float valor) {
    	 ((DefaultCategoryDataset) dataset).addValue(valor, barra, categoria);   
    }
    
    private JFreeChart createChart() {
    	JPanel aux = new JPanel();
        SpiderWebPlot spiderwebplot = new SpiderWebPlot(dataset);

        
        spiderwebplot.setOutlineVisible(true);
        spiderwebplot.setBackgroundPaint(aux.getBackground());
       
        spiderwebplot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        JFreeChart jfreechart = new JFreeChart(titolGrafic, TextTitle.DEFAULT_FONT, spiderwebplot, false);
        LegendTitle legendtitle = new LegendTitle(spiderwebplot);
        legendtitle.setPosition(RectangleEdge.RIGHT);
        jfreechart.addSubtitle(legendtitle);
    	jfreechart.setBackgroundPaint(aux.getBackground());
    	
        return jfreechart;
    }
    
}