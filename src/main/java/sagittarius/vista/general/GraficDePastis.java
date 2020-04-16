package sagittarius.vista.general;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * <b>GraficDePastis</b> genera panells amb gràfics de pastís.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GraficDePastis {
	
	private DefaultPieDataset dataset;
	private JFreeChart jfc;

	private JFreeChart chart;
    private ChartPanel chartPanel;
    private String titolGrafic;
    private boolean llegenda;
	
	public GraficDePastis(String titolGrafic, boolean llegenda) {
		this.titolGrafic = titolGrafic;
		this.llegenda = llegenda;
		dataset = new DefaultPieDataset();
	}
	
	public void afegeixDades(String title, Double numDouble) {
		dataset.setValue(title, numDouble);
	}
	
	private JFreeChart createChart() {
		jfc = ChartFactory.createPieChart3D(
				titolGrafic,  	
	            dataset,      
	            llegenda,  
	            true,
	            false
	        );
		
    	JPanel aux = new JPanel();
    	jfc.setBackgroundPaint(aux.getBackground());
		PiePlot3D  pp = (PiePlot3D) jfc.getPlot();
		pp.setStartAngle(290);
		pp.setDirection(Rotation.CLOCKWISE);
		pp.setForegroundAlpha(0.5f);
		pp.setBackgroundPaint(aux.getBackground());
		pp.setOutlineVisible(false);
		pp.setLabelGap(0.02);
		
		return jfc;
	}
	
    public JPanel generaPanell() {
    	JPanel aux = new JPanel();
    	chart = createChart();
    	chartPanel = new ChartPanel(chart);
    	chart.setBackgroundPaint(aux.getBackground());

    	aux.add(chartPanel);
    	
    	return chartPanel;
    }
	
}