package sagittarius.model.perfils;

import sagittarius.model.principal.ConfiguracioSagittarius;
import weka.core.Instances;

/**
 * <b>Perfil</b> emmagatzema les dades d'un perfil d'alumnes.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class Perfil {

	// identificador del perfil
	private int idPerfil;
	// instancies que pertànyen al perfil amb atributs numèrics
	private Instances alumnesPerfilNumeric;
	// instancies que pertànyen al perfil amb atributs nominals
	private Instances alumnesPerfilCategoric;
	// número d'alumnes del perfil
	private int numAlumnesPerfil;
	// percentatge que representa el perfil
	private float percentatgePerfil;
	// número d'alumnes totals
	private int numAlumnesTotal;
	
	private int[] estadistiques;
	
	
	public Perfil(int iPerfil, Instances datasetAlumnesPerfilNumeric, Instances datasetAlumnesPerfilLletres, int iNumAlumnesPerfil, float iPercentatgePerfil, int iNumAlumnesTotal, int[] estadistiques){
		this.idPerfil = iPerfil;
		this.alumnesPerfilNumeric = datasetAlumnesPerfilNumeric;
		this.alumnesPerfilCategoric = datasetAlumnesPerfilLletres;
		this.numAlumnesPerfil = iNumAlumnesPerfil;
		this.percentatgePerfil = iPercentatgePerfil;
		this.numAlumnesTotal = iNumAlumnesTotal;
		this.estadistiques = estadistiques;
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("-------------------------------------------------------------");
			System.out.println("Perfil: "+iPerfil);
			System.out.println("NumAlumnes (datasetAlumnesPerfil): "+datasetAlumnesPerfilNumeric.numInstances());
			System.out.println("NumAlumnes: "+iNumAlumnesPerfil);
			System.out.println("TotalNumAlumnes: "+iNumAlumnesTotal);
			System.out.println("Percent: "+iPercentatgePerfil);
			System.out.println("Alumnes perfil: "+datasetAlumnesPerfilNumeric.toString());
			System.out.println("-------------------------------------------------------------");
		}
	}
		
	public int[] getEstadistiques() {
		return estadistiques;
	}
	
	public int getiPerfil() {
		return idPerfil;
	}
	
	public void setiPerfil(int iPerfil) {
		this.idPerfil = iPerfil;
	}
	
	public Instances getDatasetAlumnesPerfilNumeric() {
		return alumnesPerfilNumeric;
	}
	
	public void setDatasetAlumnesPerfilNumeric(Instances datasetAlumnesPerfilNumeric) {
		this.alumnesPerfilNumeric = datasetAlumnesPerfilNumeric;
	}

	public Instances getDatasetAlumnesPerfilLletres() {
		return alumnesPerfilCategoric;
	}
	
	public void setDatasetAlumnesPerfilLletres(Instances datasetAlumnesPerfilLletres) {
		this.alumnesPerfilCategoric = datasetAlumnesPerfilLletres;
	}
	
	public int getiNumAlumnesPerfil() {
		return numAlumnesPerfil;
	}
	
	public void setiNumAlumnesPerfil(int iNumAlumnesPerfil) {
		this.numAlumnesPerfil = iNumAlumnesPerfil;
	}
	
	public float getiPercentatgePerfil() {
		return percentatgePerfil;
	}
	
	public void setiPercentatgePerfil(int iPercentatgePerfil) {
		this.percentatgePerfil = iPercentatgePerfil;
	}
	
	public int getiNumAlumnesTotal() {
		return numAlumnesTotal;
	}
	
	public void setiNumAlumnesTotal(int iNumAlumnesTotal) {
		this.numAlumnesTotal = iNumAlumnesTotal;
	}
	
}
