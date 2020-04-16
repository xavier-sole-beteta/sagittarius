package sagittarius.vista.analisiDeResultats;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.TitledBorder;

import sagittarius.controlador.analisiDeResultats.ControladorAnalisiDeResultats;
import sagittarius.model.principal.ConfiguracioSagittarius;

import net.miginfocom.swing.MigLayout;


/**
 * <b>PanellDistribucio</b> �s un dels panells del m�dul funcional
 * per l'an�lisi i visualitzaci� de resultats (MAR), el que .<br/>
 * Emmagatzema una refer�ncia a cadascun dels panells secundaris.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellDistribucio extends JPanel{
	
	private MigLayout layout;
	private JLabel jlNumeroAlumnesPerGrup;
	private JComboBox jcbNota;

	public PanellDistribucio(){
		
		jlNumeroAlumnesPerGrup = new JLabel("Nota:");
		
		jcbNota = new JComboBox();
		jcbNota.setEnabled(false);
		jcbNota.setFocusable(false);

		layout = new MigLayout("fillx", "[left][grow,fill]", "[]10[]");
		setLayout(layout);
		setBorder(new TitledBorder("Visualizar distribuci�n y pocentajes"));
		
		add(jlNumeroAlumnesPerGrup,"");
		add(jcbNota,"wrap");
	}
	
	public void setNota(String[] notes) {
		jcbNota.removeAllItems();
		for(int i=ConfiguracioSagittarius.numAtributsIdentificadorsIntern; i<notes.length; i++) {
			jcbNota.insertItemAt(notes[i], i-ConfiguracioSagittarius.numAtributsIdentificadorsIntern);
		}
		jcbNota.setEnabled(true);
		jcbNota.setSelectedIndex(0);
	}
	
	public int getSeleccioNota() {
		return jcbNota.getSelectedIndex();
	}
	
	public void setControlador(ControladorAnalisiDeResultats controlador){
		jcbNota.addActionListener(controlador);
	}
	
}
