package sagittarius.model.analisiDeResultats;

import java.awt.Color;
import java.util.Vector;


/**
 * <b>GeneradorColorsGrafics</b> genera colors per representar els histogrames del mòdul
 * funcional per l'anàlisi i visualització de resultats.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class GeneradorColorsGrafics {

	private Vector<Color> colors;
	private String dataset;
	
	public GeneradorColorsGrafics(String dataset) {
		this.dataset = dataset;
	}
	
	public void generaColors(int num) {
		colors = new Vector<Color>();
		for(int i=0; i<num; i++) {
			colors.add(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
		}		
	}
	
	public Color consultaColor(int quin) {
		return colors.elementAt(quin);
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

}
