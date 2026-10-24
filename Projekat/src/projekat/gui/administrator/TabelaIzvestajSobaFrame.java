package projekat.gui.administrator;

import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import projekat.gui.administrator.TableModeli.SobaModel;
import projekat.menadzeri.Menadzer;

//klasa za izvestaj soba
public class TabelaIzvestajSobaFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private String datumOd;
	private String datumDo;

	public TabelaIzvestajSobaFrame(Menadzer menadzer,String datumOd, String datumDo) {
		setTitle("Izvestaj soba");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		this.menadzer = menadzer;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		
		tabelaGUI();
		
		setVisible(true);
	}

	//metoda koja definise izgled prozora za izvestaj soba
	public void tabelaGUI() {
		LocalDate datumOd=LocalDate.parse(this.datumOd);
		LocalDate datumDo=LocalDate.parse(this.datumDo);
		HashMap<Integer, Integer> sobeBroj=new HashMap<Integer, Integer>();
		HashMap<Integer, Double> sobePrihod=new HashMap<Integer, Double>();
		
		menadzer.getMenadzerIzvestaja().racunSoba(datumOd, datumDo, menadzer, sobeBroj, sobePrihod);
		
		JTable tabela = new JTable(new SobaModel(sobeBroj,sobePrihod,menadzer.getMenadzerSoba()));
		add(tabela);
		tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane);
	}
}
