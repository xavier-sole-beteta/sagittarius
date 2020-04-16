package sagittarius.vista.perfils;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.perfils.ControladorPerfils;


import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellExportar</b> és un dels panells que conformen la vista
 * del mòdul funcional per la detecció de perfils. Ofereix controls per 
 * exportar els perfils detectats.<br/>
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
	private JRadioButton jrbNumeric;
	private JRadioButton jrbLletres;
	private JButton jbExportar;
	
	public PanellExportar() {
		
		jlInformacioExportar = new JLabel("Seleccione la modalidad de exportación a Excel (*.xls).");
		
		jlModeExportar = new JLabel("Modo:");
		bgModeExportar = new ButtonGroup();
		jrbUnUnicFull = new JRadioButton();
		jrbUnUnicFull.setText("Una única hoja");
		jrbUnUnicFull.setSelected(true);
		jrbVarisFulls = new JRadioButton();
		jrbVarisFulls.setText("Una hoja por perfil");
		jrbUnUnicFull.setEnabled(false);
		jrbVarisFulls.setEnabled(false);
		bgModeExportar.add(jrbUnUnicFull);
		bgModeExportar.add(jrbVarisFulls);
		
		jlModeNotes = new JLabel("Notas:");
		bgModeNotes = new ButtonGroup();
		jrbNumeric = new JRadioButton();
		jrbNumeric.setText("Cuantitativa");
		jrbNumeric.setFocusable(false);
		jrbNumeric.setSelected(true);
		jrbNumeric.setEnabled(false);
		jrbLletres = new JRadioButton();
		jrbLletres.setText("Cualitativa");
		jrbLletres.setFocusable(false);
		jrbLletres.setEnabled(false);
		bgModeNotes.add(jrbNumeric);
		bgModeNotes.add(jrbLletres);
		
		jbExportar = new JButton("Exportar", new ImageIcon(ClassLoader.getSystemResource("resources/exportarExcel.png")));
		jbExportar.setFocusable(false);
		jbExportar.setEnabled(false);
		jbExportar.setToolTipText("Exportar perfiles a formato Excel");
		
		layout = new MigLayout("fillx", "[left]20[grow,fill]", "[]10[][]10[][]20[]");
		setLayout(layout);
		setBorder(new TitledBorder("Exportar. Exportar la detección"));
		
		add(jlInformacioExportar, "wrap, span");
		add(jlModeExportar,"");
		add(jrbUnUnicFull,"wrap");
		add(jrbVarisFulls,"cell 1 2, wrap");
		add(jlModeNotes,"");
		add(jrbNumeric,"wrap");
		add(jrbLletres,"cell 1 4, wrap");
		add(jbExportar,"span 2, growx");
	}
	
	public void setControlador(ControladorPerfils controlador){
		jbExportar.setActionCommand("EXPORTAR");
		jbExportar.addActionListener(controlador);
	}
	
	public void activaExportar(){
		jbExportar.setEnabled(true);
	}
	
	public void activaModeExportar(){
		jrbUnUnicFull.setEnabled(true);
		jrbVarisFulls.setEnabled(true);
		jrbLletres.setEnabled(true);
		jrbNumeric.setEnabled(true);
	}
	
	public boolean getUnUnicFull(){
		return jrbUnUnicFull.isSelected();
	}

	public boolean getNumeric(){
		return jrbNumeric.isSelected();
	}
	
}
