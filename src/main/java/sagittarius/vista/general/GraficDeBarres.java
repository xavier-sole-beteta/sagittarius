package sagittarius.vista.general;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;

/**
 * <b>GraficDeBarres</b> genera panells amb gràfics de barres.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GraficDeBarres {

    private CategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    
    private String titolGrafic;
    private String titolEixY;
    private String titolLlegenda;
    private boolean llegenda;
    
    public GraficDeBarres(String titolGrafic, String titolEixY, String titolLlegenda, boolean llegenda) {
    	dataset = new DefaultCategoryDataset();
    	this.titolGrafic = titolGrafic;
    	this.titolEixY = titolEixY;
    	this.titolLlegenda = titolLlegenda;
    	this.llegenda = llegenda;
    }
    
    public JPanel generaPanell() {
    	JPanel aux = new JPanel();
    	chart = createChart(titolGrafic, titolEixY, titolLlegenda, dataset);
    	chartPanel = new ChartPanel(chart);
    	aux.add(chartPanel);
    	
    	return chartPanel;
    }
    
    public void afegeixDades(String barra, String categoria, float valor) {
    	 ((DefaultCategoryDataset) dataset).addValue(valor, barra, categoria);   
    }

    
    private JFreeChart createChart(String titol, String titolEixY, String titolLlegenda, CategoryDataset dataset) {
        
        JFreeChart chart = ChartFactory.createBarChart3D(
            titol,     
            titolLlegenda,             
            titolEixY,              
            dataset,              
            PlotOrientation.VERTICAL, 
            llegenda,              
            true,              
            false         
        );
     
        CategoryPlot plot = chart.getCategoryPlot();
        JPanel aux = new JPanel();
        chart.setBackgroundPaint(aux.getBackground());
        plot.setBackgroundPaint(aux.getBackground());
        ValueMarker marker = new ValueMarker(5f, Color.ORANGE,    
        		                 new BasicStroke(2.0f), Color.ORANGE,    
        		                 new BasicStroke(2.0f), 0.1f);
        chart.getLegend().setBackgroundPaint(aux.getBackground());
        plot.addRangeMarker(marker, Layer.BACKGROUND);   
        
        marker = new ValueMarker(0f, Color.GRAY,    
                new BasicStroke(1.0f), Color.GRAY,    
                new BasicStroke(1.0f), 0.1f);
        
         plot.addRangeMarker(marker, Layer.BACKGROUND);   

        CategoryItemRenderer renderer = plot.getRenderer();
        BarRenderer r = (BarRenderer) renderer;
        r.setMaximumBarWidth(0.05);
        
        renderer.setBaseItemLabelsVisible(true);
        
        return chart;
    }
	
}
