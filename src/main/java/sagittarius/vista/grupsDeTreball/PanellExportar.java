package sagittarius.vista.grupsDeTreball;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;


import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellConfigurarGrups</b> és un dels panells que conformen la vista del mòdul per
 * la creació de grups de treball. Ofereix els controls per exportar els resultats (grups).<br/>
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
	private JButton jbExportar;
	
	public PanellExportar() {
		
		jlInformacioExportar = new JLabel("Seleccione la modalidad de exportación a Excel (*.xls).");
		
		jlModeExportar = new JLabel("Modo:");
		bgModeExportar = new ButtonGroup();
		jrbUnUnicFull = new JRadioButton();
		jrbUnUnicFull.setText("Una única hoja");
		jrbUnUnicFull.setSelected(true);
		jrbVarisFulls = new JRadioButton();
		jrbVarisFulls.setText("Una hoja por grupo");
		jrbUnUnicFull.setEnabled(false);
		jrbVarisFulls.setEnabled(false);
		bgModeExportar.add(jrbUnUnicFull);
		bgModeExportar.add(jrbVarisFulls);
		
		jbExportar = new JButton("Exportar", new ImageIcon(ClassLoader.getSystemResource("resources/exportarExcel.png")));
		jbExportar.setFocusable(false);
		jbExportar.setEnabled(false);
		jbExportar.setToolTipText("Exportar grupos a formato Excel");
		
		layout = new MigLayout("fillx", "[left]20[grow,fill]", "[]10[][]20[]");
		setLayout(layout);
		setBorder(new TitledBorder("Exportar. Exportar la propuesta"));
		
		
		add(jlInformacioExportar, "wrap, span");
		add(jlModeExportar,"");
		add(jrbUnUnicFull,"wrap");
		add(jrbVarisFulls,"cell 1 2, wrap");
		add(jbExportar,"span 2, growx");
		
		
	}
	
	public void setControlador(ControladorGrupsDeTreball controlador){
		jbExportar.setActionCommand("EXPORTAR");
		jbExportar.addActionListener(controlador);
	}
	
	public void activaExportar(){
		jbExportar.setEnabled(true);
	}
	
	public void activaModeExportar(){
		jrbUnUnicFull.setEnabled(true);
		jrbVarisFulls.setEnabled(true);
	}
	
	public boolean getUnUnicFull(){
		return jrbUnUnicFull.isSelected();
	}

}
