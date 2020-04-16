package sagittarius.model.analisiDeResultats;

import sagittarius.model.principal.ConfiguracioSagittarius;


/**
 * <b>GeneradorDeDescripcions</b> genera descripcions per interpretar el gràfic de correlacions del cas
 * funcional per l'anàlisi i visualització de resultats.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GeneradorDeDescripcions {

	public static String generaDescripcioCorrelacio(String notaOrigen, String notaDesti, int[][] valorsCorrelacions) {

		int totalAlumnes[] = new int[5];
		String descripcio = "";

		totalAlumnes[0] = valorsCorrelacions[0][0] + valorsCorrelacions[1][0] + valorsCorrelacions[2][0] + valorsCorrelacions[3][0] + valorsCorrelacions[4][0];
		totalAlumnes[1] = valorsCorrelacions[0][1] + valorsCorrelacions[1][1] + valorsCorrelacions[2][1] + valorsCorrelacions[3][1] + valorsCorrelacions[4][1];
		totalAlumnes[2] = valorsCorrelacions[0][2] + valorsCorrelacions[1][2] + valorsCorrelacions[2][2] + valorsCorrelacions[3][2] + valorsCorrelacions[4][2];
		totalAlumnes[3] = valorsCorrelacions[0][3] + valorsCorrelacions[1][3] + valorsCorrelacions[2][3] + valorsCorrelacions[3][3] + valorsCorrelacions[4][3];
		totalAlumnes[4] = valorsCorrelacions[0][4] + valorsCorrelacions[1][4] + valorsCorrelacions[2][4] + valorsCorrelacions[3][4] + valorsCorrelacions[4][4];

		if(totalAlumnes[0]!=0) {
			descripcio = "De los "+totalAlumnes[0]+" alumnos calificados con un "+" NP "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[0][0]+" alumnos son calificados con un NP en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[0]+" alumnos calificados con un "+" NP "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[1][0]+" alumnos son calificados con un SUSPENSO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[0]+" alumnos calificados con un "+" NP "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[2][0]+" alumnos son calificados con un APROBADO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[0]+" alumnos calificados con un "+" NP "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[3][0]+" alumnos son calificados con un NOTABLE en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[0]+" alumnos calificados con un "+" NP "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[4][0]+" alumnos son calificados con un EXCELENTE en la prueba "+notaDesti+".\n\n";
		}

		if(totalAlumnes[1]!=0) {
			descripcio = descripcio+"De los "+totalAlumnes[1]+" alumnos calificados con un "+" SUSPENSO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[0][1]+" alumnos son calificados con un NP en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[1]+" alumnos calificados con un "+" SUSPENSO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[1][1]+" alumnos son calificados con un SUSPENSO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[1]+" alumnos calificados con un "+" SUSPENSO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[2][1]+" alumnos son calificados con un APROBADO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[1]+" alumnos calificados con un "+" SUSPENSO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[3][1]+" alumnos son calificados con un NOTABLE en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[1]+" alumnos calificados con un "+" SUSPENSO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[4][1]+" alumnos son calificados con un EXCELENTE en la prueba "+notaDesti+".\n\n";
		}

		if(totalAlumnes[2]!=0) {
			descripcio = descripcio+"De los "+totalAlumnes[2]+" alumnos calificados con un "+" APROBADO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[0][2]+" alumnos son calificados con un NP en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[2]+" alumnos calificados con un "+" APROBADO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[1][2]+" alumnos son calificados con un SUSPENSO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[2]+" alumnos calificados con un "+" APROBADO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[2][2]+" alumnos son calificados con un APROBADO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[2]+" alumnos calificados con un "+" APROBADO "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[3][2]+" alumnos son calificados con un NOTABLE en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[2]+" alumnos calificados con un "+" APROVAT "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[4][2]+" alumnos son calificados con un EXCELENTE en la prueba "+notaDesti+".\n\n";
		}

		if(totalAlumnes[3]!=0) {
			descripcio = descripcio+"De los "+totalAlumnes[3]+" alumnos calificados con un "+" NOTABLE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[0][3]+" alumnos son calificados con un NP en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[3]+" alumnos calificados con un "+" NOTABLE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[1][3]+" alumnos son calificados con un SUSPENSO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[3]+" alumnos calificados con un "+" NOTABLE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[2][3]+" alumnos son calificados con un APROBADO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[3]+" alumnos calificados con un "+" NOTABLE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[3][3]+" alumnos son calificados con un NOTABLE en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[3]+" alumnos calificados con un "+" NOTABLE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[4][3]+" alumnos son calificados con un EXCELENTE en la prueba "+notaDesti+".\n\n";
		}

		if(totalAlumnes[4]!=0) {
			descripcio = descripcio+"De los "+totalAlumnes[4]+" alumnos calificados con un "+" EXCELENTE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[0][4]+" alumnos son calificados con un NP en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[4]+" alumnos calificados con un "+" EXCELENTE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[1][4]+" alumnos son calificados con un SUSPENSO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[4]+" alumnos calificados con un "+" EXCELENTE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[2][4]+" alumnos son calificados con un APROBADO en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[4]+" alumnos calificados con un "+" EXCELENTE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[3][4]+" alumnos son calificados con un NOTABLE en la prueba "+notaDesti+".\n";
			descripcio = descripcio+"De los "+totalAlumnes[4]+" alumnos calificados con un "+" EXCELENTE "+"en la prueba "+notaOrigen+", "+valorsCorrelacions[4][4]+" alumnos son calificados con un EXCELENTE en la prueba "+notaDesti+".\n\n";
		}

		if (ConfiguracioSagittarius.debug) {
			int count=0;
			for(int i=0; i<valorsCorrelacions.length; i++) {
				for(int j=0; j<valorsCorrelacions[i].length; j++) {
					if(i==j) {
						count = 0;
						for(int k=0; k<valorsCorrelacions.length; k++) {
							if(valorsCorrelacions[i][j]>=valorsCorrelacions[k][i]) {
								count++;
							}
						}
						if(count==valorsCorrelacions.length) {
							System.out.println("Correlat OK!! ("+j+")");
						}
					}
				}
			}
		}

		return descripcio;
	}

}
