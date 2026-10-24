package projekat.gui.recepcioner;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import projekat.gui.administrator.TableModeli.RezervacijaModel;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;

//prozor za dnevni izvestaj
public class DnevniIzvestajFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;

	public DnevniIzvestajFrame(Menadzer menadzer) {
		setTitle("Dnevni izvestaj");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		
		izvestajGUI();
		
		setVisible(true);
	}

	//metoda koja definise izgled prozora za dnevni izvestaj
	private void izvestajGUI() {
		MigLayout layoutLevi = new MigLayout("wrap 1", "[grow]", "10[]30[]");
		ArrayList<Rezervacija> rezervacijeLeve = new ArrayList<Rezervacija>();
		ArrayList<Rezervacija> rezervacijeDesne = new ArrayList<Rezervacija>();
		menadzer.getMenadzerRecepcionera().izvestajDolasciOdlasci(menadzer, rezervacijeLeve, rezervacijeDesne);
		
		JPanel leviPanel = new JPanel();
		leviPanel.setLayout(layoutLevi);
		JLabel naslovLevi = new JLabel("Dolasci");
		JTable tabelaLeva = new JTable(new RezervacijaModel(rezervacijeLeve));
		naslovLevi.setFont(naslovLevi.getFont().deriveFont(15.0f));
		JScrollPane scrollPaneLevi = new JScrollPane(tabelaLeva);
		leviPanel.add(naslovLevi, "align center");
		leviPanel.add(scrollPaneLevi, "grow,span");
		
		JPanel desniPanel = new JPanel();
	    MigLayout layoutDesni = new MigLayout("wrap 1", "[grow]", "10[]30[]");
        desniPanel.setLayout(layoutDesni);
		JLabel naslovDesni = new JLabel("Odlasci");
		JTable tabelaDesna = new JTable(new RezervacijaModel(rezervacijeDesne));
		naslovDesni.setFont(naslovDesni.getFont().deriveFont(15.0f));
		JScrollPane scrollPaneDesni = new JScrollPane(tabelaDesna);
		desniPanel.add(naslovDesni, "align center");
		desniPanel.add(scrollPaneDesni, "grow,span");
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leviPanel, desniPanel);
		splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(0.5);
		add(splitPane);
	}
}
