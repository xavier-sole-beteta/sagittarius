package sagittarius.controlador.grupsDeTreball;






import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import sagittarius.model.general.Configuracio;
import sagittarius.model.general.ConversorCSVARFFNE;
import sagittarius.model.general.EinaArff;
import sagittarius.model.general.PreProcesNE;
import sagittarius.model.grupsDeTreball.ConfiguracioRellevanciaKMeans;
import sagittarius.model.grupsDeTreball.ExportacioExcel;
import sagittarius.model.grupsDeTreball.Grup;
import sagittarius.model.grupsDeTreball.GrupsDeTreball;
import sagittarius.model.principal.ConfiguracioSagittarius;
import sagittarius.vista.general.FinestraAmbGrafic;
import sagittarius.vista.general.GraficDeBarres;
import sagittarius.vista.general.GraficDeBarresPercentual;
import sagittarius.vista.general.GraficRadial;
import sagittarius.vista.grupsDeTreball.DialegPesosAtributs;
import sagittarius.vista.grupsDeTreball.PanellGrupsDeTreball;
import weka.core.Instances;
import jxl.write.WriteException;

/**
 * <b>ControladorGrupsDeTreball</b> és la classe controladora del triplet MVC del cas funcional
 * per la creació de grups de treball (MCGT).<br/>
 * 
 * Emmagatzema una referència a les vistes (una de tipus PanellGrupsDeTreball i una de tipus
 * DialegPesosAtributs) i una referència al model (de tipus GrupsDeTreball).<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ControladorGrupsDeTreball implements ActionListener, DocumentListener, PropertyChangeListener, KeyListener{

	/** Les vistes */
	private PanellGrupsDeTreball pPrincipal;
	private DialegPesosAtributs dPesosAtributs;

	/** El model */
	private GrupsDeTreball clustering;


	public ControladorGrupsDeTreball(PanellGrupsDeTreball panellPrincipal) {
		this.pPrincipal = panellPrincipal;
	}

	public void actionPerformed(ActionEvent event) {

		String etiquetaComponent = event.getActionCommand();

		if(event.getSource() instanceof JComboBox){
			JComboBox cb = (JComboBox)event.getSource();
			String sSeleccio = (String)cb.getSelectedItem();
			actualitzaVista(sSeleccio);
		}

		if(etiquetaComponent.equals("SELECCIONAR")){
			try{
				if (seleccioFitxerAlumnes()) {

					try {
						String[] noms = EinaArff.retornaNomsAtributs(clustering.getDataset());
						String[] aux = new String[noms.length-ConfiguracioSagittarius.numAtributsIdentificadorsIntern];

						for(int i=3; i<noms.length; i++) {
							aux[i-3] = noms[i];
						}

						ConfiguracioRellevanciaKMeans.pesosAtributs = new float[aux.length];

						for(int i=0; i<aux.length; i++) {
							ConfiguracioRellevanciaKMeans.pesosAtributs[i] = (float)1;
						}

						dPesosAtributs = new DialegPesosAtributs(aux.length, aux, pPrincipal); 
						dPesosAtributs.setControlador(new ControladorPesosAtributs(dPesosAtributs, aux.length));
						dPesosAtributs.setModal(true);

					} catch (IOException e) {
						e.printStackTrace();
					}
					pPrincipal.activaConfigurarPesos();
				}
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}

		if(etiquetaComponent.equals("CONFIGURAR-PESOS")){
			dPesosAtributs.setVisible(true);
		}

		if(etiquetaComponent.equals("VEURE")){
			try{
				veureFitxerAlumnes();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}

		if(etiquetaComponent.equals("VEURE_ALUMNES")) {
			try {
				veureAlumnes();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(etiquetaComponent.equals("GRAFIC_NOTES")) {
			mostraGraficNotes(Integer.valueOf(pPrincipal.getGrup())-1);
		}

		if(etiquetaComponent.equals("VEURE_CARACTERISTIQUES")){
			try {
				veureCaracteristiques();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(etiquetaComponent.equals("VEURE_ESTADISTIQUES")){
			veureGraficBarresPercentual();
		}

		if(etiquetaComponent.equals("AGRUPAR")){
			Configuracio.opcio = Configuracio.PESOS_KMEANS;
			agrupar();
			pPrincipal.setGrup(Integer.toString(1));
			pPrincipal.actualitzaNumIntegrants(String.valueOf(clustering.getNumIntegrants(Integer.valueOf(pPrincipal.getGrup())-1)));
			pPrincipal.actualitzaPanellCaracteristiques(EinaArff.crearEspecialitatNominalNotesString(clustering.consultaCaracteristiquesNumericGrup(Integer.valueOf(pPrincipal.getGrup())-1)));
			pPrincipal.actualitzaPanellMembres(clustering.consultaMembresGrup(Integer.valueOf(pPrincipal.getGrup())-1));
			pPrincipal.actualitzaGrau(String.valueOf(clustering.consultaGrau(Integer.valueOf(pPrincipal.getGrup())-1))+"% "+"("+String.valueOf(clustering.consultaNumAlumnesGrau(Integer.valueOf(pPrincipal.getGrup())-1))+"/"+String.valueOf(clustering.consultaNumAlumnes(Integer.valueOf(pPrincipal.getGrup())-1))+")");
			pPrincipal.activaSiguiente();
			pPrincipal.activaAnterior();
			pPrincipal.activaTipusVistaCaracteristiques();
			pPrincipal.activaVeureGrafica();
			pPrincipal.activaModeExportar();
			pPrincipal.activaExportar();
			actualitzaVista(pPrincipal.consultaSeleccioVista());
		}

		if(etiquetaComponent.equals("GRAFICA")){
			mostraGrafica(Integer.valueOf(pPrincipal.getGrup())-1);
		}

		if(etiquetaComponent.equals("EXPORTAR")){
			JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.setFileFilter(new FileNameExtensionFilter("Fichero Excel", "xls"));
			fc.setAcceptAllFileFilterUsed(false);
			fc.setDialogTitle("Sagittarius - Exportar la propuesta de grupos");
			int returnVal = fc.showSaveDialog(pPrincipal);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (file.getAbsolutePath().endsWith(".xls")) {
					exportaGrupsAExcel(file.getAbsolutePath(), pPrincipal.getUnUnicFull());	
					try {
						Desktop.getDesktop().open(new File(file.getAbsolutePath()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else{
					if(!file.getAbsolutePath().contains(".xls")) {
						exportaGrupsAExcel(file.getAbsolutePath()+".xls", pPrincipal.getUnUnicFull());
						try {
							Desktop.getDesktop().open(new File(file.getAbsolutePath()+".xls"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}

			}
		}

		if(etiquetaComponent.equals("SEGUENT")){
			if(Integer.valueOf(pPrincipal.getGrup())<clustering.getNumGrups()){
				pPrincipal.setGrup(Integer.toString(Integer.valueOf(pPrincipal.getGrup())+1));
			}
		}

		if(etiquetaComponent.equals("ANTERIOR")){
			if(Integer.valueOf(pPrincipal.getGrup())>1){
				pPrincipal.setGrup(Integer.toString(Integer.valueOf(pPrincipal.getGrup())-1));
			}
		}

	}

	private void actualitzaVista(String qui) {

		if(qui.equals("Cuantitativa")){
			pPrincipal.actualitzaPanellCaracteristiques(EinaArff.crearEspecialitatNominalNotesString(clustering.consultaCaracteristiquesNumericGrup(Integer.valueOf(pPrincipal.getGrup())-1)));		
		}
		if(qui.equals("Cualitativa")){
			pPrincipal.actualitzaPanellCaracteristiques(clustering.consultaCaracteristiquesNominalGrup(Integer.valueOf(pPrincipal.getGrup())-1));
		}
	}

	private void veureGraficBarresPercentual() {

		GraficDeBarresPercentual deBarresPercentuals = new GraficDeBarresPercentual("", "Distribución de notas (%)", "Nota", true);
		Grup grup = null;
		int[][] aux = null;
		for (int i=0; i<clustering.consultaNumAtributs(); i++) {
			grup = clustering.consultaGrup(Integer.valueOf(pPrincipal.getGrup())-1);
			aux = grup.consultaEstadistiques();

			deBarresPercentuals.afegeixDades((double)((aux[i][0]/(float)grup.getNumIntegrants())*100), "NA", clustering.consultaNomAtribut(i));					
			deBarresPercentuals.afegeixDades((double)((aux[i][1]/(float)grup.getNumIntegrants())*100), "F", clustering.consultaNomAtribut(i));				
			deBarresPercentuals.afegeixDades((double)((aux[i][2]/(float)grup.getNumIntegrants())*100), "E - D", clustering.consultaNomAtribut(i));				
			deBarresPercentuals.afegeixDades((double)((aux[i][3]/(float)grup.getNumIntegrants())*100), "C - B", clustering.consultaNomAtribut(i));				
			deBarresPercentuals.afegeixDades((double)((aux[i][4]/(float)grup.getNumIntegrants())*100), "A", clustering.consultaNomAtribut(i));
			
		}

		new FinestraAmbGrafic("Sagittarius - Graphic representation "+"(group "+(Integer.valueOf(pPrincipal.getGrup()))+")",
				"Gráfico de barras porcentual (resultados por evaluación)",
				"El siguiente gráfico de barras porcentual muestra la composición porcentual de los resultados obtenidos por los alumnos en las distintas pruebas disponibles:",
				"",
				deBarresPercentuals.generaPanell()).setVisible(true);
	}


	private void veureCaracteristiques() throws NumberFormatException, IOException {

		if (pPrincipal.consultaSeleccioVista().equals("Cualitativa")) {
			EinaArff.mostraCaracteristiquesAlumnesIntegrants(Integer.valueOf(pPrincipal.getGrup()), clustering.consultaCaracteristiquesNominalGrup(Integer.valueOf(pPrincipal.getGrup())-1));
		}

		if (pPrincipal.consultaSeleccioVista().equals("Cuantitativa")) {
			EinaArff.mostraCaracteristiquesAlumnesIntegrants(Integer.valueOf(pPrincipal.getGrup()), EinaArff.crearEspecialitatNominalNotesString(clustering.consultaCaracteristiquesNumericGrup(Integer.valueOf(pPrincipal.getGrup())-1)));
		}

	}

	private void veureAlumnes() throws NumberFormatException, IOException {
		EinaArff.mostraAlumnesIntegrants(Integer.valueOf(pPrincipal.getGrup()), clustering.consultaMembresGrup(Integer.valueOf(pPrincipal.getGrup())-1));
	}

	private void exportaGrupsAExcel(String directori, boolean unUnicFull) {

		try {
			ExportacioExcel eExcel = new ExportacioExcel();
			eExcel.setOutputFile(directori);
			eExcel.write(unUnicFull);
			for(int i=0; i<clustering.getNumGrups(); i++){
				eExcel.escriuDataSet(clustering.consultaMembresGrup(Integer.valueOf(i)), "GRUPO "+String.valueOf(i+1), i, unUnicFull);
			}
			eExcel.finalitza();
		} catch (WriteException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	private void mostraGraficNotes(int numGrup) {
		GraficDeBarres db = new GraficDeBarres("", "Nota", "Estudiante", true);


		for (int i=0; i<clustering.consultaNumAtributs(); i++) {
			Grup grup = clustering.consultaGrup(Integer.valueOf(pPrincipal.getGrup())-1);
			Instances d = grup.getCaracteristiquesAlumnesNumeric();
			Instances d2 = grup.getAlumnes();
			for(int j=0; j<grup.getNumIntegrants(); j++) {
				db.afegeixDades(d2.instance(j).stringValue(0), clustering.consultaNomAtribut(i), (float)d.instance(j).value(i));					
			}
		}

		new FinestraAmbGrafic("Sagittarius - Representación gráfica "+"(grupo "+(Integer.valueOf(pPrincipal.getGrup()))+")",
				"Gráfico de garras con categorías (visión general de los resultados del grupo)",
				"El siguiente gráifco de barras muestra los resultados alcanzados por cada miembros del equipo en las actividades de evaluación disponibles:",
				"",
				db.generaPanell()).setVisible(true);
	}


	private void mostraGrafica(int numGrup) {

		GraficRadial db = new GraficRadial("", false);

		for (int i=0; i<clustering.consultaNumAtributs(); i++) {
			Grup grup = clustering.consultaGrup(Integer.valueOf(pPrincipal.getGrup())-1);
			Instances d = grup.getCaracteristiquesAlumnesNumeric();
			Instances d2 = grup.getAlumnes();
			for(int j=0; j<grup.getNumIntegrants(); j++) {
				db.afegeixDades(clustering.consultaNomAtribut(i), d2.instance(j).stringValue(0), (float)d.instance(j).value(i));					
			}
		}

		new FinestraAmbGrafic("Sagittarius - Representación gráfica "+"(grupo "+(Integer.valueOf(pPrincipal.getGrup()))+")",
				"Gráfico radial (conocimiento de cada estudiante)",
				"El siguiente gráfico radial muestra el nivel individual de conocimiento sobre los conceptos tratados en las distintas actividades de evaluación:",
				"",
				db.generaPanell()).setVisible(true);
	}

	private void agrupar() {
		clustering.generaGrupsDeTreball(Integer.valueOf(pPrincipal.getNumAlumnesConfiguracio()), pPrincipal.getGrupsHeterogenisConfiguracio());
		if( pPrincipal.getGrupsHeterogenisConfiguracio()){
			pPrincipal.actualitzaInformacioGrau("Heterogeneidad");
		}else{
			pPrincipal.actualitzaInformacioGrau("Homogeneidad");
		}
	} 

	private boolean seleccioFitxerAlumnes() throws IOException{
		String sNomFitxer = seleccionarFitxer();

		if (sNomFitxer!=null) {
			clustering = new GrupsDeTreball(PreProcesNE.preProcessar(sNomFitxer, ConversorCSVARFFNE.NOTES_NUMERIC));
			if(clustering.getDataset()!=null) {
				int iNumAlumnes = EinaArff.retornaNumInstancies(clustering.getDataset());
				actualitzaSeleccioFitxer(sNomFitxer, iNumAlumnes);
				pPrincipal.activaNumAlumnesConfiguracio();
				pPrincipal.activaTipusGrupsConfiguracio();
				return true;
			}
		}
		return false;
	}

	private void actualitzaSeleccioFitxer(String sNomFitxer, int numAlumnes){
		pPrincipal.setNomFitxer(sNomFitxer);
		pPrincipal.setNumAlumnesFitxer(numAlumnes);
	}

	private String seleccionarFitxer(){

		JFileChooser chooser = new JFileChooser("");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV (Comma-separated values) (*.csv)", "csv");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Sagittarius - Seleccionar el fichero de alumnos");
		int returnVal = chooser.showOpenDialog(pPrincipal);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}else{
			return null;
		}
	}

	private void veureFitxerAlumnes() throws IOException{
		EinaArff.mostraFitxerAmbInformacio(clustering.getDataset(), pPrincipal.getNomFitxer());
	}

	public void changedUpdate(DocumentEvent arg0) {	
	}

	public void insertUpdate(DocumentEvent e) {
		if(e.equals(pPrincipal.getJTFNumAlumnesConfiguracio())){
		}else{
			pPrincipal.activaVeureElementsFitxer();
		}
	}

	public void removeUpdate(DocumentEvent arg0) {
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(clustering!=null){
			pPrincipal.actualitzaNumIntegrants(String.valueOf(clustering.getNumIntegrants(Integer.valueOf(pPrincipal.getGrup())-1)));
			pPrincipal.actualitzaGrau(String.valueOf(clustering.consultaGrau(Integer.valueOf(pPrincipal.getGrup())-1))+"% "+"("+String.valueOf(clustering.consultaNumAlumnesGrau(Integer.valueOf(pPrincipal.getGrup())-1))+"/"+String.valueOf(clustering.consultaNumAlumnes(Integer.valueOf(pPrincipal.getGrup())-1))+")");
			pPrincipal.actualitzaPanellMembres(clustering.consultaMembresGrup(Integer.valueOf(pPrincipal.getGrup())-1));
			pPrincipal.actualitzaPanellCaracteristiques(EinaArff.crearEspecialitatNominalNotesString(clustering.consultaCaracteristiquesNumericGrup(Integer.valueOf(pPrincipal.getGrup())-1)));
			actualitzaVista(pPrincipal.consultaSeleccioVista());
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		if(e.getSource().equals(pPrincipal.getJTFNumAlumnesConfiguracio())){
			if(valida(pPrincipal.getJTFNumAlumnesConfiguracio().getText())){
				int aux = Integer.valueOf(pPrincipal.getNumAlumnesFitxer())/Integer.valueOf(pPrincipal.getJTFNumAlumnesConfiguracio().getText());
				int aux3 = Integer.valueOf(pPrincipal.getNumAlumnesFitxer()) % Integer.valueOf(pPrincipal.getJTFNumAlumnesConfiguracio().getText());
				pPrincipal.setNumGrupsSencers(Integer.toString(aux));
				pPrincipal.setComponentsGrupsSencers(pPrincipal.getJTFNumAlumnesConfiguracio().getText());
				if(aux3!=0){
					pPrincipal.setNumGrupsReste("1");
				}else{
					pPrincipal.setNumGrupsReste("-");
				}
				if(aux3!=0){
					pPrincipal.setComponentsGrupsReste(Integer.toString(aux3));
				}else{
					pPrincipal.setComponentsGrupsReste("-");
				}
				pPrincipal.activarIniciar();
			}else{
				pPrincipal.desactivarIniciar();
				pPrincipal.setNumGrupsSencers("-");
				pPrincipal.setComponentsGrupsSencers("-");
				pPrincipal.setNumGrupsReste("-");
				pPrincipal.setComponentsGrupsReste("-");
			}
		}
	}

	private boolean valida(String sValida){
		if(sValida.equals("") || Integer.valueOf(sValida)==0){
			return false;
		}else{
			return true;
		}
	}

	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(pPrincipal.getJTFNumAlumnesConfiguracio())){
			char caracter = e.getKeyChar();
			if(((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE)){
				e.consume(); 
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

}
