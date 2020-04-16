package sagittarius.model.perfils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import weka.core.Instances;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * <b>ExportacioExcel</b> implementa les funcionalitats per exportar els perfils d'alumnes detectats.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class ExportacioExcel {

	private File file;
	private WorkbookSettings wbSettings;
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat fontTitol;
	private WritableCellFormat times;
	private String inputFile;
	private WritableSheet excelSheet;
	private WritableWorkbook workbook;
	private int fila;
	private static final String TITOL = "ECOA - Perfiles detectados";
	
	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
		fila = 1;
	}

	public void write(boolean unUnicFull) throws IOException, WriteException {
		file = new File(inputFile);
		wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
	
		createLabel();
		workbook = Workbook.createWorkbook(file, wbSettings);
		if (unUnicFull) {
			workbook.createSheet("PERFILES", 0);
			excelSheet = workbook.getSheet(0);
			addTitol(0,fila,TITOL);
		}
		
		fila = fila+2;

	}
	
	public void finalitza() throws IOException, WriteException {
		CellView cell = null;
		
		for(int i=0; i<workbook.getNumberOfSheets(); i++) {
			for(int j=1;j<10;j++) {
			    cell = workbook.getSheet(i).getColumnView(j);
			    cell.setAutosize(true);
			    workbook.getSheet(i).setColumnView(j, cell);
			}			
		}

		workbook.write();
		workbook.close();
	}

	private void createLabel() throws WriteException {
		WritableFont arial11pt = new WritableFont(WritableFont.ARIAL, 11);
		times = new WritableCellFormat(arial11pt);

		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.ARIAL, 11, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);

		WritableFont titol = new WritableFont(
				WritableFont.ARIAL, 13, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		fontTitol = new WritableCellFormat(titol);
		
		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

	}

	private void addCaption(int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		excelSheet.addCell(label);
	}
	
	private void addTitol(int column, int row, String s)
		throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, fontTitol);
		excelSheet.addCell(label);
	}

	private void addNumber(int column, int row,
			Double integer) throws WriteException, RowsExceededException {
		Number number;

		number = new Number(column, row, integer, times);
		excelSheet.addCell(number);
	}

	private void addLabel(int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		excelSheet.addCell(label);
	}
	
	public void escriuDataSet(Instances dataSetCaracteristiques, Instances dataSet, String titol, int quin, boolean unUnicFull) {
		String valor = "";

		if(unUnicFull) {
			try {
				addCaption(2, fila, titol.replaceAll("\\'", ""));
				fila++;
				addCaption(2, fila, "CARACTERÍSTICAS:");
				fila++;
				for(int i=0; i<dataSetCaracteristiques.numAttributes(); i++) {
					addCaption(i+2, fila, dataSetCaracteristiques.attribute(i).name());
				}
				fila++;
				for(int i=0; i<dataSetCaracteristiques.numAttributes(); i++) {	
					valor = dataSetCaracteristiques.instance(quin).toString(i);
					if(valor.equals("-1")) {
						addLabel(i+2, fila, "NP");
					}else{
						if(dataSetCaracteristiques.attribute(i).isNumeric()) {
							addNumber(i+2, fila, dataSetCaracteristiques.instance(quin).value(i));
						}else{
							addLabel(i+2, fila, valor.replaceAll("\\'", ""));
						}
					}
				}
				fila++;
				addCaption(2, fila, "ALUMNOS:");
				fila++;
				for(int i=0; i<dataSet.numAttributes(); i++) {
					addCaption(i+2, fila, dataSet.attribute(i).name());
				}
				fila++;
				for(int i=0; i<dataSet.numInstances(); i++){
					for(int j=0; j<dataSet.numAttributes(); j++){
						valor = dataSet.instance(i).toString(j);
						if(valor.equals("-1")) {
							addLabel(j+2, fila+i, "NP");
						}else{
							if(dataSet.attribute(j).isNumeric()) {
								addNumber(j+2, fila+i, dataSet.instance(i).value(j));
							}else{
								addLabel(j+2, fila+i, valor.replaceAll("\\'", ""));
							}
						}
					}
				}
				fila = fila + dataSet.numInstances()+2;
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				fila = 0;
				workbook.createSheet("PERFIL "+Integer.toString(quin+1), quin);
				excelSheet = workbook.getSheet(quin);
				
				addCaption(2, fila, titol.replaceAll("\\'", ""));
				fila++;
				addCaption(2, fila, "CARACTERÍSTICAS:");
				fila++;
				for(int i=0; i<dataSetCaracteristiques.numAttributes(); i++) {
					addCaption(i+2, fila, dataSetCaracteristiques.attribute(i).name());
				}
				fila++;
				for(int i=0; i<dataSetCaracteristiques.numAttributes(); i++) {	
					valor = dataSetCaracteristiques.instance(quin).toString(i);
					if(valor.equals("-1")) {
						addLabel(i+2, fila, "NP");
					}else{
						if(dataSetCaracteristiques.attribute(i).isNumeric()) {
							addNumber(i+2, fila, dataSetCaracteristiques.instance(quin).value(i));
						}else{
							addLabel(i+2, fila, valor.replaceAll("\\'", ""));
						}
					}
				}
				fila++;
				addCaption(2, fila, "ALUMNOS:");
				fila++;
				for(int i=0; i<dataSet.numAttributes(); i++) {
					addCaption(i+2, fila, dataSet.attribute(i).name());
				}
				fila++;
				for(int i=0; i<dataSet.numInstances(); i++){
					for(int j=0; j<dataSet.numAttributes(); j++){
						valor = dataSet.instance(i).toString(j);
						if(valor.equals("-1")) {
							addLabel(j+2, fila+i, "NP");
						}else{
							if(dataSet.attribute(j).isNumeric()) {
								addNumber(j+2, fila+i, dataSet.instance(i).value(j));
							}else{
								addLabel(j+2, fila+i, valor.replaceAll("\\'", ""));
							}
						}
					}
				}
				fila = fila + dataSet.numInstances()+2;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}

