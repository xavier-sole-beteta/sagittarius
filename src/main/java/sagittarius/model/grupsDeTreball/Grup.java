package sagittarius.model.grupsDeTreball;

import weka.core.Instances;

/**
 * <b>Grup</b> emmagatzema les dades d'un grups de treball.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class Grup {
	
	private Instances alumnes;
	private Instances caracteristiquesAlumnesNumeric;
	private Instances caracteristiquesAlumnesCategoric;
	private Integer iNumIntegrants;
	private int iNumAlumnesGrau;
	private int iGrau;
	private int[][] estadistiques;
	
	public Grup(){
	}
	
	public Grup(Instances alumnes, Instances caracteristiquesAlumnesNumeric, Instances caracteristiquesAlumnesNominal, Integer iNumIntegrants,  int iNumAlumnesGrau, int iGrau, int[][] estadistiques){
		this.alumnes = new Instances(alumnes);
		this.caracteristiquesAlumnesNumeric = new Instances(caracteristiquesAlumnesNumeric);
		this.caracteristiquesAlumnesCategoric = new Instances(caracteristiquesAlumnesNominal);
		this.iNumIntegrants = new Integer(iNumIntegrants);
		this.iNumAlumnesGrau = iNumAlumnesGrau;
		this.iGrau = iGrau;
		this.estadistiques = estadistiques;
	}

	public int[][] consultaEstadistiques() {
		return estadistiques;
	}
	
	public int getNumAlumnesGrau(){
		return iNumAlumnesGrau;
	}
	
	public Instances getAlumnes() {
		return alumnes;
	}

	public Instances getCaracteristiquesAlumnesNumeric() {
		return caracteristiquesAlumnesNumeric;
	}
	
	public Instances getCaracteristiquesAlumnesNominal() {
		return caracteristiquesAlumnesCategoric;
	}
	
	public void setAlumnes(Instances alumnes) {
		this.alumnes = alumnes;
	}

	public Integer getNumIntegrants() {
		return iNumIntegrants;
	}

	public void setNumIntegrants(Integer iNumIntegrants) {
		this.iNumIntegrants = iNumIntegrants;
	}

	public int getGrau() {
		return iGrau;
	}

	public void setGrau(int iGrau) {
		this.iGrau = iGrau;
	}
	
	
}
