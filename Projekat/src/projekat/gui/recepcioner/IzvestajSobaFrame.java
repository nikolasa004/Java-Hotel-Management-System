package projekat.gui.recepcioner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import projekat.gui.administrator.TableModeli.SobaModel;
import projekat.menadzeri.Menadzer;

//prozor za izvestaj o sobama
public class IzvestajSobaFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;

	public IzvestajSobaFrame(Menadzer menadzer) {
		setTitle("Izvestaj o sobama");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		
		sobaGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za izvestaj o sobama
	private void sobaGUI() {
		JTable tabela = new JTable(new SobaModel(menadzer.getMenadzerSoba()));
        add(tabela);
        tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane);
	}

}
