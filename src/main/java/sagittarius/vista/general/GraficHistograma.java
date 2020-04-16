package sagittarius.vista.general;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.Layer;

/**
 * <b>GraficHistograma</b> genera panells amb gràfics en forma d'histograma.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GraficHistograma {

	private JFreeChart jfc;
	private String titolGrafic;
	private HistogramDataset histogramdataset;
    private ChartPanel chartPanel;
    private String titolEixX;
    private String titolEixY;
	
    public GraficHistograma(String titolGrafic, String titolEixX, String titolEixY) {
    	this.titolGrafic = titolGrafic;
    	this.titolEixX = titolEixX;
    	this.titolEixY = titolEixY;
    }

	public void afegeixDades(double ad[]) {
	    histogramdataset = new HistogramDataset();
	    histogramdataset.addSeries("H1", ad, 10, 0.0d, 10.0d);
	}
    
    public JPanel generaPanell(Color color) {
    	JPanel aux = new JPanel();
    	aux.setLayout(new BorderLayout());
    	jfc = createChart(histogramdataset, color);
    	chartPanel = new ChartPanel(jfc);
    	chartPanel.setRangeZoomable(false);
    	aux.setBackground(Color.WHITE);
    	aux.add(chartPanel, BorderLayout.CENTER);
    	
    	return chartPanel;
    }
    
    private JFreeChart createChart(IntervalXYDataset intervalxydataset, Color color) {

        JFreeChart jfreechart = ChartFactory.createHistogram(titolGrafic, titolEixX, titolEixY, intervalxydataset, PlotOrientation.VERTICAL, false, false, false);
    	JPanel aux = new JPanel();
        jfreechart.setBackgroundPaint(aux.getBackground());
        
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.setForegroundAlpha(0.50F);
        xyplot.getDomainAxis().setRange(0.0d, 10.0d);

        NumberAxis rangeAxis = (NumberAxis) xyplot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperBound(rangeAxis.getUpperBound()+5.0d);
        
        rangeAxis = (NumberAxis) xyplot.getDomainAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        
        ValueMarker marker = new ValueMarker(5f, Color.BLACK,    
                new BasicStroke(1.0f), Color.BLACK,    
                new BasicStroke(1.0f), 1f);
        
        xyplot.addDomainMarker(marker, Layer.BACKGROUND);
        
        XYBarRenderer xybarrenderer = (XYBarRenderer)xyplot.getRenderer();
        xybarrenderer.setDrawBarOutline(true);
        xybarrenderer.setBaseItemLabelsVisible(true); 
        xybarrenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        
        xybarrenderer.setSeriesPaint(0, color);
        return jfreechart;
    }
}
