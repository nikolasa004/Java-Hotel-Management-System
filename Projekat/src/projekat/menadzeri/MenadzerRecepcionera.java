package projekat.menadzeri;

import projekat.enumi.Pol;
import projekat.enumi.StatusRezervacije;
import projekat.enumi.StatusSobe;
import projekat.enumi.StrucnaSprema;
import projekat.gui.administrator.DodavanjeGostaFrame;
import projekat.gui.recepcioner.DnevniIzvestajFrame;
import projekat.korisnici.Recepcioner;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenadzerRecepcionera {
	// mapa svih recepcionera, kljuc je korisnicko ime
	private HashMap<String, Recepcioner> recepcioneri = new HashMap<String, Recepcioner>();
	
	//geter
	public HashMap<String, Recepcioner> getRecepcioneri() {
		return recepcioneri;
	}
	
	//pregled rezervacija gostiju, ima mogucnost filtriranja po tipu sobe, ceni, dodatnim uslugama, po sobama koje zadovoljavaju rezervaciju
	public void pregledRezervacija(ArrayList<Rezervacija> rezervacije ,Menadzer menadzer)
	{
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if(rezervacija.getStatusRezervacije()==StatusRezervacije.NA_CEKANJU)
                rezervacije.add(rezervacija);
		}
	}
	
	//pregled soba
	public void pregledSoba(Menadzer menadzer, ArrayList<Soba> sobe, String tipSobe)
	{
		for (Soba soba : menadzer.getMenadzerSoba().sobe.values()) {
			if (soba.getStatusSobe() == StatusSobe.SLOBODNA && soba.getTipSobe().getTip().equals(tipSobe)) {
				sobe.add(soba);
			}
		}
	}
	
	//check-in gosta, mogu i da se dodaju neke dodatne usluge i da se onda promeni cena
	public void checkIn(Menadzer menadzer, int idRezervacije, int idSobe)
	{
		//promena statusa sobe
		menadzer.getMenadzerSoba().sobe.get(idSobe).setStatusSobe(StatusSobe.ZAUZETA);
		menadzer.getMenadzerSoba().pisanjeSoba();
		
		//dodavanje sobe rezervaciji
		menadzer.getMenadzerRezervacija().rezervacije.get(idRezervacije).setSoba(menadzer.getMenadzerSoba().sobe.get(idSobe));
		menadzer.getMenadzerRezervacija().pisanjeRezervacija();
	}
	
	//check-out gosta i dodela sobe odgovarajucoj sobarici
	public void checkOut(Menadzer menadzer,int brojSobe)
	{
		Soba soba=menadzer.getMenadzerSoba().sobe.get(brojSobe);
		soba.setStatusSobe(StatusSobe.SPREMANJE);
		menadzer.getMenadzerSoba().pisanjeSoba();
		menadzer.getMenadzerSobarica().dodelaSobe(soba);
	}
	
	//dodavanje gosta u sistem gde je email korisnicko ime a lozinka je broj pasoša
	public void dodavanjeGosta(Menadzer menadzer)
	{
		new DodavanjeGostaFrame(menadzer);
	}
	
	//metoda za izvestaj gostiju
	public void izvestajGostiju(Menadzer menadzer,ArrayList<Rezervacija> rezervacije)
	{
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			LocalDate datum=LocalDate.now();
			LocalDate datumDo=rezervacija.getDatumRezervacije().plusDays(rezervacija.getBrojNocenja());
			if (rezervacija.getStatusRezervacije() == StatusRezervacije.POTVRDJENA && rezervacija.getSoba().getBrojSobe() != -1 && rezervacija.getSoba().getStatusSobe() == StatusSobe.ZAUZETA
					&& (datum.isAfter(rezervacija.getDatumRezervacije()) || datum.equals(rezervacija.getDatumRezervacije())) && (datum.isBefore(datumDo) || datum.equals(datumDo)))
				rezervacije.add(rezervacija);
		}
	}
	
	//pregled dnevenog izvestaja(broj dolazaka i odlazaka u toku dana kao i ukupna zauzetost hotela)
	public void dnevniIzvestaj(Menadzer menadzer)
	{
		new DnevniIzvestajFrame(menadzer);
	}
	
	//metoda koja pronalazi sve dolaske i odlaske u toku dana
	public void izvestajDolasciOdlasci(Menadzer menadzer, ArrayList<Rezervacija> rezervacijeLeve, ArrayList<Rezervacija> rezervacijeDesne)
	{
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if (rezervacija.getDatumRezervacije().equals(LocalDate.now()) && rezervacija.getStatusRezervacije()==StatusRezervacije.POTVRDJENA && rezervacija.getSoba().getBrojSobe()==-1)
				rezervacijeLeve.add(rezervacija);
		}
		
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			LocalDate datumOdlaska = rezervacija.getDatumRezervacije().plusDays(rezervacija.getBrojNocenja());
			if (datumOdlaska.equals(LocalDate.now())
					&& rezervacija.getStatusRezervacije() == StatusRezervacije.POTVRDJENA && rezervacija.getSoba().getBrojSobe()!=-1 && rezervacija.getSoba().getStatusSobe()==StatusSobe.ZAUZETA)
				rezervacijeDesne.add(rezervacija);
		}
	}
	
	
	//metoda koja proverava kod recepcionera da li je rezervacija koju zeli da potvrdi validna, da li je soba slobodna u tom periodu
	public int proveraZauzetosti(Menadzer menadzer, Rezervacija rezervacija, ArrayList<Soba> slobodneSobe)
	{
		//pri proveri da li postoji soba koja odgovara tipu rezervacije i slobodna je za taj datum
		//prvo se prolazi kroz sve sobe i kupe one koje odgovaraju tipu
		//onda se prolazi kroz potvrdjene rezervacije za taj period i racuna koliko soba je zauzeto
		//ako je broj zauzetih soba manji od broja soba koje odgovaraju tipu rezervacije, rezervacija se potvrdjuje
		//ima 3 nacina poklapanja datuma, preklapanje sa pocetkom, preklapanje sa krajem i preklapanje sa celim periodom
    	for (Soba soba: menadzer.getMenadzerSoba().sobe.values()) {
    		if(soba.getTipSobe()==rezervacija.getTipSobe())
    		{
    			slobodneSobe.add(soba);
    		}
    	}
    	int brojZauzetihSoba=0;
    	LocalDate datumDo=rezervacija.getDatumRezervacije().plusDays(rezervacija.getBrojNocenja());
		for (Rezervacija rezervacijaDatum : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if (rezervacijaDatum.getStatusRezervacije() == StatusRezervacije.POTVRDJENA) {
				LocalDate datumDoDatum=rezervacijaDatum.getDatumRezervacije().plusDays(rezervacijaDatum.getBrojNocenja());
				if ((rezervacijaDatum.getDatumRezervacije().isBefore(rezervacija.getDatumRezervacije())
						&& datumDoDatum.isAfter(datumDo)) || (rezervacijaDatum.getDatumRezervacije().isAfter(rezervacija.getDatumRezervacije())
                                && datumDoDatum.isBefore(datumDo)) || (rezervacijaDatum.getDatumRezervacije().isBefore(rezervacija.getDatumRezervacije())
                                        && datumDoDatum.isAfter(rezervacija.getDatumRezervacije())) || (rezervacijaDatum.getDatumRezervacije().isBefore(datumDo)
                                                && datumDoDatum.isAfter(datumDo))||rezervacijaDatum.getDatumRezervacije().equals(rezervacija.getDatumRezervacije())||datumDoDatum.equals(datumDo)) {
					if (rezervacijaDatum.getTipSobe() == rezervacija.getTipSobe())
					{
						//u specijalnom slucaju bi se moglo desiti da je neko rezervisao za taj datum dosao ali otisao ranije
						//tada je soba u rezevrvaciji drugacija od -1 a slobodna
						if (rezervacijaDatum.getSoba().getBrojSobe() != -1 && rezervacijaDatum.getSoba().getStatusSobe() == StatusSobe.SLOBODNA)
						{
							continue;
						}
						
						brojZauzetihSoba++;
					}
				}
			}
		}
		
		return brojZauzetihSoba;
	}
	
	//metoda za odbijanje rezervacije
	public void odbijanjeRezervacije(Menadzer menadzer, int id) {
		Rezervacija rezervacija = menadzer.getMenadzerRezervacija().rezervacije.get(id);
		rezervacija.setStatusRezervacije(StatusRezervacije.ODBIJENA);
		rezervacija.setCena(0);
		menadzer.getMenadzerRezervacija().pisanjeRezervacija();
	}

	// ucitavanje recepcionera iz fajla
	public void ucitavanjeRecepcionera()
	{
		String pathString="datoteke"+File.separator+"Recepcioneri.txt";
		Path path=Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);
			for (String linija : linije) {
				String[] delovi = linija.split("\\|");
				
				String ime=delovi[0];
				String prezime=delovi[1];
				Pol pol=Pol.valueOf(delovi[2]);
				LocalDate datumRodjenja=LocalDate.parse(delovi[3]);
				String telefon=delovi[4];
				String adresa=delovi[5];
				String korisnickoIme=delovi[6];
				String lozinka=delovi[7];
				StrucnaSprema strucnaSprema=StrucnaSprema.valueOf(delovi[8]);
				int staz=Integer.parseInt(delovi[9]);
				Recepcioner recepcioner=new Recepcioner(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
				recepcioneri.put(korisnickoIme, recepcioner);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// upisivanje recepcionera u fajl
	public void pisanjeRecepcionera()
	{
		String pathString="datoteke"+File.separator+"Recepcioneri.txt";
		Path path = Path.of(pathString);
		
		if (recepcioneri.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi = true;
		for (Recepcioner recepcioner : recepcioneri.values()) {
			try {
				if (prvi) {
					Files.writeString(path, recepcioner.upis()+"\n");
					prvi = false;
				} else {
					Files.writeString(path, recepcioner.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud operacije za recepcionere
	public void dodavanjeRecepcionera(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz)
	{
		Recepcioner recepcioner=new Recepcioner(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
		recepcioneri.put(korisnickoIme, recepcioner);
		pisanjeRecepcionera();
	}
	public void pregledRecepcionera()
	{
		for (Recepcioner recepcioner : recepcioneri.values()) {
			System.out.println(recepcioner);
		}
	}
	public void izmenaRecepcionera(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz)
	{
		Recepcioner recepcioner = recepcioneri.get(korisnickoIme);
		recepcioner.setIme(ime);
		recepcioner.setPrezime(prezime);
		recepcioner.setPol(pol);
		recepcioner.setDatumRodjenja(datumRodjenja);
		recepcioner.setTelefon(telefon);
		recepcioner.setAdresa(adresa);
		recepcioner.setLozinka(lozinka);
		recepcioner.setStrucnaSprema(strucnaSprema);
		recepcioner.setStaz(staz);
		recepcioner.setPlata();
		pisanjeRecepcionera();
	}
	public void brisanjeRecepcionera(String korisnickoIme)
	{
		recepcioneri.remove(korisnickoIme);
		pisanjeRecepcionera();
	}
}
