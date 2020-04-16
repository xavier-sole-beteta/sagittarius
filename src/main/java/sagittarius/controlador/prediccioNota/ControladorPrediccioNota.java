package sagittarius.controlador.prediccioNota;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import sagittarius.model.general.ConversorCSVARFFNE;
import sagittarius.model.general.EinaArff;
import sagittarius.model.general.PreProcesNE;
import sagittarius.model.prediccioNota.ExportacioExcel;
import sagittarius.model.prediccioNota.PrediccioNota;
import sagittarius.model.principal.ConfiguracioSagittarius;
import sagittarius.vista.general.GraficDePastis;
import sagittarius.vista.general.GraficHistograma;
import sagittarius.vista.prediccioNota.PanellPrediccioNota;
import jxl.write.WriteException;

import weka.gui.arffviewer.ArffPanel;




/**
 * <b>ControladorAnalisiDeResultats</b> és la classe controladora del triplet MVC del cas funcional
 * per la predicció de l'èxit o fracàs de l'alumne (MPEF).<br/>
 * Emmagatzema una referència a la vista (de tipus PanellPrediccioNota) i una referència
 * al model (de tipus PrediccioNota).<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ControladorPrediccioNota implements ActionListener, DocumentListener {
	/** La vista */
	private PanellPrediccioNota pPrediccio;
	
	/** El model */
	private PrediccioNota prediccio;
	
	private int vistaActual;
	private boolean fExperiencia;
	private boolean fPrediccio;
	
	public ControladorPrediccioNota(PanellPrediccioNota pPrediccio){
		this.pPrediccio = pPrediccio;
		this.fExperiencia = false;
		this.fPrediccio = false;
		vistaActual = -1;
		prediccio = new PrediccioNota();
	}
	

	public void actionPerformed(ActionEvent e) {
		String qui = e.getActionCommand();
		
		if(e.getSource() instanceof JComboBox){
			 JComboBox cb = (JComboBox)e.getSource();
		     String sSeleccio = (String)cb.getSelectedItem();
			 actualitzaVista(sSeleccio);
		}

		if(qui.equals("SELECCIONAREXPERIENCIA")){
			try {
				seleccionarExperiencia();
				actualitzaIniciar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(qui.equals("VEUREEXPERIENCIA")){
			try{
				veureFitxerExperiencia();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		
		if(qui.equals("SELECCIONARPREDICCIO")){
			try {
				seleccionarPrediccio();
				actualitzaIniciar();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(qui.equals("OBRIR_FINESTRA")){
			try{
				veureAlumnes();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(qui.equals("HISTOGRAMA")){
				veureHistograma();
		}
		
		if(qui.equals("PASTIS")){
				veureGraficPastis();
		}
				
		if(qui.equals("VEUREPREDICCIO")){
			try{
				veureFitxerPrediccio();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(qui.equals("EXPORTAR")){
			JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.setFileFilter(new FileNameExtensionFilter("Fichero Excel", "xls"));
			fc.setAcceptAllFileFilterUsed(false);
			fc.setDialogTitle("Sagittarius - Exportar predicción de notas");
			int returnVal = fc.showSaveDialog(pPrediccio);
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		    		if (ConfiguracioSagittarius.debug) { 
		    			System.out.println(file.getAbsolutePath());
		    		}
		            if (file.getAbsolutePath().endsWith(".xls")) {
			            exportaGrupsAExcel(file.getAbsolutePath(), pPrediccio.getUnUnicFull(), pPrediccio.getResum());	
			            try {
			            	Desktop.getDesktop().open(new File(file.getAbsolutePath()));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		            }else{
		            	if(!file.getAbsolutePath().contains(".xls")) {
				            exportaGrupsAExcel(file.getAbsolutePath()+".xls", pPrediccio.getUnUnicFull(), pPrediccio.getResum());
				            try {
				            	Desktop.getDesktop().open(new File(file.getAbsolutePath()+".xls"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
		            	}
		            }
		     }
		}
		
		if(qui.equals("PREDIR")){
			predir();
			pPrediccio.activaBotons();
			pPrediccio.activaExportar();
			pPrediccio.activaModeExportar();
			vistaActual = 0;
			pPrediccio.actualitzaBotons(0);
		}
		
		if(qui.equals("VEURETOTES")){
			vistaActual = 0;
			if(pPrediccio.getTipusVista()==0){
				actualitzaVista("Resumen");
			}else{
				actualitzaVista("Histórico");
			}
			pPrediccio.actualitzaNota("Todas (NP-SUSPENSO-APROBADO-NOTABLE-EXCELENTE)");
			pPrediccio.actualitzaNumAlumnes(String.valueOf(prediccio.getNumNotes("TODAS")));
			pPrediccio.actualitzaPercentatge(String.valueOf(prediccio.getPercent("TODAS"))+"%"+" ("+String.valueOf(prediccio.getNumNotes("TODAS"))+"/"+String.valueOf(prediccio.getNumNotes("TODAS"))+")");
			pPrediccio.actualitzaBotons(0);
		}
		
		if(qui.equals("VEURENP")){
			vistaActual = 1;
			if(pPrediccio.getTipusVista()==0){
				actualitzaVista("Resumen");
			}else{
				actualitzaVista("Histórico");
			}
			pPrediccio.actualitzaNota("No presentado (NP)");
			pPrediccio.actualitzaNumAlumnes(String.valueOf(prediccio.getNumNotes("NP")));
			pPrediccio.actualitzaPercentatge(String.valueOf(prediccio.getPercent("NP"))+"%"+" ("+String.valueOf(prediccio.getNumNotes("NP"))+"/"+String.valueOf(prediccio.getNumNotes("TODAS"))+")");
			pPrediccio.actualitzaBotons(1);
		}
		
		if(qui.equals("VEURESUSPES")){
			vistaActual = 2;
			if(pPrediccio.getTipusVista()==0){
				actualitzaVista("Resumen");
			}else{
				actualitzaVista("Histórico");
			}
			pPrediccio.actualitzaNota("Suspenso (Nota <5)");
			pPrediccio.actualitzaNumAlumnes(String.valueOf(prediccio.getNumNotes("SUSPENSO")));
			pPrediccio.actualitzaPercentatge(String.valueOf(prediccio.getPercent("SUSPENSO"))+"%"+" ("+String.valueOf(prediccio.getNumNotes("SUSPENSO"))+"/"+String.valueOf(prediccio.getNumNotes("TODAS"))+")");
			pPrediccio.actualitzaBotons(2);
		}
		
		if(qui.equals("VEUREAPROVAT")){
			vistaActual = 3;
			if(pPrediccio.getTipusVista()==0){
				actualitzaVista("Resumen");
			}else{
				actualitzaVista("Histórico");
			}
			pPrediccio.actualitzaNota("Aprobado (5<=Nota<7)");
			pPrediccio.actualitzaNumAlumnes(String.valueOf(prediccio.getNumNotes("APROBADO")));
			pPrediccio.actualitzaPercentatge(String.valueOf(prediccio.getPercent("APROBADO"))+"%"+" ("+String.valueOf(prediccio.getNumNotes("APROBADO"))+"/"+String.valueOf(prediccio.getNumNotes("TODAS"))+")");
			pPrediccio.actualitzaBotons(3);
		}
		
		if(qui.equals("VEURENOTABLE")){
			vistaActual = 4;
			if(pPrediccio.getTipusVista()==0){
				actualitzaVista("Resumen");
			}else{
				actualitzaVista("Histórico");
			}
			pPrediccio.actualitzaNota("Notable (7<=Nota<9)");
			pPrediccio.actualitzaNumAlumnes(String.valueOf(prediccio.getNumNotes("NOTABLE")));
			pPrediccio.actualitzaPercentatge(String.valueOf(prediccio.getPercent("NOTABLE"))+"%"+" ("+String.valueOf(prediccio.getNumNotes("NOTABLE"))+"/"+String.valueOf(prediccio.getNumNotes("TODAS"))+")");
			pPrediccio.actualitzaBotons(4);
		}
		
		if(qui.equals("VEUREEXCELENT")){
			vistaActual = 5;
			if(pPrediccio.getTipusVista()==0){
				actualitzaVista("Resumen");
			}else{
				actualitzaVista("Histórico");
			}
			pPrediccio.actualitzaNota("Excelente (9<=Nota<=10)");
			pPrediccio.actualitzaNumAlumnes(String.valueOf(prediccio.getNumNotes("EXCELENTE")));
			pPrediccio.actualitzaPercentatge(String.valueOf(prediccio.getPercent("EXCELENTE"))+"%"+" ("+String.valueOf(prediccio.getNumNotes("EXCELENTE"))+"/"+String.valueOf(prediccio.getNumNotes("TODAS"))+")");
			pPrediccio.actualitzaBotons(5);
		}
	}

	
	private void exportaGrupsAExcel(String directori, boolean unUnicFull, boolean resum) {
		
		try {
			ExportacioExcel test = new ExportacioExcel();
			test.setOutputFile(directori);
			test.write(unUnicFull);
			int mode = 0;
			
			if(!resum) {
				mode = 1;
			}
			
			test.escriuDataSet(prediccio.getPrediccioNotes("NP", mode), "NP", 0, unUnicFull);
			test.escriuDataSet(prediccio.getPrediccioNotes("SUSPENSO", mode), "SUSPENSO", 1, unUnicFull);
			test.escriuDataSet(prediccio.getPrediccioNotes("APROBADO", mode), "APROBADO", 2, unUnicFull);
			test.escriuDataSet(prediccio.getPrediccioNotes("NOTABLE", mode), "NOTABLE", 3, unUnicFull);
			test.escriuDataSet(prediccio.getPrediccioNotes("EXCELENTE", mode), "EXCELENTE", 4, unUnicFull);
			
			test.finalitza();
		} catch (WriteException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void actualitzaIniciar() {
		if(fExperiencia && fPrediccio) {
			pPrediccio.activarIniciarPrediccio();
		}
	}
	
	
	private void veureAlumnes() throws IOException{
		ArffPanel apPanellArff= new ArffPanel();

		if(pPrediccio.getTipusVista()==0){
			if(vistaActual==0){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("TODAS", 0));	
			}
			if(vistaActual==1){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("NP", 0));	
			}
			if(vistaActual==2){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("SUSPENSO", 0));	
			}
			if(vistaActual==3){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("APROBADO", 0));	
			}
			if(vistaActual==4){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("NOTABLE", 0));	
			}
			if(vistaActual==5){
				if(prediccio.getPrediccioNotes("EXCELENTE", 0).numInstances()!=0){
					apPanellArff.setInstances(prediccio.getPrediccioNotes("EXCELENTE", 0));	
				}else{
					apPanellArff.setInstances(prediccio.getPrediccioNotes("TODAS", 0));	
				}
			}
		}
		if(pPrediccio.getTipusVista()==1){
			if(vistaActual==0){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("TODAS", 1));	
			}
			if(vistaActual==1){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("NP", 1));	
			}
			if(vistaActual==2){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("SUSPENSO", 1));	
			}
			if(vistaActual==3){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("APROBADO", 1));	
			}
			if(vistaActual==4){
				apPanellArff.setInstances(prediccio.getPrediccioNotes("NOTABLE", 1));	
			}
			if(vistaActual==5){
				if(prediccio.getPrediccioNotes("EXCELENTE", 0).numInstances()!=0){
					apPanellArff.setInstances(prediccio.getPrediccioNotes("EXCELENTE", 1));	
				}else{
					apPanellArff.setInstances(prediccio.getPrediccioNotes("AUX", 0));	
				}
			}
		}
		apPanellArff.setReadOnly(true);
		apPanellArff.setOptimalColWidths();
		JDialog aux = new JDialog();
		aux.setModalExclusionType(null);
		aux.setTitle("Sagittarius - Ver alumnos de la predicción");
		aux.setPreferredSize(new Dimension(800,600));
		aux.pack();
		aux.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aux.setLocationRelativeTo(null);
		aux.add(apPanellArff);
		aux.setVisible(true);
		aux.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/ECOA-ic.png")));
	}
	
	private void predir(){
		prediccio.generaPrediccioNota();
		pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("TODAS", 0));
		pPrediccio.actualitzaNota("Todas (NP-SUSPENSO-APROBADO-NOTABLE-EXCELENTE)");
		pPrediccio.actualitzaNumAlumnes(String.valueOf(prediccio.getNumNotes("TODAS")));
		pPrediccio.actualitzaPercentatge(String.valueOf(prediccio.getPercent("TODAS"))+"%"+" ("+String.valueOf(prediccio.getNumNotes("TODAS"))+"/"+String.valueOf(prediccio.getNumNotes("TODAS"))+")");
		pPrediccio.actualitzaBotons(0);
		pPrediccio.activaTipusVista();
		vistaActual = 0;
	}
	
	private void seleccionarPrediccio() throws IOException{
		String sNomFitxer = seleccionarFitxer();
		if(sNomFitxer!=null) {
			prediccio.setDatasetPrediccioNumeric(PreProcesNE.preProcessar(sNomFitxer, ConversorCSVARFFNE.NOTES_NUMERIC));
			prediccio.setDatasetPrediccioNominal(PreProcesNE.preProcessar(sNomFitxer, ConversorCSVARFFNE.NOTES_NOMINAL));
			if(prediccio.getDatasetPrediccioNominal()!=null){
				int iNumAlumnes = EinaArff.retornaNumInstancies(prediccio.getDatasetPrediccioNominal());
				actualitzaSeleccioPrediccio(sNomFitxer, iNumAlumnes);
				fPrediccio = true;
			}
		}
	}

	private void actualitzaSeleccioPrediccio(String sNomFitxer, int numAlumnes){
		pPrediccio.setNomFitxerPrediccio(sNomFitxer);
		pPrediccio.setNumAlumnesPrediccio(numAlumnes);
	}
	
	private void seleccionarExperiencia() throws IOException{
		String sNomFitxer = seleccionarFitxer();
		if (sNomFitxer!=null) {
			prediccio.setDatasetExperienciaNumeric(PreProcesNE.preProcessar(sNomFitxer, ConversorCSVARFFNE.NOTES_NUMERIC));
			prediccio.setDatasetExperienciaNominal(PreProcesNE.preProcessar(sNomFitxer, ConversorCSVARFFNE.NOTES_NOMINAL));
			if(prediccio.getDatasetExperienciaNominal()!=null){
				int iNumAlumnes = EinaArff.retornaNumInstancies(prediccio.getDatasetExperienciaNominal());
				actualitzaSeleccioExperiencia(sNomFitxer, iNumAlumnes);
				pPrediccio.setNotaPrediccio(EinaArff.retornaUltimAtribut(prediccio.getDatasetExperienciaNominal()));
				fExperiencia = true;
			}
		}
	}

	private void actualitzaSeleccioExperiencia(String sNomFitxer, int numAlumnes){
		pPrediccio.setNomFitxerExperiencia(sNomFitxer);
		pPrediccio.setNumAlumnesExperiencia(numAlumnes);
	}
	
	private String seleccionarFitxer(){
		
		JFileChooser chooser = new JFileChooser("");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV (Comma-separated values) (*.csv)", "csv");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Sagittarius - Seleccionar el fichero de alumnos");
		int returnVal = chooser.showOpenDialog(pPrediccio);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}else{
			return null;
		}
		
	}
	
	private void veureGraficPastis() {
		
		GraficDePastis dePastis = new GraficDePastis("", false);
		
		dePastis.afegeixDades("NP"+"\n"+prediccio.getPercent("NP")+"% ("+prediccio.getNumNotes("NP")+"/"+prediccio.getNumAlumnesPrediccio()+")", (double)prediccio.getPercent("NP"));
		dePastis.afegeixDades("SUSPENSO"+"\n"+prediccio.getPercent("SUSPENSO")+"% ("+prediccio.getNumNotes("SUSPENSO")+"/"+prediccio.getNumAlumnesPrediccio()+")", (double)prediccio.getPercent("SUSPENSO"));
		dePastis.afegeixDades("APROBADO"+"\n"+prediccio.getPercent("APROBADO")+"% ("+prediccio.getNumNotes("APROBADO")+"/"+prediccio.getNumAlumnesPrediccio()+")", (double)prediccio.getPercent("APROBADO"));
		dePastis.afegeixDades("NOTABLE"+"\n"+prediccio.getPercent("NOTABLE")+"% ("+prediccio.getNumNotes("NOTABLE")+"/"+prediccio.getNumAlumnesPrediccio()+")", (double)prediccio.getPercent("NOTABLE"));
		dePastis.afegeixDades("EXCELENTE"+"\n"+prediccio.getPercent("EXCELENTE")+"% ("+prediccio.getNumNotes("EXCELENTE")+"/"+prediccio.getNumAlumnesPrediccio()+")", (double)prediccio.getPercent("EXCELENTE"));
		
    	JDialog finestra = new JDialog();
    	finestra.setAlwaysOnTop(true);
    	
    	JPanel jpDescripcio = new JPanel();
    	jpDescripcio.setLayout(new BorderLayout());
    	JLabel jlTitol = new JLabel("Gráfico de pastel");
    	jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));
    	
    	JTextArea jlText = new JTextArea();
    	
    	jlText.setText("\nEl siguiente gráfico de pastel muestra el porcentaje, respecto al total de alumnos, de cada uno de los resultados de la predicción de notas:\n");
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
    	finestra.setTitle("Sagittarius - Visualitzación gráfica de la predicción de los resultados");
    	finestra.setLocationRelativeTo(null);
    	finestra.setVisible(true);
    	finestra.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/ECOA-ic.png")));
    	
	}
	
	private void veureHistograma() {
		
		GraficHistograma histograma = new GraficHistograma("", "Nota", "Número de alumnos");
		
		histograma.afegeixDades(prediccio.getNotesNONP());
		
    	JDialog finestra = new JDialog();
    	finestra.setAlwaysOnTop(true);
    	
    	JPanel jpDescripcio = new JPanel();
    	jpDescripcio.setLayout(new BorderLayout());
    	JLabel jlTitol = new JLabel("Gràfic de pastís");
    	jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));
    	
    	JTextArea jlText = new JTextArea();
    	
    	jlText.setText("\nEl siguiente gráfico de pastel muestra el porcentaje, respectoe al total de alumnos, que representa cada una de las notas:\n");
    	jlText.setEditable(false);  
    	jlText.setOpaque(false);      
    	jlText.setWrapStyleWord(true);  
    	jlText.setLineWrap(true); 
    	jlText.setFont(UIManager.getFont("Label.font")); 
    	
    	jpDescripcio.add(jlTitol, BorderLayout.NORTH);
    	jpDescripcio.add(jlText, BorderLayout.CENTER);
    	
    	JPanel pGrafic = new JPanel();
    	pGrafic.setLayout(new BorderLayout());
    	pGrafic.add(histograma.generaPanell(new Color((float)Math.random(), (float)Math.random(), (float)Math.random())), BorderLayout.CENTER);
    	pGrafic.setBackground(Color.white);   	
    	
    	finestra.getContentPane().add(jpDescripcio, BorderLayout.NORTH);
    	finestra.getContentPane().add(pGrafic, BorderLayout.CENTER);
    	finestra.pack();
    	finestra.setTitle("Sagittarius - Visualización gràfica de los resultados");
    	finestra.setLocationRelativeTo(null);
    	finestra.setVisible(true);
	}
	
	private void veureFitxerExperiencia() throws IOException{
		EinaArff.mostraFitxerAmbInformacio(prediccio.getDatasetExperienciaNumeric(), pPrediccio.getNomFitxerExperiencia());
	}
	
	private void veureFitxerPrediccio() throws IOException{
		EinaArff.mostraFitxerAmbInformacio(prediccio.getDatasetPrediccioNumeric(), pPrediccio.getNomFitxerPrediccio());
	}

	public void changedUpdate(DocumentEvent arg0) {
	}

	public void insertUpdate(DocumentEvent e) {
		if (ConfiguracioSagittarius.debug) { 
			System.out.println(pPrediccio.getNomFitxerExperiencia()+"-");
			System.out.println(pPrediccio.getNomFitxerPrediccio()+"-");
		}
		if(!pPrediccio.getNomFitxerExperiencia().equals("")){
			pPrediccio.activaVeureElementsExperiencia();
		}
		if(!pPrediccio.getNomFitxerPrediccio().equals("")){
			pPrediccio.activaVeureElementsPrediccio();
		}
	}

	public void removeUpdate(DocumentEvent arg0) {
	}

	private void actualitzaVista(String qui) {
		if (ConfiguracioSagittarius.debug) { 
			System.out.println("Index del combo: "+pPrediccio.getTipusVista());
			System.out.println("Vista actual: "+vistaActual);
		}
		if(qui.equals("Resumen")){
			if(vistaActual==0){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("TODAS", 0));
			}
			if(vistaActual==1){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("NP", 0));
			}
			if(vistaActual==2){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("SUSPENSO", 0));
			}
			if(vistaActual==3){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("APROBADO", 0));
			}
			if(vistaActual==4){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("NOTABLE", 0));
			}
			if(vistaActual==5){
				if(prediccio.getPrediccioNotes("EXCELENT", 0).numInstances()!=0){
					pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("EXCELENTE", 0));
				}else{
					pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("AUX", 0));
				}
			}
		}
		if(qui.equals("Histórico")){
			if(vistaActual==0){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("TODAS", 1));
			}
			if(vistaActual==1){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("NP", 1));
			}
			if(vistaActual==2){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("SUSPENSO", 1));
			}
			if(vistaActual==3){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("APROBADO", 1));
			}
			if(vistaActual==4){
				pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("NOTABLE", 1));
			}
			if(vistaActual==5){
				if(prediccio.getPrediccioNotes("EXCELENT", 0).numInstances()!=0){
					pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("EXCELENTE", 1));
				}else{
					pPrediccio.actualitzaPrediccio(prediccio.getPrediccioNotes("AUX", 1));
				}
			}
		}
	}
	
}
