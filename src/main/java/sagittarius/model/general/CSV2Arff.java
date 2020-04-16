package sagittarius.model.general;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * <b>CSV2Arff</b> converteix fitxers CSV a ARFF.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class CSV2Arff {

	public static final int NOTES_NOMINAL = 1;
	public static final int NOTES_NUMERIC = 2;	
	
	
	public CSV2Arff(String fitxerCVS, String fitxerARFF, int mode) throws IOException{
		String fTemp = new String("tmpARFF.arff");
		
		CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(fitxerCVS));
	    Instances data = loader.getDataSet();
	 
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(fTemp));
	    saver.writeBatch();
		
	    try {
			File archivo = null;
			FileReader fr = null;
			BufferedReader br = null;
	        FileWriter fichero = null;
	        PrintWriter pw = null;
	        
			archivo = new File (fTemp);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			
            fichero = new FileWriter(fitxerARFF);
            pw = new PrintWriter(fichero);
            String original = null;
            int compt = 0;
            while((original=br.readLine())!=null) {
            	if(original.contains("@attribute")) {
            		compt++;
            		if(compt<=3) {
            			String auxx[] = original.split("\\{");
            			pw.println(auxx[0].replace("'", "")+" string");
            		}else{
            			if(compt>=5) {
            				if (mode == NOTES_NUMERIC) {
                				String auxx[] = original.split("\\{");
                				pw.println(auxx[0].replace("'", "")+" numeric");
            				}
            				if (mode == NOTES_NOMINAL) {
                				String auxx[] = original.split("\\{");
                				pw.println(auxx[0].replace("'", "")+" {NP,SUSPENSO,APROBADO,NOTABLE,EXCELENTE}");
            				}
            			} else {
                			pw.println(original.replace("'",""));
            			}
            		}
            	} else {
            		original = original.replace("NP", "-1");
    				if (mode == NOTES_NOMINAL) {
    					String originalMod = "";
        				String auxx[] = original.split(",");
        				Float nota = null;
        				for (int i=0; i<auxx.length; i++) {
        					if (i<4) {
        						originalMod = originalMod.concat(auxx[i]);
        					} else {
	        					nota = Float.valueOf(auxx[i]);
	        					if (nota == -1f) {
	        						originalMod = originalMod.concat(","+"NP");
	        					}
	        					if ((nota >= 0f) &&  (nota <5f)) {
	        						originalMod = originalMod.concat(","+"SUSPENSO");
	        					}
	        					if ((nota >= 5f) &&  (nota <7f)) {
	        						originalMod = originalMod.concat(","+"APROBADO");
	        					}
	        					if ((nota >= 7f) &&  (nota <9f)) {
	        						originalMod = originalMod.concat(","+"NOTABLE");
	        					}
	        					if (nota >= 9f) {
	        						originalMod = originalMod.concat(","+"EXCELENTE");
	        					}
        					}
        				}
    					pw.println(originalMod);
    				} else {
    					pw.println(original);
    				}
            	}
            }
            fr.close();
            pw.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
