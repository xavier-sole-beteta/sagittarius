package sagittarius.vista.grupsDeTreball;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellPesosAtributs</b> és un dels panells de la vista del mòdul per
 * la creació de grups de treball. Ofereix els controls per obrir la finestra
 * per configurar la rellevància dels resultats/proves.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellPesosAtributs extends JPanel {

	private MigLayout layout;
	private JTextArea jlInformacioConfiguracioPerfils;
	private JButton jbPesosAtributs;
	
	
	public PanellPesosAtributs() {
		layout = new MigLayout("fillx","[fill, grow]","[]20[]");
		jlInformacioConfiguracioPerfils = new JTextArea();
		jlInformacioConfiguracioPerfils.setText("Opcional.- Haga click en Configurar para cambiar la relevancia de las pruebas.");
		jlInformacioConfiguracioPerfils.setEditable(false);  
		jlInformacioConfiguracioPerfils.setOpaque(false);      
		jlInformacioConfiguracioPerfils.setWrapStyleWord(true);  
		jlInformacioConfiguracioPerfils.setLineWrap(true); 
		jlInformacioConfiguracioPerfils.setFont(UIManager.getFont("Label.font")); 
		
		jbPesosAtributs = new JButton("Configurar", new ImageIcon(ClassLoader.getSystemResource("resources/configurar.png")));
		jbPesosAtributs.setEnabled(false);
		jbPesosAtributs.setFocusable(false);
		
		setBorder(new TitledBorder("Paso 3. Configurar relevancia de las pruebas (opcional)"));
		
		setLayout(layout);
		
		add(jlInformacioConfiguracioPerfils, "wrap");
		add(jbPesosAtributs);
	}
	
	public void setControlador(ControladorGrupsDeTreball controlador) {
		jbPesosAtributs.setActionCommand("CONFIGURAR-PESOS");
		jbPesosAtributs.addActionListener(controlador);
	}
	
	public void activarBotoConfigurar() {
		jbPesosAtributs.setEnabled(true);
	}
	
}
