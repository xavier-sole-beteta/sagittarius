package sagittarius.vista.grupsDeTreball;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;
import weka.core.Instances;
import weka.gui.arffviewer.ArffPanel;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellPropostaGrups</b> és un dels panells de la vista del mòdul per
 * la creació de grups de treball. Ofereix els controls per visualitzar i
 * navegar pels diferents grups de treball creats.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellPropostaGrups extends JPanel{
	
	private MigLayout layout;
	private JLabel jlTextInformatiu;
	private JButton jbSiguiente;
	private JButton jbAnterior;
	private JPanel jpResum;
	private JButton jbVeureAlumnesIntegrants;
	private ArffPanel apComponentsGrup;
	private JLabel jlInformacioCaracteristiques;
	private JButton jbVeureCaracteristiquesAlumnes;
	private JButton jbVeureGraficResultats;
	private JButton jbVeureGraficNotes;
	private ArffPanel apCaracteristiquesGrup;
	private JLabel jlNumeroDeGrup;
	private JLabel jlGrup;
	private JLabel jlInformacioNumeroIntegrants;
	private JLabel jlInformacioIntegrants;
	private JLabel jlNumeroIntegrants;
	private JLabel jlInformacioGrau;
	private JLabel jlGrau;
	private JLabel jlVistaCaracteristiques;
	private JComboBox jcbCaracteristiquesIntegrants;
	
	private JButton jbVeureGrafica;


	public PanellPropostaGrups(){
		
		jlTextInformatiu = new JLabel("Utilice los botones " +
				"Siguiente y Anterior para explorar la propuesta de grups de trabajo.");
	
		apCaracteristiquesGrup = new ArffPanel();
		jlInformacioCaracteristiques = new JLabel();
		jlInformacioCaracteristiques.setText("Características de los integrantes:");
		jbVeureCaracteristiquesAlumnes = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbVeureCaracteristiquesAlumnes.setFocusable(false);
		jbVeureCaracteristiquesAlumnes.setBorderPainted(false);
		jbVeureCaracteristiquesAlumnes.setEnabled(false);
		
		apComponentsGrup = new ArffPanel();
		
		jbVeureGrafica = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficRadial.png")));
		jbVeureGrafica.setHorizontalTextPosition(JButton.LEFT);
		jbVeureGrafica.setBorderPainted(false);
		jbVeureGrafica.setFocusable(false);
		jbVeureGrafica.setToolTipText("Muestra un gráfico radial del grupo");
		jbVeureGrafica.setActionCommand("GRAFICA");
		jbVeureGrafica.setEnabled(false);
		
		jbVeureGraficResultats = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficBarres2.png")));
		jbVeureGraficResultats.setHorizontalTextPosition(JButton.LEFT);
		jbVeureGraficResultats.setBorderPainted(false);
		jbVeureGraficResultats.setFocusable(false);
		jbVeureGraficResultats.setToolTipText("Muestra un gráfico de estadísticas");
		jbVeureGraficResultats.setActionCommand("GRAFICA");
		jbVeureGraficResultats.setEnabled(false);

		jbVeureGraficNotes = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficBarres1.png")));
		jbVeureGraficNotes.setBorderPainted(false);
		jbVeureGraficNotes.setHorizontalTextPosition(JButton.LEFT);
		jbVeureGraficNotes.setFocusable(false);
		jbVeureGraficNotes.setToolTipText("Muestra un gráfico de estadísticas");
		jbVeureGraficNotes.setActionCommand("GRAFICA");
		jbVeureGraficNotes.setEnabled(false);
		
		jlVistaCaracteristiques = new JLabel();
		jlVistaCaracteristiques.setText("Modo de visualización de las notas: ");
		
		String[] sFormats = { "Cuantitativa", "Cualitativa" };
		jcbCaracteristiquesIntegrants = new JComboBox(sFormats);
		jcbCaracteristiquesIntegrants.setSelectedIndex(0);
		jcbCaracteristiquesIntegrants.setEnabled(false);
		jcbCaracteristiquesIntegrants.setFocusable(false);

		jbSiguiente = new JButton("Siguiente ", new ImageIcon(ClassLoader.getSystemResource("resources/seguent.png")));
		jbSiguiente.setHorizontalTextPosition(JButton.LEFT);
		jbSiguiente.setFocusable(false);
		jbSiguiente.setEnabled(false);
		jbAnterior = new JButton("Anterior ", new ImageIcon(ClassLoader.getSystemResource("resources/anterior.png")));
		jbAnterior.setFocusable(false);
		jbAnterior.setEnabled(false);
		
		jbSiguiente.setActionCommand("SEGUENT");
		jbAnterior.setActionCommand("ANTERIOR");
		jlInformacioIntegrants = new JLabel();
		jlInformacioIntegrants.setText("Alumnos integrantes:");
		
		jbVeureAlumnesIntegrants = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbVeureAlumnesIntegrants.setFocusable(false);
		jbVeureAlumnesIntegrants.setBorderPainted(false);
		jbVeureAlumnesIntegrants.setEnabled(false);
		
		jpResum = new JPanel();
		jpResum.setBorder(BorderFactory.createEtchedBorder()); 
		jlNumeroDeGrup = new JLabel();
		jlNumeroDeGrup.setText("Grupo actual: ");
		jlGrup = new JLabel();
		jlGrup.setText("-");
		jlInformacioNumeroIntegrants = new JLabel();
		jlInformacioNumeroIntegrants.setText("Número de alumnos: ");
		jlNumeroIntegrants = new JLabel();
		jlNumeroIntegrants.setText("-");
		jlInformacioGrau = new JLabel();
		jlInformacioGrau.setText("Homogeneidad: ");
		jlGrau = new JLabel();
		jlGrau.setText("-");
		jpResum.setLayout(new MigLayout("","[][left]","[]10[]10[]"));
		jpResum.add(jlNumeroDeGrup, "");
		jpResum.add(jlGrup, "wrap");
		jpResum.add(jlInformacioNumeroIntegrants, "");
		jpResum.add(jlNumeroIntegrants, "wrap");
		jpResum.add(jlInformacioGrau, "");
		jpResum.add(jlGrau, "wrap");
		
		layout = new MigLayout("fillx", "[left][grow][right][]", "[]20[]10[]10[]10[]10[]10[]10[]");
		setLayout(layout);
		setBorder(new TitledBorder("Propuesta de grupos de trebajo"));
		
		add(jlTextInformatiu, "span");
		
		add(jpResum, "grow, span, wrap");

		add(jlInformacioIntegrants , "span, wrap");
		add(jbVeureAlumnesIntegrants, "cell 3 2, right, wrap");
		add(apComponentsGrup, "grow, h 150!, span, wrap");
		
		add(jbVeureGrafica, "cell 0 4, left");
		add(jbVeureGraficResultats, "cell 0 4, left");
		add(jbVeureGraficNotes, "cell 0 4, left");
		add(jlVistaCaracteristiques, "cell 2 4, right");
		add(jcbCaracteristiquesIntegrants, "cell 3 4, grow, right");
		
		add(jlInformacioCaracteristiques, "cell 0 5, left");
		add(jbVeureCaracteristiquesAlumnes, "cell 3 5, right, wrap");		

		add(apCaracteristiquesGrup, "cell 0 6, grow, h 330!, span, wrap");
		add(jbAnterior, "cell 0 7, left");
		add(jbSiguiente, "cell 3 7, right, wrap");

	}
	
	public void actualitzaPanellCaracteristiques(Instances grup){
		apCaracteristiquesGrup.setInstances(grup);
		apCaracteristiquesGrup.setOptimalColWidths();
		apCaracteristiquesGrup.setReadOnly(true);
	}
	
	public void actualitzaPanellMembres(Instances grup){
		apComponentsGrup.setInstances(grup);
		apComponentsGrup.setOptimalColWidths();
		apComponentsGrup.setReadOnly(true);
	}
	
	public void actualitzaGrau(String sGrau){
		jlGrau.setText(sGrau);
	}
	
	
	public String getGrup(){
		return jlGrup.getText();
	}

	public void actualitzaNumIntegrants(String sIntegrants){
		jlNumeroIntegrants.setText(sIntegrants);
	}
	
	public void setGrup(String sGrup){
		jlGrup.setText(sGrup);
	}
	
	public void activaSiguiente(){
		jbSiguiente.setEnabled(true);
	}

	public void activaAnterior(){
		jbAnterior.setEnabled(true);
	}
	
	public void activaTipusVistaCaracteristiques(){
		jcbCaracteristiquesIntegrants.setEnabled(true);
	}

	public void activaVeureGrafica(){
		jbVeureGrafica.setEnabled(true);
		jbVeureGraficResultats.setEnabled(true);
		jbVeureAlumnesIntegrants.setEnabled(true);
		jbVeureCaracteristiquesAlumnes.setEnabled(true);
		jbVeureGraficNotes.setEnabled(true);
	}
	
	public String consultaSeleccioVista() {
		return (String)jcbCaracteristiquesIntegrants.getSelectedItem();
	}
	
	public void setControlador(ControladorGrupsDeTreball controlador){
		jbVeureGraficNotes.setActionCommand("GRAFIC_NOTES");
		jbVeureAlumnesIntegrants.setActionCommand("VEURE_ALUMNES");
		jbVeureCaracteristiquesAlumnes.setActionCommand("VEURE_CARACTERISTIQUES");
		jbVeureCaracteristiquesAlumnes.addActionListener(controlador);
		jbVeureAlumnesIntegrants.addActionListener(controlador);
		jbVeureGrafica.addActionListener(controlador);
		jbVeureGraficResultats.setActionCommand("VEURE_ESTADISTIQUES");
		jbVeureGraficResultats.addActionListener(controlador);
		jbVeureGraficNotes.addActionListener(controlador);
		jcbCaracteristiquesIntegrants.addActionListener(controlador);
		jbSiguiente.addActionListener(controlador);
		jbAnterior.addActionListener(controlador);
		jlGrup.addPropertyChangeListener(controlador);
	}

	public void actualitzaInformacioGrau(String sTipus){
		jlInformacioGrau.setText(sTipus);
	}

}
