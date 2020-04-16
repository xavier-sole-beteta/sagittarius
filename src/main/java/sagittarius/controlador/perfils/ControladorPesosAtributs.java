package sagittarius.controlador.perfils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sagittarius.model.perfils.ConfiguracioRellevanciaXMeans;
import sagittarius.model.principal.ConfiguracioSagittarius;
import sagittarius.vista.perfils.DialegPesosAtributs;


/**
 * <b>ControladorPesosAtributs</b> �s la classe controladora de la vista per la configuraci�
 * de la rellev�ncia dels atributs (proves disponibles en el fitxer de notes).<br/>
 * 
 * Emmagatzema una refer�ncia a la vista (de tipus DialegPesosAtributs) i el nombre d'atributs
 * que mostra per la seva configuraci�. <br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
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
				ConfiguracioRellevanciaXMeans.pesosAtributs[i] = (float)vista.getValor(i)/(float)100;
				if (ConfiguracioSagittarius.debug) { 
					System.out.println("Pes de l'atribut "+i+": "+ConfiguracioRellevanciaXMeans.pesosAtributs[i]);
				}
			}
			vista.dispose();
		}
		
		if(event.getActionCommand().equals("CANCELLAR")) {
			for(int i=0; i<quantsAtributs; i++) {
				vista.setValor(i, (int) (ConfiguracioRellevanciaXMeans.pesosAtributs[i]*100));
			}
			vista.dispose();
		}
		
	}

}
