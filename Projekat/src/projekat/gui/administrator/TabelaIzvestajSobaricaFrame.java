package projekat.gui.administrator;

import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import projekat.gui.administrator.TableModeli.SobaricaModel;
import projekat.menadzeri.Menadzer;

public class TabelaIzvestajSobaricaFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private String datumOd;
	private String datumDo;

	public TabelaIzvestajSobaricaFrame(Menadzer menadzer,String datumOd, String datumDo) {
		setTitle("Izvestaj sobarica");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		
		izvestajGUI();
		
		setVisible(true);
	}
	
	private void izvestajGUI() {
		HashMap<String, Integer> sobarice=new HashMap<String, Integer>();
		LocalDate datumOd=LocalDate.parse(this.datumOd);
		LocalDate datumDo=LocalDate.parse(this.datumDo);
		
		menadzer.getMenadzerIzvestaja().racunSobarica(datumOd, datumDo, sobarice);
		
		JTable tabela = new JTable(new SobaricaModel(sobarice));
		add(tabela);
		tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane);
	}
}
