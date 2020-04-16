package sagittarius.vista.tendencies;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.tendencies.ControladorTendencies;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellConfigurarTendencies</b> és un dels panells que conformen la vista
 * del mòdul funcional pel descobriment de tendències. Ofereix controls per 
 * configurar les tendències a detectar.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellConfigurarTendencies extends JPanel{

	private MigLayout layout;
	private JLabel jlNumeroMaximTendencies;
	private JTextField jtfNumeroMaximTendencies;
	private JLabel jlRespectarTemporalitat;
	private ButtonGroup gbTemporalitat;
	private JRadioButton jrbSiTemporalitat;
	private JRadioButton jrbNoTemporalitat;

	public PanellConfigurarTendencies(){
		
		jlNumeroMaximTendencies = new JLabel("Número máximo de tendencias:");
		jtfNumeroMaximTendencies = new JTextField("");
		jtfNumeroMaximTendencies.setEditable(false);
		jtfNumeroMaximTendencies.setFocusable(false);
		jtfNumeroMaximTendencies.setActionCommand("ALUMNESPERGRUP");
		jlRespectarTemporalitat = new JLabel("Respetar temporalidad:");
		gbTemporalitat = new ButtonGroup();
		jrbSiTemporalitat = new JRadioButton();
		jrbSiTemporalitat.setText("Sí (recomendado)");
		jrbNoTemporalitat = new JRadioButton();
		jrbNoTemporalitat.setText("No");
		gbTemporalitat.add(jrbNoTemporalitat);
		gbTemporalitat.add(jrbSiTemporalitat);
		jrbSiTemporalitat.setSelected(true);
		jrbSiTemporalitat.setEnabled(false);
		jrbNoTemporalitat.setEnabled(false);

		layout = new MigLayout("fillx", "[left][grow,fill]", "[]10[][]");
		setLayout(layout);
		setBorder(new TitledBorder("Paso 2. Definir características"));
		
		add(jlNumeroMaximTendencies,"");
		add(jtfNumeroMaximTendencies,"wrap");
		add(jlRespectarTemporalitat,"");
		add(jrbNoTemporalitat,"wrap");
		add(jrbSiTemporalitat,"cell 1 2");
		
	}
	
	public JTextField getJTFNumAlumnes(){
		return jtfNumeroMaximTendencies;
	}
	
	public void activarTemporalitat(){
		jrbNoTemporalitat.setEnabled(true);
		jrbSiTemporalitat.setEnabled(true);
	}
	
	public void activaNumMaximTendencies(){
		jtfNumeroMaximTendencies.setEditable(true);
		jtfNumeroMaximTendencies.setFocusable(true);
	}
	
	public String getNumMaximTendencies(){
		return jtfNumeroMaximTendencies.getText();
	}
	
	public JTextField getJTFNumMaximTendencies(){
		return jtfNumeroMaximTendencies;
	}
	
	public void setControlador(ControladorTendencies controlador){
		jtfNumeroMaximTendencies.addActionListener(controlador);
		jtfNumeroMaximTendencies.addKeyListener(controlador);
	}
	
	public boolean getTemporalitat(){
		return jrbSiTemporalitat.isSelected();
	}
	
	public String getNumeroMaximTendencies(){
		return jtfNumeroMaximTendencies.getText();
	}
	
	public void setNumeroMaximTendencies(String sNom){
		jtfNumeroMaximTendencies.setText(sNom);
	}
}
