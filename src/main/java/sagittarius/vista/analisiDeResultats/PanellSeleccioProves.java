package sagittarius.vista.analisiDeResultats;


import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.analisiDeResultats.ControladorAnalisiDeResultats;
import sagittarius.model.principal.ConfiguracioSagittarius;
import net.miginfocom.swing.MigLayout;


/**
 * <b>PanellSeleccioProves</b> és un dels panells de la vista del mòdul
 * per l'anàlisi i visualització de resultats (MAR), el que
 * mostra els desplegables per la selecció de les proves a analitzar.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellSeleccioProves extends JPanel{
	
	private MigLayout layout;
	private JLabel jlCorrelacioOrigen;
	private JComboBox jcbCorrelacionOrigen;
	private JLabel jlCorrelacioDesti;
	private JComboBox jcbCorrelacionDesti;
	
	public PanellSeleccioProves(){
		
		jlCorrelacioOrigen = new JLabel("Prueba (A):");
		jlCorrelacioDesti = new JLabel("Prueba (B):");
		
		jcbCorrelacionOrigen = new JComboBox();
		jcbCorrelacionOrigen.setEnabled(false);
		jcbCorrelacionOrigen.setFocusable(false);
		
		jcbCorrelacionDesti = new JComboBox();
		jcbCorrelacionDesti.setEnabled(false);
		jcbCorrelacionDesti.setFocusable(false);

		
		layout = new MigLayout("fillx", "[left][grow,fill]", "[]5[]");
		setLayout(layout);
		setBorder(new TitledBorder("Paso 2. Seleccionar las pruebas a analizar"));
		
		add(jlCorrelacioOrigen,"");
		add(jcbCorrelacionOrigen,"wrap");
		add(jlCorrelacioDesti,"");
		add(jcbCorrelacionDesti,"wrap");
		
		
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
		jcbCorrelacionOrigen.addActionListener(controlador);
		jcbCorrelacionDesti.addActionListener(controlador);
		jcbCorrelacionOrigen.setActionCommand("CORRELACIONS-ORIGEN");
		jcbCorrelacionDesti.setActionCommand("CORRELACIONS-DESTI");
	}

}

