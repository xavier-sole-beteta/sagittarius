package sagittarius.model.perfils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;

import sagittarius.model.principal.ConfiguracioSagittarius;

import weka.clusterers.XMeans;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.neighboursearch.KDTree;
import weka.gui.arffviewer.ArffPanel;

/**
 * <b>Perfils</b> classe principal del model pel cas funcional pel descobriment de perfils.
 * Comprèn totes les funcionalitat del nucli per la detecció de perfils d'alumnes. Hereta
 * de la classe XMeans de la llibreria d'algorismes WEKA per tal de disposar de la implementació
 * de l'algorisme x-Means.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class Perfils extends XMeans {
	private String dataset;

	private Instances datasetComplert; 	// original del fitxer d'entrada
	private Instances datasetCaracteristiques; 	// només amb notes
	private Instances datasetResum; 	// Login, Nom, Cognoms
	private Instances centroidesNominals;	// Centroides nominals

	private Instances[] alumnesPerfilsResum;
	private Instances[] alumnesPerfilsComplert;

	private Perfil[] perfilsDetectats;


	public Perfils(String dataset) {
		this.dataset = dataset;
	}

	public String getDataset() {
		return dataset;
	}

	public void detectarPerfils(int minPerfils, int maxPerfils){
		ArffLoader cargadorARRF = new ArffLoader();
		try {
			cargadorARRF.setSource(new FileInputStream(dataset));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			datasetComplert = cargadorARRF.getDataSet();
		} catch (IOException e) {
			e.printStackTrace();
		} 


		datasetResum = new Instances(datasetComplert);
		for(int i=0; i<datasetResum.numAttributes(); i++){
			if(	!datasetResum.attribute(i).name().equals("Nombre") &&
					!datasetResum.attribute(i).name().equals("Apellidos") &&
					!datasetResum.attribute(i).name().equals("Expediente")) {
				datasetResum.deleteAttributeAt(i);
				i=i-1;
			}
		}

		int count = ConfiguracioSagittarius.numAtributsIdentificadorsIntern;
		datasetCaracteristiques = new Instances(datasetComplert);
		for(int i=0; i<datasetResum.numAttributes() && count>0; i++){
			if(datasetCaracteristiques.attribute(i).name().equals("Nombre") ||
					datasetCaracteristiques.attribute(i).name().equals("Apellidos") ||
					datasetCaracteristiques.attribute(i).name().equals("Expediente")) {
				datasetCaracteristiques.deleteAttributeAt(i);
				i=i-1;
				count--;
			}
		}     
		this.setMinNumClusters(minPerfils);
		this.setMaxNumClusters(maxPerfils);
		this.setKDTree(new KDTree(datasetCaracteristiques));


		try {
			if (ConfiguracioSagittarius.debug) { 
				System.out.println("Hi ha atributs nominals? "+this.checkForNominalAttributes(datasetCaracteristiques));
			}
			this.buildClusterer(datasetCaracteristiques);
			if (ConfiguracioSagittarius.debug) { 
				System.out.println(this.toString());
			}
			arodoneix(m_ClusterCenters);
			centroidesNominals = crearNominal(m_ClusterCenters);
			separaInstancies();
			separaInstancies2();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public int getNumPerfils(){
		return m_ClusterCenters.numInstances();
	}

	public Instances getCentroidesNumerics(){
		return m_ClusterCenters;
	}

	public Instances getCentgroidesNominals(){
		return centroidesNominals;
	}

	public void mostraCentroides(){
		if (ConfiguracioSagittarius.debug) { 
			System.out.println(this.m_ClusterCenters.toString());
		}
		try {
			int a = this.clusterInstance(datasetCaracteristiques.instance(0));
			if (ConfiguracioSagittarius.debug) { 
				System.out.println("assignada al cluster: "+a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArffPanel ap = new ArffPanel(this.m_ClusterCenters);
		JFrame k = new JFrame();
		k.add(ap);
		k.setSize(800,600);
		k.setVisible(true);

	}

	private void canviaCentroides(){
		Instances nominals = new Instances(this.m_ClusterCenters,0);

		Instance aux = new Instance(m_ClusterCenters.instance(0));

		for(int i=0; i<m_ClusterCenters.numInstances(); i++){
			for(int j=0; j<m_ClusterCenters.numAttributes(); j++){
				double valor = m_ClusterCenters.instance(i).value(j);
				if(valor < (double) 0){
					aux.setValue(j, "NP");
				}else{
					aux.setValue(j, "GOOD");
				}
			}
			nominals.add(aux);
		}
	}


	private void arodoneix(Instances numeric){
		for(int i=0; i<numeric.numInstances(); i++){
			for(int j=0; j<numeric.numAttributes(); j++){
				double valor = numeric.instance(i).value(j);
				numeric.instance(i).setValue(j, Math.round(valor));
			}
		}
	}


	public int gteAlumnesTotals(){
		return datasetCaracteristiques.numInstances();
	}



	private Instances crearNominal2(Instances datasetNumeric){
		FastVector atributs;
		FastVector especialitats;
		FastVector valors;

		atributs = new FastVector();
		valors = new FastVector();
		especialitats = new FastVector();

		valors.addElement("NP");
		valors.addElement("Suspenso");
		valors.addElement("Aprobado");
		valors.addElement("Notable");
		valors.addElement("Excelente");

		especialitats.addElement("Eng Sistemes de Telecomunicació");
		especialitats.addElement("Eng Sistemes audiovisuals");
		especialitats.addElement("Eng Telemàtica");
		especialitats.addElement("Eng Multimèdia");
		especialitats.addElement("Eng Organització de les TIC");
		especialitats.addElement("Eng Electrònica de Telecomunicació");
		especialitats.addElement("Eng Informàtica");

		// Posem els noms dels atributs del dataset nominal (mateixos que el dataset numeric)
		for(int i=0; i<datasetNumeric.numAttributes(); i++){
			if (datasetNumeric.attribute(i).isString()) {
				atributs.addElement(new Attribute(datasetNumeric.instance(0).attribute(i).name(), (FastVector)null));				
			}
			if (datasetNumeric.attribute(i).isNominal()) {
				atributs.addElement(new Attribute(datasetNumeric.instance(0).attribute(i).name(), especialitats));
			}
			if (datasetNumeric.attribute(i).isNumeric()) {
				atributs.addElement(new Attribute(datasetNumeric.instance(0).attribute(i).name(), valors));
			}
		}

		// Creem el dataset nominal
		Instances datasetNominal = new Instances("Nominal", atributs, 0);

		Instance aux = null;

		for(int i=0; i<datasetNumeric.numInstances(); i++){
			aux = new Instance(datasetNominal.numAttributes());
			aux.setDataset(datasetNominal);
			for(int j=0; j<datasetNumeric.numAttributes(); j++){
				if (datasetNumeric.attribute(j).isString()) {
					aux.setValue(j, datasetNumeric.instance(i).stringValue(j));	
				}else{
					if (datasetNumeric.instance(0).attribute(j).name().equals("Especialitat")) {
						aux.setValue(j, datasetNumeric.instance(i).stringValue(j));
					}else{
						double valor = datasetNumeric.instance(i).value(j);
						if(valor < (double) 0){
							aux.setValue(j, "NP");
						}
						if((valor >= (double) 0) && (valor < (double)5)){
							aux.setValue(j, "Suspenso");
						}
						if((valor >= (double) 5) && (valor < (double)7)){
							aux.setValue(j, "Aprobado");
						}
						if((valor >= (double) 7) && (valor < (double)9)){
							aux.setValue(j, "Notable");
						}
						if((valor >= (double) 9) && (valor <= (double)10)){
							aux.setValue(j, "Excelente");
						}
					}
				}
			}
			// Afegim al nova instancia al dataset nominal
			datasetNominal.add(aux);
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println(datasetNominal.toString());
		}
		return datasetNominal;
	}


	private Instances crearNominal(Instances datasetNumeric){
		FastVector atributs;
		FastVector valors;

		atributs = new FastVector();
		valors = new FastVector();

		valors.addElement("NP");
		valors.addElement("Suspenso");
		valors.addElement("Aprobado");
		valors.addElement("Notable");
		valors.addElement("Excelente");

		// Posem els noms dels atributs del dataset nominal (mateixos que el dataset numeric)
		for(int i=0; i<datasetNumeric.numAttributes(); i++){
			atributs.addElement(new Attribute(datasetNumeric.instance(0).attribute(i).name(), valors));
		}

		// Creem el dataset nominal
		Instances datasetNominal = new Instances("Nominal", atributs, 0);

		Instance aux = null;

		for(int i=0; i<datasetNumeric.numInstances(); i++){
			aux = new Instance(datasetNominal.numAttributes());
			aux.setDataset(datasetNominal);
			for(int j=0; j<datasetNumeric.numAttributes(); j++){
				double valor = datasetNumeric.instance(i).value(j);
				if(valor < (double) 0){
					aux.setValue(j, "NP");
				}
				if((valor >= (double) 0) && (valor < (double)5)){
					aux.setValue(j, "Suspenso");
				}
				if((valor >= (double) 5) && (valor < (double)7)){
					aux.setValue(j, "Aprobado");
				}
				if((valor >= (double) 7) && (valor < (double)9)){
					aux.setValue(j, "Notable");
				}
				if((valor >= (double) 9) && (valor <= (double)10)){
					aux.setValue(j, "Excelente");
				}
			}
			// Afegim al nova instancia al dataset nominal
			datasetNominal.add(aux);
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println(datasetNominal.toString());
		}
		return datasetNominal;
	}


	private void imprimirAssignacions(){
		int[] assignacions = null;
		try {
			assignacions = this.m_ClusterAssignments;
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0; i<m_ClusterCenters.numInstances(); i++){
			if (ConfiguracioSagittarius.debug) { 
				System.out.println("----> Elements assignats al cluster "+i);	
			}
			for (int j=0; j < assignacions.length; j++){
				if(assignacions[j]==i){
					if (ConfiguracioSagittarius.debug) { 
						System.out.println("\t "+datasetCaracteristiques.instance(j));
					}
				}
			}
		}
	}

	public Instances getAlumnesPerfilResum(int index){
		return alumnesPerfilsResum[index];
	}

	public int getNumAlumnesPerfil(int index){
		return alumnesPerfilsResum[index].numInstances();
	}

	public Instances getAlumnesPerfilCaracteristiques(int index){
		return alumnesPerfilsComplert[index];
	}

	public Instances getAlumnesPerfilCaracteristiquesNumeric(int index){
		return perfilsDetectats[index].getDatasetAlumnesPerfilNumeric();
	}

	public Instances getAlumnesPerfilCaracteristiquesLletres(int index){
		return perfilsDetectats[index].getDatasetAlumnesPerfilLletres();
	}

	public int getNumAlumnesPerfil2(int index){
		return perfilsDetectats[index].getiNumAlumnesPerfil();
	}

	public float getPercentPerfil2(int index){
		return perfilsDetectats[index].getiPercentatgePerfil();
	}

	private void separaInstancies2(){

		perfilsDetectats = new Perfil[m_ClusterCenters.numInstances()]; 

		Instances auxAlumnesPerfil;
		Float resultPercent = (float) 0;
		Float total = (float) datasetComplert.numInstances();

		int[] assignacions = null;
		try {
			assignacions = this.m_ClusterAssignments;
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(int i=0; i<this.m_ClusterCenters.numInstances(); i++){
			auxAlumnesPerfil = new Instances(datasetComplert, 0);
			for (int j=0; j < assignacions.length; j++){
				if(assignacions[j]==i){
					auxAlumnesPerfil.add(datasetComplert.instance(j));
				}
			}

			resultPercent = (auxAlumnesPerfil.numInstances()/total)*(float)100.00;

			if (ConfiguracioSagittarius.debug) { 
				System.out.println("********************: "+resultPercent);
				System.out.println("********************: "+Math.round(resultPercent));
			}
			perfilsDetectats[i] = new Perfil(i, auxAlumnesPerfil, crearNominal2(auxAlumnesPerfil), auxAlumnesPerfil.numInstances(), Math.round(resultPercent*100.0)/100.0f, datasetComplert.numInstances(), generaEstadistiques(centroidesNominals, i));
		}
	}

	public int getNumCaracteristiques() {
		return datasetCaracteristiques.numAttributes();
	}

	private void separaInstancies(){

		alumnesPerfilsResum = new Instances[m_ClusterCenters.numInstances()];
		alumnesPerfilsComplert = new Instances[m_ClusterCenters.numInstances()];    	  

		for(int i=0; i<m_ClusterCenters.numInstances(); i++){
			alumnesPerfilsResum[i] = new Instances(datasetResum, 0);
			alumnesPerfilsComplert[i] = new Instances(datasetComplert, 0);
		}


		int[] assignacions = null;
		try {
			assignacions = this.m_ClusterAssignments;
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(int i=0; i<this.m_ClusterCenters.numInstances(); i++){
			for (int j=0; j < assignacions.length; j++){
				if(assignacions[j]==i){
					alumnesPerfilsResum[i].add(datasetResum.instance(j));
					alumnesPerfilsComplert[i].add(datasetComplert.instance(j));
				}
			}
		}
	}
	
	
	private int[] generaEstadistiquesTarget(Instances caracteristiques) {
		int[] e = new int[5];
		for (int i=0; i<e.length; i++) e[i] = 0;
		double valor = 0;

		for(int i=0; i<caracteristiques.numInstances(); i++){
			valor = caracteristiques.instance(i).value(caracteristiques.numAttributes()-1);
			if(valor < 0){
				e[0] = e[0] + 1;
			}
			if(valor>=0 && valor<=4.99){
				e[1] = e[1] + 1;
			}
			if(valor>=5 && valor<=6.99){
				e[2] = e[2] + 1;
			}
			if(valor>=7 && valor<=8.99){
				e[3] = e[3] + 1;
			}
			if(valor>=9){
				e[4] = e[4] + 1;
			}
		}

		for (int i=0; i<e.length; i++) {
			if (ConfiguracioSagittarius.debug) { 
				System.out.println("i: "+e[i]);
			}
		}

		return e;
	}

	private int[] generaEstadistiques(Instances caracteristiques, int quin) {
		int[] e = new int[5];
		for (int i=0; i<e.length; i++) e[i] = 0;
		String valor = null;

		for(int i=0; i<caracteristiques.numInstances(); i++){
			for(int j=0; j<caracteristiques.numAttributes(); j++){
				if (i == quin) {
					valor = caracteristiques.instance(i).stringValue(j);
					if(valor.equals("NP")){
						e[0] = e[0] + 1;
					}
					if(valor.equals("Suspenso")){
						e[1] = e[1] + 1;
					}
					if(valor.equals("Aprobado")){
						e[2] = e[2] + 1;
					}
					if(valor.equals("Notable")){
						e[3] = e[3] + 1;
					}
					if(valor.equals("Excelente")){
						e[4] = e[4] + 1;
					}
				}
			}
		}

		for (int i=0; i<e.length; i++) {
			if (ConfiguracioSagittarius.debug) { 
				System.out.println("i: "+e[i]);
			}
		}

		return e;
	}

	public int[] getEstadistiquesPerfil(int i) {
		return perfilsDetectats[i].getEstadistiques();
	}

}
