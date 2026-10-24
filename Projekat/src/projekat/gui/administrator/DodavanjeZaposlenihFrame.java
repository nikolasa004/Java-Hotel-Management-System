package projekat.gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.enumi.Pol;
import projekat.enumi.StrucnaSprema;
import projekat.korisnici.Administrator;
import projekat.korisnici.Recepcioner;
import projekat.korisnici.Sobarica;
import projekat.menadzeri.Menadzer;
import projekat.sobe.Soba;

public class DodavanjeZaposlenihFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private String kljucIzmena;
	private JFrame roditelj;

	public DodavanjeZaposlenihFrame(String tip,Menadzer menadzer,String korisnickoIme, JFrame roditelj) {
		setTitle("Dodavanje zaposlenih");
		if (tip.equals("sobarica") && !korisnickoIme.equals(""))
			setSize(375, 525);
		else
			setSize(375, 475);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.kljucIzmena = korisnickoIme;
		this.roditelj = roditelj;
		
		korisnikGUI(tip);
		
		setVisible(true);
	}
	
	//metoda koja dodaje recepcionera ili ga menja
	private void korisnikGUI(String tip)
	{
		
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][][][][][]30[]");
		if (tip.equals("sobarica") && !kljucIzmena.equals("")) {
			layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][][][][][][]30[]");
		}
		setLayout(layout);
		
		//velicina fonta
		float velicinaFonta = 15.0f;
		
		//definisanje komponenti
		//naslov
		String recString="";
		if(tip.equals("recepcioner"))
		{
			recString = "recepcioneru";
		}
		else if(tip.equals("administrator"))
		{
			recString = "administratoru";
		}
		else
		{
			recString = "sobarici";
		}
		JLabel naslov = new JLabel("Unesite podatke o "+recString+":");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
		add(naslov, "span, align center");
		
		//ime
		JLabel labelaIme = new JLabel("Ime:");
		add(labelaIme);
		labelaIme.setFont(labelaIme.getFont().deriveFont(velicinaFonta));
		JTextField jIme = new JTextField(20);
		add(jIme);
		
		//prezime
		JLabel labelaPrezime = new JLabel("Prezime:");
		add(labelaPrezime);
		labelaPrezime.setFont(labelaPrezime.getFont().deriveFont(velicinaFonta));
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
		
		//strucna sprema
		JLabel labelaStrucnaSprema = new JLabel("Strucna sprema:");
		add(labelaStrucnaSprema);
		labelaStrucnaSprema.setFont(labelaStrucnaSprema.getFont().deriveFont(velicinaFonta));
		String[] strucneSpreme = {"Osnovna", "Srednja", "Visoka", "Doktorska"};
		JComboBox<String> jStrucnaSprema = new JComboBox<String>(strucneSpreme);
		add(jStrucnaSprema, "align center");
		
		//staz
		JLabel labelaStaz = new JLabel("Staz:");
		add(labelaStaz);
		labelaStaz.setFont(labelaStaz.getFont().deriveFont(velicinaFonta));
		JTextField jStaz = new JTextField(20);
		add(jStaz);
		
		//ako je u pitanju izmena kod sobarice treba prikazati i njeni dodeljene sobe
		JLabel labelaDodeljeneSobe = new JLabel("Dodeljene sobe:");
		add(labelaDodeljeneSobe);
		String sobeString = "";
		
		if(tip.equals("sobarica") && !kljucIzmena.equals(""))
        {
			labelaDodeljeneSobe.setFont(labelaDodeljeneSobe.getFont().deriveFont(velicinaFonta));
			Sobarica sobarica1 = menadzer.getMenadzerSobarica().getSobarice().get(kljucIzmena);
			sobeString = sobarica1.sobeString();
        }
		JTextField jDodeljeneSobe = new JTextField(20);
		jDodeljeneSobe.setText(sobeString);
		add(jDodeljeneSobe);
		
		if (!tip.equals("sobarica") || kljucIzmena.equals("")) {
			remove(labelaDodeljeneSobe);
			remove(jDodeljeneSobe);
		}
		
		//dugme za potvrdu
		String recString2="";
		if(kljucIzmena.equals(""))
			recString2="Dodaj";
		else
            recString2="Izmeni";
		JButton dugmePotvrdi = new JButton(recString2);
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta*1.5f));
		add(dugmePotvrdi, "split 2, align center, span");
		
		//popunjavanje polja ako se radi o izmeni
		if(!kljucIzmena.equals(""))
		{
			if(tip.equals("recepcioner"))
			{
				Recepcioner recepcioner = menadzer.getMenadzerRecepcionera().getRecepcioneri().get(kljucIzmena);
				jIme.setText(recepcioner.getIme());
				jPrezime.setText(recepcioner.getPrezime());
				jKorisnickoIme.setText(recepcioner.getKorisnickoIme());
				jLozinka.setText(recepcioner.getLozinka());
				if (recepcioner.getPol().equals(Pol.MUSKI)) {
					jPolM.setSelected(true);
				} else {
					jPolZ.setSelected(true);
				}
				jDatumRodjenja.setText(recepcioner.getDatumRodjenja().toString());
				jTelefon.setText(recepcioner.getTelefon());
				String adresa = recepcioner.getAdresa();
				String[] deloviAdrese = adresa.split(", ");
				jAdresa.setText(deloviAdrese[0]);
				jMesto.setText(deloviAdrese[1]);
				String sprema = recepcioner.getStrucnaSprema().toString().toLowerCase();
				sprema = sprema.substring(0, 1).toUpperCase() + sprema.substring(1);
				jStrucnaSprema.setSelectedItem(sprema);
				jStaz.setText(Integer.toString(recepcioner.getStaz()));
			}
			else if(tip.equals("administrator"))
            {
                Administrator administrator = menadzer.getMenadzerAdministratora().getAdministratori().get(kljucIzmena);
                jIme.setText(administrator.getIme());
                jPrezime.setText(administrator.getPrezime());
                jKorisnickoIme.setText(administrator.getKorisnickoIme());
                jLozinka.setText(administrator.getLozinka());
				if (administrator.getPol().equals(Pol.MUSKI)) {
					jPolM.setSelected(true);
				} else {
					jPolZ.setSelected(true);
				}
				jDatumRodjenja.setText(administrator.getDatumRodjenja().toString());
				jTelefon.setText(administrator.getTelefon());
				String adresa = administrator.getAdresa();
				String[] deloviAdrese = adresa.split(", ");
				jAdresa.setText(deloviAdrese[0]);
				jMesto.setText(deloviAdrese[1]);
				String sprema = administrator.getStrucnaSprema().toString().toLowerCase();
				sprema = sprema.substring(0, 1).toUpperCase() + sprema.substring(1);
				jStrucnaSprema.setSelectedItem(sprema);
				jStaz.setText(Integer.toString(administrator.getStaz()));
            }
			else
			{
				Sobarica sobarica = menadzer.getMenadzerSobarica().getSobarice().get(kljucIzmena);
				jIme.setText(sobarica.getIme());
				jPrezime.setText(sobarica.getPrezime());
				jKorisnickoIme.setText(sobarica.getKorisnickoIme());
				jLozinka.setText(sobarica.getLozinka());
				if (sobarica.getPol().equals(Pol.MUSKI)) {
					jPolM.setSelected(true);
				} else {
					jPolZ.setSelected(true);
				}
				jDatumRodjenja.setText(sobarica.getDatumRodjenja().toString());
				jTelefon.setText(sobarica.getTelefon());
				String adresa = sobarica.getAdresa();
				String[] deloviAdrese = adresa.split(", ");
				jAdresa.setText(deloviAdrese[0]);
				jMesto.setText(deloviAdrese[1]);
				String sprema = sobarica.getStrucnaSprema().toString().toLowerCase();
				sprema = sprema.substring(0, 1).toUpperCase() + sprema.substring(1);
				jStrucnaSprema.setSelectedItem(sprema);
				jStaz.setText(Integer.toString(sobarica.getStaz()));
				jDodeljeneSobe.setText(sobarica.sobeString());
			}
			
			jKorisnickoIme.setEditable(false);
		}
		
		dugmePotvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//provera da li su uneti podaci
				if (jIme.getText().equals("") || jPrezime.getText().equals("") || jKorisnickoIme.getText().equals("")
						|| jLozinka.getText().equals("") || jDatumRodjenja.getText().equals("")
						|| jTelefon.getText().equals("") || jAdresa.getText().equals("") || jMesto.getText().equals("")
						|| jStaz.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke!", "Greska", JOptionPane.WARNING_MESSAGE);
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
				if(tip.equals("recepcioner"))
                {
					if (kljucIzmena.equals("")) {
	                    //korisnicko ime ne sme da sadrzi razmake i ne sme da vec postoji
	                    for (String korisnickoImeBazaString : menadzer.getMenadzerRecepcionera().getRecepcioneri().keySet()) {
	                        if (korisnickoImeBazaString.equals(jKorisnickoIme.getText())) {
	                            greske += "Korisnicko ime vec postoji!\n";
	                        }
	                    }
					}
                }
				else if(tip.equals("administrator"))
                {	
					//ako je izmena onda ne sme da se promeni korisnicko ime
					if(kljucIzmena.equals(""))
					{
	                    for (String korisnickoImeBazaString : menadzer.getMenadzerAdministratora().getAdministratori().keySet()) {
	                        if (korisnickoImeBazaString.equals(jKorisnickoIme.getText())) {
	                            greske += "Korisnicko ime vec postoji!\n";
	                        }
	                    }
					}
                }
                else
                {
                	if (kljucIzmena.equals("")) {
	                    for (String korisnickoImeBazaString : menadzer.getMenadzerSobarica().getSobarice().keySet()) {
	                        if (korisnickoImeBazaString.equals(jKorisnickoIme.getText())) {
	                            greske += "Korisnicko ime vec postoji!\n";
	                        }
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
				//datum rodjenja mora biti u formatu yyyy-MM-dd i osoba mora biti starija od 18 godina i mladja od 150 jer je to realno
				boolean dobarDatum = true;
				if (!jDatumRodjenja.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					greske += "Datum rodjenja mora biti u formatu yyyy-MM-dd!\n";
					dobarDatum = false;
				}
				if (dobarDatum) {
					LocalDate datumRodjenja = LocalDate.parse(jDatumRodjenja.getText());
					LocalDate danas = LocalDate.now();
					if (danas.minusYears(18).isBefore(datumRodjenja)) {
						greske += "Osoba mora biti starija od 18 godina!\n";
					}
					if (danas.minusYears(150).isAfter(datumRodjenja)) {
						greske += "Osoba ne sme biti starija od 150 godina!\n";
					}
				}
				//telefon mora da sadrzi samo brojeve
				if (!jTelefon.getText().matches("[0-9]+")) {
					greske += "Telefon mora da sadrzi samo brojeve!\n";
				}
				//staz mora da sadrzi samo brojeve
				if (!jStaz.getText().matches("[0-9]+")) {
					greske += "Staz mora da sadrzi samo brojeve!\n";
				}
				//mesto ne sme da sadrzi brojeve
				if (jMesto.getText().matches(".*\\d.*")) {
					greske += "Mesto ne sme da sadrzi brojeve!\n";
				}
				
				//ako je u pitanju izmena kod sobarice proveravamo i dodeljene sobe gde moraju da budu u formatu "broj, broj, broj" ili prazno i te sobe moraju da postoje
				if (tip.equals("sobarica") && !kljucIzmena.equals("")) {
					String dodeljeneSobe = jDodeljeneSobe.getText();
					String[] sobe = dodeljeneSobe.split(", ");
					boolean dobarFormat = true;
					for (String soba : sobe) {
						if (!soba.matches("[0-9]+") && !soba.equals("")) {
							greske += "Dodeljene sobe moraju biti u formatu \"broj, broj, broj\"!\n";
							dobarFormat = false;
							break;
						}
					}
					if(dobarFormat)
					{
						for (String soba : sobe) {
								Soba soba2=menadzer.getMenadzerSoba().sobe.get(Integer.parseInt(soba));
							if(soba2==null) {
								greske += "Soba sa brojem "+soba+" ne postoji!\n";
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
				int staz = Integer.parseInt(jStaz.getText());
				StrucnaSprema strucnaSprema = StrucnaSprema.valueOf(jStrucnaSprema.getSelectedItem().toString().toUpperCase());
				ArrayList<Soba> dodeljeneSobe = new ArrayList<Soba>();
				if (tip.equals("sobarica") && !kljucIzmena.equals("")) {
					String dodeljeneSobeString = jDodeljeneSobe.getText();
					String[] dodeljeneSobeNiz = dodeljeneSobeString.split(", ");
					for (String dodeljenaSoba : dodeljeneSobeNiz) {
						dodeljeneSobe.add(menadzer.getMenadzerSoba().sobe.get(Integer.parseInt(dodeljenaSoba)));
					}
				}
				
				String rec="";
				if(tip.equals("recepcioner"))
				{
					if(kljucIzmena.equals(""))
					{
						menadzer.getMenadzerRecepcionera().dodavanjeRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
						((CRUDFrame)roditelj).osveziTabelu();
					}
					else {
						menadzer.getMenadzerRecepcionera().izmenaRecepcionera(ime, prezime, pol, datumRodjenja, telefon,adresa, korisnickoIme, lozinka, strucnaSprema, staz);
						((CRUDFrame) roditelj).osveziTabelu();
					}
					rec="recepcionera";
				}
				else if (tip.equals("administrator")) {
					if (kljucIzmena.equals("")) {
						menadzer.getMenadzerAdministratora().dodavanjeAdministratora(ime, prezime, pol, datumRodjenja,telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
						//refresh tabele
						((CRUDFrame)roditelj).osveziTabelu();
					} else {
						menadzer.getMenadzerAdministratora().izmenaAdministratora(ime, prezime, pol, datumRodjenja,telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
						// refresh tabele
						((CRUDFrame) roditelj).osveziTabelu();
					}
					rec = "administratora";
				}
				else
                {
					if (kljucIzmena.equals("")) {
						menadzer.getMenadzerSobarica().dodavanjeSobarica(ime, prezime, pol, datumRodjenja, telefon,
								adresa, korisnickoIme, lozinka, strucnaSprema, staz, new ArrayList<Soba>());
						((CRUDFrame)roditelj).osveziTabelu();
					} else {
						menadzer.getMenadzerSobarica().izmenaSobarica(ime, prezime, pol, datumRodjenja, telefon, adresa,
								korisnickoIme, lozinka, strucnaSprema, staz,dodeljeneSobe);
						((CRUDFrame)roditelj).osveziTabelu();
					}
                    rec="sobaricu";
                }
				
				//obavestavamo korisnika o uspesnom dodavanju ili izmeni
				if(kljucIzmena.equals(""))
                {
                 JOptionPane.showMessageDialog(null, "Uspesno ste dodali "+rec+"!", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
                }
				else {
					JOptionPane.showMessageDialog(null, "Uspesno ste izmenili "+rec+"!", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
			}
		});
	}
}
