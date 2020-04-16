package sagittarius.vista.general;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * <b>FinestraAmbExplicacio</b> és una finestra que mostra una descripció textual.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class FinestraAmbExplicacio extends JFrame {

	public FinestraAmbExplicacio(String titolFinestra, String titolGrafic, String textExplicatiu) {

    	JLabel jlTitol = new JLabel(titolGrafic);
    	jlTitol.setFont(jlTitol.getFont().deriveFont(Font.BOLD, 14f));
    	
    	JTextArea jlText = new JTextArea();
    	jlText.setText(textExplicatiu);
    	jlText.setEditable(false);  
    	jlText.setOpaque(false);      
    	jlText.setWrapStyleWord(true);  
    	jlText.setLineWrap(true); 
    	jlText.setFont(UIManager.getFont("Label.font")); 
    	jlText.setCaretPosition(0);
    	
    	JScrollPane jsp = new JScrollPane(jlText);
    	jsp.setBackground(getContentPane().getBackground());

    	
    	getContentPane().add(jlTitol, BorderLayout.NORTH);
    	getContentPane().add(jsp, BorderLayout.CENTER);
    	
    	setTitle(titolFinestra);
    	setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
       	setSize(800,450);
    	setLocationRelativeTo(null);
    	setAlwaysOnTop(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));
	}
	
}
