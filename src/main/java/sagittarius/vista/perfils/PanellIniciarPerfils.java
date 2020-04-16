package sagittarius.vista.perfils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.perfils.ControladorPerfils;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellIniciarPerfils</b> �s un dels panells que conformen la vista
 * del m�dul funcional per la detecci� de perfils. Ofereix els controls per 
 * iniciar la detecci� de perfils d'alumnes.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellIniciarPerfils extends JPanel{

	private JLabel jlTextInformatiu;
	private MigLayout layout;
	private JButton jbIniciarPerfils;

	
	public PanellIniciarPerfils(){
		jlTextInformatiu = new JLabel("Haga click en Iniciar para la detecci�n de perfiles");
		
		jbIniciarPerfils = new JButton("Iniciar", new ImageIcon(ClassLoader.getSystemResource("resources/iniciar.png")));
		jbIniciarPerfils.setActionCommand("INICIAR");
		jbIniciarPerfils.setFocusable(false);
		jbIniciarPerfils.setEnabled(false);
		
		layout = new MigLayout("fillx", "[]", "[]10[]");
		setLayout(layout);
		setBorder(new TitledBorder("Paso 4. Detectar perfiles"));
		
		add(jlTextInformatiu, "span, wrap");
		add(jbIniciarPerfils, "span, grow");
		
	}
	
	public void setControlador(ControladorPerfils controlador){
		jbIniciarPerfils.addActionListener(controlador);
	}
	
	public void activaIniciar(){
		jbIniciarPerfils.setEnabled(true);
	}
	
}
