package sagittarius.vista.grupsDeTreball;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import sagittarius.controlador.grupsDeTreball.ControladorPesosAtributs;

import net.miginfocom.swing.MigLayout;

/**
 * <b>DialegPesosAtributs</b> és una finestra per configurar els pesos dels atributs.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class DialegPesosAtributs extends JDialog {

	private JPanel jpExplicacio;
	private JLabel jlTitol;
	private JTextArea jlInformacioConfiguracioPerfils;
	
	private JLabel jlRAvaluacions;
	
	private JPanel jpAtributs;
	private JLabel[] jlNomAtributs;
	private JSlider[] jsPesos;
	private JLabel[] jlImportancia;
	private JScrollPane jspPanell;
	
	private JPanel jpBotons;	
	private JButton jbAcceptar;
	private JButton jbCancelar;

	private int quants;
	
	public DialegPesosAtributs(int quants, String[] noms, Component vista) {
		this.setLocationRelativeTo(vista);
		this.quants = quants;
		
		jlTitol = new JLabel("Configurar la relevancia de las pruebas");
    	jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));
		
		// Panell de l'explicació
		jpExplicacio = new JPanel();
		jlInformacioConfiguracioPerfils = new JTextArea();
		jlInformacioConfiguracioPerfils.setText("Utiliza los controles para determinar la relevancia de cada elemento.\nNota: 100 la máxima importancia y 0 la mínima.");
		jlInformacioConfiguracioPerfils.setEditable(false);  
		jlInformacioConfiguracioPerfils.setOpaque(false);      
		jlInformacioConfiguracioPerfils.setWrapStyleWord(true);  
		jlInformacioConfiguracioPerfils.setLineWrap(true); 
		jlInformacioConfiguracioPerfils.setFont(UIManager.getFont("Label.font"));
		jpExplicacio.setLayout(new BorderLayout());
		jpExplicacio.add(jlInformacioConfiguracioPerfils, BorderLayout.CENTER);
		
		
		jlRAvaluacions = new JLabel("Relevancia de las pruebas:");
		
		// Panell dels atributs
		
		jpAtributs = new JPanel();
		
		jsPesos = new JSlider[quants];
		jlNomAtributs = new JLabel[quants];
		jlImportancia = new JLabel[quants];
		
		for(int i=0; i<quants; i++) {
			jsPesos[i] = new JSlider(0, 100);
			jsPesos[i].setValue(100);
			jsPesos[i].setName(String.valueOf(i));
			jsPesos[i].setMinorTickSpacing(25);
			jsPesos[i].setPaintTicks(true);
			jsPesos[i].setFocusable(false);
			jlNomAtributs[i] = new JLabel(noms[i]);
			jlImportancia[i] = new JLabel("100");
		}
		String files = "";
		
		for(int i=0; i<quants; i++) {
			files = files + "[]10";
		}
		
		MigLayout lAtributs = new MigLayout("fillx", "[left]10[grow,fill]10[left]", files);
		
		jpAtributs.setLayout(lAtributs);

		for(int i=0; i<quants; i++) {
			jpAtributs.add(jlNomAtributs[i]);
			jpAtributs.add(jsPesos[i]);
			jpAtributs.add(jlImportancia[i], "wrap");
		}
						
		// Panell dels botons
		jpBotons = new JPanel();
		jbCancelar = new JButton("Cancel", new ImageIcon(ClassLoader.getSystemResource("resources/cancellar.png")));
		jbCancelar.setFocusable(false);
		jbAcceptar = new JButton("Done", new ImageIcon(ClassLoader.getSystemResource("resources/acceptar.png")));
		jbAcceptar.setFocusable(false);
		
		jpBotons.setLayout(new FlowLayout());
		
		jpBotons.add(jbCancelar);
		jpBotons.add(jbAcceptar);
		
		jspPanell = new JScrollPane(jpAtributs);
		
		
		MigLayout lTots = new MigLayout("fillx", "[grow,fill]", "[]10[]15[]5[]20[]");
		
		setLayout(lTots);
		add(jlTitol, "wrap");
		add(jpExplicacio, "wrap");
		add(jlRAvaluacions, "wrap");
		add(jspPanell, "wrap");
		add(jpBotons, "wrap");
		setIconImage(Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));		
		setTitle("Sagittarius - Configurar la relevancia de las pruebas");
		setAlwaysOnTop(true);
		setSize(450,420);
		setLocationRelativeTo(null);
	}
	
	public void setValor(int quin, int valor) {
		jlImportancia[quin].setText(String.valueOf(valor));
		jsPesos[quin].setValue(valor);
	}
	
	public int getValor(int quin) {
		return jsPesos[quin].getValue();
	}
	
	public void setControlador(ControladorPesosAtributs controlador) {
		for(int i=0; i<quants; i++) {
			jsPesos[i].addChangeListener(controlador);
		}
		jbAcceptar.setActionCommand("ACCEPTAR");
		jbAcceptar.addActionListener(controlador);
		jbCancelar.setActionCommand("CANCELLAR");
		jbCancelar.addActionListener(controlador);
	}

}
