package sagittarius.model.prediccioNota;

import sagittarius.model.principal.ConfiguracioSagittarius;
import weka.core.Instances;

/**
 * <b>Prediccio</b> emmagatzema les dades d'una categoria de la predicció de notes.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class Prediccio {

	private String sNota;
	private Instances datasetAlumnesNotaResum;
	private Instances datasetAlumnesNotaHistoric;
	private int iNumAlumnesNota;
	private float iPercentatgeNota;
	private int iNumAlumnesTotal;
	
	
	public Prediccio(String sNota, Instances datasetAlumnesNotaResum, Instances datasetAlumnesNotaHistoric, int iNumAlumnesNota, float iPercentatgeNota, int iNumAlumnesTotal){
		this.sNota = sNota;
		this.datasetAlumnesNotaResum = datasetAlumnesNotaResum;
		this.datasetAlumnesNotaHistoric = datasetAlumnesNotaHistoric;
		this.iNumAlumnesNota = iNumAlumnesNota;
		this.iPercentatgeNota = iPercentatgeNota;
		this.iNumAlumnesTotal = iNumAlumnesTotal;
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("-------------------------------------------------------------");
			System.out.println("Nota: "+sNota);
			System.out.println("NumAlumnes (datasetResum): "+datasetAlumnesNotaResum.numInstances());
			System.out.println("NumAlumnes (datasetHistoric): "+datasetAlumnesNotaHistoric.numInstances());
			System.out.println("NumAlumnes: "+iNumAlumnesNota);
			System.out.println("Percent: "+iPercentatgeNota);
			System.out.println("-------------------------------------------------------------");
		}
		
	}

	public String getsNota() {
		return sNota;
	}

	public void setsNota(String sNota) {
		this.sNota = sNota;
	}

	public Instances getDatasetAlumnesNotaHistoric() {
		return datasetAlumnesNotaHistoric;
	}

	public void setDatasetAlumnesNotaHistoric(Instances datasetAlumnesNotaHistoric) {
		this.datasetAlumnesNotaHistoric = datasetAlumnesNotaHistoric;
	}
	
	public Instances getDatasetAlumnesNotaResum() {
		return datasetAlumnesNotaResum;
	}

	public void setDatasetAlumnesNotaResum(Instances datasetAlumnesNotaResum) {
		this.datasetAlumnesNotaResum = datasetAlumnesNotaResum;
	}

	public int getiNumAlumnesNota() {
		return iNumAlumnesNota;
	}

	public void setiNumAlumnesNota(int iNumAlumnesNota) {
		this.iNumAlumnesNota = iNumAlumnesNota;
	}

	public float getiPercentatgeNota() {
		return iPercentatgeNota;
	}

	public void setiPercentatgeNota(int iPercentatgeNota) {
		this.iPercentatgeNota = iPercentatgeNota;
	}

	public int getiNumAlumnesTotal() {
		return iNumAlumnesTotal;
	}

	public void setiNumAlumnesTotal(int iNumAlumnesTotal) {
		this.iNumAlumnesTotal = iNumAlumnesTotal;
	}
	
}
