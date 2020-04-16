package sagittarius.controlador.perfils;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import sagittarius.model.general.CSV2Arff;
import sagittarius.model.general.Configuracio;
import sagittarius.model.general.EinaArff;
import sagittarius.model.general.PreProcesNE;
import sagittarius.model.perfils.ConfiguracioRellevanciaXMeans;
import sagittarius.model.perfils.ExportacioExcel;
import sagittarius.model.perfils.Perfils;
import sagittarius.model.principal.ConfiguracioSagittarius;
import sagittarius.vista.general.GraficDeBarres;
import sagittarius.vista.general.GraficDeBarresPercentual;
import sagittarius.vista.general.GraficDePastis;
import sagittarius.vista.perfils.DialegPesosAtributs;
import sagittarius.vista.perfils.PanellPerfils;
import jxl.write.WriteException;
import weka.core.Instance;
import weka.gui.arffviewer.ArffPanel;


/**
 * <b>ControladorPerfils</b> és la classe controladora del triplet MVC del cas funcional
 * per la detecció de perfils d'alumnes (MDP).<br/>
 * 
 * Emmagatzema una referència a les vistes (una de tipus PanellPerfils i una de tipus
 * DialegPesosAtributs) i una referència al model (de tipus Perfils).<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ControladorPerfils implements ActionListener, DocumentListener, PropertyChangeListener, KeyListener{

	/** Les vistes */
	private PanellPerfils pPerfils;
	private DialegPesosAtributs vPesosAtributs;

	/** El model */
	private Perfils perfils;


	public ControladorPerfils(PanellPerfils pPerfils){
		this.pPerfils = pPerfils;
	}

	public void actionPerformed(ActionEvent e) {

		String qui = e.getActionCommand();

		if(e.getSource() instanceof JComboBox){
			JComboBox cb = (JComboBox)e.getSource();
			String sSeleccio = (String)cb.getSelectedItem();
			actualitzaVista(sSeleccio);
		}

		if(qui.equals("SELECCIONAR")){
			try{
				if (seleccioFitxerAlumnes()) {
					try {
						String[] noms = EinaArff.retornaNomsAtributs(perfils.getDataset());
						String[] aux = new String[noms.length-ConfiguracioSagittarius.numAtributsIdentificadorsIntern];
						for(int i=3; i<noms.length; i++) {
							aux[i-3] = noms[i];
						}
						ConfiguracioRellevanciaXMeans.pesosAtributs = new float[aux.length];
						for(int i=0; i<aux.length; i++) {
							ConfiguracioRellevanciaXMeans.pesosAtributs[i] = (float)1;
						}
						vPesosAtributs = new DialegPesosAtributs(aux.length, aux, pPerfils); 
						vPesosAtributs.setControlador(new ControladorPesosAtributs(vPesosAtributs, aux.length));
						vPesosAtributs.setModal(true);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					pPerfils.activaConfigurarPesos();
				}
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}

		if(qui.equals("EXPORTAR")){
			JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.setFileFilter(new FileNameExtensionFilter("Fitxer Excel", "xls"));
			fc.setAcceptAllFileFilterUsed(false);
			fc.setDialogTitle("ECOA - Exportar perfiles detectados");
			int returnVal = fc.showSaveDialog(pPerfils);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (file.getAbsolutePath().endsWith(".xls")) {
					exportaGrupsAExcel(file.getAbsolutePath(), pPerfils.getUnUnicFull(), pPerfils.getNumeric());	
					try {
						Desktop.getDesktop().open(new File(file.getAbsolutePath()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else{
					if(!file.getAbsolutePath().contains(".xls")) {
						exportaGrupsAExcel(file.getAbsolutePath()+".xls", pPerfils.getUnUnicFull(), pPerfils.getNumeric());
						try {
							Desktop.getDesktop().open(new File(file.getAbsolutePath()+".xls"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}

		if(qui.equals("OBRIR_CARACTERISTIQUES")){
			try {
				veureCaracteristiques();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if(qui.equals("OBRIR_ALUMNES")){
			try {
				veureAlumnes();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if(qui.equals("VEURE")){
			try{
				veureFitxerAlumnes();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}

		if(qui.equals("GRAFIC_BARRES_PERCENTUAL")){
			veureGraficBarresPercentual();
		}

		if(qui.equals("GRAFIC_BARRES_DETALLAT")){
			mostraGraficNotes();
		}

		if(qui.equals("INICIAR")){
			if(pPerfils.getMaxNumPerfils().equals("") && pPerfils.getMinNumPerfils().equals("")){
				Configuracio.opcio = Configuracio.PESOS_XMEANS;
				iniciarDeteccioPerfils(2,5);
			}
			if(!pPerfils.getMaxNumPerfils().equals("") && !pPerfils.getMinNumPerfils().equals("")){
				Configuracio.opcio = Configuracio.PESOS_XMEANS;
				iniciarDeteccioPerfils(Integer.valueOf(pPerfils.getMinNumPerfils()), Integer.valueOf(pPerfils.getMaxNumPerfils()));
			}

			pPerfils.actualitzaNumPerfil(String.valueOf(0));
			pPerfils.actualitzaNumPerfil(String.valueOf(1));
			actualitzaVista(pPerfils.consultaSeleccioVista());
			pPerfils.actualitzaNumPerfilsDetectats(String.valueOf(perfils.getNumPerfils()));

			pPerfils.activarVistaPerfils();
			pPerfils.activaModeExportar();
			pPerfils.activaExportar();
		}

		if(qui.equals("CONFIGURAR-PESOS")){
			vPesosAtributs.setVisible(true);
		}

		if(qui.equals("SEGUENT")){
			if(Integer.valueOf(pPerfils.getNumPerfil())<perfils.getNumPerfils()){
				pPerfils.actualitzaNumPerfil(Integer.toString(Integer.valueOf(pPerfils.getNumPerfil())+1));
			}
		}

		if(qui.equals("ANTERIOR")){
			if(Integer.valueOf(pPerfils.getNumPerfil())>1){
				pPerfils.actualitzaNumPerfil(Integer.toString(Integer.valueOf(pPerfils.getNumPerfil())-1));
			}
		}

		if(qui.equals("GRAFICA")){
			veureGraficPastis();
		}

	}

	private void iniciarDeteccioPerfils(int min, int max){
		perfils.detectarPerfils(min, max);
	}

	private void exportaGrupsAExcel(String directori, boolean unUnicFull, boolean numeric) {

		try {
			ExportacioExcel test = new ExportacioExcel();
			test.setOutputFile(directori);
			test.write(unUnicFull);
			for(int i=0; i<perfils.getNumPerfils(); i++){
				if(numeric) {
					test.escriuDataSet(perfils.getCentroidesNumerics(),perfils.getAlumnesPerfilCaracteristiquesNumeric(Integer.valueOf(i)), "PERFIL "+String.valueOf(i+1), i, unUnicFull);
				}else{
					test.escriuDataSet(perfils.getCentgroidesNominals(),perfils.getAlumnesPerfilCaracteristiquesLletres(Integer.valueOf(i)), "PERFIL "+String.valueOf(i+1), i, unUnicFull);
				}
			}
			test.finalitza();
		} catch (WriteException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	private boolean seleccioFitxerAlumnes() throws IOException{
		String sNomFitxer = seleccionarFitxer();
		if(sNomFitxer != null){
			perfils = new Perfils(PreProcesNE.preProcessar(sNomFitxer,CSV2Arff.NOTES_NUMERIC));
			if(perfils.getDataset()!=null) {
				int iNumAlumnes = EinaArff.retornaNumInstancies(perfils.getDataset());
				actualitzaSeleccioFitxer(sNomFitxer, iNumAlumnes);
				return true;
			}
		}
		return false;
	}

	private void veureGraficPastis() {

		GraficDePastis dePastis = new GraficDePastis("", false);
		for (int i=0; i<perfils.getNumPerfils(); i++) {
			dePastis.afegeixDades("Perfil "+String.valueOf(i+1)+"\n"+perfils.getPercentPerfil2(i)+"% ("+perfils.getNumAlumnesPerfil2(i)+"/"+perfils.gteAlumnesTotals()+")", (double)perfils.getPercentPerfil2(i));
		}

		JDialog finestra = new JDialog();
		finestra.setAlwaysOnTop(true);

		JPanel jpDescripcio = new JPanel();
		jpDescripcio.setLayout(new BorderLayout());
		JLabel jlTitol = new JLabel("Gráfico de pastel (visualización de los perfiles detectados)");
		jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));

		JTextArea jlText = new JTextArea();
		jlText.setText("\nEl siguiente gráfico de pastel muestra el porcentaje del total de alumnos de los perfiles detectados:\n");
		jlText.setEditable(false);  
		jlText.setOpaque(false);      
		jlText.setWrapStyleWord(true);  
		jlText.setLineWrap(true); 
		jlText.setFont(UIManager.getFont("Label.font")); 

		jpDescripcio.add(jlTitol, BorderLayout.NORTH);
		jpDescripcio.add(jlText, BorderLayout.CENTER);

		JPanel pGrafic = new JPanel();
		pGrafic.setLayout(new BorderLayout());
		pGrafic.add(dePastis.generaPanell(), BorderLayout.CENTER);
		pGrafic.setBackground(Color.white);   	

		finestra.getContentPane().add(jpDescripcio, BorderLayout.NORTH);
		finestra.getContentPane().add(pGrafic, BorderLayout.CENTER);
		finestra.pack();
		finestra.setTitle("Sagittarius - Representación gráifca de los perfiles detectados");
		finestra.setLocationRelativeTo(null);
		finestra.setVisible(true);
		finestra.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));
	}

	private void mostraGraficNotes() {
		GraficDeBarres db = new GraficDeBarres("", "Nota media por perfil", "Actividad/Test/Examen", true);


		for (int i=0; i<perfils.getCentroidesNumerics().numInstances(); i++) {			
			Instance pp = perfils.getCentroidesNumerics().instance(i);
			for(int j=0; j<pp.numAttributes(); j++) {
				db.afegeixDades(pp.attribute(j).name(), "Perfil "+(i+1),(float)pp.value(j));					
			}
		}

		JDialog finestra = new JDialog();

		finestra.setAlwaysOnTop(true);

		JPanel jpDescripcio = new JPanel();
		jpDescripcio.setLayout(new BorderLayout());
		JLabel jlTitol = new JLabel("Gráfico de barras (visualización de los resultados medios de los perfiles)");
		jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));

		JTextArea jlText = new JTextArea();

		jlText.setText("\nEl siguiente gráfico de barras muestra la media de los resultats que han obtenido los alumnos de cada perfil:\n");
		jlText.setEditable(false);  
		jlText.setOpaque(false);      
		jlText.setWrapStyleWord(true);  
		jlText.setLineWrap(true); 
		jlText.setFont(UIManager.getFont("Label.font")); 

		jpDescripcio.add(jlTitol, BorderLayout.NORTH);
		jpDescripcio.add(jlText, BorderLayout.CENTER);

		JPanel pGrafic = new JPanel();
		pGrafic.setLayout(new BorderLayout());
		pGrafic.add(db.generaPanell(), BorderLayout.CENTER);
		pGrafic.setBackground(Color.white);   	

		finestra.getContentPane().add(jpDescripcio, BorderLayout.NORTH);
		finestra.getContentPane().add(pGrafic, BorderLayout.CENTER);

		finestra.pack();
		finestra.setLocationRelativeTo(null);
		finestra.setVisible(true);
		finestra.setTitle("Sagittarius - Visualización de los perfiles detectados");
		finestra.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));
	}


	private void veureGraficBarresPercentual() {

		GraficDeBarresPercentual deBarresPercentuals = new GraficDeBarresPercentual("","Distribución de notas por perfil","", true);
		int[] aux = null;
		for (int i=0; i<perfils.getNumPerfils(); i++) {
			aux = perfils.getEstadistiquesPerfil(i);
			deBarresPercentuals.afegeixDades((double)((aux[0]/(float)perfils.getNumCaracteristiques())*100), "NA", "Perfil "+(i+1));					
			deBarresPercentuals.afegeixDades((double)((aux[1]/(float)perfils.getNumCaracteristiques())*100), "F", "Perfil "+(i+1));				
			deBarresPercentuals.afegeixDades((double)((aux[2]/(float)perfils.getNumCaracteristiques())*100), "E - D", "Perfil "+(i+1));				
			deBarresPercentuals.afegeixDades((double)((aux[3]/(float)perfils.getNumCaracteristiques())*100), "C - B", "Perfil "+(i+1));				
			deBarresPercentuals.afegeixDades((double)((aux[4]/(float)perfils.getNumCaracteristiques())*100), "A", "Perfil "+(i+1));					
		}


		JDialog finestra = new JDialog();
		finestra.setAlwaysOnTop(true);

		JPanel jpDescripcio = new JPanel();
		jpDescripcio.setLayout(new BorderLayout());
		JLabel jlTitol = new JLabel("Gráfico de barras (visualización de los resulados porcentuales de los perfiles)");
		jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));

		JTextArea jlText = new JTextArea();
		
		jlText.setText("\nEl siguiente gráfico de barras muestra la composición porcentual de las evaluaciones ded los  perfiles detectados:\n");
		jlText.setEditable(false);  
		jlText.setOpaque(false);      
		jlText.setWrapStyleWord(true);  
		jlText.setLineWrap(true); 
		jlText.setFont(UIManager.getFont("Label.font")); 

		jpDescripcio.add(jlTitol, BorderLayout.NORTH);
		jpDescripcio.add(jlText, BorderLayout.CENTER);

		JPanel pGrafic = new JPanel();
		pGrafic.setLayout(new BorderLayout());
		pGrafic.add(deBarresPercentuals.generaPanell(), BorderLayout.CENTER);
		pGrafic.setBackground(Color.white);   	

		finestra.getContentPane().add(jpDescripcio, BorderLayout.NORTH);
		finestra.getContentPane().add(pGrafic, BorderLayout.CENTER);

		finestra.pack();
		finestra.setTitle("Sagittarius - Representación gráifca de los perfiles detectados");
		finestra.setLocationRelativeTo(null);
		finestra.setVisible(true);
		finestra.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));

	}

	private void actualitzaSeleccioFitxer(String sNomFitxer, int numAlumnes){
		pPerfils.setNomFitxer(sNomFitxer);
		pPerfils.setNumAlumnes(numAlumnes);
	}

	private void veureAlumnes() throws IOException{
		ArffPanel apPanellArff= new ArffPanel();
		if (pPerfils.consultaSeleccioVista().equals("Cualitativa")) {
			apPanellArff.setInstances(perfils.getAlumnesPerfilCaracteristiquesLletres(Integer.valueOf(pPerfils.getNumPerfil())-1));	
		}

		if (pPerfils.consultaSeleccioVista().equals("Cuantitativa")) {
			apPanellArff.setInstances(EinaArff.crearEspecialitatNominalNotesString(perfils.getAlumnesPerfilCaracteristiquesNumeric(Integer.valueOf(pPerfils.getNumPerfil())-1)));
		}

		apPanellArff.setReadOnly(true);
		apPanellArff.setOptimalColWidths();
		JDialog aux = new JDialog();
		aux.setModalExclusionType(null);
		aux.setTitle("Sagittarius - Explorar alumnos del perfil");
		aux.setPreferredSize(new Dimension(800,600));
		aux.pack();
		aux.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aux.setLocationRelativeTo(null);
		aux.add(apPanellArff);
		aux.setVisible(true);
		aux.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));

	}

	private void veureCaracteristiques() throws IOException{
		ArffPanel apPanellArff= new ArffPanel();
		if (pPerfils.consultaSeleccioVista().equals("Cualitativa")) {
			apPanellArff.setInstances(perfils.getCentgroidesNominals());			
		}

		if (pPerfils.consultaSeleccioVista().equals("Cuantitativa")) {
			apPanellArff.setInstances(EinaArff.crearEspecialitatNominalNotesString(perfils.getCentroidesNumerics()));	
		}

		apPanellArff.setReadOnly(true);
		apPanellArff.setOptimalColWidths();
		JDialog aux = new JDialog();
		aux.setModalExclusionType(null);
		aux.setTitle("Sagittarius - Explorar características/resultados medios del perfil");
		aux.setPreferredSize(new Dimension(800,600));
		aux.pack();
		aux.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aux.setLocationRelativeTo(null);
		aux.add(apPanellArff);
		aux.setVisible(true);
		aux.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));
	}

	private void veureFitxerAlumnes() throws IOException{
		EinaArff.mostraFitxerAmbInformacio(perfils.getDataset(), pPerfils.getNomFitxer());
	}

	private String seleccionarFitxer(){

		JFileChooser chooser = new JFileChooser("");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV (Comma-separated values) (*.csv)", "csv");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Sagittarius - Seleccionar el fichero de los alumnos");
		int returnVal = chooser.showOpenDialog(pPerfils);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}else{
			return null;
		}
	}

	public void changedUpdate(DocumentEvent arg0) { }

	public void insertUpdate(DocumentEvent arg0) {
		pPerfils.activaVeureElements();
		pPerfils.activaJTFMaxNumPerfils();
		pPerfils.activaJTFMinNumPerfils();
		pPerfils.activaIniciar();
	}

	public void removeUpdate(DocumentEvent arg0) {
	}

	private void actualitzaVista(String qui) {

		if(qui.equals("Cuantitativa")){
			pPerfils.actualitzaPanellPerfils(EinaArff.crearEspecialitatNominalNotesString(perfils.getCentroidesNumerics()));
			pPerfils.actualitzaAlumnesPerfil(EinaArff.crearEspecialitatNominalNotesString(perfils.getAlumnesPerfilCaracteristiquesNumeric(Integer.valueOf(pPerfils.getNumPerfil())-1)));
		}
		if(qui.equals("Cualitativa")){
			pPerfils.actualitzaPanellPerfils(perfils.getCentgroidesNominals());
			pPerfils.actualitzaAlumnesPerfil(perfils.getAlumnesPerfilCaracteristiquesLletres(Integer.valueOf(pPerfils.getNumPerfil())-1));
		}
	}


	public void propertyChange(PropertyChangeEvent evt) {
		if(perfils!=null && !pPerfils.getNumPerfil().equals("0")){
			pPerfils.actualitzaNumAlumnesPerfil(String.valueOf(perfils.getNumAlumnesPerfil2(Integer.valueOf(pPerfils.getNumPerfil())-1)));
			pPerfils.actualitzaPercentatgePerfil(String.valueOf(perfils.getPercentPerfil2(Integer.valueOf(pPerfils.getNumPerfil())-1))+"%"
					+" ("+perfils.getNumAlumnesPerfil2(Integer.valueOf(pPerfils.getNumPerfil())-1)+"/"+String.valueOf(perfils.gteAlumnesTotals())+")");		
			actualitzaVista(pPerfils.consultaSeleccioVista());
		}
	}

	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(pPerfils.getJTFMaxNumPerfils())){
			char caracter = e.getKeyChar();
			if(((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE)){
				e.consume(); 
				Toolkit.getDefaultToolkit().beep();
			}
		}

		if(e.getSource().equals(pPerfils.getJTFMinNumPerfils())){
			char caracter = e.getKeyChar();
			if(((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE)){
				e.consume(); 
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

	public void keyPressed(KeyEvent e) { }

	public void keyReleased(KeyEvent e) { }

}
