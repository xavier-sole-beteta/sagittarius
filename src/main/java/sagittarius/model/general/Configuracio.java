package sagittarius.model.general;

/**
 * <b>Configuracio</b> emmagatzema par�metres de configuraci� dels casos funcionals
 * per la creaci� de grups de treball i per la detecci� de perfils d'alumnes.<br/>
 * 
 * @author Xavier Sol�-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class Configuracio {

	public static int opcio;
	public static final int SENSE_PESOS = 0;
	public static final int NO_PESOS_KMEANS = 1;
	public static final int PESOS_KMEANS = 2;
	public static final int NO_PESOS_XMEANS = 3;
	public static final int PESOS_XMEANS = 4;
}