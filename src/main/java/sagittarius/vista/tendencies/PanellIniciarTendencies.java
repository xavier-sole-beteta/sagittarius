package sagittarius.vista.tendencies;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import sagittarius.controlador.tendencies.ControladorTendencies;


import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellIniciarTendencies</b> és un dels panells que conformen la vista
 * del mòdul funcional pel descobriment de tendències. Ofereix els controls per 
 * iniciar la detecció de tendències en els resultats.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellIniciarTendencies extends JPanel{

		private JLabel jlTextInformatiu;
		private MigLayout layout;
		private JButton jbIniciarTendencies;

		
		public PanellIniciarTendencies(){
			jlTextInformatiu = new JLabel("Haga click en Iniciar para la detección de tendencias");
			
			jbIniciarTendencies = new JButton("Iniciar", new ImageIcon(ClassLoader.getSystemResource("resources/iniciar.png")));
			jbIniciarTendencies.setActionCommand("INICIAR");
			jbIniciarTendencies.setEnabled(false);
			
			layout = new MigLayout("fillx", "[]", "[]10[]");
			setLayout(layout);
			setBorder(new TitledBorder("Paso 3. Detectar tendencias"));
			
			add(jlTextInformatiu, "span, wrap");
			add(jbIniciarTendencies, "span, grow");
			
		}
		
		public void activaIniciar(){
			jbIniciarTendencies.setEnabled(true);
		}

		public void desactivarIniciar(){
			jbIniciarTendencies.setEnabled(false);
		}
		
		public void setControlador(ControladorTendencies controlador){
			jbIniciarTendencies.addActionListener(controlador);
		}
		
}
