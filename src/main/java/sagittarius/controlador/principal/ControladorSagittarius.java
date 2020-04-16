package sagittarius.controlador.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import sagittarius.model.principal.ConfiguracioSagittarius;
import sagittarius.vista.general.About;
import sagittarius.vista.principal.FinestraSagittarius;


/**
 * <b>ControladorECOA</b> és la classe controladora del triplet MVC del mòdul principal
 * del sistema Sagittarius.<br/>
 * Emmagatzema una referència a la vista (de tipus FinestraSagittarius).<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ControladorSagittarius extends WindowAdapter implements ActionListener {

	/** La vista */
	private FinestraSagittarius vista;

	public ControladorSagittarius(FinestraSagittarius vista) {
		this.vista = vista;
	}

	public void actionPerformed(ActionEvent event) {
		
		if(event.getActionCommand().equals("OBRIR-PEF")){
			vista.mostraPanellPEF();
		}
		if(event.getActionCommand().equals("OBRIR-CGT")){
			vista.mostraPanellCGT();
		}
		if(event.getActionCommand().equals("OBRIR-DP")){
			vista.mostraPanellDP();
		}
		if(event.getActionCommand().equals("OBRIR-DT")){
			vista.mostraPanellDT();
		}
		if(event.getActionCommand().equals("OBRIR-AR")){
			vista.mostraPanellAR();
		}
		if(event.getActionCommand().equals("OBRIR-ABOUT")){
	    	About a = new About();
	    	a.setModal(true);
	    	a.setVisible(true);
		}
	}

    private boolean esborrarDirectoriTemporal(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = esborrarDirectoriTemporal(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    } 
	
	public void windowClosing(WindowEvent event) {
    	File aux = new File(ConfiguracioSagittarius.tmpFolder);
    	if (aux.exists()) {
    		esborrarDirectoriTemporal(aux);
    		if (ConfiguracioSagittarius.debug) {
    			System.out.println("Directori eliminat");
    		}
    	}
    	event.getWindow().dispose();
    	System.exit(0);
	}
	
}
