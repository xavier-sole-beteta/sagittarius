package sagittarius.vista.analisiDeResultats;

import javax.swing.JPanel;

import sagittarius.controlador.analisiDeResultats.ControladorAnalisiDeResultats;
import net.miginfocom.swing.MigLayout;

/**
 * <b>PanellAnalisiDeResultats</b> és la classe principal de la vista del triplet MVC del mòdul funcional
 * per l'anàlisi i visualització de resultats (MAR).<br/>
 * Emmagatzema una referència a cadascun dels panells secundaris.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PanellAnalisiDeResultats extends JPanel{

	private PanellFitxerEntrada pFitxerEntrada;
	private PanellSeleccioProves pSeleccioProves;
	private PanellEstadistiques pEstadistiques;
	private PanellGrafics pGrafics;

	
	public PanellAnalisiDeResultats(){
		pFitxerEntrada = new PanellFitxerEntrada();
		pSeleccioProves = new PanellSeleccioProves();
		pEstadistiques = new PanellEstadistiques();
		pGrafics = new PanellGrafics();
		
		this.setLayout(new MigLayout("insets 10 10 10 10", "[]40[grow]", "[]20[]20[]"));
		
		add(pFitxerEntrada, "cell 0 0, w 300!, top");
		add(pSeleccioProves, "cell 0 1, w 300!, top");
		add(pEstadistiques, "cell 0 2, w 300!, top");
		add(pGrafics, "cell 1 0, span 0 3, grow, top");
	}
	
	public void setControlador(ControladorAnalisiDeResultats controlador) {
		pFitxerEntrada.setControlador(controlador);
		pSeleccioProves.setControlador(controlador);
		pGrafics.setControlador(controlador);
	}
	
	
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
	
	public void setNotes(String[] notes) {
		pSeleccioProves.setNota(notes);
	}
	
	public String getNotaOrigen() {
		return pSeleccioProves.getSeleccioNotaOrigen();
	}
	
	public String getNotaDesti() {
		return pSeleccioProves.getSeleccioNotaDesti();
	}
	
	public int getIndexNotaOrigen() {
		return pSeleccioProves.getIndexNotaOrigen();
	}
	
	public int getIndexNotaDesti() {
		return pSeleccioProves.getIndexNotaDesti();
	}
	
	public void setNotaOrigen(String jlNotaOrigen) {
		pEstadistiques.setNotaOrigen(jlNotaOrigen);
	}

	public void setAlumnesNPOrigen(String jlAlumnesNPOrigen, String percentatge) {
		pEstadistiques.setAlumnesNPOrigen(jlAlumnesNPOrigen, percentatge);
	}

	public void setAlumnesSUSPESOrigen(String jlAlumnesSUSPESOrigen, String percentatge) {
		pEstadistiques.setAlumnesSUSPESOrigen(jlAlumnesSUSPESOrigen, percentatge);
	}

	public void setAlumnesAPROVATOrigen(String jlAlumnesAPROVATOrigen, String percentatge) {
		pEstadistiques.setAlumnesAPROVATOrigen(jlAlumnesAPROVATOrigen, percentatge);
	}

	public void setAlumnesNOTABLEOrigen(String jlAlumnesNOTABLEOrigen, String percentatge) {
		pEstadistiques.setAlumnesNOTABLEOrigen(jlAlumnesNOTABLEOrigen, percentatge);
	}

	public void setAlumnesEXCELLENTOrigen(String jlAlumnesEXCELLENTOrigen, String percentatge) {
		pEstadistiques.setAlumnesEXCELLENTOrigen(jlAlumnesEXCELLENTOrigen, percentatge);
	}
	
	public void setNotaMAXIMAOrigen(String jlAlumnesEXCELLENTOrigen) {
		pEstadistiques.setNotaMAXIMAOrigen(jlAlumnesEXCELLENTOrigen);
	}
	
	public void setNotaMINIMAOrigen(String jlAlumnesEXCELLENTOrigen) {
		pEstadistiques.setNotaMINIMAOrigen(jlAlumnesEXCELLENTOrigen);
	}
	
	public void setNotaMITJANAOrigen(String jlAlumnesEXCELLENTOrigen) {
		pEstadistiques.setNotaMITJANAOrigen(jlAlumnesEXCELLENTOrigen);
	}
	
	public void setNotaDesti(String jlNotaDesti) {
		pEstadistiques.setNotaDesti(jlNotaDesti);
	}

	public void setAlumnesNPDesti(String jlAlumnesNPDesti, String percentatge) {
		pEstadistiques.setAlumnesNPDesti(jlAlumnesNPDesti, percentatge);
	}

	public void setAlumnesSUSPESDesti(String jlAlumnesSUSPESDesti, String percentatge) {
		pEstadistiques.setAlumnesSUSPESDesti(jlAlumnesSUSPESDesti, percentatge);
	}

	public void setAlumnesAPROVATDesti(String jlAlumnesAPROVATDesti, String percentatge) {
		pEstadistiques.setAlumnesAPROVATDesti(jlAlumnesAPROVATDesti, percentatge);
	}

	public void setAlumnesNOTABLEDesti(String jlAlumnesNOTABLEDesti, String percentatge) {
		pEstadistiques.setAlumnesNOTABLEDesti(jlAlumnesNOTABLEDesti, percentatge);
	}

	public void setAlumnesEXCELLENTDesti(String jlAlumnesEXCELLENTDesti, String percentatge) {
		pEstadistiques.setAlumnesEXCELLENTDesti(jlAlumnesEXCELLENTDesti, percentatge);
	}
	
	public void setNotaMAXIMADesti(String jlAlumnesEXCELLENTDesti) {
		pEstadistiques.setNotaMAXIMADesti(jlAlumnesEXCELLENTDesti);
	}
	
	public void setNotaMINIMADesti(String jlAlumnesEXCELLENTDesti) {
		pEstadistiques.setNotaMINIMADesti(jlAlumnesEXCELLENTDesti);
	}
	
	public void setNotaMITJANADesti(String jlAlumnesEXCELLENTDesti) {
		pEstadistiques.setNotaMITJANADesti(jlAlumnesEXCELLENTDesti);
	}
	
	public void actualitzaGrafic(JPanel graficBarres, JPanel graficPastis, JPanel graficPastisNONP, String notaOrigen, String notaDesti, int mode) {
		pGrafics.actualitzaGrafic(graficBarres, graficPastis, graficPastisNONP, notaOrigen, notaDesti, mode);
	}
	
	public void activaVeureGrafiques() {
		pGrafics.activaVeureGrafiques();
	}
	
	
}
