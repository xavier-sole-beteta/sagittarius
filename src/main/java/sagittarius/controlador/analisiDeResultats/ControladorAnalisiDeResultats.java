package sagittarius.controlador.analisiDeResultats;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import sagittarius.model.analisiDeResultats.GeneradorColorsGrafics;
import sagittarius.model.analisiDeResultats.GeneradorDeDescripcions;
import sagittarius.model.general.ConversorCSVARFFNE;
import sagittarius.model.general.EinaArff;
import sagittarius.model.general.PreProcesNE;
import sagittarius.model.principal.ConfiguracioSagittarius;
import sagittarius.vista.analisiDeResultats.PanellAnalisiDeResultats;
import sagittarius.vista.general.FinestraAmbExplicacio;
import sagittarius.vista.general.FinestraAmbGrafic;
import sagittarius.vista.general.GraficDeCorrelacions;
import sagittarius.vista.general.GraficDePastis;
import sagittarius.vista.general.GraficHistograma;



/**
 * <b>ControladorAnalisiDeResultats</b> és la classe controladora del triplet MVC del mòdul funcional
 * per l'anàlisi i visualització de resultats (MAR).<br/>
 * Emmagatzema una referència a la vista (de tipus PanellAnalisiDeResultats) i una referència
 * al model (de tipus GeneradorColorGrafics).<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ControladorAnalisiDeResultats implements ActionListener, DocumentListener   {

	/** Vista */
	private PanellAnalisiDeResultats pVisualitzacio;
	
	/** Model */
	private GeneradorColorsGrafics model;
	

	public ControladorAnalisiDeResultats(PanellAnalisiDeResultats pVisualitzacio){
		this.pVisualitzacio = pVisualitzacio;
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String etiquetaComponent = event.getActionCommand();
		
		if(event.getSource() instanceof JComboBox){
		     try {
		    	pVisualitzacio.actualitzaGrafic(generaHistograma(pVisualitzacio.getIndexNotaOrigen()), generaHistograma(pVisualitzacio.getIndexNotaDesti()), visualitzaCorrelacions(model.getDataset(), pVisualitzacio.getNotaOrigen(), pVisualitzacio.getNotaDesti()),pVisualitzacio.getNotaOrigen(),pVisualitzacio.getNotaDesti(),1);
				int[] p = EinaArff.getEstadistiques(model.getDataset(), pVisualitzacio.getNotaOrigen());
				float[] pOrigen = EinaArff.getEstadistiques(model.getDataset(), pVisualitzacio.getIndexNotaOrigen());
				float[] pDesti = EinaArff.getEstadistiques(model.getDataset(), pVisualitzacio.getIndexNotaDesti());
				
				pVisualitzacio.setNotaOrigen(pVisualitzacio.getNotaOrigen());
				pVisualitzacio.setNotaDesti(pVisualitzacio.getNotaDesti());
				pVisualitzacio.setAlumnesNPOrigen(Integer.toString(p[0]), Float.toString(pOrigen[0]));
				pVisualitzacio.setAlumnesSUSPESOrigen(Integer.toString(p[1]), Float.toString(pOrigen[1]));
				pVisualitzacio.setAlumnesAPROVATOrigen(Integer.toString(p[2]), Float.toString(pOrigen[2]));
				pVisualitzacio.setAlumnesNOTABLEOrigen(Integer.toString(p[3]), Float.toString(pOrigen[3]));
				pVisualitzacio.setAlumnesEXCELLENTOrigen(Integer.toString(p[4]), Float.toString(pOrigen[4]));
				p = EinaArff.getEstadistiques(model.getDataset(), pVisualitzacio.getNotaDesti());
				pVisualitzacio.setAlumnesNPDesti(Integer.toString(p[0]), Float.toString(pDesti[0]));
				pVisualitzacio.setAlumnesSUSPESDesti(Integer.toString(p[1]), Float.toString(pDesti[1]));
				pVisualitzacio.setAlumnesAPROVATDesti(Integer.toString(p[2]), Float.toString(pDesti[2]));
				pVisualitzacio.setAlumnesNOTABLEDesti(Integer.toString(p[3]), Float.toString(pDesti[3]));
				pVisualitzacio.setAlumnesEXCELLENTDesti(Integer.toString(p[4]), Float.toString(pDesti[4]));
				float[] a = EinaArff.getEstadistiquesNumeric(model.getDataset(), pVisualitzacio.getNotaOrigen());
				pVisualitzacio.setNotaMITJANAOrigen(Float.toString(a[0]));
				pVisualitzacio.setNotaMAXIMAOrigen(Float.toString(a[1]));
				pVisualitzacio.setNotaMINIMAOrigen(Float.toString(a[2]));
				a = EinaArff.getEstadistiquesNumeric(model.getDataset(), pVisualitzacio.getNotaDesti());
				pVisualitzacio.setNotaMITJANADesti(Float.toString(a[0]));
				pVisualitzacio.setNotaMAXIMADesti(Float.toString(a[1]));
				pVisualitzacio.setNotaMINIMADesti(Float.toString(a[2]));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("SELECCIONAR")){
			try{
				if (seleccioFitxerAlumnes()) {
					String[] aux = EinaArff.retornaNomsAtributs(model.getDataset());
					model.generaColors(aux.length);
					pVisualitzacio.setNotes(aux);
					pVisualitzacio.activaVeureGrafiques();
				}
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("VEURE")){
			try{
				veureFitxerAlumnes();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		

		if(etiquetaComponent.equals("GRAFICA-ORIGEN-AMB-NP")){
			try{
				generaGraficPastisAmbNP(pVisualitzacio.getIndexNotaOrigen(), 0);
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("GRAFICA-DESTI-AMB-NP")){
			try{
				generaGraficPastisAmbNP(pVisualitzacio.getIndexNotaDesti(), 1);
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("GRAFICA-ORIGEN-SENSE-NP")){
			try{
				generaGraficPastisSenseNP(pVisualitzacio.getIndexNotaOrigen(), 0);
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("GRAFICA-DESTI-SENSE-NP")){
			try{
				generaGraficPastisSenseNP(pVisualitzacio.getIndexNotaDesti(), 1);
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("OBRIR-HISTOGRAMA-ORIGEN")){
			try{
				new FinestraAmbGrafic("ECOA - Histograma de la prueba "+pVisualitzacio.getNotaOrigen(),
						"Histrograma ("+pVisualitzacio.getNotaOrigen()+")",
						"El siguiente gráfico es un histograma de los resultados de la prueba "+pVisualitzacio.getNotaOrigen()+":",
						"",
						generaHistograma(pVisualitzacio.getIndexNotaOrigen())).setVisible(true);
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("OBRIR-HISTOGRAMA-DESTI")){
			try{
				new FinestraAmbGrafic("ECOA - Histograma de la nota "+pVisualitzacio.getNotaDesti(),
						"Histrograma ("+pVisualitzacio.getNotaDesti()+")",
						"El siguiente gráfico es un histograma de los resultados de la prueba "+pVisualitzacio.getNotaDesti()+":",
						"",
						generaHistograma(pVisualitzacio.getIndexNotaDesti())).setVisible(true);
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(etiquetaComponent.equals("OBRIR-CORRELACIONS")){
			try{
				new FinestraAmbGrafic("ECOA - Correlaciones entre las pruebas "+pVisualitzacio.getNotaOrigen()+" y "+pVisualitzacio.getNotaDesti(),
						"Correlaciones ("+pVisualitzacio.getNotaOrigen()+" y "+pVisualitzacio.getNotaDesti()+")",
						"El siguiente gráfico de barras muestra la correlación de los resultados de las pruebas "+pVisualitzacio.getNotaOrigen()+" y "+pVisualitzacio.getNotaDesti(),
						"NP(no presentado) - SUSPENSO(nota entre 0.00 y 4.99) - APROBADO(nota entre 5.00 y 6.99) - NOTABLE(nota entre 7.00 y 8.99) - EXCELENTE(nota entre 9.00 y 10.00))",
						visualitzaCorrelacions(model.getDataset(), pVisualitzacio.getNotaOrigen(), pVisualitzacio.getNotaDesti())).setVisible(true);
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		if(etiquetaComponent.equals("AJUDA-CORRELACIONS")){
			try {
				String descripcio = GeneradorDeDescripcions.generaDescripcioCorrelacio(pVisualitzacio.getNotaOrigen(), pVisualitzacio.getNotaDesti(), EinaArff.calculaCorrelacions(model.getDataset(), pVisualitzacio.getNotaOrigen(), pVisualitzacio.getNotaDesti()));	
				new FinestraAmbExplicacio("ECOA - Interpretación de la correlación de los resultados de las pruebas "+pVisualitzacio.getNotaOrigen()+" y "+pVisualitzacio.getNotaDesti(),
						"Interpretación de la correlación ("+pVisualitzacio.getNotaOrigen()+" y "+pVisualitzacio.getNotaDesti()+")",
						descripcio).setVisible(true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private JPanel generaHistograma(int index) throws IOException {
		
		GraficHistograma histograma = new GraficHistograma("", "Mark", "Students");
		histograma.afegeixDades(EinaArff.getNotesNONP(model.getDataset(), index));

		return histograma.generaPanell(model.consultaColor(index+1));
	}
	
	private void generaGraficPastisAmbNP(int quin, int desti) throws IOException {
		
		GraficDePastis dePastis = new GraficDePastis("", false);
		float[] aux = EinaArff.getEstadistiques(model.getDataset(), quin);
		dePastis.afegeixDades("NP "+"\n"+aux[0]+"%", (double)aux[0]);
		dePastis.afegeixDades("Suspenso "+"\n"+aux[1]+"%", (double)aux[1]);
		dePastis.afegeixDades("Aprobado "+"\n"+aux[2]+"%", (double)aux[2]);
		dePastis.afegeixDades("Notable "+"\n"+aux[3]+"%", (double)aux[3]);
		dePastis.afegeixDades("Excelente "+"\n"+aux[4]+"%", (double)aux[4]);

		String resultat;
		if(desti == 0) {
			resultat = pVisualitzacio.getNotaOrigen();
		}else{
			resultat = pVisualitzacio.getNotaDesti();
		}
		
		new FinestraAmbGrafic("ECOA - Gráfico de pastel de la prueba "+resultat,
			"Gràfico de pastel ("+resultat+")",
			"El siguiente gráfico muestra el porcentaje, respecto al total de alumnos, de los resultados obtenidos:\n",
			"NP(no presentado) - SUSPENSO(nota entre 0.00 y 4.99) - APROBADO(nota entre 5.00 y 6.99) - NOTABLE(nota entre 7.00 y 8.99) - EXCELENTE(nota entre 9.00 y 10.00))",
			dePastis.generaPanell()).setVisible(true);
	}
	
	private void generaGraficPastisSenseNP(int quin, int desti) throws IOException {
		
		GraficDePastis dePastis = new GraficDePastis("", false);
		float[] aux = EinaArff.getEstadistiquesNONP(model.getDataset(), quin);
		dePastis.afegeixDades("Suspenso "+"\n"+aux[1]+"%", (double)aux[1]);
		dePastis.afegeixDades("Aprobado "+"\n"+aux[2]+"%", (double)aux[2]);
		dePastis.afegeixDades("Notable "+"\n"+aux[3]+"%", (double)aux[3]);
		dePastis.afegeixDades("Excelente "+"\n"+aux[4]+"%", (double)aux[4]);

		String resultat;
		if(desti == 0) {
			resultat = pVisualitzacio.getNotaOrigen();
		}else{
			resultat = pVisualitzacio.getNotaDesti();
		}
		
		new FinestraAmbGrafic("ECOA - Gráfico de pastel de la nota "+resultat,
				"Gráfico de pastel ("+resultat+")",
				"El siguiente gráfico de pastel muestra el porcentaje, respecto al total de alumnos, de los resultados obtenidos:\n",
				"NP(no presentado) - SUSPENSO(nota entre 0.00 y 4.99) - APROBADO(nota entre 5.00 y 6.99) - NOTABLE(nota entre 7.00 y 8.99) - EXCELENTE(nota entre 9.00 y 10.00))",
				dePastis.generaPanell()).setVisible(true);
	}

	private JPanel visualitzaCorrelacions(String fitxer, String origen, String desti) throws FileNotFoundException, IOException {
		
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("QUIN ORIGEN:"+origen);
			System.out.println("QUIN DESTI:"+desti);
		}
		int r[][] = EinaArff.calculaCorrelacions(fitxer, origen, desti);
		
		GraficDeCorrelacions b = new GraficDeCorrelacions(desti);

		b.afegeixDades("NA","NA",r[0][0]);
		b.afegeixDades("NA","D or F",r[0][1]);
		b.afegeixDades("NA","C",r[0][2]);
		b.afegeixDades("NA","B",r[0][3]);
		b.afegeixDades("NA","A or with honors",r[0][4]);
		
		b.afegeixDades("D or F","NA",r[1][0]);
		b.afegeixDades("D or F","D or F",r[1][1]);
		b.afegeixDades("D or F","C",r[1][2]);
		b.afegeixDades("D or F","B",r[1][3]);
		b.afegeixDades("D or F","A or with honors",r[1][4]);
		
		b.afegeixDades("C","NA",r[2][0]);
		b.afegeixDades("C","D or F",r[2][1]);
		b.afegeixDades("C","C",r[2][2]);
		b.afegeixDades("C","B",r[2][3]);
		b.afegeixDades("C","A or with honors",r[2][4]);
		
		b.afegeixDades("B","NA",r[3][0]);
		b.afegeixDades("B","D or F",r[3][1]);
		b.afegeixDades("B","C",r[3][2]);
		b.afegeixDades("B","B",r[3][3]);
		b.afegeixDades("B","A or with honors",r[3][4]);
		
		b.afegeixDades("A or with honors","NA",r[4][0]);
		b.afegeixDades("A or with honors","D or F",r[4][1]);
		b.afegeixDades("A or with honors","C",r[4][2]);
		b.afegeixDades("A or with honors","B",r[4][3]);
		b.afegeixDades("A or with honors","A or with honors",r[4][4]);
		
		return b.generaPanell();
	}
	
	private boolean seleccioFitxerAlumnes() throws IOException{
		String sNomFitxer = seleccionarFitxer();
		if(sNomFitxer!=null && sNomFitxer!="") {
			model = new GeneradorColorsGrafics(PreProcesNE.preProcessar(sNomFitxer, ConversorCSVARFFNE.NOTES_NUMERIC));
			if (model.getDataset()!=null) {
				int iNumAlumnes = EinaArff.retornaNumInstancies(model.getDataset());
				actualitzaSeleccioFitxer(sNomFitxer, iNumAlumnes);
				return true;
			}
		}
		return false;
	}
	
	
	private void actualitzaSeleccioFitxer(String sNomFitxer, int numAlumnes){
		pVisualitzacio.setNomFitxer(sNomFitxer);
		pVisualitzacio.setNumAlumnes(numAlumnes);
	}
	
	private void veureFitxerAlumnes() throws IOException{
		EinaArff.mostraFitxerAmbInformacio(model.getDataset(), pVisualitzacio.getNomFitxer());
	}
	
	private String seleccionarFitxer(){
		
		JFileChooser chooser = new JFileChooser("");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV (Comma-separated values) (*.csv)", "csv");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Sagittarius - Selecionar el fichero de alumnos");
		int returnVal = chooser.showOpenDialog(pVisualitzacio);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}else{
			return null;
		}
		
	}

	public void insertUpdate(DocumentEvent e) {
		pVisualitzacio.activaVeureElements();
	}

	public void removeUpdate(DocumentEvent e) {}
	public void changedUpdate(DocumentEvent e) {}
	
}
