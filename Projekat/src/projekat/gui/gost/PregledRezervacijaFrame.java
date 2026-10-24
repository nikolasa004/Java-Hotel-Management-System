package projekat.gui.gost;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import projekat.gui.administrator.TableModeli.RezervacijaModel;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;

//klasa koja predstavlja prozor za pregled rezervacija kod gosta
public class PregledRezervacijaFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private String korisnickoIme;
	private JTable tabelaRezervacija;
	private JButton dugmeOtkazi;

	public PregledRezervacijaFrame(Menadzer menadzer, String korisnickoIme) {
		setTitle("Pregled rezervacija");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		this.korisnickoIme = korisnickoIme;
		
		pregledRezervacijaGUI();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za pregled rezervacija
	private void pregledRezervacijaGUI()
    {
        JToolBar toolbar = new JToolBar();
        add(toolbar, BorderLayout.NORTH);
        
        dugmeOtkazi = new JButton("Otkazi rezervaciju");
        toolbar.add(dugmeOtkazi);
        
        JLabel ukupanTrosak = new JLabel();
        ukupanTrosak.setFont(ukupanTrosak.getFont().deriveFont(15.0f));
        toolbar.add(ukupanTrosak);
        
        ArrayList<Rezervacija> rezervacije = menadzer.getMenadzerGostiju().getGosti().get(korisnickoIme).getRezervacije();
        tabelaRezervacija = new JTable(new RezervacijaModel(rezervacije));
        add(tabelaRezervacija);
        tabelaRezervacija.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaRezervacija.getTableHeader().setReorderingAllowed(false);
		
		//racun troska
		double trosak = 0;
		for (Rezervacija rezervacija : rezervacije) {
			trosak += rezervacija.getCena();
		}
		ukupanTrosak.setText("Ukupan trosak: " + trosak + " dinara");
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabelaRezervacija);
		add(scrollPane);
		
		//listener za dugme otkazi
		dugmeOtkazi.addActionListener(new ActionListener() {
			    
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (tabelaRezervacija.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati rezervaciju!", "Greska", JOptionPane.WARNING_MESSAGE);
				}
            	else {
					int id=Integer.parseInt(tabelaRezervacija.getValueAt(tabelaRezervacija.getSelectedRow(), 0).toString());
					Rezervacija rezervacija=menadzer.getMenadzerRezervacija().rezervacije.get(id);
					//provera da li je rezervacija u buducnosti ako nije ne moze se otkazati
					if (rezervacija.getDatumRezervacije().isBefore(LocalDate.now())) {
						JOptionPane.showMessageDialog(null, "Rezervacija je vec pocela ili prosla, ne mozete je otkazati!",
								"Greska", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int opcija = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da otkazete rezervaciju?", "Otkazivanje rezervacije", JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
                        menadzer.getMenadzerGostiju().otkaziRezervaciju(id, korisnickoIme);
                        menadzer.getMenadzerRezervacija().pisanjeRezervacija();
                        ((RezervacijaModel)tabelaRezervacija.getModel()).fireTableDataChanged();
					}
				}
            }
        });
    }
}
