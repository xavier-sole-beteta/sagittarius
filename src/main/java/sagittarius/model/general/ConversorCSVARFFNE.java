package sagittarius.model.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import sagittarius.model.principal.ConfiguracioSagittarius;


/**
 * <b>ConversorCSVARFF</b> converteix fitxers CSV a ARFF.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ConversorCSVARFFNE {

	public static final int NOTES_NOMINAL = 1;
	public static final int NOTES_NUMERIC = 2;
	public static int count = 0;

	public ConversorCSVARFFNE(String sFitxerOrigen, String sFitxerDesti, int mode) {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		FileWriter fichero = null;
		PrintWriter pw = null;


		try {
			archivo = new File (sFitxerOrigen);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			fichero = new FileWriter(sFitxerDesti);
			pw = new PrintWriter(fichero);

			pw.println("@relation test");
			pw.println("");

			String original = null;
			boolean dinsDades = false;

			while((original = br.readLine())!=null) {
				if (ConfiguracioSagittarius.debug) { 
					System.out.println("Hem llegit: "+original);
				}
				if (original.contains("Expediente") && !dinsDades) {
					// Estreure els atributs
					String aux[] = original.split(",");
					for (int i=0; i<aux.length; i++) {
						if (i<ConfiguracioSagittarius.numAtributsIdentificadorsIntern) {
							pw.println("@attribute "+aux[i]+" string");
						} else {
							if (mode == NOTES_NOMINAL) {
									pw.println("@attribute "+aux[i]+" {NP,SUSPENSO,APROBADO,NOTABLE,EXCELENTE}");
							}
							if (mode == NOTES_NUMERIC) {
									pw.println("@attribute "+aux[i]+" numeric");
							}
						}
					}
					pw.println("");
					pw.println("@data");
					pw.println("");
					dinsDades = true;
				} else {
					if (dinsDades == true) {
						original = original.replace("NP", "-1");
						if (mode == NOTES_NOMINAL) {
							String originalMod = "";
							String auxx[] = original.split(",");
							Float nota = null;
							for (int i=0; i<auxx.length; i++) {
								if (i<ConfiguracioSagittarius.numAtributsIdentificadorsIntern) {
									if (i==0) {
										originalMod = originalMod.concat("'"+auxx[i]+"'");
									}else {
										originalMod = originalMod.concat(",'"+auxx[i]+"'");
									}
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
							String originalMod = "";
							String auxx[] = original.split(",");
							for (int i=0; i<auxx.length; i++) {
								if (i<4) {
									if (i==0) {
										originalMod = originalMod.concat("'"+auxx[i]+"'");
									}else {
										originalMod = originalMod.concat(",'"+auxx[i]+"'");
									}
								} else {
									originalMod = originalMod.concat(","+auxx[i]);
								}
							}
							pw.println(originalMod);
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

}
