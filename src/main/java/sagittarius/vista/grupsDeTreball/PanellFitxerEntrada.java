package sagittarius.vista.grupsDeTreball;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;
import net.miginfocom.swing.MigLayout;


/**
 * <b>PanellFitxerEntrada</b> �s un dels panells que conformen la vista del m�dul per
 * la creaci� de grups de treball. Ofereix els controls per seleccionar el fitxer d'alumnes.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellFitxerEntrada extends JPanel{
	
	private MigLayout layout;
	private JLabel jlFitxerSeleccionat;
	private JLabel jlNumeroAlumnes;
	private JTextField jtfFitxerSeleccionat;
	private JTextField jtfNumeroAlumnes;
	private JButton jbSeleccionarFitxer;
	private JButton jbVeureContingut;
	
	public PanellFitxerEntrada(){
		
		jlFitxerSeleccionat = new JLabel("Fichero seleccionado:");
		jlNumeroAlumnes = new JLabel("N�mero de alumnos:");
		jtfFitxerSeleccionat = new JTextField("");
		jtfNumeroAlumnes= new JTextField("");

		jtfFitxerSeleccionat.setEditable(false);
		jtfFitxerSeleccionat.setFocusable(false);
		jtfFitxerSeleccionat.setActionCommand("FITXERSELECCIONAT");
		jtfNumeroAlumnes.setEditable(false);
		jtfNumeroAlumnes.setFocusable(false);
		
		jbSeleccionarFitxer = new JButton("Seleccionar fichero", new ImageIcon(ClassLoader.getSystemResource("resources/seleccionarFitxer.png")));
		jbSeleccionarFitxer.setFocusable(false);
		jbSeleccionarFitxer.setActionCommand("SELECCIONAR");

		jbVeureContingut = new JButton("Explorar el contenido", new ImageIcon(ClassLoader.getSystemResource("resources/veureContingut.png")));
		jbVeureContingut.setFocusable(false);
		jbVeureContingut.setActionCommand("VEURE");

		jbVeureContingut.setEnabled(false);
		
		layout = new MigLayout("fillx", "[left][grow,fill]", "[]10[]20[][]");
		setLayout(layout);
		setBorder(new TitledBorder("Paso 1. Seleccionar fichero de alumnos"));
		
		add(jlFitxerSeleccionat,"");
		add(jtfFitxerSeleccionat,"wrap");
		add(jlNumeroAlumnes,"");
		add(jtfNumeroAlumnes,"wrap");
		add(jbSeleccionarFitxer,"span 2, growx, wrap");
		add(jbVeureContingut,"span 2, growx");
		
	}
	
	public void setControlador(ControladorGrupsDeTreball controlador){
		jtfFitxerSeleccionat.getDocument().addDocumentListener(controlador);
		jtfFitxerSeleccionat.addActionListener(controlador);
		jbSeleccionarFitxer.addActionListener(controlador);
		jbVeureContingut.addActionListener(controlador);
	}

	public String getNomFitxer(){
		return jtfFitxerSeleccionat.getText();
	}
	
	public void setNomFitxer(String sNom){
		jtfFitxerSeleccionat.setText(sNom);
	}

	public void setNumAlumnes(int iNumeroAlumnes){
		jtfNumeroAlumnes.setText(Integer.toString(iNumeroAlumnes));
	}
	
	public String getNumAlumnes(){
		return jtfNumeroAlumnes.getText();
	}
	
	
	public void activaVeureElements(){
		jbVeureContingut.setEnabled(true);
	}
}
