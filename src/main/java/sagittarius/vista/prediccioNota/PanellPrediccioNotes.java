package sagittarius.vista.prediccioNota;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.prediccioNota.ControladorPrediccioNota;
import net.miginfocom.swing.MigLayout;
import weka.core.Instances;
import weka.gui.arffviewer.ArffPanel;

/**
 * <b>PanellPrediccioNotes</b> és un dels panells que conformen la vista
 * del mòdul funcional per la predicció de l'èxit o fracàs de l'alumne. Ofereix els controls per 
 * visualitzar i navegar la predicció de notes.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellPrediccioNotes extends JPanel{

	private MigLayout layout;
	
	private JLabel jlInformacioPrediccio;
	
	private JPanel jpResum;
	private JLabel jlInformacioNomNota;
	private JLabel jlNomNota;
	private JLabel jlInformacioNumAlumnes;
	private JLabel jlNumAlumnes;
	private JLabel jlInformacioPercentatge;
	private JLabel jlPercentatge;
	
	private JButton jbVeureGraficPastis;
	private JButton jbObrirAlumnesNota;
	private JButton jbVeureHistograma;
	
	private JButton jbVeureNotaTots;
	private JButton jbVeureNotaNP;
	private JButton jbVeureNotaSuspenso;
	private JButton jbVeureNotaAprobado;
	private JButton jbVeureNotaNotable;
	private JButton jbVeureNotaExcelent;
	
	private ArffPanel apPrediccio;
	private JLabel jlInformacioVistaLlistat;
	private JComboBox jcbVista;
	
	
	public PanellPrediccioNotes(){
		jlInformacioPrediccio = new JLabel();
		jlInformacioPrediccio.setText("Predicción de las notas de la prueba:");
		
		jbObrirAlumnesNota = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbObrirAlumnesNota.setFocusable(false);
		jbObrirAlumnesNota.setBorderPainted(false);
		jbObrirAlumnesNota.setActionCommand("OBRIR_FINESTRA");
		jbObrirAlumnesNota.setEnabled(false);
		
		jbVeureGraficPastis = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficPastis.png")));
		jbVeureGraficPastis.setFocusable(false);
		jbVeureGraficPastis.setBorderPainted(false);
		jbVeureGraficPastis.setActionCommand("PASTIS");
		jbVeureGraficPastis.setEnabled(false);

		jbVeureHistograma = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficBarres2.png")));
		jbVeureHistograma.setFocusable(false);
		jbVeureHistograma.setBorderPainted(false);
		jbVeureHistograma.setActionCommand("HISTOGRAMA");
		jbVeureHistograma.setEnabled(false);
		
		jbVeureNotaTots = new JButton("Todas");
		jbVeureNotaTots.setActionCommand("VEURETOTES");
		jbVeureNotaNP = new JButton("NP");
		jbVeureNotaNP.setActionCommand("VEURENP");
		jbVeureNotaSuspenso = new JButton("Suspenso");
		jbVeureNotaSuspenso.setActionCommand("VEURESUSPES");
		jbVeureNotaAprobado = new JButton("Aprobado");
		jbVeureNotaAprobado.setActionCommand("VEUREAPROVAT");
		jbVeureNotaNotable = new JButton("Notable");
		jbVeureNotaNotable.setActionCommand("VEURENOTABLE");
		jbVeureNotaExcelent = new JButton("Excelente");
		jbVeureNotaExcelent.setActionCommand("VEUREEXCELENT");
		apPrediccio = new ArffPanel();
		
		jbVeureNotaTots.setEnabled(false);
		jbVeureNotaNP.setEnabled(false);
		jbVeureNotaSuspenso.setEnabled(false);
		jbVeureNotaAprobado.setEnabled(false);
		jbVeureNotaNotable.setEnabled(false);
		jbVeureNotaExcelent.setEnabled(false);
		
		jpResum = new JPanel();
		jpResum.setBorder(BorderFactory.createEtchedBorder()); 
		jlInformacioNomNota = new JLabel();
		jlInformacioNomNota.setText("Nota actual: ");
		jlNomNota = new JLabel();
		jlNomNota.setText("-");
		jlInformacioNumAlumnes = new JLabel();
		jlInformacioNumAlumnes.setText("Número de alumnos: ");
		jlNumAlumnes = new JLabel();
		jlNumAlumnes.setText("-");
		jlInformacioPercentatge = new JLabel();
		jlInformacioPercentatge.setText("Porcentaje respecto al total: ");
		jlPercentatge = new JLabel();
		jlPercentatge.setText("-");
		jpResum.setLayout(new MigLayout("","[][left]","[]10[]10[]"));
		jpResum.add(jlInformacioNomNota, "");
		jpResum.add(jlNomNota, "wrap");
		jpResum.add(jlInformacioNumAlumnes, "");
		jpResum.add(jlNumAlumnes, "wrap");
		jpResum.add(jlInformacioPercentatge, "");
		jpResum.add(jlPercentatge, "wrap");
		
		String[] sNotes = { "Resumen", "Histórico" };

		jcbVista = new JComboBox(sNotes);
		jcbVista.setSelectedIndex(0);
		jcbVista.setEnabled(false);
		jcbVista.setEditable(false);
		
		jlInformacioVistaLlistat = new JLabel();
		jlInformacioVistaLlistat.setText("Modo de visualización de las notas: ");
		
		layout = new MigLayout("fillx", "[][right][]", "[]20[]10[]10[]10[]");
		setLayout(layout);
		setBorder(new TitledBorder("Predicción de la prueba"));
		add(jlInformacioPrediccio, "wrap");
		add(jpResum, "grow, span, wrap");
		
		add(jbVeureGraficPastis, "cell 0 2, align left");
		add(jbObrirAlumnesNota, "cell 2 2, align right, wrap");
		
		add(apPrediccio, "grow, span, h 480!, wrap");
		add(jbVeureNotaTots, "cell 0 4, align left");
		add(jbVeureNotaNP, "cell 0 4, align left");
		add(jbVeureNotaSuspenso, "cell 0 4, align left");
		add(jbVeureNotaAprobado, "cell 0 4, align left");
		add(jbVeureNotaNotable, "cell 0 4, align left");
		add(jbVeureNotaExcelent, "cell 0 4, align left");
		add(jlInformacioVistaLlistat, "cell 1 4, align right");
		add(jcbVista, "cell 2 4, grow, align left");		
	}
	
	public void activaTipusVista(){
		jcbVista.setEnabled(true);
		jcbVista.setEditable(false);
	}
	
	public int getTipusVista(){
		return jcbVista.getSelectedIndex();
	}
	
	public void activaBotons() {
		jbVeureNotaTots.setEnabled(true);
		jbVeureNotaNP.setEnabled(true);
		jbVeureNotaSuspenso.setEnabled(true);
		jbVeureNotaAprobado.setEnabled(true);
		jbVeureNotaNotable.setEnabled(true);
		jbVeureNotaExcelent.setEnabled(true);
		jbObrirAlumnesNota.setEnabled(true);
		jbVeureGraficPastis.setEnabled(true);
		jbVeureHistograma.setEnabled(true);
	}
	
	public void actualitzaBotons(int quin){
		if(quin==0){
			jbVeureNotaTots.setEnabled(false);
			jbVeureNotaNP.setEnabled(true);
			jbVeureNotaSuspenso.setEnabled(true);
			jbVeureNotaAprobado.setEnabled(true);
			jbVeureNotaNotable.setEnabled(true);
			jbVeureNotaExcelent.setEnabled(true);
		}
		if(quin==1){
			jbVeureNotaTots.setEnabled(true);
			jbVeureNotaNP.setEnabled(false);
			jbVeureNotaSuspenso.setEnabled(true);
			jbVeureNotaAprobado.setEnabled(true);
			jbVeureNotaNotable.setEnabled(true);
			jbVeureNotaExcelent.setEnabled(true);
		}
		if(quin==2){
			jbVeureNotaTots.setEnabled(true);
			jbVeureNotaNP.setEnabled(true);
			jbVeureNotaSuspenso.setEnabled(false);
			jbVeureNotaAprobado.setEnabled(true);
			jbVeureNotaNotable.setEnabled(true);
			jbVeureNotaExcelent.setEnabled(true);
		}
		if(quin==3){
			jbVeureNotaTots.setEnabled(true);
			jbVeureNotaNP.setEnabled(true);
			jbVeureNotaSuspenso.setEnabled(true);
			jbVeureNotaAprobado.setEnabled(false);
			jbVeureNotaNotable.setEnabled(true);
			jbVeureNotaExcelent.setEnabled(true);
		}
		if(quin==4){
			jbVeureNotaTots.setEnabled(true);
			jbVeureNotaNP.setEnabled(true);
			jbVeureNotaSuspenso.setEnabled(true);
			jbVeureNotaAprobado.setEnabled(true);
			jbVeureNotaNotable.setEnabled(false);
			jbVeureNotaExcelent.setEnabled(true);
		}
		if(quin==5){
			jbVeureNotaTots.setEnabled(true);
			jbVeureNotaNP.setEnabled(true);
			jbVeureNotaSuspenso.setEnabled(true);
			jbVeureNotaAprobado.setEnabled(true);
			jbVeureNotaNotable.setEnabled(true);
			jbVeureNotaExcelent.setEnabled(false);
		}
	}
	
	public void actualitzaNota(String sNota){
		jlNomNota.setText(sNota);
	}
	
	public void actualitzaNumAlumnes(String sNumAlumnes){
		jlNumAlumnes.setText(sNumAlumnes);
	}
	
	public void actualitzaPercentatge(String sPercentatge){
		jlPercentatge.setText(sPercentatge);
	}
	
	public void actualitzaPrediccio(Instances iPrediccio){
		apPrediccio.setInstances(iPrediccio);
		apPrediccio.setOptimalColWidths();
		apPrediccio.setReadOnly(true);
	}
	
	public void setControlador(ControladorPrediccioNota controlador){
		jbVeureNotaTots.addActionListener(controlador);
		jbVeureNotaNP.addActionListener(controlador);
		jbVeureNotaSuspenso.addActionListener(controlador);
		jbVeureNotaAprobado.addActionListener(controlador);
		jbVeureNotaNotable.addActionListener(controlador);
		jbVeureNotaExcelent.addActionListener(controlador);
		jcbVista.addActionListener(controlador);
		jbObrirAlumnesNota.addActionListener(controlador);
		jbVeureGraficPastis.addActionListener(controlador);
		jbVeureHistograma.addActionListener(controlador);
	}
	
	
}
