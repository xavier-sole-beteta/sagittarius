package sagittarius.vista.perfils;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import sagittarius.controlador.perfils.ControladorPerfils;
import weka.core.Instances;

import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellPerfils</b> és el panell principal de la vista del mòdul
 * funcional per la detecció de perfils. Comprèn la resta de panells de la vista.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellPerfils extends JPanel{

	private PanellFitxerEntrada pFitxerEntrada;
	private PanellConfigurarPerfils pConfigurarPerfils;
	private PanellPesosAtributs pPesosAtributs;
	private PanellIniciarPerfils pIniciarPerfils;
	private PanellExportar pExportarPerfils;
	private PanellPerfilsDetectats pPerfilsDetectats;

		
	public PanellPerfils(){
		pFitxerEntrada = new PanellFitxerEntrada();
		pConfigurarPerfils = new PanellConfigurarPerfils();
		pPesosAtributs = new PanellPesosAtributs();
		pIniciarPerfils = new PanellIniciarPerfils();
		pExportarPerfils = new PanellExportar();
		pPerfilsDetectats = new PanellPerfilsDetectats();
				
		this.setLayout(new MigLayout("insets 10 10 10 10", "[]40[grow]", "[]20[]20[]20[]20[]"));
		
		add(pFitxerEntrada, "cell 0 0, w 300!, top");
		add(pConfigurarPerfils, "cell 0 1, w 300!, top");
		add(pPesosAtributs, "cell 0 2, w 300!, top");
		add(pIniciarPerfils, "grow, cell 0 3, w 300!, top");
		add(pExportarPerfils, "cell 0 4, w 300!, top");
		add(pPerfilsDetectats, "grow, cell 1 0, span 0 5, top");
	}
	
	public void setControlador(ControladorPerfils controlador) {
		pFitxerEntrada.setControlador(controlador);
		pConfigurarPerfils.setControlador(controlador);
		pPesosAtributs.setControlador(controlador);
		pIniciarPerfils.setControlador(controlador);
		pExportarPerfils.setControlador(controlador);
		pPerfilsDetectats.setControlador(controlador);
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL DEL FITXER D'ENTRADA */
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
	
	/* MÈTODES RELACIONATS AMB EL PANELL DE CONFIGURACIÓ DELS PERFILS */
	public String getMaxNumPerfils(){
		return pConfigurarPerfils.getMaxNumPerfils();
	}

	public void activaJTFMaxNumPerfils(){
		pConfigurarPerfils.activaJTFMaxNumPerfils();
	}
	
	public void activaJTFMinNumPerfils(){
		pConfigurarPerfils.activaJTFMinNumPerfils();
	}
	
	public String getMinNumPerfils(){
		return pConfigurarPerfils.getMinNumPerfils();
	}
	
	public JTextField getJTFMaxNumPerfils(){
		return pConfigurarPerfils.getJTFMaxNumPerfils();
	}
	
	public JTextField getJTFMinNumPerfils(){
		return pConfigurarPerfils.getJTFMinNumPerfils();
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL D'INICIAR LA DETECCIÓ DELS PERFILS */
	public void activaIniciar(){
		pIniciarPerfils.activaIniciar();
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL D'EXPORTAR ELS PERFILS */
	public void activaExportar(){
		pExportarPerfils.activaExportar();
	}
	
	public void activaModeExportar(){
		pExportarPerfils.activaModeExportar();
	}
	
	public boolean getUnUnicFull(){
		return pExportarPerfils.getUnUnicFull();
	}

	public boolean getNumeric(){
		return pExportarPerfils.getNumeric();
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL DELS PERFILS DETECTATS */
	public void actualitzaNumPerfilsDetectats(String sPerfils){
		pPerfilsDetectats.actualitzaNumPerfilsDetectats(sPerfils);
	}
	
	public void actualitzaNumPerfil(String sPerfil){
		pPerfilsDetectats.actualitzaNumPerfil(sPerfil);
	}

	public String getNumPerfil(){
		return pPerfilsDetectats.getNumPerfil();
	}
	
	public void actualitzaAlumnesPerfil(Instances grup){
		pPerfilsDetectats.actualitzaAlumnesPerfil(grup);
	}
	
	public void actualitzaPanellPerfils(Instances grup){
		pPerfilsDetectats.actualitzaPanellPerfils(grup);
	}
	
	public void actualitzaNumAlumnesPerfil(String sNumAlumnes){
		pPerfilsDetectats.actualitzaNumAlumnesPerfil(sNumAlumnes);
	}
	
	public void actualitzaPercentatgePerfil(String sNumAlumnes){
		pPerfilsDetectats.actualitzaPercentatgePerfil(sNumAlumnes);
	}
	
	public void activarVistaPerfils(){
		pPerfilsDetectats.activarVistaPerfils();
	}
	
	public String consultaSeleccioVista() {
		return pPerfilsDetectats.consultaSeleccioVista();
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL PESOS DELS ATRIBUTS */
	public void activaConfigurarPesos() {
		pPesosAtributs.activarBotoConfigurar();
	}

	
}
