package sagittarius.model.grupsDeTreball;

import java.io.FileInputStream;

import sagittarius.model.principal.ConfiguracioSagittarius;



import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


/*
 * API WEKA STABLE
 * http://weka.sourceforge.net/doc.stable/
 * 
 */
/**
 * <b>GrupsDeTreball</b> classe principal del model pel cas funcional per la creació de grups
 * de treball. Comprèn totes les funcionalitat del nucli per la creació de grups de treball. Hereta
 * de la classe SimpleKMeans de la llibreria d'algorismes WEKA per tal de disposar de la implementació
 * de l'algorisme k-Means.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GrupsDeTreball extends SimpleKMeans{

	private String datasetPath;
	private Instances datasetComplert;
	private Instances datasetCaracteristiques;
	private Instances datasetResum;
	
	private Integer numCentroides;
	
	private Instances[] assignacionsCaracteristiques;
	private Instances[] assignacionsResum;
	
	private int numGrups;
	
	private boolean bHeterogenis;
	
	private Grup[] propostaGrups;


	public GrupsDeTreball(String dataset) {
		this.datasetPath = dataset;
	}
	
	public String getDataset() {
		return datasetPath;
	}
	
    public void generaGrupsDeTreball(Integer numCentroides, boolean bHeterogenis) {

        try {
            // Cargar dataset desde ARFF
            ArffLoader cargadorARRF = new ArffLoader();
            cargadorARRF.setSource(new FileInputStream(datasetPath));
                        
            // Dataset original del fitxer d'entrada
            datasetComplert = cargadorARRF.getDataSet(); 

            // Crear datasets Resum i Caracteristiques
            crearDatasets();
        	
            this.bHeterogenis = bHeterogenis;
            this.numCentroides = numCentroides;
            
            // Configuració K-MEANS
            this.setNumClusters(numCentroides);
            this.setMaxIterations(ConfiguracioRellevanciaKMeans.MAX_ITERACIONS);
            this.setPreserveInstancesOrder(ConfiguracioRellevanciaKMeans.CONSERVAR_ORDRE_ORIGINAL);
            this.setDontReplaceMissingValues(ConfiguracioRellevanciaKMeans.NO_SUBSTITUIR_VALORS_DESCONEGUTS);
        	
            // Determinació de la millor solució, es busca la solució que més s'acosti
            // a l'equilibri ideal. L'equilibri ideal és igual al nombre d'elements dividit
            // pel nombre de clústers.
            float equilibriIdeal = datasetCaracteristiques.numInstances()/numCentroides;
            int millorSolucio = 0;
            float millorGrauDesequilibri = 999999;
            float desequilibriActual;
        	
            // Execució KMEANS
            for(int i=0; i<ConfiguracioRellevanciaKMeans.MAX_ITERACIONS_EQUILIBRAT; i++) {
        		if (ConfiguracioSagittarius.debug) { 
        			System.out.println("Generant solució número: "+i);
        		}
            	this.setNumClusters(numCentroides);
            	this.setSeed(i);
            	try{
            		this.buildClusterer(datasetCaracteristiques);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            	separaInstancies();
            	desequilibriActual = calculaGrauDesequilibri(equilibriIdeal);
            	if(desequilibriActual < millorGrauDesequilibri) {
            		millorSolucio = i;
            		millorGrauDesequilibri = desequilibriActual;
            	}
        		if (ConfiguracioSagittarius.debug) { 
        			System.out.println("----------------------------------------------------------");
        		}
            }
            
    		if (ConfiguracioSagittarius.debug) { 
    			System.out.println("La millor solució és: "+millorSolucio);
    			System.out.println("Amb un equilibri de: "+millorGrauDesequilibri);
    		}
        	this.setNumClusters(numCentroides);
            this.setSeed(millorSolucio);
        	this.buildClusterer(datasetCaracteristiques);

        	// Crear datasets de les assignacions als clusters
        	separaInstancies();
        	
            // Crear els grups de treball
            if(this.bHeterogenis){
            	try{
            		creaGrupsDeTreballHeterogenis();
            	}catch(Exception e) {
            		if (ConfiguracioSagittarius.debug) { 
            			System.out.println("Error en la creació de GRUPS DE TREBALL HETEROGENIS");
            		}
            		e.printStackTrace();
            	}
            }else{
            	try{
            		creaGrupsDeTreballHomogenis();
            	}catch(Exception e) {
            		if (ConfiguracioSagittarius.debug) { 
            			System.out.println("Error en la creació de GRUPS DE TREBALL HOMOGENIS");
            		}
            		e.printStackTrace();
            	}
            }
            
            imprimirAssignacions();
        } catch (Exception e) {
        	if (ConfiguracioSagittarius.debug) { 
        		System.out.println("Error en la creació de grups de treball: " + e.getMessage());
        	}
        }
        
    }
    
    private float calculaGrauDesequilibri(float optim) {
    	float grauDesequilibri = 0;
    	
    	for(int i=0; i<numCentroides; i++) {
    		if (ConfiguracioSagittarius.debug) { 
    			System.out.println("->El cluster num."+i+" té "+assignacionsCaracteristiques[i].numInstances()+" instancies");
    		}
    		grauDesequilibri = grauDesequilibri + Math.abs(optim-assignacionsCaracteristiques[i].numInstances());
    	}
    	if (ConfiguracioSagittarius.debug) { 
    		System.out.println("** El grau de la SOLUCIÓ és: "+grauDesequilibri);
    	}
    	return grauDesequilibri;
    }
    
 
	private void crearDatasets(){
		int count = ConfiguracioSagittarius.numAtributsIdentificadorsIntern;
        // Crear dataset resum -> ("Apellidos", "Nombre", i "Expediente")
        datasetResum = new Instances(datasetComplert);
        for(int i=0; i<datasetResum.numAttributes(); i++){
           	if(	!datasetResum.attribute(i).name().equals("Nombre") &&
           		!datasetResum.attribute(i).name().equals("Apellidos") &&
           		!datasetResum.attribute(i).name().equals("Expediente")){
           			datasetResum.deleteAttributeAt(i);
           			i=i-1;
        	}
        }
        // Crear dataset per realitzar el clustering -> ("Apellidos", "Nombre", i "Expediente")
        datasetCaracteristiques = new Instances(datasetComplert);
        for(int i=0; i<datasetResum.numAttributes() && count>0; i++){
           	if(datasetCaracteristiques.attribute(i).name().equals("Nombre") ||
           		datasetCaracteristiques.attribute(i).name().equals("Apellidos") ||
           		datasetCaracteristiques.attribute(i).name().equals("Expediente")){
           			datasetCaracteristiques.deleteAttributeAt(i);
           			i=i-1;
           			count--;
        	}
        }
	}
	
	public Grup consultaGrup(int numGrup) {
		return propostaGrups[numGrup];
	}
    
    private void imprimirCentroides(){
    	  int count = 0;
          int[] tamanoClusters = this.getClusterSizes();
          Instances centroides = this.getClusterCentroids();
          Instance centroide;
          if (ConfiguracioSagittarius.debug) { 
        	  System.out.println("\nCentroides k-means ...");
          }
          try {
			for(int cluster=0; cluster < this.numberOfClusters(); cluster++){
			  	count += tamanoClusters[cluster];
			      centroide = centroides.instance(cluster);
			      System.out.print("\t -> Cluster "+cluster+" ("+tamanoClusters[cluster]+" instancias): ");
			      if (ConfiguracioSagittarius.debug) { 
			    	  System.out.println("Centroide["+centroide.toString()+"]");
			      }
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("Instancies clusteritzades: "+count);
		}
    }
    
    
    private void imprimirAssignacions(){
    	
    	int[] assignacions = null;
    	int n=0;
		try {
			assignacions = this.getAssignments();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(int i=0; i<numCentroides; i++){
    	   n = 0;
    	   if (ConfiguracioSagittarius.debug) { 
    		   System.out.println("***** [CLUSTER "+i+"] En total "+assignacionsCaracteristiques[i].numInstances()+" elements assignats!");
    	   }
       }
    }
    
    
    public void veureElementsClusters() throws Exception{

        int[] assignacions = this.getAssignments();
        for(int i=0; i<numCentroides; i++){
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
    
    
    private void separaInstancies(){

    	assignacionsCaracteristiques = new Instances[numCentroides];
     	assignacionsResum = new Instances[numCentroides];
    	
    	for(int i=0; i<numCentroides; i++){
    		assignacionsCaracteristiques[i] = new Instances(datasetCaracteristiques, 0);
    		assignacionsResum[i] = new Instances(datasetResum, 0);
    	}
		int[] assignacions = null;
		try {
			assignacions = this.getAssignments();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<numCentroides; i++){
			for (int j=0; j < assignacions.length; j++){
				if(assignacions[j]==i){
					assignacionsCaracteristiques[i].add(datasetCaracteristiques.instance(j));
					assignacionsResum[i].add(datasetResum.instance(j));
			 	}
		  	}
		}
    }
    
     
    public void creaGrupsDeTreballHeterogenis(){
    	
    	Instances[] propostaCaracteristiques;
    	Instances[] propostaResum;
    	
		numGrups = datasetResum.numInstances()/numCentroides;
		int aux = datasetResum.numInstances() - (numGrups*numCentroides);
		
		if(aux!=0){
			numGrups++;
		}
	 
		Integer[] numElementsPerCluster = new Integer[numCentroides];
		propostaGrups = new Grup[numGrups];
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("Caselles de numElementsPerCluster = "+numElementsPerCluster.length);
			System.out.println("Caselles de assignacionsCaracteristiques = "+assignacionsCaracteristiques.length);
		}
		for(int i=0; i<numCentroides; i++){
			numElementsPerCluster[i] = new Integer(assignacionsCaracteristiques[i].numInstances());
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("-> Grups complerts que podem fer: " +
					""+datasetCaracteristiques.numInstances()/numCentroides);
			System.out.println("-> Grups TOTALS que podem fer: " +
					""+numGrups);
		}
		propostaCaracteristiques = new Instances[numGrups];
		propostaResum = new Instances[numGrups];
		
		for(int i=0; i<numGrups; i++){
			propostaCaracteristiques[i] = new Instances(datasetCaracteristiques, 0);
			propostaResum[i] = new Instances(datasetResum, 0);
    	}
		
		int[] hiha = new int[numCentroides];
		int[] compren = new int[numCentroides];
		for(int k=0; k<hiha.length; k++){
			hiha[k] = 0;
			compren[k] = 0;
		}
		
		for(int i=0; i<numGrups; i++){
			for(int j=0; j<numCentroides; j++){
				if(numElementsPerCluster[j]>0){
					hiha[j] = 1;
				}else{
					hiha[j] = 0;
				}
				compren[j] = 0;
			}
			int assignats = 0;
			int actual = 0;
			boolean fi = false;
			while(assignats<numCentroides && !fi){
				if(hiha[actual]==1){
					propostaCaracteristiques[i].add(assignacionsCaracteristiques[actual].instance(assignacionsCaracteristiques[actual].numInstances()-numElementsPerCluster[actual]));
					propostaResum[i].add(assignacionsResum[actual].instance(assignacionsResum[actual].numInstances()-numElementsPerCluster[actual]));
					numElementsPerCluster[actual] = numElementsPerCluster[actual]-1;
					if(numElementsPerCluster[actual]==0){
						hiha[actual] = 0;
					}
					assignats++;
					compren[actual] = 1; 
				}
				actual++;
				if(actual==numCentroides) actual = 0;
				if(i==numGrups-1 && assignats==aux && aux!=0) fi = true;
			}
			propostaGrups[i] = new Grup(propostaResum[i], propostaCaracteristiques[i], crearNominal(propostaCaracteristiques[i]), propostaResum[i].numInstances(), (int) calculaGrauHeterogeneitat(compren), (int) ((calculaGrauHeterogeneitat(compren)/assignats)*100), generaEstadistiques(crearNominal(propostaCaracteristiques[i])));	
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("---> GRUPS CREATS: "+numGrups);
		}
 	}
    
    
 	private float calculaGrauHeterogeneitat(int[] compren){
 		
 		float grau = 0;
 		
 		for(int i=0; i<compren.length; i++){
 			if(compren[i]!=0){
 				grau++;
 			}
 		}
 		return grau;
 	} 
    
    
	public void creaGrupsDeTreballHomogenis(){
    	
		Instances[] propostaCaracteristiques;
		Instances[] propostaResum;
		
		numGrups = datasetResum.numInstances()/numCentroides;
		int aux = datasetResum.numInstances() - (numGrups*numCentroides);
		
		if(aux!=0){
			numGrups++;
		}
	 
		Integer[] numElementsPerCluster = new Integer[numCentroides];
		propostaGrups = new Grup[numGrups];
		
		for(int i=0; i<numCentroides; i++){
			numElementsPerCluster[i] = new Integer(assignacionsCaracteristiques[i].numInstances());
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("-> Grups complerts que podem fer: " +
					""+datasetCaracteristiques.numInstances()/numCentroides);
			System.out.println("-> Grups TOTALS que podem fer: " +
					""+numGrups);
		}
		propostaCaracteristiques = new Instances[numGrups];
		propostaResum = new Instances[numGrups];
		
		for(int i=0; i<numGrups; i++){
			propostaCaracteristiques[i] = new Instances(datasetCaracteristiques, 0);
			propostaResum[i] = new Instances(datasetResum, 0);
    	}
		
		int[] hiha = new int[numCentroides];
		int[] compren = new int[numCentroides];
		for(int k=0; k<hiha.length; k++){
			hiha[k] = 0;
			compren[k] = 0;
		}
		
		for(int i=0; i<numGrups; i++){
			for(int j=0; j<numCentroides; j++){
				if(numElementsPerCluster[j]>0){
					hiha[j] = 1;
				}else{
					hiha[j] = 0;
				}
				compren[j] = 0;
			}
			int assignats = 0;
			int actual = 0;
			boolean fi = false;
			while(assignats<numCentroides && !fi){
				if(hiha[actual]==1){
					propostaCaracteristiques[i].add(assignacionsCaracteristiques[actual].instance(assignacionsCaracteristiques[actual].numInstances()-numElementsPerCluster[actual]));
					propostaResum[i].add(assignacionsResum[actual].instance(assignacionsResum[actual].numInstances()-numElementsPerCluster[actual]));
					numElementsPerCluster[actual] = numElementsPerCluster[actual]-1;
					if(numElementsPerCluster[actual]==0){
						hiha[actual] = 0; 
					}
					compren[actual] = compren[actual] +1;	
					assignats++;
				}else{
					actual++;
				}
				if(actual==numCentroides) actual = 0;
				if(i==numGrups-1 && assignats==aux && aux!=0) fi = true;
			}
			propostaGrups[i] = new Grup(propostaResum[i], propostaCaracteristiques[i], crearNominal(propostaCaracteristiques[i]), propostaResum[i].numInstances(), (int) calculaGrauHomogeneitat(compren), (int) ((calculaGrauHomogeneitat(compren)/assignats)*100), generaEstadistiques(crearNominal(propostaCaracteristiques[i])));	
		}
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("---> GRUPS CREATS: "+numGrups);
		}
 	}
    
	private int[][] generaEstadistiques(Instances caracteristiques) {
		int[] e = new int[5];
		for (int i=0; i<e.length; i++) e[i] = 0;
		String valor = null;
		int[][] eNoves = new int[caracteristiques.numAttributes()][5];
		
		for(int i=0; i<caracteristiques.numInstances(); i++){
			for(int j=0; j<caracteristiques.numAttributes(); j++){
				valor = caracteristiques.instance(i).stringValue(j);
				if(valor.equals("NP")){
					e[0] = e[0] + 1;
					eNoves[j][0] = eNoves[j][0]+1;
				}
				if(valor.equals("Suspenso")){
					e[1] = e[1] + 1;
					eNoves[j][1] = eNoves[j][1]+1;
				}
				if(valor.equals("Aprobado")){
					e[2] = e[2] + 1;
					eNoves[j][2] = eNoves[j][2]+1;
				}
				if(valor.equals("Notable")){
					e[3] = e[3] + 1;
					eNoves[j][3] = eNoves[j][3]+1;
				}
				if(valor.equals("Excelente")){
					e[4] = e[4] + 1;
					eNoves[j][4] = eNoves[j][4]+1;
				}
			}
		}
		return eNoves;
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
		
		return datasetNominal;
	}
	
	
    
    private float calculaGrauHomogeneitat(int[] compren){
    	
 		float grau = -1;
 		
 		for(int i=0; i<compren.length; i++){
 			if(compren[i]>grau){
 				grau = compren[i];
 			}
 		}
 		
 		return grau;
 	} 
    

    public int getNumGrups(){
    	return numGrups;
    }
    
    
    public Instances getCentroides(){
    	return this.getClusterCentroids();
    }

    
    public int getNumIntegrants(int index){
    	if(propostaGrups==null) {
    		if (ConfiguracioSagittarius.debug) { 
    			System.out.println("prostaGrup és NULL!!!!!!!!!!!!!!!!");
    		}
    	}else{
        	if(propostaGrups[index]==null) {
        		if (ConfiguracioSagittarius.debug) { 
        			System.out.println("prostaGrup casella "+index+" és NULL!!!!!!!!!!!!");
        		}
        	}
    	}
    	return propostaGrups[index].getNumIntegrants();
    }
    
    
 	public int consultaGrau(int index){
 		return propostaGrups[index].getGrau();
 	}
 	
 	
 	public int consultaNumAlumnesGrau(int index){
 		return propostaGrups[index].getNumAlumnesGrau();
 	}

 	
 	public int consultaNumAlumnes(int index){
 		return propostaGrups[index].getNumIntegrants();
 	}
 	
    public Instances consultaMembresGrup(int index){
    	return propostaGrups[index].getAlumnes();
    }
 	
    public int consultaNumAtributs() {
    	return propostaGrups[0].getCaracteristiquesAlumnesNumeric().numAttributes();
    }
    
    public String consultaNomAtribut(int quin) {
    	return propostaGrups[0].getCaracteristiquesAlumnesNumeric().attribute(quin).name();
    }
    
 	public Instances consultaCaracteristiquesNumericGrup(int index){
    	return propostaGrups[index].getCaracteristiquesAlumnesNumeric();
    }
 	
 	public Instances consultaCaracteristiquesNominalGrup(int index){
    	return propostaGrups[index].getCaracteristiquesAlumnesNominal();
    }
    
 	public int getNumCentroides() {
 		return numCentroides;
 	}
    
}
