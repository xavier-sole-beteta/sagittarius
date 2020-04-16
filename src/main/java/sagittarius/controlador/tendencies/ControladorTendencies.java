package sagittarius.controlador.tendencies;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import sagittarius.model.general.CSV2Arff;
import sagittarius.model.general.EinaArff;
import sagittarius.model.general.PreProcesNE;
import sagittarius.model.tendencies.Tendencies;
import sagittarius.vista.tendencies.PanellTendencies;



/**
 * <b>ControladorTendencies</b> és la classe controladora del triplet MVC del cas funcional
 * pel descobriment de tendències (MDT).<br/>
 * Emmagatzema una referència a la vista (de tipus PanellTendencies) i una referència
 * al model (de tipus Tendencies).<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ControladorTendencies implements ActionListener, DocumentListener, KeyListener{

	/** Vista */
	private PanellTendencies vista;
	
	/** Model */
	private Tendencies model;
	
	
	public ControladorTendencies(PanellTendencies pTendencies){
		this.vista = pTendencies;
	}


	public void actionPerformed(ActionEvent e) {
		String qui = e.getActionCommand();
		
		if(qui.equals("SELECCIONAR")){
			try{
				seleccioFitxerAlumnes();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(qui.equals("VEURE")){
			try{
				veureFitxerAlumnes();
			}catch (IOException ioeExcepcio){
				ioeExcepcio.printStackTrace();
			}
		}
		
		if(qui.equals("INICIAR")){
				iniciarTendencies();
		}
	}
	
	private void iniciarTendencies(){
		model.detectaTendencies(Integer.valueOf(vista.getNumMaximTendencies()), vista.getTemporalitat());
		vista.actualitzaNumTendencies(String.valueOf(model.getNumTendencies()));
		if(model.getNumTendencies()!=0) {
			vista.actualitzaTendenciesDetectades(model.getRegles());
		}
	}
	
	private void veureFitxerAlumnes() throws IOException{
		EinaArff.mostraFitxerAmbInformacio(model.getDatasetNumeric(), vista.getNomFitxer());
	}
	
	private void seleccioFitxerAlumnes() throws IOException{
		String sNomFitxer = seleccionarFitxer();
		if(sNomFitxer != null){
			model = new Tendencies(PreProcesNE.preProcessar(sNomFitxer,CSV2Arff.NOTES_NOMINAL));
			model.setDatasetNumeric(PreProcesNE.preProcessar(sNomFitxer,CSV2Arff.NOTES_NUMERIC));
			if(model.getDataset()!=null) {
				int iNumAlumnes = EinaArff.retornaNumInstancies(model.getDataset());
				actualitzaSeleccioFitxer(sNomFitxer, iNumAlumnes);
				vista.activaNumMaximTendencies();
				vista.activarTemporalitat();
			}
		}
	}

	private String seleccionarFitxer(){
		
		JFileChooser chooser = new JFileChooser("");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV (Comma-separated values) (*.csv)", "csv");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Sagittarius - Triar el fixter d'alumnes");
		int returnVal = chooser.showOpenDialog(vista);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}else{
			return null;
		}
	}
	
	private void actualitzaSeleccioFitxer(String sNomFitxer, int numAlumnes){
		vista.setNomFitxer(sNomFitxer);
		vista.setNumAlumnes(numAlumnes);
	}



	public void changedUpdate(DocumentEvent e) {
	}

	public void insertUpdate(DocumentEvent arg0) {
		vista.activaVeureElements();
	}


	private boolean valida(String sValida){
		if(sValida.equals("") || Integer.valueOf(sValida)==0){
			return false;
		}else{
			return true;
		}
	}
	
	public void removeUpdate(DocumentEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(vista.getJTFNumMaximTendencies())){
			char caracter = e.getKeyChar();
			if(((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE)){
				e.consume(); 
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}


	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent e) {
		if(e.getSource().equals(vista.getJTFNumMaximTendencies())){
			if(valida(vista.getJTFNumMaximTendencies().getText())){
				vista.activaIniciar();
			}else{
				vista.desactivarIniciar();
			}
		}		
	}
	
}
