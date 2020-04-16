package sagittarius.model.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import sagittarius.model.principal.ConfiguracioSagittarius;


/**
 * <b>PreProcesPropi</b> ofereix operacions de preproces de les dades.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class PreProcesNE {

	private Vector<String> atributs;
	private String llistaAtributs;
	
	public PreProcesNE(String origen, String desti) {
		File aux1 = new File(ConfiguracioSagittarius.tmpFolder);
		aux1.mkdir();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
        FileWriter fichero = null;
        PrintWriter pw = null;

		llistaAtributs = new String("");
		atributs = new Vector<String>();
		atributs.add("Expediente");
		atributs.add("Apellidos");
		atributs.add("Nombre");
		
		try {
			archivo = new File (origen);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			
            fichero = new FileWriter(desti);
            pw = new PrintWriter(fichero);

			String original;
			String nPuntComa;
			String nPunt;
			String totOK;
			boolean dinsDades = false;

			while((original=br.readLine())!=null) {
				// Extreure el nom dels atributs (nom de les columens del CSV)
				if(original.contains("Expediente")) {
					String[] aux = original.split(";");
					for (int i=0; i<aux.length; i++) {
						if (ConfiguracioSagittarius.debug) { 
							System.out.println(aux[i]);
						}
						if (i>ConfiguracioSagittarius.numAtributsIdentificadorsExtern-1) {
							aux[i] = aux[i].replace(" ","");
							atributs.add(aux[i]);
						}
					}
					// Generació de la primera línia del CSV, el nom de les columnes
					for (int i=0; i<atributs.size(); i++) {
						llistaAtributs = llistaAtributs.concat(atributs.get(i));
						if(i<atributs.size()-1) {
							llistaAtributs = llistaAtributs.concat(",");
						}
					}
					if (ConfiguracioSagittarius.debug) { 
						System.out.println(llistaAtributs);
					}
					pw.println(llistaAtributs);
					//original=br.readLine();
					// Indiquem que a continuació hi ha les dades, els valors
					dinsDades = true;
				}else{
					if(dinsDades) {
						// Treiem "basura"
						nPuntComa = original.replaceAll(";;", "");
						nPunt = nPuntComa.replaceAll(",", ".");
						totOK = nPunt.replaceAll(";", ",");
						totOK = totOK.replaceAll(",,", "");
						totOK = totOK.replaceAll("  ", "");
						totOK = totOK.replaceAll(", ", ",");						
						totOK = totOK.replaceAll(" ,", ",");
						
						//totOK = extreuEspecialitat(totOK);
						
						// Separem per coma
						String[] l = totOK.split(",");
						// Separem els cognoms del nom
						String[] k = l[1].split("\\.");
						totOK = new String("");
						// Generem la linia
						for(int i=0; i<l.length; i++) {
							// Si el "nom" canviem per "cognoms, nom,"
							if(i==1) {
								// cognoms
								totOK = totOK.concat(k[0]+",");
								// nom
								totOK = totOK.concat(k[1]+",");
							// si no ho deixem com estava
							}else{
								totOK = totOK.concat(l[i]);
								if(i<l.length-1) {
									totOK = totOK.concat(",");
								}
							}
						}
						if (ConfiguracioSagittarius.debug) { 
							System.out.println(totOK);
						}
						try {
							// Escrivim en el fitxer de sortida
							pw.println(totOK);
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}catch(Exception e){
				e.printStackTrace();
		}finally{
			try{                    
				if( null != fr ){   
					fr.close();     
				}             
				pw.close();
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
	}
	
	public static String preProcessar(String fEntradaCSV, int mode) {
		// Fitxer CSV després del pre-processament
		String fSortidaCSV = ConfiguracioSagittarius.tmpFolder+ConfiguracioSagittarius.tmpFileTemplate+".csv";
		// Fitxer ARFF de sortida, després del pre-processament
		String fSortidaARFF = ConfiguracioSagittarius.tmpFolder+ConfiguracioSagittarius.tmpFileTemplate+ConversorCSVARFFNE.count+".arff";
		ConversorCSVARFFNE.count = ConversorCSVARFFNE.count+1;
		// Pre-procés
		PreProcesNE p = new PreProcesNE(fEntradaCSV,fSortidaCSV);
		// Convertir de CSV a ARFF
		ConversorCSVARFFNE aux = new ConversorCSVARFFNE(fSortidaCSV,fSortidaARFF,mode);
		return fSortidaARFF;
	}
	
}
