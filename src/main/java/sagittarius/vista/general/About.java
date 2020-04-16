package sagittarius.vista.general;

import java.awt.BorderLayout;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import sagittarius.model.principal.ConfiguracioSagittarius;
import net.miginfocom.swing.MigLayout;


/**
 * <b>About</b> és una finestra que mostra informació referent al programari.<br/>
 * 
 * @author Xavier Solé-Beteta (xavier.sole@salle.url.edu)
 * 
 */
public class About extends JDialog {

	private JLabel jlLaSalleLogo;
	private JLabel jlTitolSagittarius;
	private JLabel jlVersio;
	private JLabel jlUniversitatLaSalle;
	private JTextArea jlLlicencia;
	private JLabel jlSagittariusLogo;
	private JPanel jpDret;

	public About() {
		setLayout(new BorderLayout());

		jlLaSalleLogo = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/LaSalleENG-logo.png"))));

		MigLayout layout = new MigLayout("fillx", "[left]10[grow,fill]", "20[top][]20[][]");
		jpDret = new JPanel();
		jpDret.setLayout(layout);

		jlSagittariusLogo = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-logo.png"))));
		jlTitolSagittarius = new JLabel("Sagittarius");
		jlVersio = new JLabel("Versión "+ConfiguracioSagittarius.version+ " ("+ConfiguracioSagittarius.date+")");

		jlTitolSagittarius.setFont(jlTitolSagittarius.getFont().deriveFont(18f));
		jlTitolSagittarius.setFont(jlTitolSagittarius.getFont().deriveFont(Font.BOLD));
		jlVersio.setFont(jlVersio.getFont().deriveFont(12f));

		jlUniversitatLaSalle = new JLabel("<html><b><a href=\"\">La Salle</a> - Universitat Ramon Llull.</b></html>");
		afegirLinkALabel(jlUniversitatLaSalle, "www.salle.url.edu");

		jlLlicencia = new JTextArea();
		jlLlicencia.setText("Sagittarius es un proyecto de código abierto del Campus Universitario La Salle, Universitat Ramon Llull (Barcelona), que puede descargarse y usarse libremente. La autoría pertenece a Xavier Solé-Beteta (xavier.sole@salle.url.edu).");
		jlLlicencia.setEditable(false);
		jlLlicencia.setOpaque(false);      
		jlLlicencia.setWrapStyleWord(true);  
		jlLlicencia.setLineWrap(true); 
		jlLlicencia.setFont(UIManager.getFont("Label.font")); 

		jpDret.add(jlSagittariusLogo, "span 0 3");
		jpDret.add(jlTitolSagittarius);
		jpDret.add(jlVersio, "cell 1 1");
		jpDret.add(jlUniversitatLaSalle, "cell 1 2");
		jpDret.add(jlLlicencia, "cell 1 3");		
		add(jlLaSalleLogo, BorderLayout.WEST);
		add(jpDret, BorderLayout.CENTER);

		setSize(500,200);
		setResizable(false);
		setTitle("Acerca de Sagittarius");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/Sagittarius-ic.png")));
	}

	private void afegirLinkALabel(final JLabel label, final String url){
		label.addMouseListener(new MouseListener() {			
			public void mouseExited(MouseEvent arg0) {
				label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mouseEntered(MouseEvent arg0) {
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent arg0) {
				try {
					if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)){
						label.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						Desktop.getDesktop().browse(new URI(url));
						label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					} else {
						System.err.println("Not supported!");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					System.err.println("URI Syntax error!");
				}
			}
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
		});
	}

}
