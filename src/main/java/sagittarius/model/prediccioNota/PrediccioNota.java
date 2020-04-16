package sagittarius.model.prediccioNota;

import java.io.FileInputStream;
import java.io.IOException;

import sagittarius.model.principal.ConfiguracioSagittarius;


import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * <b>PrediccioNota</b> classe principal del model pel cas funcional per la predicció de l'èxit
 * o fracàs de l'alumne.Comprèn totes les funcionalitat del nucli per la predicció de notes. Hereta
 * de la classe J48 Means de la llibreria d'algorismes WEKA per tal de disposar de la implementació
 * de l'algorisme C4.5.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PrediccioNota extends J48 {

	private static final int NOTA_NP = 0;
	private static final int NOTA_SUSPENSO = 1;
	private static final int NOTA_APROBADO = 2;
	private static final int NOTA_NOTABLE = 3;
	private static final int NOTA_EXCELENTE = 4;
	private static final int NOTA_TODAS = 5;

	private String datasetExperienciaNominal;
	private String datasetPrediccioNominal;

	private String datasetExperienciaNumeric;
	private String datasetPrediccioNumeric;

	private Instances datasetComplertExperiencia;
	private Instances datasetResumExperiencia;
	private Instances datasetComplertPrediccio;
	private Instances datasetResumPrediccio;

	private Prediccio[] prediccio;

	private Instances datasetResumResumPrediccio;
	private Instances auxResum;
	private Instances auxHistoric;

	
	public PrediccioNota() {
		datasetExperienciaNominal = null;
		datasetPrediccioNominal = null;
	}

	public String getDatasetExperienciaNominal() {
		return datasetExperienciaNominal;
	}
	public String getDatasetPrediccioNominal() {
		return datasetPrediccioNominal;
	}

	public String getDatasetExperienciaNumeric() {
		return datasetExperienciaNumeric;
	}
	public String getDatasetPrediccioNumeric() {
		return datasetPrediccioNumeric;
	}

	public void setDatasetExperienciaNominal(String datasetExperienciaNominal) {
		this.datasetExperienciaNominal = datasetExperienciaNominal;
	}
	public void setDatasetPrediccioNominal(String datasetPrediccioNominal) {
		this.datasetPrediccioNominal = datasetPrediccioNominal;
	}

	public void setDatasetExperienciaNumeric(String datasetExperienciaNumeric) {
		this.datasetExperienciaNumeric = datasetExperienciaNumeric;
	}
	public void setDatasetPrediccioNumeric(String datasetPrediccioNumeric) {
		this.datasetPrediccioNumeric = datasetPrediccioNumeric;
	}


	public void generaPrediccioNota(){
		if(datasetExperienciaNominal!=null && datasetPrediccioNominal!=null) {

			ArffLoader cargadorARRF1 = new ArffLoader();
			ArffLoader cargadorARRF2 = new ArffLoader();
			try{
				cargadorARRF1.setSource(new FileInputStream(datasetExperienciaNominal));
				datasetComplertExperiencia = cargadorARRF1.getDataSet();
				cargadorARRF2.setSource(new FileInputStream(datasetPrediccioNominal));
				datasetComplertPrediccio = cargadorARRF2.getDataSet();
			}catch (IOException e) {
				e.printStackTrace();
			}

			// Dataset que contindrà els valors del dataset d'experincia, les notes amb les qual
			// s'aprendrà un model per predir la nota
			datasetResumExperiencia = new Instances(datasetComplertExperiencia);
			// Eliminem els atributs login, cognoms, nom i especialitat
			datasetResumExperiencia.deleteAttributeAt(0);
			datasetResumExperiencia.deleteAttributeAt(0);
			datasetResumExperiencia.deleteAttributeAt(0);
			//			datasetResumExperiencia.deleteAttributeAt(0);
			if (ConfiguracioSagittarius.debug) { 
				System.out.println(datasetResumExperiencia.toString());
			}
			// Indiquem que la classe es el l'últim atribut
			datasetResumExperiencia.setClassIndex(datasetResumExperiencia.numAttributes()-1);

			// Dataset que contindrà els valors del dataset de predicció, s'afegirà la nota predicció
			// la nota predicció és la darrera columna o darrer atribut del dataset d'experiencia
			datasetResumPrediccio = new Instances(datasetComplertPrediccio);
			// Eliminem els atributs login, cognoms, nom i especialitat
			for (int i=0; i<ConfiguracioSagittarius.numAtributsIdentificadorsIntern; i++) {
				datasetResumPrediccio.deleteAttributeAt(0);				
			}

			if (ConfiguracioSagittarius.debug) { 
				System.out.println(datasetResumPrediccio.toString());
			}

			datasetResumResumPrediccio = new Instances(datasetComplertPrediccio);
			for(int i=0; i<datasetResumResumPrediccio.numAttributes(); i++){
				if(	!datasetResumResumPrediccio.attribute(i).name().equals("Nombre") &&
						!datasetResumResumPrediccio.attribute(i).name().equals("Apellidos") &&
						!datasetResumResumPrediccio.attribute(i).name().equals("Expediente")) {
					datasetResumResumPrediccio.deleteAttributeAt(i);
					i=i-1;
				}
			}
			if (ConfiguracioSagittarius.debug) { 
				System.out.println(datasetResumPrediccio.toString());
			}
			datasetResumExperiencia.attribute(0).setWeight(0.2);
			datasetResumExperiencia.attribute(1).setWeight(0.1);
			datasetResumExperiencia.attribute(2).setWeight(1.0);

			try {
				this.buildClassifier(datasetResumExperiencia);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (ConfiguracioSagittarius.debug) { 
				System.out.println(this.toString());
			}
			Instance aPredir = new Instance(datasetResumPrediccio.instance(0));
			if (ConfiguracioSagittarius.debug) { 
				System.out.println("Abans: "+aPredir);
			}
			aPredir.insertAttributeAt(aPredir.numAttributes());
			
			try {
				prediu();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (ConfiguracioSagittarius.debug) { 
				System.out.println("Pred (0):  "+aPredir.toString());
			}
		}

	}

	private void prediu() throws Exception{

		datasetComplertPrediccio.insertAttributeAt(datasetResumExperiencia.attribute(datasetResumExperiencia.numAttributes()-1), datasetComplertPrediccio.numAttributes());

		datasetResumResumPrediccio.insertAttributeAt(datasetResumExperiencia.attribute(datasetResumExperiencia.numAttributes()-1), datasetResumResumPrediccio.numAttributes());
		datasetResumPrediccio.insertAttributeAt(datasetResumExperiencia.attribute(datasetResumExperiencia.numAttributes()-1), datasetResumPrediccio.numAttributes());
		datasetResumResumPrediccio.setClassIndex(datasetResumResumPrediccio.numAttributes()-1);
		datasetResumPrediccio.setClassIndex(datasetResumPrediccio.numAttributes()-1);
		for(int i=0; i<datasetComplertPrediccio.numInstances(); i++){
			datasetComplertPrediccio.instance(i).setValue(datasetComplertPrediccio.numAttributes()-1, classifyInstance(datasetResumPrediccio.instance(i)));
			datasetResumResumPrediccio.instance(i).setValue(datasetResumResumPrediccio.numAttributes()-1, this.classifyInstance(datasetResumPrediccio.instance(i)));
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("DATASET DESPRÉS DE LA PREDICCIO:");
			System.out.println(datasetComplertPrediccio);
		}
		generaGrups();		

	}

	private void generaGrups(){

		prediccio = new Prediccio[6];
		Instances auxResum;
		Instances auxHistoric;
		int actual = 0;
		float actualPercent = (float) 0;
		float resultPercent = (float) 0;
		int total = datasetResumResumPrediccio.numInstances();
		String sNota = null;

		for(int i=0; i<6; i++){
			auxResum = new Instances(datasetResumResumPrediccio, 0);
			auxHistoric = new Instances(datasetComplertPrediccio, 0);
			switch (i){
			case 0:
				for(int j=0; j<datasetResumResumPrediccio.numInstances();j++){
					if(datasetResumResumPrediccio.instance(j).value(datasetResumResumPrediccio.numAttributes()-1)==0){
						auxResum.add(datasetResumResumPrediccio.instance((j)));
						auxHistoric.add(datasetComplertPrediccio.instance((j)));
					}
				}
				sNota = new String("NP");
				actual = auxResum.numInstances();
				actualPercent = actual;
				resultPercent = (actualPercent/(float)total)*(float)100.00;
				break;

			case 1:
				for(int j=0; j<datasetResumResumPrediccio.numInstances();j++){
					if(datasetResumResumPrediccio.instance(j).value(datasetResumResumPrediccio.numAttributes()-1)==1){
						auxResum.add(datasetResumResumPrediccio.instance((j)));
						auxHistoric.add(datasetComplertPrediccio.instance((j)));
					}
				}
				sNota = new String("Suspenso");
				actual = auxResum.numInstances();
				actualPercent = actual;
				resultPercent = (actualPercent/(float)total)*(float)100.00;
				break;

			case 2:
				for(int j=0; j<datasetResumResumPrediccio.numInstances();j++){
					if(datasetResumResumPrediccio.instance(j).value(datasetResumResumPrediccio.numAttributes()-1)==2){
						auxResum.add(datasetResumResumPrediccio.instance((j)));
						auxHistoric.add(datasetComplertPrediccio.instance((j)));
					}
				}
				sNota = new String("Aprobado");
				actual = auxResum.numInstances();
				actualPercent = actual;
				resultPercent = (actualPercent/(float)total)*(float)100.00;
				break;

			case 3:
				for(int j=0; j<datasetResumResumPrediccio.numInstances();j++){
					if(datasetResumResumPrediccio.instance(j).value(datasetResumResumPrediccio.numAttributes()-1)==3){
						auxResum.add(datasetResumResumPrediccio.instance((j)));
						auxHistoric.add(datasetComplertPrediccio.instance((j)));
					}
				}
				sNota = new String("Notable");
				actual = auxResum.numInstances();
				actualPercent = actual;
				resultPercent = (actualPercent/(float)total)*100;
				break;

			case 4:
				for(int j=0; j<datasetResumResumPrediccio.numInstances();j++){
					if(datasetResumResumPrediccio.instance(j).value(datasetResumResumPrediccio.numAttributes()-1)==4){
						auxResum.add(datasetResumResumPrediccio.instance((j)));
						auxHistoric.add(datasetComplertPrediccio.instance((j)));
					}
				}
				sNota = new String("Excelente");
				actual = auxResum.numInstances();
				actualPercent = actual;
				resultPercent = (actualPercent/(float)total)*(float)100.00;
				break;

			case 5:
				auxResum = new Instances(datasetResumResumPrediccio);
				auxHistoric = new Instances(datasetComplertPrediccio);
				sNota = new String("Totes");
				actual = auxResum.numInstances();
				actualPercent = actual;
				resultPercent = (actualPercent/(float)total)*(float)100.00;
				break;
			}
			prediccio[i] = new Prediccio(sNota, auxResum, auxHistoric, actual, Math.round(resultPercent*100.0)/100.0f, total);
		}

		boolean fi =false;

		for(int i=0; i<6 && !fi; i++){
			if(prediccio[i].getDatasetAlumnesNotaHistoric().numInstances()!=0){
				auxResum = new Instances(prediccio[i].getDatasetAlumnesNotaResum(),0);
				auxHistoric = new Instances(prediccio[i].getDatasetAlumnesNotaHistoric(),0);
			}
		}

	}

	public Instances getPrediccioNotes(String sNota, int iMode){
		if(sNota.equals("TODAS")){
			if(iMode==1){
				return prediccio[NOTA_TODAS].getDatasetAlumnesNotaHistoric();
			}else{
				return prediccio[NOTA_TODAS].getDatasetAlumnesNotaResum();
			}
		}

		if(sNota.equals("NP")){
			if(iMode==1){
				return prediccio[NOTA_NP].getDatasetAlumnesNotaHistoric();
			}else{
				return prediccio[NOTA_NP].getDatasetAlumnesNotaResum();
			}
		}

		if(sNota.equals("SUSPENSO")){
			if(iMode==1){
				return prediccio[NOTA_SUSPENSO].getDatasetAlumnesNotaHistoric();
			}else{
				return prediccio[NOTA_SUSPENSO].getDatasetAlumnesNotaResum();
			}
		}

		if(sNota.equals("APROBADO")){
			if(iMode==1){
				return prediccio[NOTA_APROBADO].getDatasetAlumnesNotaHistoric();
			}else{
				return prediccio[NOTA_APROBADO].getDatasetAlumnesNotaResum();
			}
		}

		if(sNota.equals("AUX")){
			if(iMode==1){
				return auxHistoric;
			}else{
				return auxResum;
			}
		}

		if(sNota.equals("NOTABLE")){
			if(iMode==1){
				return prediccio[NOTA_NOTABLE].getDatasetAlumnesNotaHistoric();
			}else{
				return prediccio[NOTA_NOTABLE].getDatasetAlumnesNotaResum();
			}
		}else{
			if(iMode==1){
				return prediccio[NOTA_EXCELENTE].getDatasetAlumnesNotaHistoric();
			}else{
				return prediccio[NOTA_EXCELENTE].getDatasetAlumnesNotaResum();
			}
		}

	}


	public int getNumNotes(String sNota){
		if(sNota.equals("TODAS")){
			return prediccio[NOTA_TODAS].getiNumAlumnesNota();
		}

		if(sNota.equals("NP")){
			return prediccio[NOTA_NP].getiNumAlumnesNota();
		}

		if(sNota.equals("SUSPENSO")){
			return prediccio[NOTA_SUSPENSO].getiNumAlumnesNota();
		}

		if(sNota.equals("APROBADO")){
			return prediccio[NOTA_APROBADO].getiNumAlumnesNota();
		}

		if(sNota.equals("NOTABLE")){
			return prediccio[NOTA_NOTABLE].getiNumAlumnesNota();
		}else{
			return prediccio[NOTA_EXCELENTE].getiNumAlumnesNota();
		}
	}

	public float getPercent(String sNota){
		if(sNota.equals("TODAS")){
			return prediccio[NOTA_TODAS].getiPercentatgeNota();
		}

		if(sNota.equals("NP")){
			return prediccio[NOTA_NP].getiPercentatgeNota();
		}

		if(sNota.equals("SUSPENSO")){
			return prediccio[NOTA_SUSPENSO].getiPercentatgeNota();
		}

		if(sNota.equals("APROBADO")){
			return prediccio[NOTA_APROBADO].getiPercentatgeNota();
		}

		if(sNota.equals("NOTABLE")){
			return prediccio[NOTA_NOTABLE].getiPercentatgeNota();
		}else{
			return prediccio[NOTA_EXCELENTE].getiPercentatgeNota();
		}
	}

	public double[] getNotes() {
		double[] notes = new double[datasetComplertPrediccio.numInstances()];
		for(int i=0; i<datasetComplertPrediccio.numInstances(); i++) {
			notes[i] = datasetComplertPrediccio.instance(i).value(datasetComplertPrediccio.numAttributes()-1);
		}
		return notes;
	}

	public double[] getNotesNONP() {
		double[] notes = null;
		int compt = 0;
		for(int i=0; i<datasetComplertPrediccio.numInstances(); i++) {
			if(datasetComplertPrediccio.instance(i).value(datasetComplertPrediccio.numAttributes()-1)!=-1) {
				compt++;
			}
		}

		notes = new double[compt];
		compt = 0;

		for(int i=0; i<datasetComplertPrediccio.numInstances(); i++) {
			if(datasetComplertPrediccio.instance(i).value(datasetComplertPrediccio.numAttributes()-1)!=-1) {
				notes[compt] = datasetComplertPrediccio.instance(i).value(datasetComplertPrediccio.numAttributes()-1);
				compt++;
			}
		}

		return notes;
	}

	public int getNumAlumnesPrediccio() {
		return datasetComplertPrediccio.numInstances();
	}

}
