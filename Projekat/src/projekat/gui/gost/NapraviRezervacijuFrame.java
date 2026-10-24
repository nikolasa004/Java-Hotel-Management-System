package projekat.gui.gost;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.enumi.StatusRezervacije;
import projekat.enumi.StatusSobe;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.usluge.Cenovnik;
import projekat.usluge.DodatnaUsluga;

//ovaj prozor se koristi za kreiranje rezervacije kod gosta
public class NapraviRezervacijuFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private String korisnickoIme;

	public NapraviRezervacijuFrame(Menadzer menadzer, String korisnickoIme) {
		setTitle("Napravi rezervaciju");
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.korisnickoIme = korisnickoIme;
		
		rezervacijaGUI();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za rezervaciju
	private void rezervacijaGUI() {
		MigLayout layout;
		if(korisnickoIme.equals(""))
		{
			layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][][][]30[]");
		}
		else {
			layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][][]30[]");
		}
		setLayout(layout);
		
		//velicina fonta
		float velicinaFonta = 15f;
		
		//komponente
		//naslov
		JLabel naslov = new JLabel("Unesite podatke za rezervaciju:");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
        add(naslov, "span, align center");
        
        //datum rezervacije
	    JLabel datumLabela = new JLabel("Datum rezervacije:");
	    datumLabela.setFont(datumLabela.getFont().deriveFont(velicinaFonta));
	    add(datumLabela);
	    JTextField datumPolje = new JTextField(20);
	    add(datumPolje);
	    
	    //broj nocenja
        JLabel nocenjaLabela = new JLabel("Broj nocenja:");
        nocenjaLabela.setFont(nocenjaLabela.getFont().deriveFont(velicinaFonta));
        add(nocenjaLabela);
        JTextField nocenjaPolje = new JTextField(20);
        add(nocenjaPolje);
        
        //tip  sobe
        JLabel tipLabela = new JLabel("Tip sobe:");
        tipLabela.setFont(tipLabela.getFont().deriveFont(velicinaFonta));
        add(tipLabela);
        String[] tipovi=menadzer.getMenadzerTipovaSoba().tipoviSoba.keySet().toArray(new String[0]);
        JComboBox<String> tipPolje = new JComboBox<String>(tipovi);
        add(tipPolje,"align center");
        
        //broj osoba
        JLabel osobaLabela = new JLabel("Broj osoba:");
        osobaLabela.setFont(osobaLabela.getFont().deriveFont(velicinaFonta));
        add(osobaLabela);
        JTextField osobaPolje = new JTextField(20);
        add(osobaPolje);
        
        //dodatne usluge
        JLabel uslugeLabela = new JLabel("Dodatne usluge:");
        uslugeLabela.setFont(uslugeLabela.getFont().deriveFont(velicinaFonta));
        add(uslugeLabela,"wrap");
        HashMap<String, JCheckBox> usluge = new HashMap<String, JCheckBox>();
        JPanel panelUsluga = new JPanel();
        panelUsluga.setLayout(new MigLayout("wrap 1", "[]", "[]"));
		for (String usluga : menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.keySet())
		{
			JCheckBox uslugaPolje = new JCheckBox(usluga);
			usluge.put(usluga, uslugaPolje);
			panelUsluga.add(uslugaPolje);
		}
		JScrollPane scrollPane = new JScrollPane(panelUsluga);
		scrollPane.setPreferredSize(new Dimension(300, 200));
		add(scrollPane, "span");
		
		//dodaci vezani za sobe
		JLabel osobineSobe = new JLabel("Osobine sobe:");
		osobineSobe.setFont(osobineSobe.getFont().deriveFont(velicinaFonta));
		add(osobineSobe, "span");
		HashMap<String, JCheckBox> osobine = new HashMap<String, JCheckBox>();
		JPanel panelOsobina = new JPanel();
		panelOsobina.setLayout(new MigLayout("wrap 1", "[]", "[]"));
		JCheckBox klima = new JCheckBox("Klima");
		osobine.put("Klima", klima);
		panelOsobina.add(klima);
		JCheckBox tv = new JCheckBox("TV");
		osobine.put("TV", tv);
		panelOsobina.add(tv);
		JCheckBox balkon = new JCheckBox("Balkon");
		osobine.put("Balkon", balkon);
		panelOsobina.add(balkon);
		JCheckBox pusacka = new JCheckBox("Pusacka");
		osobine.put("Pusacka", pusacka);
		panelOsobina.add(pusacka);
		JScrollPane scrollPaneOsobine = new JScrollPane(panelOsobina);
		scrollPaneOsobine.setPreferredSize(new Dimension(300, 200));
		add(scrollPaneOsobine, "span");
		
		//polje za unos imena gosta ako pravi recepcioner
		JLabel imeLabela;
		JTextField imePolje;
		imeLabela = new JLabel("Gost:");
		imeLabela.setFont(imeLabela.getFont().deriveFont(velicinaFonta));
		imePolje = new JTextField(20);
		if (korisnickoIme.equals("")) {
			add(imeLabela);
			add(imePolje);
		}
		
		//dugme za potvrdu
		JButton dugmePotvrdi = new JButton("Potvrdi");
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta));
		add(dugmePotvrdi, "span, align center");
		
		//dodavanje akcije na dugme
		dugmePotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//provera da li je sve uneto
				if (datumPolje.getText().equals("") || nocenjaPolje.getText().equals("")
						|| tipPolje.getSelectedItem().toString().equals("") || osobaPolje.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke!", "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//provera ispravnosti unetih podataka
				String greske = "";
				
				//datum mora biti u formatu yyyy-MM-d i mora biti u buducnosti
				String datum = datumPolje.getText();
				boolean ispravanDatum = true;
				if (!datum.matches("\\d{4}-\\d{2}-\\d{2}")) {
					greske += "Datum mora biti u formatu yyyy-MM-dd.\n";
					ispravanDatum = false;
				}
				if (ispravanDatum) {
					LocalDate trenutniDatum = LocalDate.now();
					LocalDate unetiDatum = LocalDate.parse(datum);
					if (unetiDatum.isBefore(trenutniDatum)) {
						greske += "Datum mora biti u buducnosti.\n";
					}
				}
				
				//broj nocenja mora biti broj i mora biti pozitivan
				int nocenja = -1;
				boolean ispravanBrojNocenja = true;
				try {
					nocenja = Integer.parseInt(nocenjaPolje.getText());
					if (nocenja <= 0) {
						greske += "Broj nocenja mora biti pozitivan broj.\n";
						ispravanBrojNocenja = false;
					}
				} catch (NumberFormatException e1) {
					greske += "Broj nocenja mora biti broj.\n";
					ispravanBrojNocenja = false;
				}
				
				//broj osoba mora biti broj
				int osoba = -1;
				try {
					osoba = Integer.parseInt(osobaPolje.getText());
				} catch (NumberFormatException e1) {
					greske += "Broj osoba mora biti broj.\n";
				}
				
				//pravljene napomene
				String napomena = "";
				for (String osobina : osobine.keySet()) {
					if (osobine.get(osobina).isSelected()) {
						napomena += osobina + ", ";
					}
				}
				if (napomena.length() > 0)
					napomena = napomena.substring(0, napomena.length() - 2);
				
				//tip sobe mora biti slobodan u tom periodu i mora da zadovoljava napomene
				TipSobe tipSobe=menadzer.getMenadzerTipovaSoba().tipoviSoba.get(tipPolje.getSelectedItem().toString());
				if(ispravanDatum && ispravanBrojNocenja)
				{
					ArrayList<Soba> slobodneSobe = new ArrayList<Soba>();
	            	for (Soba soba: menadzer.getMenadzerSoba().sobe.values()) {
	            		if(soba.getTipSobe()==tipSobe)
	            		{
							if (klima.isSelected() && !soba.isKlima_uredjaj())
								continue;
							if (tv.isSelected() && !soba.isTv())
								continue;
							if (balkon.isSelected() && !soba.isBalkon())
								continue;
							if (pusacka.isSelected() && !soba.isPusacka())
								continue;
							
	            			slobodneSobe.add(soba);
	            		}
	            	}
	            	int brojZauzetihSoba=0;
	            	LocalDate datumOd=LocalDate.parse(datum);
	            	LocalDate datumDo=LocalDate.parse(datum).plusDays(nocenja);
					for (Rezervacija rezervacijaDatum : menadzer.getMenadzerRezervacija().rezervacije.values()) {
						if (rezervacijaDatum.getStatusRezervacije() == StatusRezervacije.POTVRDJENA) {
							LocalDate datumDoDatum=rezervacijaDatum.getDatumRezervacije().plusDays(rezervacijaDatum.getBrojNocenja());
							if ((rezervacijaDatum.getDatumRezervacije().isBefore(datumOd)
									&& datumDoDatum.isAfter(datumDo)) || (rezervacijaDatum.getDatumRezervacije().isAfter(datumOd)
                                            && datumDoDatum.isBefore(datumDo)) || (rezervacijaDatum.getDatumRezervacije().isBefore(datumOd)
                                                    && datumDoDatum.isAfter(datumOd)) || (rezervacijaDatum.getDatumRezervacije().isBefore(datumDo)
											&& datumDoDatum.isAfter(datumDo))
									|| (rezervacijaDatum.getDatumRezervacije().isEqual(datumOd)
											|| datumDoDatum.isEqual(datumDo))) {
								if (rezervacijaDatum.getTipSobe() == tipSobe) {
									//u specijalnom slucaju bi se moglo desiti da je neko rezervisao za taj datum dosao ali otisao ranije
									//tada je soba u rezevrvaciji drugacija od -1 a slobodna
									if (rezervacijaDatum.getSoba().getBrojSobe() != -1 && rezervacijaDatum.getSoba().getStatusSobe() == StatusSobe.SLOBODNA)
										continue;
									brojZauzetihSoba++;
									break;
								}
							}
						}
					}
					if(brojZauzetihSoba>=slobodneSobe.size())
					{
						JOptionPane.showMessageDialog(null, "Nema slobodnih soba ovog tipa za izabran datum, pokusajte ponovo!", "Greska",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				
				//provera da nije uneto vise osoba nego sto soba moze da primi
				if(osoba!=-1)
				{
					String tipString=tipPolje.getSelectedItem().toString();
					String[] delovi=tipString.split("\\+");
					int brojKreveta=0;
					for (int i = 0; i < delovi.length; i++) {
						brojKreveta += Integer.parseInt(delovi[i]);
					}
					if (osoba > brojKreveta) {
						greske += "Soba tog tipa ne moze da primi toliko osoba\n";
					}
				}
				
				//ako pravi recepcioner, provera da li je uneto ime i da li postoji gost sa tim imenom
				if (korisnickoIme.equals("")) {
					String ime = imePolje.getText();
					if (ime.equals("")) {
						greske += "Niste uneli ime gosta.\n";
					}
					if (!menadzer.getMenadzerGostiju().getGosti().containsKey(ime)) {
						greske += "Ne postoji gost sa tim imenom.\n";
					}
				}
				
				//ako ima gresaka, obavesti korisnika
				if (!greske.equals("")) {
					JOptionPane.showMessageDialog(null, greske, "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (korisnickoIme.equals("")) {
					korisnickoIme = imePolje.getText();
				}
				
				//dodavanje rezervaacije
				int id=0;
				for (int i : menadzer.getMenadzerRezervacija().rezervacije.keySet()) {
					if (i > id) {
						id = i;
					}
				}
				id++;
				StatusRezervacije status=StatusRezervacije.NA_CEKANJU;
			    LocalDate datumRezervacije = LocalDate.parse(datum);
			    ArrayList<DodatnaUsluga> dodatneUsluge = new ArrayList<DodatnaUsluga>();
				for (String usluga : usluge.keySet()) {
					if (usluge.get(usluga).isSelected()) {
						dodatneUsluge.add(menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.get(usluga));
					}
				}
				String imeRezervacije=korisnickoIme;
				double cena=0;
				Cenovnik cenovnik=menadzer.getMenadzerCenovnika().pronalazakCenovnika(datumRezervacije);
				if (cenovnik == null) {
					JOptionPane.showMessageDialog(null, "Nema cenovnika za uneti datum.", "Greska",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				LocalDate datumNocenja=LocalDate.parse(datum);
				while (datumNocenja.isBefore(LocalDate.parse(datum).plusDays(nocenja))) {
					if (datumNocenja.isAfter(cenovnik.getDatumOd()) && datumNocenja.isBefore(cenovnik.getDatumDo())) {
						cena += cenovnik.getCeneSoba().get(tipSobe);
						for (DodatnaUsluga usluga : dodatneUsluge) {
							cena += cenovnik.getCeneUsluga().get(usluga)*osoba;
						}
						datumNocenja = datumNocenja.plusDays(1);
					}
					else {
						cenovnik = menadzer.getMenadzerCenovnika().pronalazakCenovnika(datumNocenja);
						if (cenovnik == null) {
							JOptionPane.showMessageDialog(null, "Nema cenovnika za uneti datum.", "Greska",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
				
				int opcija = JOptionPane.showConfirmDialog(null, "Cena rezervacije je " + cena + ". Da li ste sigurni da zelite da napravite rezervaciju?", "Potvrda", JOptionPane.YES_NO_OPTION);
				if (opcija == JOptionPane.YES_OPTION) {
					menadzer.getMenadzerRezervacija().dodavanjeRezervacija(id,status,datumRezervacije,tipSobe,osoba,nocenja, dodatneUsluge,cena ,imeRezervacije,napomena);
					menadzer.getMenadzerGostiju().napraviRezervaciju(korisnickoIme, id,menadzer.getMenadzerRezervacija());
					
					JOptionPane.showMessageDialog(null, "Uspesno ste napravili rezervaciju.", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		});
	}
}
