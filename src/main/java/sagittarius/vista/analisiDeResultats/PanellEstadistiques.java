package sagittarius.vista.analisiDeResultats;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.analisiDeResultats.ControladorAnalisiDeResultats;
import sagittarius.model.principal.ConfiguracioSagittarius;

import net.miginfocom.swing.MigLayout;


/**
 * <b>PanellEstadistiques</b> és un dels panells de la vista del mòdul
 * per l'anàlisi i visualització de resultats (MAR), el que
 * mostra els valors estadístics de cadascuna de les proves seleccionades.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellEstadistiques extends JPanel{
	
	private MigLayout layout;
	
	private JLabel jlNotaOrigen;
	private JLabel jlNotaNPOrigen;
	private JLabel jlNotaSUSPESOrigen;
	private JLabel jlNotaAPROVATOrigen;
	private JLabel jlNotaNOTABLEOrigen;
	private JLabel jlNotaEXCELLENTOrigen;
	private JLabel jlMITJANAOrigen;
	private JLabel jlMAXIMAOrigen;
	private JLabel jlMINIMAOrigen;
	
	private JLabel jlAlumnesNPOrigen;
	private JLabel jlAlumnesSUSPESOrigen;
	private JLabel jlAlumnesAPROVATOrigen;
	private JLabel jlAlumnesNOTABLEOrigen;
	private JLabel jlAlumnesEXCELLENTOrigen;
	private JLabel jlAlumnesNPPercentatgeOrigen;
	private JLabel jlAlumnesSUSPESPercentatgeOrigen;
	private JLabel jlAlumnesAPROVATPercentatgeOrigen;
	private JLabel jlAlumnesNOTABLEPercentatgeOrigen;
	private JLabel jlAlumnesEXCELLENTPercentatgeOrigen;
	private JLabel jlNotaMITJANAOrigen;
	private JLabel jlNotaMAXIMAOrigen;
	private JLabel jlNotaMINIMAOrigen;
	
	private JLabel jlNotaDesti;
	private JLabel jlNotaNPDesti;
	private JLabel jlNotaSUSPESDesti;
	private JLabel jlNotaAPROVATDesti;
	private JLabel jlNotaNOTABLEDesti;
	private JLabel jlNotaEXCELLENTDesti;
	private JLabel jlAlumnesNPPercentatgeDesti;
	private JLabel jlAlumnesSUSPESPercentatgeDesti;
	private JLabel jlAlumnesAPROVATPercentatgeDesti;
	private JLabel jlAlumnesNOTABLEPercentatgeDesti;
	private JLabel jlAlumnesEXCELLENTPercentatgeDesti;
	private JLabel jlMITJANADesti;
	private JLabel jlMAXIMADesti;
	private JLabel jlMINIMADesti;

	private JLabel jlAlumnesNPDesti;
	private JLabel jlAlumnesSUSPESDesti;
	private JLabel jlAlumnesAPROVATDesti;
	private JLabel jlAlumnesNOTABLEDesti;
	private JLabel jlAlumnesEXCELLENTDesti;
	private JLabel jlNotaMITJANADesti;
	private JLabel jlNotaMAXIMADesti;
	private JLabel jlNotaMINIMADesti;

	private JComboBox jcbCorrelacionOrigen;
	private JComboBox jcbCorrelacionDesti;
	private JButton jbVisualitzar;
	
	
	public PanellEstadistiques(){

		jlNotaOrigen = new JLabel("Prueba (A): --");
		jlNotaOrigen.setFont(jlNotaOrigen.getFont().deriveFont(Font.BOLD));
		jlNotaNPOrigen = new JLabel("       NP:");;
		jlNotaSUSPESOrigen = new JLabel("       Suspenso:");
		jlNotaAPROVATOrigen = new JLabel("       Aprobado:");
		jlNotaNOTABLEOrigen = new JLabel("       Notable:");
		jlNotaEXCELLENTOrigen = new JLabel("       Excelente:");
		jlMITJANAOrigen = new JLabel("       Nota media:");
		jlMAXIMAOrigen = new JLabel("       Nota máxima:");
		jlMINIMAOrigen = new JLabel("       Nota mínima:");
		
		jlAlumnesNPOrigen = new JLabel("--");
		jlAlumnesSUSPESOrigen = new JLabel("--");
		jlAlumnesAPROVATOrigen = new JLabel("--");
		jlAlumnesNOTABLEOrigen = new JLabel("--");
		jlAlumnesEXCELLENTOrigen = new JLabel("--");
		jlAlumnesNPPercentatgeOrigen = new JLabel("--");
		jlAlumnesSUSPESPercentatgeOrigen = new JLabel("--");
		jlAlumnesAPROVATPercentatgeOrigen = new JLabel("--");
		jlAlumnesNOTABLEPercentatgeOrigen = new JLabel("--");
		jlAlumnesEXCELLENTPercentatgeOrigen = new JLabel("--");
		jlNotaMITJANAOrigen = new JLabel("--");
		jlNotaMAXIMAOrigen = new JLabel("--");
		jlNotaMINIMAOrigen = new JLabel("--");
		
		jlNotaDesti = new JLabel("Prueba (B): --");
		jlNotaDesti.setFont(jlNotaDesti.getFont().deriveFont(Font.BOLD));
		jlNotaNPDesti = new JLabel("       NP:");
		jlNotaSUSPESDesti = new JLabel("       Suspenso:");
		jlNotaAPROVATDesti = new JLabel("       Aprobado:");
		jlNotaNOTABLEDesti = new JLabel("       Notable:");
		jlNotaEXCELLENTDesti = new JLabel("       Excelente:");
		jlMITJANADesti = new JLabel("       Nota media:");
		jlMAXIMADesti = new JLabel("       Nota máxima:");
		jlMINIMADesti = new JLabel("       Nota mínima:");
		
		jlAlumnesNPDesti = new JLabel("--");
		jlAlumnesSUSPESDesti = new JLabel("--");
		jlAlumnesAPROVATDesti = new JLabel("--");
		jlAlumnesNOTABLEDesti = new JLabel("--");
		jlAlumnesEXCELLENTDesti = new JLabel("--");
		jlAlumnesNPPercentatgeDesti = new JLabel("--");
		jlAlumnesSUSPESPercentatgeDesti = new JLabel("--");
		jlAlumnesAPROVATPercentatgeDesti = new JLabel("--");
		jlAlumnesNOTABLEPercentatgeDesti = new JLabel("--");
		jlAlumnesEXCELLENTPercentatgeDesti = new JLabel("--");
		jlNotaMITJANADesti = new JLabel("--");
		jlNotaMAXIMADesti = new JLabel("--");
		jlNotaMINIMADesti = new JLabel("--");
		
		layout = new MigLayout("fillx", "[left][left][grow,fill]", "[][][][][][][][][]20[][][][][][][][][]");
		setLayout(layout);
		setBorder(new TitledBorder("Estadísticas de las pruebas seleccionadas"));
		
		add(jlNotaOrigen,"span, wrap");

		add(jlNotaNPOrigen,"");
		add(jlAlumnesNPOrigen,"");
		add(jlAlumnesNPPercentatgeOrigen,"wrap");
		
		add(jlNotaSUSPESOrigen,"");
		add(jlAlumnesSUSPESOrigen,"");
		add(jlAlumnesSUSPESPercentatgeOrigen,"wrap");
		
		add(jlNotaAPROVATOrigen,"");
		add(jlAlumnesAPROVATOrigen,"");
		add(jlAlumnesAPROVATPercentatgeOrigen,"wrap");
		
		add(jlNotaNOTABLEOrigen,"");
		add(jlAlumnesNOTABLEOrigen,"");
		add(jlAlumnesNOTABLEPercentatgeOrigen,"wrap");
		
		add(jlNotaEXCELLENTOrigen,"");
		add(jlAlumnesEXCELLENTOrigen,"");
		add(jlAlumnesEXCELLENTPercentatgeOrigen,"wrap");
		
		add(jlMITJANAOrigen,"");
		add(jlNotaMITJANAOrigen,"wrap");
		
		add(jlMAXIMAOrigen,"");
		add(jlNotaMAXIMAOrigen,"wrap");
		
		add(jlMINIMAOrigen,"");
		add(jlNotaMINIMAOrigen,"wrap");
		
		
		add(jlNotaDesti,"span, wrap");

		add(jlNotaNPDesti,"");
		add(jlAlumnesNPDesti,"");
		add(jlAlumnesNPPercentatgeDesti,"wrap");
		
		add(jlNotaSUSPESDesti,"");
		add(jlAlumnesSUSPESDesti,"");
		add(jlAlumnesSUSPESPercentatgeDesti,"wrap");
		
		add(jlNotaAPROVATDesti,"");
		add(jlAlumnesAPROVATDesti,"");
		add(jlAlumnesAPROVATPercentatgeDesti,"wrap");
		
		add(jlNotaNOTABLEDesti,"");
		add(jlAlumnesNOTABLEDesti,"");
		add(jlAlumnesNOTABLEPercentatgeDesti,"wrap");
		
		add(jlNotaEXCELLENTDesti,"");
		add(jlAlumnesEXCELLENTDesti,"");
		add(jlAlumnesEXCELLENTPercentatgeDesti,"wrap");
		
		add(jlMITJANADesti,"");
		add(jlNotaMITJANADesti,"wrap");
		
		add(jlMAXIMADesti,"");
		add(jlNotaMAXIMADesti,"wrap");
		
		add(jlMINIMADesti,"");
		add(jlNotaMINIMADesti,"wrap");
	}
	
	public void setNotaOrigen(String jlNotaOrigen) {
		this.jlNotaOrigen.setText("Prueba (A): "+jlNotaOrigen);
	}

	public void setAlumnesNPOrigen(String jlAlumnesNPOrigen, String percentatge) {
		this.jlAlumnesNPOrigen.setText("   "+jlAlumnesNPOrigen+" alumno/s");
		this.jlAlumnesNPPercentatgeOrigen.setText("   "+percentatge+" %");
	}

	public void setAlumnesSUSPESOrigen(String jlAlumnesSUSPESOrigen, String percentatge) {
		this.jlAlumnesSUSPESOrigen.setText("   "+jlAlumnesSUSPESOrigen+" alumno/s");
		this.jlAlumnesSUSPESPercentatgeOrigen.setText("   "+percentatge+" %");
	}

	public void setAlumnesAPROVATOrigen(String jlAlumnesAPROVATOrigen, String percentatge) {
		this.jlAlumnesAPROVATOrigen.setText("   "+jlAlumnesAPROVATOrigen+" alumno/s");
		this.jlAlumnesAPROVATPercentatgeOrigen.setText("   "+percentatge+" %");
	}

	public void setAlumnesNOTABLEOrigen(String jlAlumnesNOTABLEOrigen, String percentatge) {
		this.jlAlumnesNOTABLEOrigen.setText("   "+jlAlumnesNOTABLEOrigen+" alumno/s");
		this.jlAlumnesNOTABLEPercentatgeOrigen.setText("   "+percentatge+" %");
	}

	public void setAlumnesEXCELLENTOrigen(String jlAlumnesEXCELLENTOrigen, String percentatge) {
		this.jlAlumnesEXCELLENTOrigen.setText("   "+jlAlumnesEXCELLENTOrigen+" alumno/s");
		this.jlAlumnesEXCELLENTPercentatgeOrigen.setText("   "+percentatge+" %");
	}
	
	public void setNotaMAXIMAOrigen(String jlAlumnesEXCELLENTOrigen) {
		this.jlNotaMAXIMAOrigen.setText("   "+jlAlumnesEXCELLENTOrigen);
	}
	
	public void setNotaMINIMAOrigen(String jlAlumnesEXCELLENTOrigen) {
		this.jlNotaMINIMAOrigen.setText("   "+jlAlumnesEXCELLENTOrigen);
	}
	
	public void setNotaMITJANAOrigen(String jlAlumnesEXCELLENTOrigen) {
		this.jlNotaMITJANAOrigen.setText("   "+jlAlumnesEXCELLENTOrigen);
	}
	
	public void setNotaDesti(String jlNotaDesti) {
		this.jlNotaDesti.setText("Prueba (B): "+jlNotaDesti);
	}

	public void setAlumnesNPDesti(String jlAlumnesNPDesti, String percentatge) {
		this.jlAlumnesNPDesti.setText("   "+jlAlumnesNPDesti+" alumno/s");
		this.jlAlumnesNPPercentatgeDesti.setText("   "+percentatge+" %");
	}

	public void setAlumnesSUSPESDesti(String jlAlumnesSUSPESDesti, String percentatge) {
		this.jlAlumnesSUSPESDesti.setText("   "+jlAlumnesSUSPESDesti+" alumno/s");
		this.jlAlumnesSUSPESPercentatgeDesti.setText("   "+percentatge+" %");
	}

	public void setAlumnesAPROVATDesti(String jlAlumnesAPROVATDesti, String percentatge) {
		this.jlAlumnesAPROVATDesti.setText("   "+jlAlumnesAPROVATDesti+" alumno/s");
		this.jlAlumnesAPROVATPercentatgeDesti.setText("   "+percentatge+" %");
	}

	public void setAlumnesNOTABLEDesti(String jlAlumnesNOTABLEDesti, String percentatge) {
		this.jlAlumnesNOTABLEDesti.setText("   "+jlAlumnesNOTABLEDesti+" alumno/s");
		this.jlAlumnesNOTABLEPercentatgeDesti.setText("   "+percentatge+" %");
	}

	public void setAlumnesEXCELLENTDesti(String jlAlumnesEXCELLENTDesti, String percentatge) {
		this.jlAlumnesEXCELLENTDesti.setText("   "+jlAlumnesEXCELLENTDesti+" alumno/s");
		this.jlAlumnesEXCELLENTPercentatgeDesti.setText("   "+percentatge+" %");
	}
	
	public void setNotaMAXIMADesti(String jlAlumnesEXCELLENTDesti) {
		this.jlNotaMAXIMADesti.setText("   "+jlAlumnesEXCELLENTDesti);
	}
	
	public void setNotaMINIMADesti(String jlAlumnesEXCELLENTDesti) {
		this.jlNotaMINIMADesti.setText("   "+jlAlumnesEXCELLENTDesti);
	}
	
	public void setNotaMITJANADesti(String jlAlumnesEXCELLENTDesti) {
		this.jlNotaMITJANADesti.setText("   "+jlAlumnesEXCELLENTDesti);
	}

	public void setNota(String[] notes) {
		jcbCorrelacionOrigen.removeAllItems();
		jcbCorrelacionDesti.removeAllItems();
		for(int i=ConfiguracioSagittarius.numAtributsIdentificadorsIntern; i<notes.length; i++) {
			jcbCorrelacionOrigen.insertItemAt(notes[i], i-ConfiguracioSagittarius.numAtributsIdentificadorsIntern);
			jcbCorrelacionDesti.insertItemAt(notes[i], i-ConfiguracioSagittarius.numAtributsIdentificadorsIntern);
		}
		jcbCorrelacionOrigen.setEnabled(true);
		jcbCorrelacionDesti.setEnabled(true);
		jcbCorrelacionOrigen.setSelectedIndex(0);
		jcbCorrelacionDesti.setSelectedIndex(0);
	}
	
	public String getSeleccioNotaOrigen() {
		return (String)jcbCorrelacionOrigen.getSelectedItem();
	}
	
	public String getSeleccioNotaDesti() {
		return (String)jcbCorrelacionDesti.getSelectedItem();
	}
	
	public int getIndexNotaOrigen() {
		return jcbCorrelacionOrigen.getSelectedIndex();
	}
	
	public int getIndexNotaDesti() {
		return jcbCorrelacionDesti.getSelectedIndex();
	}
	
	public void setControlador(ControladorAnalisiDeResultats controlador){
		jbVisualitzar.setActionCommand("CORRELACIONS");
		jbVisualitzar.addActionListener(controlador);
	}
	
}