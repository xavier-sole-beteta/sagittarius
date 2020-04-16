package sagittarius.vista.grupsDeTreball;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;
import net.miginfocom.swing.MigLayout;


/**
 * <b>PanellIniciarAgrupament</b> és un dels panells de la vista del mòdul per
 * la creació de grups de treball. Ofereix els controls per iniciar la creació de grups de treball.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellIniciarAgrupament extends JPanel{
	
	private MigLayout layout;
	private JLabel jlTextInformatiu;
	private JButton jbIniciarCreacioDeGrupsDeTreball;
	private JLabel jlEsCrearan;
	private JLabel jlNumGrupsSencers;
	private JLabel jlNumAlumnesSencers;
	private JLabel jlComponentsGrupsSencers;
	private JLabel jlNumGrupsReste;
	private JLabel jlNumAlumnesReste;
	private JLabel jlComponentsGrupsReste;	
	private JLabel jlAlumnes1;	
	private JLabel jlAlumnes2;	
	

	
	public PanellIniciarAgrupament(){

		jlTextInformatiu = new JLabel("Haga click en Iniciar para la creación de grupos de trabajo.");
	
		jbIniciarCreacioDeGrupsDeTreball = new JButton("Iniciar", new ImageIcon(ClassLoader.getSystemResource("resources/iniciar.png")));
		jbIniciarCreacioDeGrupsDeTreball.setEnabled(false);
		jbIniciarCreacioDeGrupsDeTreball.setFocusable(false);
		jbIniciarCreacioDeGrupsDeTreball.setActionCommand("AGRUPAR");
		
		jlEsCrearan = new JLabel();
		jlNumGrupsSencers = new JLabel();;
		jlNumAlumnesSencers = new JLabel();;
		jlNumGrupsReste = new JLabel();;
		jlNumAlumnesReste = new JLabel();;
		jlComponentsGrupsSencers = new JLabel();;
		jlComponentsGrupsReste = new JLabel();;
		jlAlumnes1 = new JLabel();;
		jlAlumnes2 = new JLabel();;
		
		jlEsCrearan.setText("Se propondrán:");
		
		jlNumGrupsSencers.setText("-");
		jlNumAlumnesSencers.setText("grupos de trabajo de");
		jlComponentsGrupsSencers.setText("-");
		
		jlNumGrupsReste.setText("-");
		jlNumAlumnesReste.setText("grupos de trabajo de");
		jlComponentsGrupsReste.setText("-");


		jlAlumnes1.setText("alumnos");
		jlAlumnes2.setText("alumno/s");
		
		layout = new MigLayout("", "[][][][grow]", "[]10[]10[]20[]10[]");
		setLayout(layout);
		setBorder(new TitledBorder("Paso 4. Crear grupos de trabajo"));
		
		add(jlEsCrearan, "span");
		
		add(jlNumGrupsSencers, "gapleft 10");
		add(jlNumAlumnesSencers, "");
		add(jlComponentsGrupsSencers, "");
		add(jlAlumnes1, "wrap");
		
		add(jlNumGrupsReste, "gapleft 10");
		add(jlNumAlumnesReste, "");
		add(jlComponentsGrupsReste, "");
		add(jlAlumnes2, "wrap");
		
		add(jlTextInformatiu, "span");
		
		add(jbIniciarCreacioDeGrupsDeTreball, "span, grow");
		
	}
	
	
	public void setControlador(ControladorGrupsDeTreball controlador){
		jbIniciarCreacioDeGrupsDeTreball.addActionListener(controlador);
	}
	
	
	public void desactivarIniciar(){
		jbIniciarCreacioDeGrupsDeTreball.setEnabled(false);
	}
	
	public void activaIniciar(){
		jbIniciarCreacioDeGrupsDeTreball.setEnabled(true);
	}
	
	public void setNumGrupsSencers(String sNumGrupsSencers){
		jlNumGrupsSencers.setText(sNumGrupsSencers);
	}
	
	public void setComponentsGrupsSencers(String sComponentsGrupsSencers){
		jlComponentsGrupsSencers.setText(sComponentsGrupsSencers);
	}
		
	
	public void setNumGrupsReste(String sNumGrupsReste){
		jlNumGrupsReste.setText(sNumGrupsReste);
	}
	
	public void setComponentsGrupsReste(String sComponentsGrupsReste){
		jlComponentsGrupsReste.setText(sComponentsGrupsReste);
	}
	
	public void activarIniciar(){
		jbIniciarCreacioDeGrupsDeTreball.setEnabled(true);
	}

}
