package sagittarius.controlador.grupsDeTreball;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sagittarius.model.grupsDeTreball.ConfiguracioRellevanciaKMeans;
import sagittarius.model.principal.ConfiguracioSagittarius;
import sagittarius.vista.grupsDeTreball.DialegPesosAtributs;


/**
 * <b>ControladorPesosAtributs</b> és la classe controladora de la vista per la configuració
 * de la rellevància dels atributs (proves disponibles en el fitxer de notes).<br/>
 * 
 * Emmagatzema una referència a la vista (de tipus DialegPesosAtributs) i el nombre d'atributs
 * que mostra per la seva configuració. <br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ControladorPesosAtributs implements ChangeListener, ActionListener {

	private DialegPesosAtributs vista;
	private int quantsAtributs;
	
	public ControladorPesosAtributs(DialegPesosAtributs vista, int quantsAtributs) {
		this.vista = vista;
		this.quantsAtributs = quantsAtributs;
	}
	
	public void stateChanged(ChangeEvent event) {
		JSlider font = (JSlider)event.getSource();
		vista.setValor(Integer.valueOf(font.getName()), font.getValue());
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("ACCEPTAR")) {
			for(int i=0; i<quantsAtributs; i++) {
				ConfiguracioRellevanciaKMeans.pesosAtributs[i] = (float)vista.getValor(i)/(float)100;
				if (ConfiguracioSagittarius.debug) { 
					System.out.println("Pes de l'atribut "+i+": "+ConfiguracioRellevanciaKMeans.pesosAtributs[i]);
				}
			}
			vista.dispose();
		}
		
		if(event.getActionCommand().equals("CANCELLAR")) {
			for(int i=0; i<quantsAtributs; i++) {
				vista.setValor(i, (int) (ConfiguracioRellevanciaKMeans.pesosAtributs[i]*100));
			}
			vista.dispose();
		}
		
	}

}
