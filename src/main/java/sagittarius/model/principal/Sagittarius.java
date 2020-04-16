package sagittarius.model.principal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import sagittarius.controlador.principal.ControladorSagittarius;
import sagittarius.vista.principal.FinestraSagittarius;

/**
 * <b>Sagittarius</b> classe principal del sistema ECOA. Comprèn el procediment principal que
 * crea la vista i el controlador del triple MVC principal del programari. Un cop ha creat
 * la vista principal la fa visible i el sistema està preparat per seu usat.<br/>
 * 
 * @author Xavier Solé (xavier.sole@salle.url.edu)
 * 
 */
public class Sagittarius implements Runnable {
	
	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
		}finally{
			FinestraSagittarius sagittarius = new FinestraSagittarius();
			ControladorSagittarius controlador = new ControladorSagittarius(sagittarius);
			sagittarius.setControlador(controlador);
	    	sagittarius.setVisible(true);
		}
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Sagittarius());
	}
}
