package projekat.gui.recepcioner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import projekat.gui.administrator.TableModeli.RezervacijaModel;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;

//klasa koja predstavlja prozor za izvestaj gostiju, prikazuje rezervacije gostiju koji su trenutno u hotelu
public class IzvestajGostijuFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JTable tabelaRezervacija;

	public IzvestajGostijuFrame(Menadzer menadzer) {
		setTitle("Izvestaj gostiju");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;

		izvestajGostijuGUI();

		setVisible(true);
	}

	// metoda koja definise izgled prozora za izvestaj gostiju
	private void izvestajGostijuGUI() {
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		
		//popunjavanje liste rezervacija
		menadzer.getMenadzerRecepcionera().izvestajGostiju(menadzer, rezervacije);
		
		JToolBar meni = new JToolBar();
		add(meni,BorderLayout.NORTH);
		
		JButton filter = new JButton("Filtriraj");
		meni.add(filter);
		
		//prikaz rezervacija
		tabelaRezervacija = new JTable(new RezervacijaModel(rezervacije));
		tabelaRezervacija.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaRezervacija.getTableHeader().setReorderingAllowed(false);
		add(tabelaRezervacija);
		
		//panel za skrol
		JScrollPane scroll = new JScrollPane(tabelaRezervacija);
		add(scroll);
		
		//dodavanje akcija
		filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new FiltriranjeGostijuFrame(menadzer, rezervacije, IzvestajGostijuFrame.this);
            }
        });
	}
	
	//metoda koja azurira tabelu rezervacija
	public void azurirajTabelu(ArrayList<Rezervacija> rezervacije) {
		tabelaRezervacija.setModel(new RezervacijaModel(rezervacije));
		((RezervacijaModel) tabelaRezervacija.getModel()).fireTableDataChanged();
	}
}
