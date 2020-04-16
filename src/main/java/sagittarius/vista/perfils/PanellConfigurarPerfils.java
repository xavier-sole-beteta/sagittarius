package sagittarius.vista.perfils;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.perfils.ControladorPerfils;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellConfigurarPerfils</b> és un dels panells que conformen la vista
 * del mòdul funcional per la detecció de perfils. Ofereix controls per 
 * configurar els perfils que es volen detectar.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellConfigurarPerfils extends JPanel{

	private MigLayout layout;
	private JTextArea jlInformacioConfiguracioPerfils;
	private JLabel jlInformacioNumMaxPerfils;
	private JTextField jtfNumMaxPerfils;
	private JLabel jlInformacioNumMinPerfils;
	private JTextField jtfNumMinPerfils;
	
	public PanellConfigurarPerfils(){
		
		layout = new MigLayout("fillx","[left][grow,fill]","[]20[]10[]10");
		jlInformacioConfiguracioPerfils = new JTextArea();
		jlInformacioConfiguracioPerfils.setText("Opcional. - Indique a continuación el número máximo y mínimo de perfiles a detectar.");
		jlInformacioConfiguracioPerfils.setEditable(false);  
		jlInformacioConfiguracioPerfils.setOpaque(false);      
		jlInformacioConfiguracioPerfils.setWrapStyleWord(true);  
		jlInformacioConfiguracioPerfils.setLineWrap(true); 
		jlInformacioConfiguracioPerfils.setFont(UIManager.getFont("Label.font")); 
		
		jlInformacioNumMaxPerfils = new JLabel();
		jlInformacioNumMaxPerfils.setText("Número máximo de perfiles: ");
		jtfNumMaxPerfils = new JTextField();
		jtfNumMaxPerfils.setEnabled(false);
		
		jlInformacioNumMinPerfils = new JLabel();
		jlInformacioNumMinPerfils.setText("Número mínimo de perfiles: ");	
		jtfNumMinPerfils = new JTextField();
		jtfNumMinPerfils.setEnabled(false);
		
		setLayout(layout);
		setBorder(new TitledBorder("Paso 2. Definir características (opcional)"));
		
		add(jlInformacioConfiguracioPerfils, "span, grow");
		add(jlInformacioNumMinPerfils, "");
		add(jtfNumMinPerfils, "wrap");
		add(jlInformacioNumMaxPerfils, "");
		add(jtfNumMaxPerfils, "wrap");
	}
	
	public void setControlador(ControladorPerfils controlador){
		jtfNumMaxPerfils.addKeyListener(controlador);
		jtfNumMinPerfils.addKeyListener(controlador);
	}
	
	public String getMaxNumPerfils(){
		return jtfNumMaxPerfils.getText();
	}

	public void activaJTFMaxNumPerfils(){
		jtfNumMaxPerfils.setEnabled(true);
	}
	
	public void activaJTFMinNumPerfils(){
		jtfNumMinPerfils.setEnabled(true);
	}
	
	public String getMinNumPerfils(){
		return jtfNumMinPerfils.getText();
	}
	
	public JTextField getJTFMaxNumPerfils(){
		return jtfNumMaxPerfils;
	}
	
	public JTextField getJTFMinNumPerfils(){
		return jtfNumMinPerfils;
	}
	
}
