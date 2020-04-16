package sagittarius.vista.tendencies;

import javax.swing.JPanel;
import javax.swing.JTextField;

import sagittarius.controlador.tendencies.ControladorTendencies;
import sagittarius.vista.tendencies.PanellFitxerEntrada;

import weka.core.Instances;

import net.miginfocom.swing.MigLayout;



/**
 * <b>PanellTendencies</b> �s el panell principal de la vista del m�dul
 * funcional pel descobriment de tend�ncies. Compr�n la resta de panells de la vista.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellTendencies extends JPanel{

	private PanellFitxerEntrada pFitxerEntrada;
	private PanellConfigurarTendencies pConfigurarTendencies;
	private PanellIniciarTendencies pIniciarTendencies;
	private PanellTendenciesDetectades pTendenciesDetectades;
	
	
	public PanellTendencies(){
		
		pFitxerEntrada = new PanellFitxerEntrada();
		pConfigurarTendencies = new PanellConfigurarTendencies();
		pIniciarTendencies = new PanellIniciarTendencies();
		pTendenciesDetectades = new PanellTendenciesDetectades();

		
		this.setLayout(new MigLayout("insets 10 10 10 10", "[]40[grow]", "[]20[]20[]"));
		
		add(pFitxerEntrada, "grow, cell 0 0, w 300!, top");
		add(pConfigurarTendencies, "grow, cell 0 1, w 300!, top");
		add(pIniciarTendencies, "cell 0 2, w 300!, top");
		add(pTendenciesDetectades, "grow, cell 1 0, span 0 3, top");
	}
	
	public void setControlador(ControladorTendencies controlador) {
		pFitxerEntrada.setControlador(controlador);
		pConfigurarTendencies.setControlador(controlador);
		pIniciarTendencies.setControlador(controlador);
	}
	
	/* M�TODES RELACIONATS AMB EL PANELL DEL FITXER D'ENTRADA */
	public String getNomFitxer(){
		return pFitxerEntrada.getNomFitxer();
	}
	
	public void setNomFitxer(String sNom){
		pFitxerEntrada.setNomFitxer(sNom);
	}

	public void setNumAlumnes(int iNumeroAlumnes){
		pFitxerEntrada.setNumAlumnes(iNumeroAlumnes);
	}
	
	public String getNumAlumnes(){
		return pFitxerEntrada.getNumAlumnes();
	}
	
	public void activaVeureElements(){
		pFitxerEntrada.activaVeureElements();
	}
	
	/* M�TODES RELACIONATS AMB EL PANELL DE CONFIGURACIO DE LES TENDENCIES */
	public JTextField getJTFNumAlumnes(){
		return pConfigurarTendencies.getJTFNumAlumnes();
	}
	
	public void activarTemporalitat(){
		pConfigurarTendencies.activarTemporalitat();
	}
	
	public void activaNumMaximTendencies(){
		pConfigurarTendencies.activaNumMaximTendencies();
	}
	
	public String getNumMaximTendencies(){
		return pConfigurarTendencies.getNumMaximTendencies();
	}
	
	public JTextField getJTFNumMaximTendencies(){
		return pConfigurarTendencies.getJTFNumMaximTendencies();
	}
	
	public boolean getTemporalitat(){
		return pConfigurarTendencies.getTemporalitat();
	}
	
	public String getNumeroMaximTendencies(){
		return pConfigurarTendencies.getNumeroMaximTendencies();
	}
	
	public void setNumeroMaximTendencies(String sNom){
		pConfigurarTendencies.setNumeroMaximTendencies(sNom);
	}
	
	/* M�TODES RELACIONATS AMB EL PANELL D'INICIAR TENDENCIES */
	public void activaIniciar(){
		pIniciarTendencies.activaIniciar();
	}

	public void desactivarIniciar(){
		pIniciarTendencies.desactivarIniciar();
	}
	
	/* M�TODES RELACIONATS AMB EL PANELL DE TENDENCIES DETECTADES */
	public void actualitzaTendenciesDetectades(Instances tendencies){
		pTendenciesDetectades.actualitzaTendenciesDetectades(tendencies);
	}
	
	public void actualitzaNumTendencies(String sTendencies){
		pTendenciesDetectades.actualitzaNumTendencies(sTendencies);
	}
	
}
