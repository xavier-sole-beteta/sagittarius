package sagittarius.vista.general;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * <b>FinestraAmbGrafic</b> és una finestra que mostra un gràfic.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class FinestraAmbGrafic extends JDialog {
		
	public FinestraAmbGrafic(String titolFinestra, String titolGrafic, String textExplicatiu, String textPeu, JPanel contingut) {
	   	
		JPanel jpDescripcio = new JPanel();
    	jpDescripcio.setLayout(new BorderLayout());
    	JLabel jlTitol = new JLabel(titolGrafic);
    	jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));
    	
    	JTextArea jlText = new JTextArea();
    	
    	jlText.setText("\n"+textExplicatiu+"\n");
    	jlText.setEditable(false);  
    	jlText.setOpaque(false);      
    	jlText.setWrapStyleWord(true);  
    	jlText.setLineWrap(true); 
    	jlText.setFont(UIManager.getFont("Label.font")); 
    	  	
    	jpDescripcio.add(jlTitol, BorderLayout.NORTH);
    	jpDescripcio.add(jlText, BorderLayout.CENTER);
    	
    	JPanel pGrafic = new JPanel();
    	pGrafic.setLayout(new BorderLayout());
    	pGrafic.add(contingut, BorderLayout.CENTER);
    	pGrafic.setBackground(Color.white);   	
    	
    	getContentPane().add(jpDescripcio, BorderLayout.NORTH);
    	getContentPane().add(pGrafic, BorderLayout.CENTER);
    	if(textPeu!="") {
        	JTextArea jlTextPeu = new JTextArea("\n"+textPeu+"\n");
        	jlTextPeu.setEditable(false);  
        	jlTextPeu.setOpaque(false);      
        	jlTextPeu.setWrapStyleWord(true);  
        	jlTextPeu.setLineWrap(true); 
        	jlTextPeu.setFont(UIManager.getFont("Label.font")); 
        	getContentPane().add(jlTextPeu, BorderLayout.SOUTH);
    	}
    	
    	setTitle(titolFinestra);
    	setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    	setSize(800,450);
    	setLocationRelativeTo(null);
    	setAlwaysOnTop(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));

	}
	
}
