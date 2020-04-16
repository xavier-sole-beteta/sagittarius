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
 * <b>PanellConfigurarPerfils</b> �s un dels panells que conformen la vista
 * del m�dul funcional per la detecci� de perfils. Ofereix controls per 
 * configurar els perfils que es volen detectar.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
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
		jlInformacioConfiguracioPerfils.setText("Opcional. - Indique a continuaci�n el n�mero m�ximo y m�nimo de perfiles a detectar.");
		jlInformacioConfiguracioPerfils.setEditable(false);  
		jlInformacioConfiguracioPerfils.setOpaque(false);      
		jlInformacioConfiguracioPerfils.setWrapStyleWord(true);  
		jlInformacioConfiguracioPerfils.setLineWrap(true); 
		jlInformacioConfiguracioPerfils.setFont(UIManager.getFont("Label.font")); 
		
		jlInformacioNumMaxPerfils = new JLabel();
		jlInformacioNumMaxPerfils.setText("N�mero m�ximo de perfiles: ");
		jtfNumMaxPerfils = new JTextField();
		jtfNumMaxPerfils.setEnabled(false);
		
		jlInformacioNumMinPerfils = new JLabel();
		jlInformacioNumMinPerfils.setText("N�mero m�nimo de perfiles: ");	
		jtfNumMinPerfils = new JTextField();
		jtfNumMinPerfils.setEnabled(false);
		
		setLayout(layout);
		setBorder(new TitledBorder("Paso 2. Definir caracter�sticas (opcional)"));
		
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
