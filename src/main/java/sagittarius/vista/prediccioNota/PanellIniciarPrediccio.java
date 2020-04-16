package sagittarius.vista.prediccioNota;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.prediccioNota.ControladorPrediccioNota;


import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellIniciarPrediccio</b> �s un dels panells que conformen la vista
 * del m�dul funcional per la predicci� de l'�xit o frac�s de l'alumne. Ofereix els controls per 
 * iniciar la predicci� de notes.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellIniciarPrediccio extends JPanel{

	private JLabel jlInformacioPrediccio;
	private JTextField jlNotaPrediccio;
	private JLabel jlInformacioIniciarPrediccio;
	private JButton jbIniciarPrediccio;
	private MigLayout layout;
	
	public PanellIniciarPrediccio(){
		jlInformacioPrediccio = new JLabel();
		jlInformacioPrediccio.setText("Se predecir�n los resultados de: ");
		
		jlNotaPrediccio = new JTextField();
		jlNotaPrediccio.setText("");
		jlNotaPrediccio.setEditable(false);
		jlNotaPrediccio.setFocusable(false);
		
		jlInformacioIniciarPrediccio = new JLabel();
		jlInformacioIniciarPrediccio.setText("Haga click en Iniciar para la predicci�n.");
		
		jbIniciarPrediccio = new JButton("Iniciar", new ImageIcon(ClassLoader.getSystemResource("resources/iniciar.png")));
		jbIniciarPrediccio.setActionCommand("PREDIR");		
		jbIniciarPrediccio.setEnabled(false);
		
		layout = new MigLayout("", "[][grow]", "[]10[]10[]");
		setLayout(layout);
		setBorder(new TitledBorder("Paso 3. Inicar la predicci�n"));
		
		add(jlInformacioPrediccio, "");
		add(jlNotaPrediccio, "grow,wrap");
		add(jlInformacioIniciarPrediccio, "span");
		add(jbIniciarPrediccio, "span, grow");
		
	}
	
	public void setNotaPrediccio(String sNotaPrediccio){
		jlNotaPrediccio.setText(sNotaPrediccio);
	}
	
	public void activarIniciarPrediccio(){
		jbIniciarPrediccio.setEnabled(true);
	}
	
	public void setControlador(ControladorPrediccioNota controlador){
		jbIniciarPrediccio.addActionListener(controlador);
	}
	
	
}
