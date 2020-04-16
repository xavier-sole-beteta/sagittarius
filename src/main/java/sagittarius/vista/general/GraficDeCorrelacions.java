package sagittarius.vista.general;

import java.awt.Color;    
import javax.swing.JPanel;   
import org.jfree.chart.*;   
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;   
import org.jfree.chart.plot.CategoryPlot;   
import org.jfree.chart.plot.PlotOrientation;   
import org.jfree.chart.renderer.category.StackedBarRenderer;   
import org.jfree.data.category.CategoryDataset;   
import org.jfree.data.category.DefaultCategoryDataset;   
import org.jfree.ui.ApplicationFrame;   
import org.jfree.ui.HorizontalAlignment;   

  
/**
 * <b>GraficDeCorrelacions</b> genera panells amb gràfics de barres de correlacions.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GraficDeCorrelacions extends ApplicationFrame {   
 
    private DefaultCategoryDataset defaultcategorydataset;
    private JFreeChart jfreechart;
    private String titolLlegenda;
   
    public GraficDeCorrelacions(String titolLlegenda){ 
    	super("");
    	this.titolLlegenda = titolLlegenda;
        defaultcategorydataset = new DefaultCategoryDataset();     
    }   
    
    public void afegeixDades(String barra, String categoria, float valor) {  
    	defaultcategorydataset.addValue(valor, barra, categoria);    
    }   
   
    private JFreeChart createChart(CategoryDataset categorydataset) {   
    	JPanel aux = new JPanel();
    	
        JFreeChart jfreechart = ChartFactory.createStackedBarChart("", titolLlegenda, "Students", categorydataset, PlotOrientation.VERTICAL, true, true, false);   
        jfreechart.setBackgroundPaint(aux.getBackground());   
        
        jfreechart.getLegend().setBackgroundPaint(aux.getBackground());
        jfreechart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();   
        categoryplot.setBackgroundPaint(Color.lightGray);   
        categoryplot.setRangeGridlinePaint(Color.white);   
        categoryplot.setForegroundAlpha(0.80f);

        categoryplot.getDomainAxis().setLabelFont( categoryplot.getDomainAxis().getLabelFont().deriveFont(new Float(10)) );

        StackedBarRenderer stackedbarrenderer = (StackedBarRenderer)categoryplot.getRenderer();   
        stackedbarrenderer.setDrawBarOutline(false);   
        stackedbarrenderer.setBaseItemLabelsVisible(true);  
        stackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        
        
        return jfreechart;   
    }   
   
    public ChartPanel generaPanell() {   
        jfreechart = createChart(defaultcategorydataset);   
        ChartPanel panell = new ChartPanel(jfreechart);
        panell.setPopupMenu(null);
        return panell;
    }   

}   