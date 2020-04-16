package sagittarius.vista.principal;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sagittarius.controlador.analisiDeResultats.ControladorAnalisiDeResultats;
import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;
import sagittarius.controlador.perfils.ControladorPerfils;
import sagittarius.controlador.prediccioNota.ControladorPrediccioNota;
import sagittarius.controlador.tendencies.ControladorTendencies;
import sagittarius.vista.analisiDeResultats.PanellAnalisiDeResultats;
import sagittarius.vista.grupsDeTreball.PanellGrupsDeTreball;
import sagittarius.vista.perfils.PanellPerfils;
import sagittarius.vista.prediccioNota.PanellPrediccioNota;
import sagittarius.vista.tendencies.PanellTendencies;




/*
 * ICONES
 * URL: http://www.iconfinder.com/search/?q=iconset%3Afatcow
 * License: Creative Commons (Attribution 3.0 United States)
 */

/**
 * <b>FinestraSagittarius</b> vista principal de l'aplicació Sagittarius.
 * Comprèn el menú principal amb totes les opcions del programa.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class FinestraSagittarius extends JFrame {

	// Panell principal
	private JPanel jpPrincipal;
	
	// Gestor del panell principal
	private CardLayout clPrincipal;

	// Barra menu principal
	private JMenuBar bMenu;

	// Menu Grups de treball
	private JMenu jmGrupsDeTreball;
	private JMenuItem jmiGrupsDeTreball;
		
	// Menu Perfils
	private JMenu jmPerfils;
	private JMenuItem jmiPerfils;
	
	// Menu Predicció
	private JMenu jmPrediccio;
	// Per incloure la opcio de predicció amb regressió
	// private JMenuItem jmiPrediccioRegressio;
	private JMenuItem jmiPrediccioNotes;

	// Menu Tendencies
	private JMenu jmTendencies;
	private JMenuItem jmiTendencies;
	
	// Menu Visualitzacio
	private JMenu jmVisualitzacio;
	private JMenuItem jmiVisualitzacio;
	
	// Menu Ocions
	private JMenu jmAbout;
	private JMenuItem jmiAbout;
	
	// Panells
	private PanellAnalisiDeResultats pAnalisiDeResultats;
	private sagittarius.vista.prediccioNota.PanellPrediccioNota pPrediccioNota;
	private PanellGrupsDeTreball pGrupsDeTreball;
	private PanellPerfils pPerfils;
	private PanellTendencies pTendencies;
	
	// Panells amb scroll
	private JScrollPane spAnalisiDeResultats;
	private JScrollPane spPrediccioNota;
	private JScrollPane spGrupsDeTreball;
	private JScrollPane spPerfils;
	private JScrollPane spTendencies;
	
	public FinestraSagittarius() {
		inicialitzarComponents();
		this.setPreferredSize(new Dimension(1024, 768));
		this.setSize(1024, 768);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);

	}
	
	private void inicialitzarComponents(){
		bMenu = new JMenuBar();
		this.setJMenuBar(bMenu);
		
		jmVisualitzacio = new JMenu();
		jmVisualitzacio.setText("Análisis de resultados");
		jmVisualitzacio.setMnemonic('R');
		jmiVisualitzacio = new JMenuItem("Analizar resultados", new ImageIcon(ClassLoader.getSystemResource("resources/analisi.png")));
		
		jmVisualitzacio.add(jmiVisualitzacio);
		
		jmGrupsDeTreball = new JMenu();
		jmGrupsDeTreball.setMnemonic('G');
		jmGrupsDeTreball.setText("Creación de grupos");
		
		jmiGrupsDeTreball = new JMenuItem("Crear grupos", new ImageIcon(ClassLoader.getSystemResource("resources/grups.png")));
				
		jmGrupsDeTreball.add(jmiGrupsDeTreball);
		
		jmPerfils = new JMenu();
		jmPerfils.setText("Detección de perfiles");
		jmPerfils.setMnemonic('P');
		
		jmiPerfils = new JMenuItem("Detectar perfiles", new ImageIcon(ClassLoader.getSystemResource("resources/perfils.png")));
		
		jmPerfils.add(jmiPerfils);
		
		jmPrediccio = new JMenu();
		jmPrediccio.setText("Predicción de resultados");
		jmPrediccio.setMnemonic('n');
		jmiPrediccioNotes = new JMenuItem("Predecir resultados", new ImageIcon(ClassLoader.getSystemResource("resources/prediccio.png")));
		
		jmPrediccio.add(jmiPrediccioNotes);
		
		jmTendencies = new JMenu();
		jmTendencies.setText("Detección de tendencias");
		jmTendencies.setMnemonic('T');

		jmiTendencies = new JMenuItem("Detectar tendencias", new ImageIcon(ClassLoader.getSystemResource("resources/tendencies.png")));
		
		jmTendencies.add(jmiTendencies);
		
		bMenu.add(jmPrediccio);
		bMenu.add(jmGrupsDeTreball);
		bMenu.add(jmPerfils);
		bMenu.add(jmTendencies);
		bMenu.add(jmVisualitzacio);
		
		jmAbout = new JMenu("Ayuda");
		jmiAbout= new JMenuItem("Acerca de Sagittarius", new ImageIcon(ClassLoader.getSystemResource("resources/info.png")));
		jmAbout.add(jmiAbout);
		
		bMenu.add(jmAbout);
		
		jpPrincipal = new JPanel();
		clPrincipal = new CardLayout();
		jpPrincipal.setLayout(clPrincipal);
		
		// Panell per l'anàlisi de resultats
		pAnalisiDeResultats = new PanellAnalisiDeResultats();
		ControladorAnalisiDeResultats cAnalisiDeResultats = new ControladorAnalisiDeResultats(pAnalisiDeResultats);
		pAnalisiDeResultats.setControlador(cAnalisiDeResultats);
		spAnalisiDeResultats = new JScrollPane(pAnalisiDeResultats);
		
		// Panell per la predicció de notes
		pPrediccioNota = new PanellPrediccioNota();
		ControladorPrediccioNota cPrediccioNotes = new ControladorPrediccioNota(pPrediccioNota);
		pPrediccioNota.setControlador(cPrediccioNotes);
		spPrediccioNota = new JScrollPane(pPrediccioNota);
		
		// Panell per la generació de grups de treball
		pGrupsDeTreball = new PanellGrupsDeTreball();
		ControladorGrupsDeTreball cGrupsDeTreball = new ControladorGrupsDeTreball(pGrupsDeTreball);
		pGrupsDeTreball.setControlador(cGrupsDeTreball);
		spGrupsDeTreball = new JScrollPane(pGrupsDeTreball);
		
		// Panell per la detecció de perfils
		pPerfils = new PanellPerfils();
		ControladorPerfils cPerfils = new ControladorPerfils(pPerfils);
		pPerfils.setControlador(cPerfils);
		spPerfils = new JScrollPane(pPerfils);
		
		// Panell pel descobriment de tendències
		pTendencies = new PanellTendencies();
		ControladorTendencies cTendencies = new ControladorTendencies(pTendencies);
		pTendencies.setControlador(cTendencies);
		spTendencies = new JScrollPane(pTendencies);
		
		// Afegim els panells a la finestra principal (administrador de disseny CardLayout)
		jpPrincipal.add(spAnalisiDeResultats, "v");
		jpPrincipal.add(spPrediccioNota, "n");
		jpPrincipal.add(spGrupsDeTreball, "c");
		jpPrincipal.add(spTendencies, "t");
		jpPrincipal.add(spPerfils, "f");

		jmGrupsDeTreball.setFont(jmGrupsDeTreball.getFont().deriveFont(12f));
		jmGrupsDeTreball.setFont(jmGrupsDeTreball.getFont().deriveFont(Font.BOLD));
		jmPrediccio.setFont(jmPrediccio.getFont().deriveFont(12f));
		jmPrediccio.setFont(jmPrediccio.getFont().deriveFont(Font.BOLD));
		jmTendencies.setFont(jmTendencies.getFont().deriveFont(12f));
		jmTendencies.setFont(jmTendencies.getFont().deriveFont(Font.BOLD));
		jmPerfils.setFont(jmPerfils.getFont().deriveFont(12f));
		jmPerfils.setFont(jmPerfils.getFont().deriveFont(Font.BOLD));
		jmVisualitzacio.setFont(jmVisualitzacio.getFont().deriveFont(12f));
		jmVisualitzacio.setFont(jmVisualitzacio.getFont().deriveFont(Font.BOLD));
		jmAbout.setFont(jmVisualitzacio.getFont().deriveFont(12f));
		jmiAbout.setFont(jmVisualitzacio.getFont().deriveFont(Font.BOLD));
		
		this.getContentPane().add(jpPrincipal);
		// Mostrem per defecte el panell per la predicció de notes
		mostraPanellPEF();
		setIconImage(Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));
	}
	
	public void setControlador(ActionListener controlador) {
		jmiPrediccioNotes.setActionCommand("OBRIR-PEF");
		jmiPrediccioNotes.addActionListener(controlador);
		jmiGrupsDeTreball.setActionCommand("OBRIR-CGT");
		jmiGrupsDeTreball.addActionListener(controlador);
		jmiPerfils.setActionCommand("OBRIR-DP");
		jmiPerfils.addActionListener(controlador);
		jmiTendencies.setActionCommand("OBRIR-DT");
		jmiTendencies.addActionListener(controlador);
		jmiVisualitzacio.setActionCommand("OBRIR-AR");
		jmiVisualitzacio.addActionListener(controlador);
		jmiAbout.setActionCommand("OBRIR-ABOUT");
		jmiAbout.addActionListener(controlador);
		this.addWindowListener((WindowListener) controlador);
	}
	
	public void mostraPanellDT(){
		clPrincipal.show(jpPrincipal, "t");
		this.setTitle("Sagittarius [DETECCIÓN DE TENDENCIAS]");
	}
	
	public void canviaPanellDades(){
		clPrincipal.show(jpPrincipal, "p");
	}
	
	public void mostraPanellCGT(){
		clPrincipal.show(jpPrincipal, "c");
		this.setTitle("Sagittarius [CREACIÓN DE GRUPOS]");
	}
	
	public void mostraPanellAR(){
		clPrincipal.show(jpPrincipal, "v");
		this.setTitle("Sagittarius [ANÁLISIS DE RESULTADOS]");
	}

	public void mostraPanellPEF(){
		clPrincipal.show(jpPrincipal, "n");
		this.setTitle("Sagittarius [PREDICCIÓN DE RESULTADOS]");
	}
	
	public void mostraPanellDP(){
		clPrincipal.show(jpPrincipal, "f");
		this.setTitle("Sagittarius [DETECCIÓN DE PERFILES]");
	}
	
}
