package sagittarius.vista.perfils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.perfils.ControladorPerfils;

import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellPesosAtributs</b> és un dels panells que conformen la vista
 * del mòdul funcional per la detecció de perfils. Ofereix els controls per 
 * mostrar una finestra i configurar la rellevància de les proves.<br/>
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
		jlInformacioConfiguracioPerfils.setText("Opcional.- Haga click en Configurar per cambiar la relevancia de las pruebas.");
		jlInformacioConfiguracioPerfils.setEditable(false);  
		jlInformacioConfiguracioPerfils.setOpaque(false);      
		jlInformacioConfiguracioPerfils.setWrapStyleWord(true);  
		jlInformacioConfiguracioPerfils.setLineWrap(true); 
		jlInformacioConfiguracioPerfils.setFont(UIManager.getFont("Label.font")); 
		
		jbPesosAtributs = new JButton("Configurar", new ImageIcon(ClassLoader.getSystemResource("resources/configurar.png")));
		jbPesosAtributs.setEnabled(false);
		jbPesosAtributs.setFocusable(false);
		
		setBorder(new TitledBorder("Paso 3. Configurar relevanció de las pruebas (opcional)"));
		
		setLayout(layout);
		
		add(jlInformacioConfiguracioPerfils, "wrap");
		add(jbPesosAtributs);
	}
	
	public void setControlador(ControladorPerfils controlador) {
		jbPesosAtributs.setActionCommand("CONFIGURAR-PESOS");
		jbPesosAtributs.addActionListener(controlador);
	}
	
	public void activarBotoConfigurar() {
		jbPesosAtributs.setEnabled(true);
	}
	
}
