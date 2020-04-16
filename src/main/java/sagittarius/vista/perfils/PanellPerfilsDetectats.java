package sagittarius.vista.perfils;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.perfils.ControladorPerfils;


import net.miginfocom.swing.MigLayout;

import weka.core.Instances;
import weka.gui.arffviewer.ArffPanel;

/**
 * <b>PanellPerfilsDetectats</b> és un dels panells que conformen la vista
 * del mòdul funcional per la detecció de perfils. Ofereix els controls per 
 * visualitzar i navegar pels diferents perfils descoberts.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellPerfilsDetectats extends JPanel{

	private MigLayout layout;
	private JLabel jlInfoPerfilsDetectats;
	private JLabel jlNumPerfilsDetectats;
	private JLabel jlInfoPerfils;
	private JLabel jlInfoCaracteristiquesPerfils;
	private JButton jbObrirFinestraCaracteristiques;
	private ArffPanel apPerfils;
	private JLabel jlInformacioVistaPerfils;
	private JButton jbVeureGraficPastis;
	private JButton jbVeureGraficBarres;
	private JButton jbVeureGraficBarresPercentual;
	private JComboBox jcbVistaPerfils;
	private JLabel jlInformacioAlumnesPerfil;

	private ArffPanel apAlumnesPerfil;
	private JButton jbAnterior;
	private JButton jbSiguiente;
	private JPanel jpResum;
	private JLabel jlInformacioPerfilActual;
	private JLabel jlPerfilActual;
	private JLabel jlInformacioNumAlumnes;
	private JLabel jlNumAlumnes;
	private JLabel jlInformacioPercentatge;
	private JLabel jlPercentatge;
	private JButton jbObrirFinestraAlumnesPerfil;
	
	
	public PanellPerfilsDetectats(){
	
		jlInfoPerfilsDetectats = new JLabel();
		jlInfoPerfilsDetectats.setText("Se han detectado");
		
		jlNumPerfilsDetectats = new JLabel();
		jlNumPerfilsDetectats.setText("-");
		
		jlInfoPerfils = new JLabel();
		jlInfoPerfils.setText("perfiles.");
		
		jlInfoCaracteristiquesPerfils = new JLabel();
		jlInfoCaracteristiquesPerfils.setText("Características de los perfiles:");
		
		jbObrirFinestraCaracteristiques = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbObrirFinestraCaracteristiques.setFocusable(false);
		jbObrirFinestraCaracteristiques.setBorderPainted(false);
		jbObrirFinestraCaracteristiques.setFocusable(false);
		jbObrirFinestraCaracteristiques.setBorderPainted(false);
		jbObrirFinestraCaracteristiques.setEnabled(false);

		
		apPerfils = new ArffPanel();
		
		jlInformacioVistaPerfils = new JLabel();
		jlInformacioVistaPerfils.setText("Modo de visualización de las notas: ");
		
		jbVeureGraficPastis = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficPastis.png")));
		jbVeureGraficPastis.setFocusable(false);
		jbVeureGraficPastis.setBorderPainted(false);
		jbVeureGraficPastis.setActionCommand("GRAFICA");
		jbVeureGraficPastis.setEnabled(false);
		
		jbVeureGraficBarres = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficBarres2.png")));
		jbVeureGraficBarres.setFocusable(false);
		jbVeureGraficBarres.setBorderPainted(false);
		jbVeureGraficBarres.setActionCommand("GRAFIC_BARRES_PERCENTUAL");
		jbVeureGraficBarres.setEnabled(false);
		
		jbVeureGraficBarresPercentual = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/veureGraficBarres1.png")));
		jbVeureGraficBarresPercentual.setFocusable(false);
		jbVeureGraficBarresPercentual.setBorderPainted(false);
		jbVeureGraficBarresPercentual.setActionCommand("GRAFIC_BARRES_DETALLAT");
		jbVeureGraficBarresPercentual.setEnabled(false);
		
		String[] sFormats = { "Cuantitativa", "Cualitativa" };
		jcbVistaPerfils = new JComboBox(sFormats);
		jcbVistaPerfils.setSelectedIndex(0);
		jcbVistaPerfils.setEnabled(false);
		jcbVistaPerfils.setFocusable(false);
		
		jlInformacioAlumnesPerfil = new JLabel();
		jlInformacioAlumnesPerfil.setText("Alumnos del perfil ");
		
	
		jpResum = new JPanel();
		jpResum.setBorder(BorderFactory.createEtchedBorder()); 
		jlInformacioPerfilActual = new JLabel();
		jlInformacioPerfilActual.setText("Perfil actual: ");
		jlPerfilActual = new JLabel();
		jlPerfilActual.setText("-");
		jlInformacioNumAlumnes = new JLabel();
		jlInformacioNumAlumnes.setText("Número de alumnos: ");
		jlNumAlumnes = new JLabel();
		jlNumAlumnes.setText("-");
		jlInformacioPercentatge = new JLabel();
		jlInformacioPercentatge.setText("Porcentaje respecto al total: ");
		jlPercentatge = new JLabel();
		jlPercentatge.setText("-");

		jpResum.setLayout(new MigLayout("","[][left]","[]10[]10[]"));
		jpResum.add(jlInformacioPerfilActual, "");
		jpResum.add(jlPerfilActual, "wrap");
		jpResum.add(jlInformacioNumAlumnes, "");
		jpResum.add(jlNumAlumnes, "wrap");
		jpResum.add(jlInformacioPercentatge, "");
		jpResum.add(jlPercentatge, "wrap");
		
		apAlumnesPerfil = new ArffPanel();
		
		jbAnterior = new JButton(" Anterior", new ImageIcon(ClassLoader.getSystemResource("resources/anterior.png")));
		jbAnterior.setFocusable(false);
		jbAnterior.setEnabled(false);
		
		jbSiguiente = new JButton("Siguiente ", new ImageIcon(ClassLoader.getSystemResource("resources/seguent.png")));
		jbSiguiente.setHorizontalTextPosition(JButton.LEFT);
		jbSiguiente.setFocusable(false);
		jbSiguiente.setEnabled(false);
		
		jbSiguiente.setActionCommand("SEGUENT");
		jbAnterior.setActionCommand("ANTERIOR");
		
		layout = new MigLayout("fillx","[][][][][]","[]5[]5[]10[]10[]5[]5[]");
		setLayout(layout);
		setBorder(new TitledBorder("Perfiles detectados"));
		
		add(jlInfoPerfilsDetectats, "");
		add(jlNumPerfilsDetectats, "");
		add(jlInfoPerfils, "wrap");
		add(jlInfoCaracteristiquesPerfils, "");
		add(jbObrirFinestraCaracteristiques, "span, right, wrap");
		add(apPerfils, "span, grow, h 150!, wrap");
		add(jbVeureGraficPastis, "cell 0 3");
		add(jbVeureGraficBarres, "cell 0 3, gapleft 10");
		add(jbVeureGraficBarresPercentual, "cell 0 3, gapleft 10");
		add(jlInformacioVistaPerfils, "cell 4 3, right");
		add(jcbVistaPerfils, "cell 4 3, gapleft 10, right, wrap");
		add(jpResum, "span, grow, wrap");
		jbObrirFinestraAlumnesPerfil = new JButton("", new ImageIcon(ClassLoader.getSystemResource("resources/obrirFinestra.png")));
		jbObrirFinestraAlumnesPerfil.setFocusable(false);
		jbObrirFinestraAlumnesPerfil.setBorderPainted(false);
		jbObrirFinestraAlumnesPerfil.setEnabled(false);
		add(jbObrirFinestraAlumnesPerfil, "span, right, wrap");
		add(apAlumnesPerfil, "span, grow, h 380!, wrap");
		add(jbAnterior, "span2, align left");
		add(jbSiguiente, "span3, align right");
	}
	
	public void setControlador(ControladorPerfils controlador){
		jbVeureGraficBarresPercentual.addActionListener(controlador);
		jbVeureGraficBarres.addActionListener(controlador);
		jbVeureGraficPastis.addActionListener(controlador);
		jcbVistaPerfils.addActionListener(controlador);
		jlPerfilActual.addPropertyChangeListener(controlador);
		jbObrirFinestraCaracteristiques.setActionCommand("OBRIR_CARACTERISTIQUES");
		jbObrirFinestraCaracteristiques.addActionListener(controlador);
		jbObrirFinestraAlumnesPerfil.setActionCommand("OBRIR_ALUMNES");
		jbObrirFinestraAlumnesPerfil.addActionListener(controlador);
		jbAnterior.addActionListener(controlador);
		jbSiguiente.addActionListener(controlador);
	}
	
	public void actualitzaNumPerfilsDetectats(String sPerfils){
		jlNumPerfilsDetectats.setText(sPerfils);
	}
	
	public void actualitzaNumPerfil(String sPerfil){
		jlPerfilActual.setText(sPerfil);
	}

	public String getNumPerfil(){
		return jlPerfilActual.getText();
	}
	
	public void actualitzaAlumnesPerfil(Instances grup){
		apAlumnesPerfil.setInstances(grup);
		apAlumnesPerfil.setOptimalColWidths();
		apAlumnesPerfil.setReadOnly(true);
	}
	
	public void actualitzaPanellPerfils(Instances grup){
		apPerfils.setInstances(grup);
		apPerfils.setOptimalColWidths();
		apPerfils.setReadOnly(true);
	}
	
	public void actualitzaNumAlumnesPerfil(String sNumAlumnes){
		jlNumAlumnes.setText(sNumAlumnes);
	}
	
	public void actualitzaPercentatgePerfil(String sNumAlumnes){
		jlPercentatge.setText(sNumAlumnes);
	}
	
	public void activarVistaPerfils(){
		jcbVistaPerfils.setEnabled(true);
		jbVeureGraficPastis.setEnabled(true);
		jbSiguiente.setEnabled(true);
		jbAnterior.setEnabled(true);
		jbObrirFinestraAlumnesPerfil.setEnabled(true);
		jbObrirFinestraCaracteristiques.setEnabled(true);
		jbVeureGraficBarres.setEnabled(true);
		jbVeureGraficBarresPercentual.setEnabled(true);
	}
	
	public String consultaSeleccioVista() {
		return (String)jcbVistaPerfils.getSelectedItem();
	}
	
}
