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
 * <b>PanellFitxerEntrada</b> és un dels panells que conformen la vista
 * del mòdul funcional per la predicció de l'èxit o fracàs de l'alumne. Ofereix controls
 * per seleccionar el fitxer d'alumnes (es repeteix tan per l'historic com pel fitxer del curs vigent).<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
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
	
	public PanellFitxerEntrada(String sTitol, String sBotoSeleccionar, String sBotoVeure){
		
		jlFitxerSeleccionat = new JLabel("Fichero seleccionado:");
		jlNumeroAlumnes = new JLabel("Número de alumnos:");
		jtfFitxerSeleccionat = new JTextField("");
		jtfNumeroAlumnes= new JTextField("");

		
		jtfFitxerSeleccionat.setEditable(false);
		jtfFitxerSeleccionat.setFocusable(false);
		jtfFitxerSeleccionat.setActionCommand("FITXERSELECCIONAT");
		jtfNumeroAlumnes.setEditable(false);
		jtfNumeroAlumnes.setFocusable(false);
		
		jbSeleccionarFitxer = new JButton("Seleccionar fichero", new ImageIcon(ClassLoader.getSystemResource("resources/seleccionarFitxer.png")));
		jbSeleccionarFitxer.setActionCommand(sBotoSeleccionar);

		jbVeureContingut = new JButton("Explorar contenido", new ImageIcon(ClassLoader.getSystemResource("resources/veureContingut.png")));
		jbVeureContingut.setActionCommand(sBotoVeure);

		jbVeureContingut.setEnabled(false);
		
		layout = new MigLayout("fillx", "[left][grow,fill]", "[]10[]20[][]");
		setLayout(layout);
		setBorder(new TitledBorder(sTitol));
		
		add(jlFitxerSeleccionat,"");
		add(jtfFitxerSeleccionat,"wrap");
		add(jlNumeroAlumnes,"");
		add(jtfNumeroAlumnes,"wrap");
		add(jbSeleccionarFitxer,"span 2, growx, wrap");
		add(jbVeureContingut,"span 2, growx");
		
	}
	
	public void setControlador(ControladorPrediccioNota controlador){
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
	
	public JTextField getJTFNomFitxer(){
		return jtfFitxerSeleccionat;
	}
	
	public void activaVeureElements(){
		jbVeureContingut.setEnabled(true);
	}
}
