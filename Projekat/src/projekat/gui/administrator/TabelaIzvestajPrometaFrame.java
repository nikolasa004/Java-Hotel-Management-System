package projekat.gui.administrator;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import projekat.gui.administrator.TableModeli.RashodModel;
import projekat.gui.administrator.TableModeli.RezervacijaModel;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;

public class TabelaIzvestajPrometaFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private LocalDate datumOd;
	private LocalDate datumDo;

	public TabelaIzvestajPrometaFrame(Menadzer menadzer,LocalDate datumOd, LocalDate datumDo) {
		setTitle("Izvestaj prometa");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		this.datumOd = datumOd;
		this.datumDo = datumDo;

		izvestajPrometaGUI();

		setVisible(true);
	}

	// metoda koja definise izgled prozora za izvestaj prometa
	private void izvestajPrometaGUI() {
		//prihodi
		ArrayList<Rezervacija> prihodi = new ArrayList<Rezervacija>();
		//rashodi
		ArrayList<String[]> rashodi = new ArrayList<String[]>();
		
		double podaci[]=menadzer.getMenadzerIzvestaja().racunPrometa(prihodi,rashodi,datumOd, datumDo, menadzer);
		double prihod=podaci[0];
		double rashod=podaci[1];

		MigLayout layoutLevi = new MigLayout("wrap 1", "[grow]", "10[]30[]");
		
		JPanel leviPanel = new JPanel();
		leviPanel.setLayout(layoutLevi);
		JLabel naslovLevi = new JLabel("Prihodi: "+prihod+" dinara");
		JTable tabelaLeva = new JTable(new RezervacijaModel(prihodi));
		naslovLevi.setFont(naslovLevi.getFont().deriveFont(15.0f));
		JScrollPane scrollPaneLevi = new JScrollPane(tabelaLeva);
		leviPanel.add(naslovLevi, "align center");
		leviPanel.add(scrollPaneLevi, "grow,span");
		
		JPanel desniPanel = new JPanel();
	    MigLayout layoutDesni = new MigLayout("wrap 1", "[grow]", "10[]30[]");
        desniPanel.setLayout(layoutDesni);
		JLabel naslovDesni = new JLabel("Rashodi: "+rashod+" dinara");
		JTable tabelaDesna = new JTable(new RashodModel(rashodi));
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
