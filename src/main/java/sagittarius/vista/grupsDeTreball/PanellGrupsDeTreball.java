package sagittarius.vista.grupsDeTreball;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import sagittarius.controlador.grupsDeTreball.ControladorGrupsDeTreball;
import weka.core.Instances;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellGrupsDeTreball</b> és el panell principal de la vista del mòdul per
 * la creació de grups de treball. Comprèn la resta de panells que conformen la vista.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellGrupsDeTreball extends JPanel{

	// Vista
	private PanellFitxerEntrada pFitxerEntrada;
	private PanellConfigurarGrups pConfigurarGrups;
	private PanellIniciarAgrupament pIniciarAgrupament;
	private PanellPropostaGrups pPropostaGrups;
	private PanellExportar pExportar;
	private PanellPesosAtributs pPesosAtributs;
	
	
	public PanellGrupsDeTreball(){
		
		pFitxerEntrada = new PanellFitxerEntrada();
		pConfigurarGrups = new PanellConfigurarGrups();
		pIniciarAgrupament = new PanellIniciarAgrupament();
		pPropostaGrups = new PanellPropostaGrups();
		pExportar = new PanellExportar();
		pPesosAtributs = new PanellPesosAtributs();
		

		this.setLayout(new MigLayout("insets 10 10 10 10", "[]40[grow]", "[]20[]20[]20[]20[]"));
		
		add(pFitxerEntrada, "grow, cell 0 0, w 300!, top");
		add(pConfigurarGrups, "grow, cell 0 1, w 300!, top");
		add(pPesosAtributs, "grow, cell 0 2, w 300!, top");
		add(pIniciarAgrupament, "grow, cell 0 3, w 300!, top");
		add(pExportar, "grow, cell 0 4, w 300!, top");
		add(pPropostaGrups, "grow, cell 1 0, span 0 5, top");
		
	}
	
	public void setControlador(ControladorGrupsDeTreball controlador) {
		pFitxerEntrada.setControlador(controlador);
		pConfigurarGrups.setControlador(controlador);
		pIniciarAgrupament.setControlador(controlador);
		pPropostaGrups.setControlador(controlador);
		pExportar.setControlador(controlador);
		pPesosAtributs.setControlador(controlador);
	}
	
	
	/* MÈTODES RELACIONATS AMB EL PANELL DEL FITXER D'ENTRADA */
	public String getNomFitxer() {
		return pFitxerEntrada.getNomFitxer();
	}
	
	public void setNomFitxer(String sNomFitxer) {
		pFitxerEntrada.setNomFitxer(sNomFitxer);		
	}
	
	public void setNumAlumnesFitxer(int numAlumnes) {
		pFitxerEntrada.setNumAlumnes(numAlumnes);		
	}
	
	public void activaVeureElementsFitxer() {
		pFitxerEntrada.activaVeureElements();		
	}
	
	public String getNumAlumnesFitxer() {
		return pFitxerEntrada.getNumAlumnes();
	}

	/* MÈTODES RELACIONATS AMB EL PANELL DE CONFIGURACIÓ */
	public boolean getGrupsHeterogenisConfiguracio() {
		return pConfigurarGrups.getGrupsHeterogenis();
	}
	
	public void activaNumAlumnesConfiguracio() {
		pConfigurarGrups.activaNumAlumnes();
	}
	
	public void activaTipusGrupsConfiguracio() {
		pConfigurarGrups.activaTipusGrups();
	}
	
	public JTextField getJTFNumAlumnesConfiguracio() {
		return pConfigurarGrups.getJTFNumAlumnes();
	}
	
	public String getNumAlumnesConfiguracio() {
		return pConfigurarGrups.getNumAlumnes();
	}

	/* MÈTODES RELACIONATS AMB EL PANELL D'AGRUPAMENT */
	public void setNumGrupsSencers(String valor) {
		pIniciarAgrupament.setNumGrupsSencers(valor);
	}
	
	public void setComponentsGrupsSencers(String gSencers){
		pIniciarAgrupament.setComponentsGrupsSencers(gSencers);
	}
	
	public void setNumGrupsReste(String gReste) {
		pIniciarAgrupament.setNumGrupsReste(gReste);		
	}
	
	public void setComponentsGrupsReste(String gReste) {
		pIniciarAgrupament.setComponentsGrupsReste(gReste);
	}
	
	public void desactivarIniciar() {
		pIniciarAgrupament.desactivarIniciar();
	}

	public void activarIniciar() {
		pIniciarAgrupament.activarIniciar();
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL D'EXPORTAR */
	public void activaModeExportar() {
		pExportar.activaModeExportar();
	}
	
	public void activaExportar() {
		pExportar.activaExportar();		
	}

	public boolean getUnUnicFull() {
		return pExportar.getUnUnicFull();			
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL DE LA PROPOSTA GRUPS */
	public void actualitzaPanellCaracteristiques(Instances grup){
		pPropostaGrups.actualitzaPanellCaracteristiques(grup);
	}
	
	public void actualitzaPanellMembres(Instances grup){
		pPropostaGrups.actualitzaPanellMembres(grup);
	}
	
	public void actualitzaGrau(String sGrau){
		pPropostaGrups.actualitzaGrau(sGrau);
	}
	
	public String getGrup(){
		return pPropostaGrups.getGrup();
	}

	public void actualitzaNumIntegrants(String sIntegrants){
		pPropostaGrups.actualitzaNumIntegrants(sIntegrants);
	}
	
	public void setGrup(String sGrup){
		pPropostaGrups.setGrup(sGrup);
	}
	
	public void activaSiguiente(){
		pPropostaGrups.activaSiguiente();
	}

	public void activaAnterior(){
		pPropostaGrups.activaAnterior();
	}
	
	public void activaTipusVistaCaracteristiques(){
		pPropostaGrups.activaTipusVistaCaracteristiques();
	}

	public void activaVeureGrafica(){
		pPropostaGrups.activaVeureGrafica();
	}
	
	public String consultaSeleccioVista() {
		return (String) pPropostaGrups.consultaSeleccioVista();
	}
	
	public void setControlsador(ControladorGrupsDeTreball controlador){
		pPropostaGrups.setControlador(controlador);
	}
	
	public void actualitzaInformacioGrau(String sTipus){
		pPropostaGrups.actualitzaInformacioGrau(sTipus);
	}

	/* MÈTODES RELACIONATS AMB EL PANELL PESOS DELS ATRIBUTS */
	public void activaConfigurarPesos() {
		pPesosAtributs.activarBotoConfigurar();
	}

}
