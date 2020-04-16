package sagittarius.vista.prediccioNota;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.prediccioNota.ControladorPrediccioNota;


import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellExportar</b> és un dels panells que conformen la vista
 * del mòdul funcional per la predicció de l'èxit o fracàs de l'alumne. Ofereix controls per 
 * exportar la predicció de notes.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellExportar extends JPanel {
	
	private MigLayout layout;
	private JLabel jlInformacioExportar;
	private JLabel jlModeExportar;
	private ButtonGroup bgModeExportar;
	private JRadioButton jrbUnUnicFull;
	private JRadioButton jrbVarisFulls;
	private JLabel jlModeNotes;
	private ButtonGroup bgModeNotes;
	private JRadioButton jrbResum;
	private JRadioButton jrbHistoric;
	private JButton jbExportar;
	
	public PanellExportar() {
		
		jlInformacioExportar = new JLabel("Seleccione la modalidad de exportación a Excel (*.xls).");
		
		jlModeExportar = new JLabel("Modo:");
		bgModeExportar = new ButtonGroup();
		jrbUnUnicFull = new JRadioButton();
		jrbUnUnicFull.setText("Una única hoja");
		jrbUnUnicFull.setSelected(true);
		jrbVarisFulls = new JRadioButton();
		jrbVarisFulls.setText("Una hoja por nota");
		jrbUnUnicFull.setEnabled(false);
		jrbVarisFulls.setEnabled(false);
		bgModeExportar.add(jrbUnUnicFull);
		bgModeExportar.add(jrbVarisFulls);
		
		jlModeNotes = new JLabel("Notas:");
		bgModeNotes = new ButtonGroup();
		jrbResum = new JRadioButton();
		jrbResum.setText("Resumen");
		jrbResum.setFocusable(false);
		jrbResum.setSelected(true);
		jrbResum.setEnabled(false);
		jrbHistoric = new JRadioButton();
		jrbHistoric.setText("Histórico");
		jrbHistoric.setFocusable(false);
		jrbHistoric.setEnabled(false);
		bgModeNotes.add(jrbResum);
		bgModeNotes.add(jrbHistoric);

		jbExportar = new JButton("Exportar", new ImageIcon(ClassLoader.getSystemResource("resources/exportarExcel.png")));
		jbExportar.setFocusable(false);
		jbExportar.setEnabled(false);
		jbExportar.setToolTipText("Exportar la predicción a formato Excel");
		
		layout = new MigLayout("fillx", "[left]20[grow,fill]", "[]10[][]10[][]20[]");
		setLayout(layout);
		setBorder(new TitledBorder("Exportar. Exportar la predicción"));
		
		add(jlInformacioExportar, "wrap, span");
		add(jlModeExportar,"");
		add(jrbUnUnicFull,"wrap");
		add(jrbVarisFulls,"cell 1 2, wrap");
		add(jlModeNotes,"");
		add(jrbResum,"wrap");
		add(jrbHistoric,"cell 1 4, wrap");
		add(jbExportar,"span 2, growx");
	}
	
	public void setControlador(ControladorPrediccioNota controlador){
		jbExportar.setActionCommand("EXPORTAR");
		jbExportar.addActionListener(controlador);
	}
	
	public void activaExportar(){
		jbExportar.setEnabled(true);
	}
	
	public void activaModeExportar(){
		jrbUnUnicFull.setEnabled(true);
		jrbVarisFulls.setEnabled(true);
		jrbHistoric.setEnabled(true);
		jrbResum.setEnabled(true);
	}
	
	public boolean getUnUnicFull(){
		return jrbUnUnicFull.isSelected();
	}

	public boolean getResum(){
		return jrbResum.isSelected();
	}
	
}
