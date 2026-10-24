package projekat.gui.recepcioner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import projekat.enumi.StatusRezervacije;
import projekat.gui.administrator.TableModeli.RezervacijaModel;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;

//prozor za pregled rezervacija od strane recepcionera
public class PregledRezervacijaRecepcionerFrame extends JFrame{

private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JTable tabelaRezervacija;
	private JButton dugmePotvrdi;
	private JButton dugmeOdbij;
	private ArrayList<Rezervacija> rezervacije=new ArrayList<Rezervacija>();
	private JFrame ovajProzor=this;
	
	public PregledRezervacijaRecepcionerFrame(Menadzer menadzer, String korisnickoIme) {
		this.menadzer = menadzer;
		setTitle("Pregled rezervacija");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);

		recepcionerGUI();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za recepcionera
	private void recepcionerGUI() {
		JToolBar toolbar = new JToolBar();
        add(toolbar, BorderLayout.NORTH);
        
        dugmePotvrdi = new JButton("Odobri");
        toolbar.add(dugmePotvrdi);
        
        dugmeOdbij = new JButton("Odbij");
        toolbar.add(dugmeOdbij);
        
        JButton dugmeFiltriraj = new JButton("Filtriraj");
        toolbar.add(dugmeFiltriraj);
        
		//popunjavanje liste rezervacija
        menadzer.getMenadzerRecepcionera().pregledRezervacija(rezervacije, menadzer);
        
        tabelaRezervacija = new JTable(new RezervacijaModel(rezervacije));
        add(tabelaRezervacija);
        tabelaRezervacija.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaRezervacija.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabelaRezervacija);
		add(scrollPane);
		
		//listener za dugme potvrdi
		dugmePotvrdi.addActionListener(new ActionListener() {
			    
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(tabelaRezervacija.getSelectedRow() == -1) {
            		JOptionPane.showMessageDialog(null, "Morate selektovati rezervaciju!", "Greska", JOptionPane.WARNING_MESSAGE);
            	} 
            	else {
            		int id=Integer.parseInt(tabelaRezervacija.getValueAt(tabelaRezervacija.getSelectedRow(), 0).toString());
            		Rezervacija rezervacija = menadzer.getMenadzerRezervacija().rezervacije.get(id);
            		ArrayList<Soba> slobodneSobe = new ArrayList<Soba>();
            		int brojZauzetihSoba=menadzer.getMenadzerRecepcionera().proveraZauzetosti(menadzer, rezervacija, slobodneSobe);
					if(brojZauzetihSoba<slobodneSobe.size())
					{
						int opcija=JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da potvrdite rezervaciju?", "Potvrdjena rezervacija", JOptionPane.YES_NO_OPTION);
						if (opcija == JOptionPane.YES_OPTION) {
							rezervacija.setStatusRezervacije(StatusRezervacije.POTVRDJENA);
							menadzer.getMenadzerRezervacija().pisanjeRezervacija();
							((RezervacijaModel) tabelaRezervacija.getModel()).removeRow(tabelaRezervacija.getSelectedRow());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nema slobodnih soba za ovu rezervaciju!", "Greska",JOptionPane.WARNING_MESSAGE);
					}
            	}
            }
		});
		
		//listener za dugme odbij
		dugmeOdbij.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabelaRezervacija.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati rezervaciju!", "Greska", JOptionPane.WARNING_MESSAGE);
					} 
				else 
				{
					int opcija=JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da odbijete rezervaciju?", "Odbijena rezervacija", JOptionPane.YES_NO_OPTION);
					int id=Integer.parseInt(tabelaRezervacija.getValueAt(tabelaRezervacija.getSelectedRow(), 0).toString());
					if(opcija==JOptionPane.YES_OPTION)
					{
						//odbijanje
						menadzer.getMenadzerRecepcionera().odbijanjeRezervacije(menadzer, id);
						((RezervacijaModel)tabelaRezervacija.getModel()).removeRow(tabelaRezervacija.getSelectedRow());
					}
				}
			}
		});
		
		//listener za dugme filtriraj
		dugmeFiltriraj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FiltriranjeRezervacijaFrame(menadzer,rezervacije,ovajProzor);
			}
		});
	}
	
	//metoda za osvezavanje tabele posle filtriranja
	public void osveziTabelu(ArrayList<Rezervacija> rezervacije) {
		tabelaRezervacija.setModel(new RezervacijaModel(rezervacije));
		((RezervacijaModel) tabelaRezervacija.getModel()).fireTableDataChanged();
	}
}
