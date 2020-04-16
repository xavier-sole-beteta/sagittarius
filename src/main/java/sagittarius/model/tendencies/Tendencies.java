package sagittarius.model.tendencies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sagittarius.model.principal.ConfiguracioSagittarius;

import weka.associations.Apriori;
import weka.associations.AprioriItemSet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * <b>Tendencies</b> classe principal del model pel cas funcional pel descobriment de tendències.
 * Comprèn totes les funcionalitat del nucli pel descobriment de tendències. Hereta
 * de la classe Apriori de la llibreria d'algorismes WEKA per tal de disposar de la implementació
 * de l'algorisme Apriori.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class Tendencies extends Apriori{

	private String dataset;
	private String datasetNumeric;

	private Instances datasetComplet;
	private Instances datasetTendencies;

	private int numMaxReglesDetectar;
	private int numReglesDetectadesActual;

	private boolean bTemporalitat;

	private int iNumReglesActual;
	private int incrementNumReglesActual;


	public Tendencies(String dataset) {
		this.dataset = dataset;
	}

	public String getDataset() {
		return dataset;
	}

	public void detectaTendencies(int iNumMaximTendencies, boolean bTemporalitat){

		ArffLoader cargadorARRF = new ArffLoader();
		try {
			cargadorARRF.setSource(new FileInputStream(dataset));
			datasetComplet = cargadorARRF.getDataSet();
			this.numMaxReglesDetectar = iNumMaximTendencies;
			this.bTemporalitat = bTemporalitat;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



		for(int i=0; i<datasetComplet.numAttributes(); i++){
			if(	datasetComplet.attribute(i).name().equals("Nombre") ||
					datasetComplet.attribute(i).name().equals("Apellidos") ||
					datasetComplet.attribute(i).name().equals("Expediente") ) {
				datasetComplet.deleteAttributeAt(i);
				i=i-1;
			}
		}

		double confianca = 0.9;
		int numReglesUltimaIteracio=0;

		setLowerBoundMinSupport(0.1);
		setUpperBoundMinSupport(1.0);

		int iteracio = 0;

		if(bTemporalitat){

			numReglesDetectadesActual = 0;
			incrementNumReglesActual = 0;
			iNumReglesActual = iNumMaximTendencies * 100;

			// Mentre el nombre de regles detectades sigui inferior al dessitjat i no haguem
			// arribat a la confiança mínima.
			while(numReglesDetectadesActual<=this.numMaxReglesDetectar){
				if (ConfiguracioSagittarius.debug) { 
					System.out.println("\n==========================================================");
					System.out.println("ITERACIÓ = "+iteracio);
				}
				setNumRules(iNumReglesActual);
				setMinMetric(confianca);
				if (ConfiguracioSagittarius.debug) { 
					System.out.println(" -> NUM. REGLES APRIORI: "+getNumRules());
					System.out.println(" -> SUPORT LOWER: "+getLowerBoundMinSupport());
					System.out.println(" -> SUPORT UPPER: "+getUpperBoundMinSupport());
					System.out.println(" -> CONFIANÇA UPPER: "+getMinMetric());
					System.out.println(" -> SIGNIFICANÇA: "+getSignificanceLevel());
				}

				numReglesDetectadesActual = 0;

				descobrirTendencies();

				creaDatasetTendencies();

				escriuTendencies();
				if (ConfiguracioSagittarius.debug) { 
					System.out.println(" -> NUM. REGLES OK DETECTADES: "+numReglesDetectadesActual);
				}
				if(numReglesUltimaIteracio==numReglesDetectadesActual) {
					if (ConfiguracioSagittarius.debug) { 
						System.out.println(" * NUM. REGLES DETECTADES IGUAL A L'ANTERIOR: SI");
					}
					iNumReglesActual = iNumReglesActual+(incrementNumReglesActual+1)*10000;
					incrementNumReglesActual++;
				}

				if(incrementNumReglesActual == 10) {
					iNumReglesActual = iNumMaximTendencies;
					incrementNumReglesActual = 0;
					if(confianca>=0.1) {
						confianca = confianca - 0.05;
					}else{
						break;
					}
					iteracio++;
				}

				numReglesUltimaIteracio = numReglesDetectadesActual;
				if (ConfiguracioSagittarius.debug) { 
					System.out.println("==========================================================");
				}
			}
		}else{
			numReglesDetectadesActual = 0;
			incrementNumReglesActual = 0;
			iNumReglesActual = iNumMaximTendencies;

			// Mentre el nombre de regles detectades sigui inferior al dessitjat i no haguem
			// arribat a la confiança mínima.
			while(numReglesDetectadesActual<=this.numMaxReglesDetectar){
				if (ConfiguracioSagittarius.debug) { 
					System.out.println("\n==========================================================");
					System.out.println("ITERACIÓ = "+iteracio);
				}
				setNumRules(iNumReglesActual);
				setMinMetric(confianca);
				if (ConfiguracioSagittarius.debug) { 
					System.out.println(" -> NUM. REGLES APRIORI: "+getNumRules());
					System.out.println(" -> SUPORT LOWER: "+getLowerBoundMinSupport());
					System.out.println(" -> SUPORT UPPER: "+getUpperBoundMinSupport());
					System.out.println(" -> CONFIANÇA UPPER: "+getMinMetric());
					System.out.println(" -> SIGNIFICANÇA: "+getSignificanceLevel());
				}

				numReglesDetectadesActual = 0;

				descobrirTendencies();

				creaDatasetTendencies();

				escriuTendencies();
				if (ConfiguracioSagittarius.debug) { 
					System.out.println(" -> NUM. REGLES OK DETECTADES: "+numReglesDetectadesActual);
				}
				if(confianca>=0.1) {
					confianca = confianca - 0.05;
				}else{
					break;
				}
				numReglesUltimaIteracio = numReglesDetectadesActual;
				if (ConfiguracioSagittarius.debug) { 
					System.out.println("==========================================================");
				}
			}

		}

	}

	private void descobrirTendencies(){
		try {
			this.buildAssociations(datasetComplet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void creaDatasetTendencies(){
		FastVector atts;
		atts = new FastVector();
		atts.addElement(new Attribute("Tendencia", (FastVector) null));
		atts.addElement(new Attribute("Soporte (%)"));
		atts.addElement(new Attribute("Confianza (%)"));
		datasetTendencies = new Instances("Tendencias descubiertas", atts, 0);
	}


	private void escriuTendencies(){
		FastVector[] regles = this.m_allTheRules;
		String regla = null;
		boolean bGeneraTendencia = false;
		if (ConfiguracioSagittarius.debug) { 
			System.out.println(regles.length);
		}
		for(int i=0; i<regles[0].size() && (numReglesDetectadesActual<numMaxReglesDetectar); i++){
			AprioriItemSet antecedents = (AprioriItemSet) regles[0].elementAt(i);
			AprioriItemSet consequents = (AprioriItemSet) regles[1].elementAt(i);

			bGeneraTendencia = false;

			if(bTemporalitat){
				if(comprovaTemporalitat(antecedents.items(), consequents.items())){
					bGeneraTendencia = true;
				}else{
					bGeneraTendencia = false;
				}
			}else{
				bGeneraTendencia = true;
			}

			if(bGeneraTendencia){
				numReglesDetectadesActual++;
				regla = new String("SI ");
				float actualPercent = (float) 0;
				float resultConfiança = (float) 0;

				float suportPercent = (float) 0;
				float resultSuport = (float) 0;

				actualPercent = (float) consequents.counter() / (float) antecedents.counter();
				resultConfiança = actualPercent * 100;

				suportPercent = (float) consequents.support() / (float) datasetComplet.numInstances();
				resultSuport = suportPercent * 100;

				boolean primer = true;

				for(int j=0; j<antecedents.items().length; j++){
					int[] g = antecedents.items();
					if(g[j]!=-1){
						if(primer){
							primer = false;
							regla = regla + datasetComplet.attribute(j).name()+"="+datasetComplet.attribute(j).value(g[j]);
						}else{
							regla = regla + " y "+ datasetComplet.attribute(j).name()+"="+datasetComplet.attribute(j).value(g[j]);
						}
					}
				}

				regla = regla + " --> ";

				primer = true;
				for(int j=0; j<consequents.items().length; j++){
					int[] g = consequents.items();
					if(g[j]!=-1){
						if(primer){
							primer = false;
							regla = regla + datasetComplet.attribute(j).name()+"="+datasetComplet.attribute(j).value(g[j]);
						}else{
							regla = regla + " y "+ datasetComplet.attribute(j).name()+"="+datasetComplet.attribute(j).value(g[j]);
						}
					}
				}
				afegeixTendencia(regla, (int) resultSuport, (int) resultConfiança);

			}
		}

	}


	private void afegeixTendencia(String regla, int suport, int resultPercent){
		Instance aux = new Instance(3);
		aux.setDataset(datasetTendencies);
		aux.setValue(0, regla);
		aux.setValue(1, suport);
		aux.setValue(2, resultPercent);
		datasetTendencies.add(aux);
	}

	public int getNumTendencies(){
		return numReglesDetectadesActual;
	}



	private boolean comprovaTemporalitat(int[] conjuntA, int[] conjuntB){

		int maxIndexA = 0;
		int minIndexB = 0;
		boolean fiB = false;

		for(int i=0; i<conjuntA.length; i++){
			if(conjuntA[i]!=-1){
				maxIndexA = i;
			}
		}

		for(int i=0; i<conjuntB.length && !fiB; i++){
			if(conjuntB[i]!=-1){
				minIndexB = i;
				fiB = true;
			}
		}

		return (maxIndexA<minIndexB);
	}

	public Instances getRegles(){
		return datasetTendencies;
	}

	public void setDatasetNumeric(String datasetNumeric) {
		this.datasetNumeric = datasetNumeric;
	}

	public String getDatasetNumeric() {
		return this.datasetNumeric;
	}
}
