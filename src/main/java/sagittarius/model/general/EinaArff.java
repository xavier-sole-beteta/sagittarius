package sagittarius.model.general;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sagittarius.model.principal.ConfiguracioSagittarius;


import net.miginfocom.swing.MigLayout;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.gui.arffviewer.ArffPanel;

/**
 * <b>EinaArff</b> ofereix operacions per tractar fitxer ARFF.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class EinaArff {

	public static int retornaNumInstancies(String sNomFitxer)
			throws IOException {

		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));

		return cargadorARRF.getDataSet().numInstances();

	}

	public static Instances crearEspecialitatNominalNotesString(
			Instances datasetNumeric) {
		FastVector atributs;
		FastVector especialitats;

		atributs = new FastVector();
		especialitats = new FastVector();

		especialitats.addElement("Eng Sistemes de Telecomunicació");
		especialitats.addElement("Eng Sistemes audiovisuals");
		especialitats.addElement("Eng Telemàtica");
		especialitats.addElement("Eng Multimèdia");
		especialitats.addElement("Eng Organització de les TIC");
		especialitats.addElement("Eng Electrònica de Telecomunicació");
		especialitats.addElement("Eng Informàtica");

		// Posem els noms dels atributs del dataset nominal (mateixos que el
		// dataset numeric)
		for (int i = 0; i < datasetNumeric.numAttributes(); i++) {
			if (datasetNumeric.attribute(i).isString()) {
				atributs.addElement(new Attribute(datasetNumeric.instance(0)
						.attribute(i).name(), (FastVector) null));
			}
			if (datasetNumeric.attribute(i).isNominal()) {
				atributs.addElement(new Attribute(datasetNumeric.instance(0)
						.attribute(i).name(), especialitats));
			}
			if (datasetNumeric.attribute(i).isNumeric()) {
				atributs.addElement(new Attribute(datasetNumeric.instance(0)
						.attribute(i).name(), (FastVector) null));
			}
		}

		// Creem el dataset nominal
		Instances datasetNominal = new Instances("Nominal", atributs, 0);

		Instance aux = null;

		for (int i = 0; i < datasetNumeric.numInstances(); i++) {
			aux = new Instance(datasetNominal.numAttributes());
			aux.setDataset(datasetNominal);
			for (int j = 0; j < datasetNumeric.numAttributes(); j++) {
				if (datasetNumeric.attribute(j).isString()) {
					aux.setValue(j, datasetNumeric.instance(i).stringValue(j));
				} else {
					if (datasetNumeric.instance(0).attribute(j).name().equals(
							"Especialitat")) {
						aux.setValue(j, datasetNumeric.instance(i).stringValue(
								j));
					} else {
						double valor = datasetNumeric.instance(i).value(j);
						if (valor < (double) 0) {
							aux.setValue(j, "NP");
						}
						if ((valor >= (double) 0) && (valor < (double) 5)) {
							aux.setValue(j, Double.toString(valor));
						}
						if ((valor >= (double) 5) && (valor < (double) 7)) {
							aux.setValue(j, Double.toString(valor));
						}
						if ((valor >= (double) 7) && (valor < (double) 9)) {
							aux.setValue(j, Double.toString(valor));
						}
						if ((valor >= (double) 9) && (valor <= (double) 10)) {
							aux.setValue(j, Double.toString(valor));
						}
					}
				}
			}
			// Afegim al nova instancia al dataset nominal
			datasetNominal.add(aux);
		}

		return datasetNominal;
	}

	public static void mostraFitxerAmbInformacio(String sNomFitxerIntern,
			String nomFitxerReal) throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxerIntern));

		Instances auxDataset = crearEspecialitatNominalNotesString(cargadorARRF
				.getDataSet());

		ArffPanel apPanellArff = new ArffPanel();
		apPanellArff.setInstances(auxDataset);
		apPanellArff.setReadOnly(true);
		apPanellArff.setOptimalColWidths();

		JDialog aux = new JDialog();
		MigLayout layout = new MigLayout("", "[grow]", "[]10[]5[]");
		aux.setLayout(layout);
		aux.setAlwaysOnTop(true);
		aux.setModalExclusionType(null);
		aux.setIconImage(Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));

		aux.setTitle("Sagittarius - Explorar contenido del fichero");
		aux.setSize(800, 600);
		aux.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aux.setLocationRelativeTo(null);

		JLabel nomFitxer = new JLabel("<html><b>Nombre del fichero:</b> "
				+ nomFitxerReal + "</html>");
		JLabel contingut = new JLabel("<html><b>Contenido:</b></html>");

		aux.add(nomFitxer, "top, wrap");
		aux.add(contingut, "top, wrap");
		aux.add(apPanellArff, "grow, wrap");

		aux.setVisible(true);
	}

	public static void mostraAlumnesIntegrants(int numGrup, Instances integrants)
			throws IOException {

		ArffPanel apPanellArff = new ArffPanel();
		apPanellArff.setInstances(integrants);
		apPanellArff.setReadOnly(true);
		apPanellArff.setOptimalColWidths();

		JDialog aux = new JDialog();
		MigLayout layout = new MigLayout("", "[grow]", "[]10[]5[]");
		aux.setLayout(layout);
		aux.setAlwaysOnTop(true);
		aux.setModalExclusionType(null);
		aux.setIconImage(Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));

		aux.setTitle("Sagittarius - Ver alumnos integrantes (grupo " + numGrup
				+ ")");
		aux.setSize(800, 600);
		aux.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aux.setLocationRelativeTo(null);

		JLabel nomFitxer = new JLabel("<html><b>Propuesta de grupo:</b> "
				+ numGrup + "</html>");
		JLabel contingut = new JLabel(
				"<html><b>Alumnos integrantes:</b></html>");

		aux.add(nomFitxer, "top, wrap");
		aux.add(contingut, "top, wrap");
		aux.add(apPanellArff, "grow, wrap");

		aux.setVisible(true);
	}

	public static void mostraCaracteristiquesAlumnesIntegrants(int numGrup,
			Instances integrants) throws IOException {

		ArffPanel apPanellArff = new ArffPanel();
		apPanellArff.setInstances(integrants);
		apPanellArff.setReadOnly(true);
		apPanellArff.setOptimalColWidths();

		JDialog aux = new JDialog();
		aux.setIconImage(Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));
		MigLayout layout = new MigLayout("", "[grow]", "[]10[]5[]");
		aux.setLayout(layout);
		aux.setAlwaysOnTop(true);
		aux.setModalExclusionType(null);
		aux.setIconImage(Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));

		aux
				.setTitle("Sagittarius - Explorar los resultados de los alumnos integrantes (grup "
						+ numGrup + ")");
		aux.setSize(800, 600);
		aux.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aux.setLocationRelativeTo(null);

		JLabel nomFitxer = new JLabel("<html><b>Propuesta de grupo:</b> "
				+ numGrup + "</html>");
		JLabel contingut = new JLabel(
				"<html><b>Resultados de los alumnos integrantes:</b></html>");

		aux.add(nomFitxer, "top, wrap");
		aux.add(contingut, "top, wrap");
		aux.add(apPanellArff, "grow, wrap");

		aux.setVisible(true);
	}

	public static String retornaUltimAtribut(String sNomFitxer)
			throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();

		return aux.attribute(aux.numAttributes() - 1).name();
	}

	public static String[] retornaNomsAtributs(String sNomFitxer)
			throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		String[] noms = new String[aux.numAttributes()];

		for (int i = 0; i < aux.numAttributes(); i++) {
			noms[i] = aux.attribute(i).name();
			if (ConfiguracioSagittarius.debug) {
				if (ConfiguracioSagittarius.debug) {
					System.out.println(aux.attribute(i).name());
				}
			}
		}

		return noms;
	}

	public static double[] getNotesNONP(String sNomFitxer, int quin)
			throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		double[] notes = null;
		int compt = 0;
		for (int i = 0; i < aux.numInstances(); i++) {
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) != -1) {
				compt++;
			}
		}

		notes = new double[compt];
		compt = 0;

		for (int i = 0; i < aux.numInstances(); i++) {
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) != -1) {
				notes[compt] = aux
						.instance(i)
						.value(
								quin
										+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern);
				compt++;
			}
		}

		return notes;
	}

	public static float[] getEstadistiques(String sNomFitxer, int quin)
			throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		int[] notes = new int[5];
		float[] notesF = new float[5];

		for (int i = 0; i < aux.numInstances(); i++) {
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) == -1) {
				notes[0] = notes[0] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 0
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 5) {
				notes[1] = notes[1] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 5
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 7) {
				notes[2] = notes[2] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 7
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 9) {
				notes[3] = notes[3] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 9
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) <= 10) {
				notes[4] = notes[4] + 1;
			}
		}

		for (int i = 0; i < notes.length; i++) {
			notesF[i] = (notes[i] / (float) aux.numInstances())
					* (float) 100.00;
			notesF[i] = Math.round(notesF[i] * 100.0) / 100.0f;
			if (ConfiguracioSagittarius.debug) {
				System.out.println(notesF[i]);
			}
		}

		return notesF;
	}

	public static int[] getEstadistiques(String sNomFitxer, String nomAtribut)
			throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		int[] notes = new int[5];
		float[] notesF = new float[5];
		int quin = 0;

		for (int i = 0; i < aux.numAttributes(); i++) {
			if (aux.attribute(i).name().equals(nomAtribut)) {
				quin = i
						- ConfiguracioSagittarius.numAtributsIdentificadorsIntern;
			}
		}

		for (int i = 0; i < aux.numInstances(); i++) {
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) == -1) {
				notes[0] = notes[0] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 0
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 5) {
				notes[1] = notes[1] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 5
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 7) {
				notes[2] = notes[2] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 7
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 9) {
				notes[3] = notes[3] + 1;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 9
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) <= 10) {
				notes[4] = notes[4] + 1;
			}
		}

		for (int i = 0; i < notes.length; i++) {
			notesF[i] = (notes[i] / (float) aux.numInstances())
					* (float) 100.00;
			notesF[i] = Math.round(notesF[i] * 100.0) / 100.0f;
		}

		return notes;
	}

	public static float[] getEstadistiquesNumeric(String sNomFitxer,
			String nomAtribut) throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		float[] notes = new float[3];
		float[] notesF = new float[5];
		int quin = 0;
		int countNP = 0;
		float countNota = 0.0f;
		float max, min;
		max = -9.0f;
		min = 10.0f;

		for (int i = 0; i < aux.numAttributes(); i++) {
			if (aux.attribute(i).name().equals(nomAtribut)) {
				quin = i
						- ConfiguracioSagittarius.numAtributsIdentificadorsIntern;
			}
		}

		for (int i = 0; i < aux.numInstances(); i++) {
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) == -1) {
				countNP++;
			} else {
				countNota = countNota
						+ (float) aux
								.instance(i)
								.value(
										quin
												+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern);
				if ((float) (aux.instance(i)
						.value(quin
								+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern)) > max) {
					max = (float) aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern);
				}
				if ((float) (aux.instance(i)
						.value(quin
								+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern)) < min) {
					min = (float) aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern);
				}
			}
		}

		notes[0] = countNota / ((float) aux.numInstances() - (float) countNP);
		notes[0] = Math.round(notes[0] * 100.0) / 100.0f;
		notes[1] = Math.round(max * 100.0) / 100.0f;
		notes[2] = Math.round(min * 100.0) / 100.0f;
		if (ConfiguracioSagittarius.debug) {
			System.out.println("mitjana " + notes[0]);
			System.out.println("Maxima " + notes[1]);
			System.out.println("minima " + notes[2]);
		}
		for (int i = 0; i < notes.length; i++) {
			notesF[i] = (notes[i] / (float) aux.numInstances())
					* (float) 100.00;
			notesF[i] = Math.round(notesF[i] * 100.0) / 100.0f;
		}

		return notes;
	}

	public static float[] getEstadistiquesNONP(String sNomFitxer, int quin)
			throws IOException {
		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		int[] notes = new int[5];
		float[] notesF = new float[5];
		int comptNONP = 0;

		for (int i = 0; i < aux.numInstances(); i++) {
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 0
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 5) {
				notes[1] = notes[1] + 1;
				comptNONP++;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 5
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 7) {
				notes[2] = notes[2] + 1;
				comptNONP++;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 7
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) < 9) {
				notes[3] = notes[3] + 1;
				comptNONP++;
			}
			if (aux
					.instance(i)
					.value(
							quin
									+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) >= 9
					&& aux
							.instance(i)
							.value(
									quin
											+ ConfiguracioSagittarius.numAtributsIdentificadorsIntern) <= 10) {
				notes[4] = notes[4] + 1;
				comptNONP++;
			}
		}

		for (int i = 0; i < notes.length; i++) {
			if (notes[i] != 0f) {
				notesF[i] = (notes[i] / (float) comptNONP) * (float) 100.00;
				notesF[i] = Math.round(notesF[i] * 100.0) / 100.0f;
			}
		}

		return notesF;
	}

	public static int[][] calculaCorrelacions(String sNomFitxer, String b,
			String a) throws FileNotFoundException, IOException {
		int[][] correlacions = new int[5][5];

		for (int i = 0; i < correlacions.length; i++) {
			for (int j = 0; j < correlacions[i].length; j++) {
				correlacions[i][j] = 0;
			}
		}

		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		int quinA = 0;
		int quinB = 0;

		for (int i = 0; i < aux.numAttributes(); i++) {
			if (aux.attribute(i).name().equals(a)) {
				quinA = i;
				if (ConfiguracioSagittarius.debug) {
					System.out.println("IGUAL");
				}
			}
			if (aux.attribute(i).name().equals(b)) {
				quinB = i;
				if (ConfiguracioSagittarius.debug) {
					System.out.println("IGUAL");
				}
			}
		}

		for (int i = 0; i < aux.numInstances(); i++) {

			if (aux.instance(i).value(quinA) < 0) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).value(quinB) < 0) {
							correlacions[0][0] = correlacions[0][0] + 1;
						}
						if (aux.instance(j).value(quinB) >= 0
								&& aux.instance(j).value(quinB) < 5) {
							correlacions[0][1] = correlacions[0][1] + 1;
						}
						if (aux.instance(j).value(quinB) >= 5
								&& aux.instance(j).value(quinB) < 7) {
							correlacions[0][2] = correlacions[0][2] + 1;
						}
						if (aux.instance(j).value(quinB) >= 7
								&& aux.instance(j).value(quinB) < 9) {
							correlacions[0][3] = correlacions[0][3] + 1;
						}
						if (aux.instance(j).value(quinB) >= 9
								&& aux.instance(j).value(quinB) <= 10) {
							correlacions[0][4] = correlacions[0][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).value(quinA) >= 0
					&& aux.instance(i).value(quinA) < 5) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).value(quinB) < 0) {
							correlacions[1][0] = correlacions[1][0] + 1;
						}
						if (aux.instance(j).value(quinB) >= 0
								&& aux.instance(j).value(quinB) < 5) {
							correlacions[1][1] = correlacions[1][1] + 1;
						}
						if (aux.instance(j).value(quinB) >= 5
								&& aux.instance(j).value(quinB) < 7) {
							correlacions[1][2] = correlacions[1][2] + 1;
						}
						if (aux.instance(j).value(quinB) >= 7
								&& aux.instance(j).value(quinB) < 9) {
							correlacions[1][3] = correlacions[1][3] + 1;
						}
						if (aux.instance(j).value(quinB) >= 9
								&& aux.instance(j).value(quinB) <= 10) {
							correlacions[1][4] = correlacions[1][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).value(quinA) >= 5
					&& aux.instance(i).value(quinA) < 7) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).value(quinB) < 0) {
							correlacions[2][0] = correlacions[2][0] + 1;
						}
						if (aux.instance(j).value(quinB) >= 0
								&& aux.instance(j).value(quinB) < 5) {
							correlacions[2][1] = correlacions[2][1] + 1;
						}
						if (aux.instance(j).value(quinB) >= 5
								&& aux.instance(j).value(quinB) < 7) {
							correlacions[2][2] = correlacions[2][2] + 1;
						}
						if (aux.instance(j).value(quinB) >= 7
								&& aux.instance(j).value(quinB) < 9) {
							correlacions[2][3] = correlacions[2][3] + 1;
						}
						if (aux.instance(j).value(quinB) >= 9
								&& aux.instance(j).value(quinB) <= 10) {
							correlacions[2][4] = correlacions[2][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).value(quinA) >= 7
					&& aux.instance(i).value(quinA) < 9) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).value(quinB) < 0) {
							correlacions[3][0] = correlacions[3][0] + 1;
						}
						if (aux.instance(j).value(quinB) >= 0
								&& aux.instance(j).value(quinB) < 5) {
							correlacions[3][1] = correlacions[3][1] + 1;
						}
						if (aux.instance(j).value(quinB) >= 5
								&& aux.instance(j).value(quinB) < 7) {
							correlacions[3][2] = correlacions[3][2] + 1;
						}
						if (aux.instance(j).value(quinB) >= 7
								&& aux.instance(j).value(quinB) < 9) {
							correlacions[3][3] = correlacions[3][3] + 1;
						}
						if (aux.instance(j).value(quinB) >= 9
								&& aux.instance(j).value(quinB) <= 10) {
							correlacions[3][4] = correlacions[3][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).value(quinA) >= 9
					&& aux.instance(i).value(quinA) <= 10) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).value(quinB) < 0) {
							correlacions[4][0] = correlacions[4][0] + 1;
						}
						if (aux.instance(j).value(quinB) >= 0
								&& aux.instance(j).value(quinB) < 5) {
							correlacions[4][1] = correlacions[4][1] + 1;
						}
						if (aux.instance(j).value(quinB) >= 5
								&& aux.instance(j).value(quinB) < 7) {
							correlacions[4][2] = correlacions[4][2] + 1;
						}
						if (aux.instance(j).value(quinB) >= 7
								&& aux.instance(j).value(quinB) < 9) {
							correlacions[4][3] = correlacions[4][3] + 1;
						}
						if (aux.instance(j).value(quinB) >= 9
								&& aux.instance(j).value(quinB) <= 10) {
							correlacions[4][4] = correlacions[4][4] + 1;
						}
					}

				}
			}
		}

		if (ConfiguracioSagittarius.debug) {
			System.out.print("Correlacions: \n");
			for (int i = 0; i < correlacions.length; i++) {
				for (int j = 0; j < correlacions[i].length; j++) {
					System.out.print(correlacions[i][j] + " ");
				}
				System.out.print("----------------\n");
			}
		}
		return correlacions;
	}

	public static int[][] calculaCorrelacionsNOM(String sNomFitxer, String b,
			String a) throws FileNotFoundException, IOException {
		int[][] correlacions = new int[5][5];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				correlacions[i][j] = 0;
			}
		}

		ArffLoader cargadorARRF = new ArffLoader();
		cargadorARRF.setSource(new FileInputStream(sNomFitxer));
		Instances aux = cargadorARRF.getDataSet();
		int quinA = 0;
		int quinB = 0;

		for (int i = 0; i < aux.numAttributes(); i++) {
			if (aux.attribute(i).name().equals(a)) {
				quinA = i;
			}
			if (aux.attribute(i).name().equals(b)) {
				quinB = i;
			}
		}

		for (int i = 0; i < aux.numInstances(); i++) {

			if (aux.instance(i).stringValue(quinA).equals("NP")) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).stringValue(quinB).equals("NP")) {
							correlacions[0][0] = correlacions[0][0] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"SUSPENSO")) {
							correlacions[0][1] = correlacions[0][1] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"APROBADO")) {
							correlacions[0][2] = correlacions[0][2] + 1;
						}
						if (aux.instance(j).stringValue(quinB)
								.equals("NOTABLE")) {
							correlacions[0][3] = correlacions[0][3] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"EXCELENTE")) {
							correlacions[0][4] = correlacions[0][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).stringValue(quinA).equals("SUSPENSO")) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).stringValue(quinB).equals("NP")) {
							correlacions[1][0] = correlacions[1][0] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"SUSPENSO")) {
							correlacions[1][1] = correlacions[1][1] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"APROBADO")) {
							correlacions[1][2] = correlacions[1][2] + 1;
						}
						if (aux.instance(j).stringValue(quinB)
								.equals("NOTABLE")) {
							correlacions[1][3] = correlacions[1][3] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"EXCELENTE")) {
							correlacions[1][4] = correlacions[1][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).stringValue(quinA).equals("APROBADO")) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).stringValue(quinB).equals("NP")) {
							correlacions[2][0] = correlacions[2][0] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"SUSPENSO")) {
							correlacions[2][1] = correlacions[2][1] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"APROBADO")) {
							correlacions[2][2] = correlacions[2][2] + 1;
						}
						if (aux.instance(j).stringValue(quinB)
								.equals("NOTABLE")) {
							correlacions[2][3] = correlacions[2][3] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"EXCELENTE")) {
							correlacions[2][4] = correlacions[2][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).stringValue(quinA).equals("NOTABLE")) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).stringValue(quinB).equals("NP")) {
							correlacions[3][0] = correlacions[3][0] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"SUSPENSO")) {
							correlacions[3][1] = correlacions[3][1] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"APROBADO")) {
							correlacions[3][2] = correlacions[3][2] + 1;
						}
						if (aux.instance(j).stringValue(quinB)
								.equals("NOTABLE")) {
							correlacions[3][3] = correlacions[3][3] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"EXCELENTE")) {
							correlacions[3][4] = correlacions[3][4] + 1;
						}
					}

				}
			}

			if (aux.instance(i).stringValue(quinA).equals("EXCELENTE")) {
				for (int j = 0; j < aux.numInstances(); j++) {
					if (aux.instance(j).stringValue(0).equals(
							aux.instance(i).stringValue(0))) {
						if (aux.instance(j).stringValue(quinB).equals("NP")) {
							correlacions[4][0] = correlacions[4][0] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"SUSPENSO")) {
							correlacions[4][1] = correlacions[4][1] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"APROBADO")) {
							correlacions[4][2] = correlacions[4][2] + 1;
						}
						if (aux.instance(j).stringValue(quinB)
								.equals("NOTABLE")) {
							correlacions[4][3] = correlacions[4][3] + 1;
						}
						if (aux.instance(j).stringValue(quinB).equals(
								"EXCELENTE")) {
							correlacions[4][4] = correlacions[4][4] + 1;
						}
					}

				}
			}
		}

		return correlacions;
	}

}
