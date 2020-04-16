package sagittarius.vista.tendencies;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import weka.core.Instances;
import weka.gui.arffviewer.ArffPanel;

/**
 * <b>PanellTendenciesDetectades</b> �s un dels panells que conformen la vista
 * del m�dul funcional pel descobriment de tend�ncies. Ofereix els controls per 
 * visualitzar i navegar per les tend�ncies detectades.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellTendenciesDetectades extends JPanel{

	private JLabel jlInformacioNumTendencies;
	private JLabel jlNumTendencies;
	private JLabel jlInformacioTendencies;
	private ArffPanel apTendencies;
	
	private MigLayout layout;
	
	public PanellTendenciesDetectades(){
		
		jlInformacioNumTendencies = new JLabel();
		jlInformacioNumTendencies.setText("Se han detectado");
		
		jlNumTendencies = new JLabel();
		jlNumTendencies.setText("-");
		
		jlInformacioTendencies = new JLabel();
		jlInformacioTendencies.setText("tendencias.");
		
		apTendencies = new ArffPanel();
		
		layout = new MigLayout("fillx","[left][left][grow]","[]20[]");
		this.setLayout(layout);
		setBorder(new TitledBorder("Tendencias detectadas"));
		
		
		add(jlInformacioNumTendencies, "");
		add(jlNumTendencies, "");
		add(jlInformacioTendencies, "wrap");
		add(apTendencies, "span, grow");
		
	}
	
	public void actualitzaTendenciesDetectades(Instances tendencies){
		apTendencies.setInstances(tendencies);
		apTendencies.setOptimalColWidths();
		apTendencies.setReadOnly(true);
	}
	
	public void actualitzaNumTendencies(String sTendencies){
		jlNumTendencies.setText(sTendencies);
	}
	
}