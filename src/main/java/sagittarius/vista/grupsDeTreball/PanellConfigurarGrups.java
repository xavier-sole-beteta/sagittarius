package sagittarius.vista.grupsDeTreball;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;

import net.miginfocom.swing.MigLayout;


/**
 * <b>PanellConfigurarGrups</b> és un dels panells que conformen la vista del mòdul per
 * la creació de grups de treball. Ofereix els controls per configurar els grups.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellConfigurarGrups extends JPanel{
	
	private MigLayout layout;
	private JLabel jlNumeroAlumnesPerGrup;
	private JTextField jtfNumeroAlumnesPerGrup;
	private JLabel jlTipusGrup;
	private ButtonGroup bgTipusGrup;
	private JRadioButton jrbHomogenis;
	private JRadioButton jrbHeterogenis;

	public PanellConfigurarGrups(){
		
		jlNumeroAlumnesPerGrup = new JLabel("Número de alumnos por grupo:");
		jtfNumeroAlumnesPerGrup = new JTextField("");
		jtfNumeroAlumnesPerGrup.setEditable(false);
		jtfNumeroAlumnesPerGrup.setFocusable(false);
		jtfNumeroAlumnesPerGrup.setActionCommand("ALUMNESPERGRUP");
		jlTipusGrup = new JLabel("Tipo de grupos:");
		bgTipusGrup = new ButtonGroup();
		jrbHomogenis = new JRadioButton();
		jrbHomogenis.setText("Homogéneos");
		jrbHeterogenis = new JRadioButton();
		jrbHeterogenis.setText("Heterogéneos");
		bgTipusGrup.add(jrbHeterogenis);
		bgTipusGrup.add(jrbHomogenis);
		jrbHeterogenis.setSelected(true);
		jrbHeterogenis.setEnabled(false);
		jrbHeterogenis.setActionCommand("HETEROGENIS");
		jrbHomogenis.setEnabled(false);
		jrbHomogenis.setActionCommand("HOMOGENIS");
		
		layout = new MigLayout("fillx", "[left][grow,fill]", "[]10[][]");
		setLayout(layout);
		setBorder(new TitledBorder("Paso 2. Definir características"));
		
		add(jlNumeroAlumnesPerGrup,"");
		add(jtfNumeroAlumnesPerGrup,"wrap");
		add(jlTipusGrup,"");
		add(jrbHeterogenis,"wrap");
		add(jrbHomogenis,"cell 1 2");
		
	}
	
	public JTextField getJTFNumAlumnes(){
		return jtfNumeroAlumnesPerGrup;
	}
	
	public void activaTipusGrups(){
		jrbHeterogenis.setEnabled(true);
		jrbHomogenis.setEnabled(true);
	}
	
	public void activaNumAlumnes(){
		jtfNumeroAlumnesPerGrup.setEditable(true);
		jtfNumeroAlumnesPerGrup.setFocusable(true);
	}
	
	public String getNumAlumnes(){
		return jtfNumeroAlumnesPerGrup.getText();
	}
	
	public void setControlador(ControladorGrupsDeTreball controlador){
		jtfNumeroAlumnesPerGrup.addActionListener(controlador);
		jtfNumeroAlumnesPerGrup.addKeyListener(controlador);
		jrbHeterogenis.addActionListener(controlador);
		jrbHomogenis.addActionListener(controlador);
	}

	public boolean getGrupsHeterogenis(){
		return jrbHeterogenis.isSelected();
	}

}
