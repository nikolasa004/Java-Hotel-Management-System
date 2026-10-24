package projekat.gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.enumi.Pol;
import projekat.korisnici.Gost;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;

//klasa za dodavanje gosta
public class DodavanjeGostaFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JFrame roditelj;
	private String kljuc;

	public DodavanjeGostaFrame(Menadzer menadzer, String korisnickoIme, JFrame roditelj) {
		setTitle("Dodavanje gosta");
		setSize(375, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.kljuc = korisnickoIme;
		this.roditelj = roditelj;
		
		gostGUI();
		
		setVisible(true);
	}
	
	//konstruktor za recepcionera
	public DodavanjeGostaFrame(Menadzer menadzer)
	{
		setTitle("Dodavanje gosta");
		setSize(375, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.kljuc = "";
		this.roditelj = null;

		gostGUI();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za dodavanje i izmenu gosta
	private void gostGUI()
    {
        //definisanje layout-a prozora
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][][][][]30[]");
        setLayout(layout);
        
        //velicina fonta
        float velicinaFonta = 15f;
        
        //definisanje komponenti
        //naslov
        JLabel naslov = new JLabel("Unesite podatke o gostu:");
        naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
        add(naslov, "span, align center");
        
        //ime
        JLabel ime = new JLabel("Ime:");
        ime.setFont(ime.getFont().deriveFont(velicinaFonta));
        add(ime);
        JTextField jIme = new JTextField(20);
        add(jIme);
        
        //prezime
        JLabel prezime = new JLabel("Prezime:");
        prezime.setFont(prezime.getFont().deriveFont(velicinaFonta));
        add(prezime);
        JTextField jPrezime = new JTextField(20);
        add(jPrezime);
        
        //korisnicko ime
  		JLabel labelaKorisnickoIme = new JLabel("Korisnicko ime:");
  		add(labelaKorisnickoIme);
  		labelaKorisnickoIme.setFont(labelaKorisnickoIme.getFont().deriveFont(velicinaFonta));
  		JTextField jKorisnickoIme = new JTextField(20);
  		add(jKorisnickoIme);
  		
  		//lozinka
  		JLabel labelaLozinka = new JLabel("Lozinka:");
  		add(labelaLozinka);
  		labelaLozinka.setFont(labelaLozinka.getFont().deriveFont(velicinaFonta));
  		JTextField jLozinka = new JTextField(20);
  		add(jLozinka);
        
		//pol
		JLabel labelaPol = new JLabel("Pol:");
		add(labelaPol);
		labelaPol.setFont(labelaPol.getFont().deriveFont(velicinaFonta));
		ButtonGroup polGrupa = new ButtonGroup();
		JRadioButton jPolM = new JRadioButton("Muski");
		jPolM.setSelected(true);
		JRadioButton jPolZ = new JRadioButton("Zenski");
		polGrupa.add(jPolM);
		polGrupa.add(jPolZ);
		add(jPolM, "split 2, align center");
		add(jPolZ);
		
		//datum rodjenja
		JLabel labelaDatumRodjenja = new JLabel("Datum rodjenja:");
		add(labelaDatumRodjenja);
		labelaDatumRodjenja.setFont(labelaDatumRodjenja.getFont().deriveFont(velicinaFonta));
		JTextField jDatumRodjenja = new JTextField(20);
		add(jDatumRodjenja);
		
		//telefon
		JLabel labelaTelefon = new JLabel("Telefon:");
		add(labelaTelefon);
		labelaTelefon.setFont(labelaTelefon.getFont().deriveFont(velicinaFonta));
		JTextField jTelefon = new JTextField(20);
		add(jTelefon);
		
		//adresa
		JLabel labelaAdresa = new JLabel("Ulica i broj:");
		add(labelaAdresa);
		labelaAdresa.setFont(labelaAdresa.getFont().deriveFont(velicinaFonta));
		JTextField jAdresa = new JTextField(20);
		add(jAdresa);
		
		//mesto
		JLabel labelaMesto = new JLabel("Mesto:");
		add(labelaMesto);
		labelaMesto.setFont(labelaMesto.getFont().deriveFont(velicinaFonta));
		JTextField jMesto = new JTextField(20);
		add(jMesto);
		
		//rezerivacije
		JLabel labelaRezervacije = new JLabel("Rezervacije:");
		add(labelaRezervacije);
		labelaRezervacije.setFont(labelaRezervacije.getFont().deriveFont(velicinaFonta));
		JTextField jRezervacije = new JTextField(20);
		add(jRezervacije);
		
		//dugme za potvrdu
		String rec="";
		if (kljuc.equals(""))
			rec = "Dodaj";
		else
			rec = "Izmeni";
		JButton dugmePotvrdi = new JButton(rec);
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta));
		add(dugmePotvrdi, "split 2, align center, span");
		
		//popunjavanje polja za izmenu
		if(!kljuc.equals(""))
		{
			Gost gost = menadzer.getMenadzerGostiju().getGosti().get(kljuc);
			jIme.setText(gost.getIme());
			jPrezime.setText(gost.getPrezime());
			jKorisnickoIme.setText(gost.getKorisnickoIme());
			jLozinka.setText(gost.getLozinka());
			if (gost.getPol().equals(Pol.MUSKI)) {
				jPolM.setSelected(true);
			} else {
				jPolZ.setSelected(true);
			}
			jDatumRodjenja.setText(gost.getDatumRodjenja().toString());
			jTelefon.setText(gost.getTelefon());
			String adresa = gost.getAdresa();
			String[] deloviAdrese = adresa.split(", ");
			jAdresa.setText(deloviAdrese[0]);
			jMesto.setText(deloviAdrese[1]);
			jRezervacije.setText(gost.rezervacijeString());
			
			jKorisnickoIme.setEditable(false);
		}
		
		//akcija na dugme za potvrdu
		dugmePotvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //provera da li su uneti podaci
				if (jIme.getText().equals("") || jPrezime.getText().equals("") || jKorisnickoIme.getText().equals("")
						|| jLozinka.getText().equals("") || jDatumRodjenja.getText().equals("")
						|| jTelefon.getText().equals("") || jAdresa.getText().equals("")
						|| jMesto.getText().equals("")) {
					// ako nisu, obavestavamo korisnika preko dijaloga
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke!", "Greska",
							JOptionPane.WARNING_MESSAGE);

					return;
				}
				
				//provera da li su uneti podaci ispravni
				String greske = "";
				//ime ne sme da sadrzi brojeve
				if (jIme.getText().matches(".*\\d.*")) {
					greske += "Ime ne sme da sadrzi brojeve!\n";
				}
				//prezime ne sme da sadrzi brojeve
				if (jPrezime.getText().matches(".*\\d.*")) {
					greske += "Prezime ne sme da sadrzi brojeve!\n";
				}

				if (kljuc.equals("")) {
                    //korisnicko ime ne sme da sadrzi razmake i ne sme da vec postoji
                    for (String korisnickoImeBazaString : menadzer.getMenadzerGostiju().getGosti().keySet()) {
                        if (korisnickoImeBazaString.equals(jKorisnickoIme.getText())) {
                            greske += "Korisnicko ime vec postoji!\n";
                        }
                    }
				}

				if (jKorisnickoIme.getText().contains(" ")) {
                    greske += "Korisnicko ime ne sme da sadrzi razmake!\n";
				}
				//lozinka ne sme da sadrzi razmake
				if (jLozinka.getText().contains(" ")) {
					greske += "Lozinka ne sme da sadrzi razmake!\n";
				}
				//datum rodjenja mora biti u formatu yyyy-MM-dd i osoba mora biti starija od 18 godina i mladja od 150 godina
				boolean dobarDatum = true;
				if (!jDatumRodjenja.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					greske += "Datum rodjenja mora biti u formatu yyyy-MM-dd!\n";
					dobarDatum = false;
				}
				if (dobarDatum) {
					LocalDate datumRodjenja = LocalDate.parse(jDatumRodjenja.getText());
					LocalDate sada = LocalDate.now();
					if (datumRodjenja.plusYears(18).isAfter(sada)) {
						greske += "Osoba mora biti starija od 18 godina!\n";
					}
					if (datumRodjenja.plusYears(150).isBefore(sada)) {
						greske += "Osoba ne moze biti starija od 150 godina!\n";
					}
				}
				//telefon mora da sadrzi samo brojeve
				if (!jTelefon.getText().matches("[0-9]+")) {
					greske += "Telefon mora da sadrzi samo brojeve!\n";
				}
				//mesto ne sme da sadrzi brojeve
				if (jMesto.getText().matches(".*\\d.*")) {
					greske += "Mesto ne sme da sadrzi brojeve!\n";
				}
				//rezervacije  moraju da budu u formatu "broj, broj, broj" ili prazno i moraju da postoje
				if (!jRezervacije.getText().equals("")) {
					boolean dobarFormat = true;
					if (!jRezervacije.getText().matches("\\d+(, \\d+)*")) {
						greske += "Rezervacije moraju biti u formatu \"broj, broj, broj\" ili prazno!\n";
						dobarFormat = false;
					}
					if (dobarFormat) {
						String[] rezervacijeNiz = jRezervacije.getText().split(", ");
						for (String sifra : rezervacijeNiz) {
                                Rezervacija rezervacija= menadzer.getMenadzerRezervacija().rezervacije.get(Integer.parseInt(sifra));
                            if(rezervacija==null) {
                                greske += "Rezervacija sa sifrom " + sifra + " ne postoji!\n";
                            }
						}
					}
				}
				
				//ako ima gresaka, obavestavamo korisnika
				if (!greske.equals("")) {
					JOptionPane.showMessageDialog(null, greske, "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//ako nema gresaka, dodajemo recepcionera ili ga menjamo
				String ime = jIme.getText();
				String prezime = jPrezime.getText();
				String korisnickoIme = jKorisnickoIme.getText();
				String lozinka = jLozinka.getText();
				Pol pol=null;
				if (jPolM.isSelected()) {
					pol = Pol.MUSKI;
				} else {
					pol = Pol.ZENSKI;
				}
				LocalDate datumRodjenja = LocalDate.parse(jDatumRodjenja.getText());
				String telefon = jTelefon.getText();
				String adresa = jAdresa.getText()+", "+jMesto.getText();
				ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
				if (!jRezervacije.getText().equals("")) {
                    String[] rezervacijeNiz = jRezervacije.getText().split(", ");
                    for (String sifra : rezervacijeNiz) {
                    	rezervacije.add(menadzer.getMenadzerRezervacija().rezervacije.get(Integer.parseInt(sifra)));
                    }
                }
				
				if(kljuc.equals(""))
				{
					menadzer.getMenadzerGostiju().dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka,new ArrayList<Rezervacija>());
					if(roditelj!=null)
						((CRUDFrame)roditelj).osveziTabelu();
				}
				else {
					menadzer.getMenadzerGostiju().izmenaGostiju(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
					((CRUDFrame) roditelj).osveziTabelu();
				}
				
				//obavestavamo korisnika o uspesnom dodavanju ili izmeni
				if(kljuc.equals(""))
                {
                 JOptionPane.showMessageDialog(null, "Uspesno ste dodali gosta!", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
                }
				else {
					JOptionPane.showMessageDialog(null, "Uspesno ste izmenili gosta!", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
			}
		 });
	}
}    