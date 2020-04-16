package sagittarius.vista.analisiDeResultats;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.analisiDeResultats.ControladorAnalisiDeResultats;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellGrafics</b> és un dels panells de la vista del mòdul
 * per l'anàlisi i visualització de resultats (MAR), el que
 * mostra els histogrames i els controls per visualitzar altres gràfics.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellGrafics extends JPanel{

	private MigLayout layout;
	private JButton jbVeureGraficOrigenAmbNP;
	private JButton jbVeureGraficOrigenSenseNP;
	private JButton jbVeureGraficDestiAmbNP;
	private JButton jbVeureGraficDestiSenseNP;

	private JButton jbObrirHistogramaOrigen;
	private JButton jbObrirHistogramaDesti;
	private JButton jbObrirCorrelacions;

	private JButton jbAjudaCorrelacions;
		
	private JLabel jlNotaOrigen;
	private JLabel jlNotaDesti;
	private JLabel jlCorrelacio;

	
	public PanellGrafics(){
		
		JPanel aux1 = new JPanel(); aux1.setPreferredSize(new Dimension(500,500));
		JPanel aux2 = new JPanel(); aux2.setPreferredSize(new Dimension(500,500));
		JPanel aux3 = new JPanel(); aux3.setPreferredSize(new Dimension(500,500));
		
		jlNotaOrigen = new JLabel("<html><center><b>Histograma de los resultados de (A) ---</b></center></html>");
		jlNotaDesti = new JLabel("<html><center><b>Histograma de los resultados de (B) ---<b></center></html>");
		jlCorrelacio = new JLabel("<html><center><b>Correlación de los resultados de (A and B) --- and ---<b></center></html>");
		
		jlNotaOrigen.setFont(jlNotaOrigen.getFont().deriveFont(12f));
		jlNotaDesti.setFont(jlNotaOrigen.getFont());
		jlCorrelacio.setFont(jlNotaOrigen.getFont());

		jbVeureGraficOrigenAmbNP = new JButton("+NA", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficPastis.png")));
		jbVeureGraficOrigenAmbNP.setFocusable(false);
		jbVeureGraficOrigenAmbNP.setBorderPainted(false);
		jbVeureGraficOrigenAmbNP.setEnabled(false);
		
		jbVeureGraficOrigenSenseNP = new JButton("-NA", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficPastis.png")));
		jbVeureGraficOrigenSenseNP.setFocusable(false);
		jbVeureGraficOrigenSenseNP.setBorderPainted(false);
		jbVeureGraficOrigenSenseNP.setEnabled(false);
		
		jbVeureGraficDestiAmbNP = new JButton("+NA", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficPastis.png")));
		jbVeureGraficDestiAmbNP.setFocusable(false);
		jbVeureGraficDestiAmbNP.setBorderPainted(false);
		jbVeureGraficDestiAmbNP.setEnabled(false);
		
		jbVeureGraficDestiSenseNP = new JButton("-NA", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficPastis.png")));
		jbVeureGraficDestiSenseNP.setFocusable(false);
		jbVeureGraficDestiSenseNP.setBorderPainted(false);
		jbVeureGraficDestiSenseNP.setEnabled(false);
		
		jbObrirHistogramaOrigen = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbObrirHistogramaOrigen.setFocusable(false);
		jbObrirHistogramaOrigen.setBorderPainted(false);
		jbObrirHistogramaOrigen.setEnabled(false);
		jbObrirHistogramaDesti = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbObrirHistogramaDesti.setFocusable(false);
		jbObrirHistogramaDesti.setBorderPainted(false);
		jbObrirHistogramaDesti.setEnabled(false);
		jbObrirCorrelacions = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbObrirCorrelacions.setFocusable(false);
		jbObrirCorrelacions.setBorderPainted(false);
		jbObrirCorrelacions.setEnabled(false);
		jbAjudaCorrelacions = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/ajuda.png")));
		jbAjudaCorrelacions.setFocusable(false);
		jbAjudaCorrelacions.setBorderPainted(false);
		jbAjudaCorrelacions.setEnabled(false);
		
		layout = new MigLayout("fillx","[][]","[]10[]20[]10[]20[]10[]");
		this.setLayout(layout);
		setBorder(new TitledBorder("Visualization of histograms and correlations of the results"));
		
		add(jlNotaOrigen, "cell 0 0, grow");
		add(jbVeureGraficOrigenAmbNP, "cell 1 0, right");
		add(jbVeureGraficOrigenSenseNP, "cell 1 0, right");
		add(jbObrirHistogramaOrigen, "cell 1 0, right");
		add(aux1, "cell 0 1, grow, h 230!, span");
		add(jlNotaDesti, "cell 0 2, grow");
		add(jbVeureGraficDestiAmbNP, "cell 1 2, right");
		add(jbVeureGraficDestiSenseNP, "cell 1 2, right");
		add(jbObrirHistogramaDesti, "cell 1 2, right");
		add(aux2, "cell 0 3, grow, h 230!, span");
		add(jlCorrelacio, "cell 0 4, grow");
		add(jbAjudaCorrelacions, "cell 1 4, right");
		add(jbObrirCorrelacions, "cell 1 4, right");
		add(aux3, "cell 0 5, grow, h 230!, span");
	}
	
	public void activaVeureGrafiques() {
		jbVeureGraficOrigenAmbNP.setEnabled(true);
		jbVeureGraficOrigenSenseNP.setEnabled(true);
		jbVeureGraficDestiAmbNP.setEnabled(true);
		jbVeureGraficDestiSenseNP.setEnabled(true);
		jbObrirHistogramaOrigen.setEnabled(true);
		jbObrirHistogramaDesti.setEnabled(true);
		jbObrirCorrelacions.setEnabled(true);
		jbAjudaCorrelacions.setEnabled(true);
	}
	
	public void setControlador(ControladorAnalisiDeResultats controlador){
		jbVeureGraficOrigenAmbNP.setActionCommand("GRAFICA-ORIGEN-AMB-NP");
		jbVeureGraficOrigenSenseNP.setActionCommand("GRAFICA-ORIGEN-SENSE-NP");
		jbVeureGraficDestiAmbNP.setActionCommand("GRAFICA-DESTI-AMB-NP");
		jbVeureGraficDestiSenseNP.setActionCommand("GRAFICA-DESTI-SENSE-NP");
		jbObrirHistogramaOrigen.setActionCommand("OBRIR-HISTOGRAMA-ORIGEN");
		jbObrirHistogramaDesti.setActionCommand("OBRIR-HISTOGRAMA-DESTI");
		jbObrirCorrelacions.setActionCommand("OBRIR-CORRELACIONS");
		jbAjudaCorrelacions.setActionCommand("AJUDA-CORRELACIONS");
		jbVeureGraficOrigenAmbNP.addActionListener(controlador);
		jbVeureGraficOrigenSenseNP.addActionListener(controlador);
		jbVeureGraficDestiAmbNP.addActionListener(controlador);
		jbVeureGraficDestiSenseNP.addActionListener(controlador);
		jbObrirHistogramaOrigen.addActionListener(controlador);
		jbObrirHistogramaDesti.addActionListener(controlador);
		jbObrirCorrelacions.addActionListener(controlador);
		jbAjudaCorrelacions.addActionListener(controlador);
	}

	public void actualitzaGrafic(JPanel pGraficBarres, JPanel pGraficPastis, JPanel pGraficPastisNONP, String notaOrigen, String notaDesti, int mode) {
		this.removeAll();
		if(mode == 0) {
			layout = new MigLayout("fillx","[left][left]","[]20[]");
			this.setLayout(layout);
			setBorder(new TitledBorder("Visualization of histograms and correlations of the results"));
			
			add(pGraficBarres, "span, grow");
			add(pGraficPastis, "grow");
			add(pGraficPastisNONP, "span, grow");
			SwingUtilities.updateComponentTreeUI(this);
		}
		
		if(mode==1) {
			jlNotaOrigen = new JLabel("<html><center><b>Histograma de los resultados de (A) "+notaOrigen+"</b></center></html>");
			jlNotaDesti = new JLabel("<html><center><b>Histograma de los resultados de (B) "+notaDesti+"<b></center></html>");
			jlCorrelacio = new JLabel("<html><center><b>Correlación de los resultados de (A and B) "+notaOrigen+" i "+notaDesti+"<b></center></html>");
			
			jlNotaOrigen.setFont(jlNotaOrigen.getFont().deriveFont(12f));
			jlNotaDesti.setFont(jlNotaOrigen.getFont());
			jlCorrelacio.setFont(jlNotaOrigen.getFont());
			
			layout = new MigLayout("fillx","[][]","[]10[]20[]10[]20[]10[]");
			this.setLayout(layout);
			setBorder(new TitledBorder("Visualización de histogramas y correlación de los resultados"));
			
			add(jlNotaOrigen, "cell 0 0, grow");
			add(jbVeureGraficOrigenAmbNP, "cell 1 0, right");
			add(jbVeureGraficOrigenSenseNP, "cell 1 0, right");
			add(jbObrirHistogramaOrigen, "cell 1 0, right");
			add(pGraficBarres, "cell 0 1, grow, h 230!, span");
			add(jlNotaDesti, "cell 0 2, grow");
			add(jbVeureGraficDestiAmbNP, "cell 1 2, right");
			add(jbVeureGraficDestiSenseNP, "cell 1 2, right");
			add(jbObrirHistogramaDesti, "cell 1 2, right");
			add(pGraficPastis, "cell 0 3, grow, h 230!, span");
			add(jlCorrelacio, "cell 0 4, grow");
			add(jbAjudaCorrelacions, "cell 1 4, right");
			add(jbObrirCorrelacions, "cell 1 4, right");
			add(pGraficPastisNONP, "cell 0 5, grow, h 230!, span");

			SwingUtilities.updateComponentTreeUI(this);
		}
	}	
}