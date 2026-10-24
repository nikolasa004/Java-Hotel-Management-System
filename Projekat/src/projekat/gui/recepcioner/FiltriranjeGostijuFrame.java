package projekat.gui.recepcioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.korisnici.Gost;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;

public class FiltriranjeGostijuFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private ArrayList<Rezervacija> pocetneRezervacije= new ArrayList<Rezervacija>();
	private ArrayList<Rezervacija> rezervacije= new ArrayList<Rezervacija>();
	private JFrame roditelj;

	public FiltriranjeGostijuFrame(Menadzer menadzer,ArrayList<Rezervacija> rezervacije,JFrame roditelj) {
		setTitle("Filtriranje gostiju");
		setSize(375, 275);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		this.pocetneRezervacije=rezervacije;
		this.roditelj = roditelj;

		filtriranjeGostijuGUI();

		setVisible(true);
	}

	private void filtriranjeGostijuGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][]30[]");
		setLayout(layout);
		
		//font
		float velicinaFonta = 15f;
		
		//naslov
		JLabel naslov = new JLabel("Filtriranje gostiju:");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
		add(naslov, "span , align center");
		
		//filtriranje po broju sobe
		JLabel brojSobe = new JLabel("Broj sobe:");
		brojSobe.setFont(brojSobe.getFont().deriveFont(velicinaFonta));
		add(brojSobe);
		JTextField unosBrojSobe = new JTextField(20);
		add(unosBrojSobe);
		
		//filtriranje po imenu
		JLabel ime = new JLabel("Ime:");
		ime.setFont(ime.getFont().deriveFont(velicinaFonta));
		add(ime);
		JTextField unosIme = new JTextField(20);
		add(unosIme);
		
		//filtriranje po prezimenu
		JLabel prezime = new JLabel("Prezime:");
		prezime.setFont(prezime.getFont().deriveFont(velicinaFonta));
		add(prezime);
		JTextField unosPrezime = new JTextField(20);
		add(unosPrezime);
		
		//filtriranje po korisnickom imenu
		JLabel korisnickoIme = new JLabel("Korisnicko ime:");
		korisnickoIme.setFont(korisnickoIme.getFont().deriveFont(velicinaFonta));
		add(korisnickoIme);
		JTextField unosKorisnickoIme = new JTextField(20);
		add(unosKorisnickoIme);
		
		//dugme za potvrdu
		JButton potvrdi = new JButton("Pronadji");
		potvrdi.setFont(potvrdi.getFont().deriveFont(velicinaFonta));
		add(potvrdi, "span, align center");
		
		//listener za dugme
		potvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String brojSobe = unosBrojSobe.getText();
				String ime = unosIme.getText();
				String prezime = unosPrezime.getText();
				String korisnickoIme = unosKorisnickoIme.getText();
				
				String greske = "";
				//soba mora biti broj i da postoji
				int brojSobeInt = -1;
				if (!brojSobe.equals("")) {
					boolean sobaOk = true;
					try {
						brojSobeInt = Integer.parseInt(brojSobe);
					} catch (NumberFormatException e1) {
						sobaOk = false;
						greske += "Broj sobe mora biti broj.\n";
					}
					
					if (sobaOk) {
						boolean sobaPostoji = false;
						for (Soba soba: menadzer.getMenadzerSoba().sobe.values()) {
							if (soba.getBrojSobe() == brojSobeInt) {
								sobaPostoji = true;
								break;
							}
						}
						if (!sobaPostoji) {
							greske += "Soba sa tim brojem ne postoji.\n";
						}
					}
				}
				
				//korisnicko ime mora da postoji
				boolean korisnickoImePostoji = false;
				if (!korisnickoIme.equals("")) {
					for (Gost gost : menadzer.getMenadzerGostiju().getGosti().values()) {
						if (gost.getKorisnickoIme().equals(korisnickoIme)) {
							korisnickoImePostoji = true;
							break;
						}
					}
					if (!korisnickoImePostoji) {
						greske += "Korisnicko ime ne postoji.\n";
					}
				}
				
				if (!greske.equals("")) {
					JOptionPane.showMessageDialog(null, greske, "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// filtriranje rezervacija
				for (Rezervacija rezervacija : pocetneRezervacije) {
					Gost gost=menadzer.getMenadzerGostiju().getGosti().get(rezervacija.getImeRezervacije());
					if (!brojSobe.equals("") && rezervacija.getSoba().getBrojSobe() != brojSobeInt) {
						continue;
					}
					if (!ime.equals("") && !gost.getIme().toLowerCase().contains(ime.toLowerCase())) {
						continue;
					}
					if (!prezime.equals("") && !gost.getPrezime().toLowerCase().contains(prezime.toLowerCase())) {
						continue;
					}
					if (!korisnickoIme.equals("") && !gost.getKorisnickoIme().toLowerCase().contains(korisnickoIme.toLowerCase())) {
						continue;
					}
					rezervacije.add(rezervacija);
				}

				JOptionPane.showMessageDialog(null, "Gosti su uspesno filtrirani.", "Filtriranje uspesno", JOptionPane.INFORMATION_MESSAGE);
				((IzvestajGostijuFrame)roditelj).azurirajTabelu(rezervacije);
				dispose();
			}
		});
	}
}
