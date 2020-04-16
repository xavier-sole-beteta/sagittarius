package sagittarius.vista.prediccioNota;

import javax.swing.JPanel;
import javax.swing.JTextField;

import sagittarius.controlador.prediccioNota.ControladorPrediccioNota;
import sagittarius.vista.prediccioNota.PanellFitxerEntrada;

import weka.core.Instances;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellPrediccioNota</b> és el panell principal de la vista del mòdul
 * funcional per la predicció de l'èxit o fracàs de l'alumne.
 * Comprèn la resta de panells de la vista.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellPrediccioNota extends JPanel{
	
	private PanellFitxerEntrada pFitxerEntradaExperiencia;
	private PanellFitxerEntrada pFitxerEntradaPrediccio;
	private PanellIniciarPrediccio pIniciarPrediccio;
	private PanellPrediccioNotes pPrediccioNotes;
	private PanellExportar pExportarPrediccio;
	
	public PanellPrediccioNota(){
		pFitxerEntradaExperiencia = new PanellFitxerEntrada("Paso 1. Seleccionar el histórico de notas (experiencia)", "SELECCIONAREXPERIENCIA", "VEUREEXPERIENCIA");
		pFitxerEntradaPrediccio = new PanellFitxerEntrada("Paso 2. Seleccionar el fichero del curso actual (predicción)", "SELECCIONARPREDICCIO", "VEUREPREDICCIO");
		pIniciarPrediccio = new PanellIniciarPrediccio();
		pPrediccioNotes = new PanellPrediccioNotes();
		pExportarPrediccio = new PanellExportar();

		this.setLayout(new MigLayout("insets 10 10 10 10", "[]40[grow]", "[]20[]20[]20[]"));
		
		add(pFitxerEntradaExperiencia, "grow, cell 0 0, w 300!, top");
		add(pFitxerEntradaPrediccio, "grow, cell 0 1, w 300!, top");
		add(pIniciarPrediccio, "grow, cell 0 2, w 300!, top");
		add(pExportarPrediccio, "grow, cell 0 3, w 300!, top");
		add(pPrediccioNotes, "grow, cell 1 0, span 0 4, top");
	}
	
	
	public void setControlador(ControladorPrediccioNota controlador) {
		pFitxerEntradaExperiencia.setControlador(controlador);
		pFitxerEntradaPrediccio.setControlador(controlador);
		pIniciarPrediccio.setControlador(controlador);
		pPrediccioNotes.setControlador(controlador);
		pExportarPrediccio.setControlador(controlador);
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL D'EXPORTAR */
	public void activaExportar(){
		pExportarPrediccio.activaExportar();
	}
	
	public void activaModeExportar(){
		pExportarPrediccio.activaModeExportar();
	}
	
	public boolean getUnUnicFull(){
		return pExportarPrediccio.getUnUnicFull();
	}
	
	public boolean getResum(){
		return pExportarPrediccio.getResum();
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL D'INICIAR PREDICCIO */
	public void setNotaPrediccio(String sNotaPrediccio){
		pIniciarPrediccio.setNotaPrediccio(sNotaPrediccio);
	}
	
	public void activarIniciarPrediccio(){
		pIniciarPrediccio.activarIniciarPrediccio();
	}
	
	/* MÈTODES RELACIONATS AMB EL PANELL DELS RESULTATS DE LA PREDICCIO */
	public void activaTipusVista(){
		pPrediccioNotes.activaTipusVista();
	}
	
	public int getTipusVista(){
		return pPrediccioNotes.getTipusVista();
	}
	
	public void activaBotons() {
		pPrediccioNotes.activaBotons();
	}
	
	public void actualitzaBotons(int quin){
		pPrediccioNotes.actualitzaBotons(quin);
	}
	
	public void actualitzaNota(String sNota){
		pPrediccioNotes.actualitzaNota(sNota);
	}
	
	public void actualitzaNumAlumnes(String sNumAlumnes){
		pPrediccioNotes.actualitzaNumAlumnes(sNumAlumnes);
	}
	
	public void actualitzaPercentatge(String sPercentatge){
		pPrediccioNotes.actualitzaPercentatge(sPercentatge);
	}
	
	public void actualitzaPrediccio(Instances iPrediccio){
		pPrediccioNotes.actualitzaPrediccio(iPrediccio);
	}
	
	/* MÈTODES RELACIONATS AMB LA SELECCIO DEL FITXER D'EXPERIENCIA */
	public String getNomFitxerExperiencia(){
		return pFitxerEntradaExperiencia.getNomFitxer();
	}
	
	public void setNomFitxerExperiencia(String sNom){
		pFitxerEntradaExperiencia.setNomFitxer(sNom);
	}

	public void setNumAlumnesExperiencia(int iNumeroAlumnes){
		pFitxerEntradaExperiencia.setNumAlumnes(iNumeroAlumnes);
	}
	
	public String getNumAlumnesExperiencia(){
		return pFitxerEntradaExperiencia.getNumAlumnes();
	}
	
	public JTextField getJTFNomFitxerExperiencia(){
		return pFitxerEntradaExperiencia.getJTFNomFitxer();
	}
	
	public void activaVeureElementsExperiencia(){
		pFitxerEntradaExperiencia.activaVeureElements();
	}
	
	/* MÈTODES RELACIONATS AMB LA SELECCIO DEL FITXER DE PREDICCIO  */
	public String getNomFitxerPrediccio(){
		return pFitxerEntradaPrediccio.getNomFitxer();
	}
	
	public void setNomFitxerPrediccio(String sNom){
		pFitxerEntradaPrediccio.setNomFitxer(sNom);
	}

	public void setNumAlumnesPrediccio(int iNumeroAlumnes){
		pFitxerEntradaPrediccio.setNumAlumnes(iNumeroAlumnes);
	}
	
	public String getNumAlumnesPrediccio(){
		return pFitxerEntradaPrediccio.getNumAlumnes();
	}
	
	public JTextField getJTFNomFitxerPrediccio(){
		return pFitxerEntradaPrediccio.getJTFNomFitxer();
	}
	
	public void activaVeureElementsPrediccio(){
		pFitxerEntradaPrediccio.activaVeureElements();
	}
	
}
