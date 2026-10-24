package projekat.menadzeri;

import projekat.rezervacije.Rezervacija;
import projekat.enumi.Pol;
import projekat.enumi.StatusRezervacije;
import projekat.gui.gost.PregledRezervacijaFrame;
import projekat.korisnici.Gost;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenadzerGostiju {
	// mapa svih gostiju, kljuc je korisnicko ime
	private HashMap<String, Gost> gosti = new HashMap<String, Gost>();
	
	//geter
	public HashMap<String, Gost> getGosti() {
		return gosti;
	}
	
	//metoda za dodavanje rezervacije u listu rezervacija gosta
	public void napraviRezervaciju(String korisnickoImeGosta, int sifraRezervacije,MenadzerRezervacija menadzerRezervacija) {
		Gost gost = gosti.get(korisnickoImeGosta);
		ArrayList<Rezervacija> rezervacije = gost.getRezervacije();
		rezervacije.add(menadzerRezervacija.rezervacije.get(sifraRezervacije));
		pisanjeGostiju();
	}
	
	//metoda za otkazivanje rezervacije
	public void otkaziRezervaciju(int sifraRezervacije, String korisnickoImeGosta) {
		Gost gost = gosti.get(korisnickoImeGosta);
		ArrayList<Rezervacija> rezervacije = gost.getRezervacije();
		for (Rezervacija rezervacija : rezervacije) {
			if (rezervacija.getId() == sifraRezervacije) {
				rezervacija.setStatusRezervacije(StatusRezervacije.OTKAZANA);
				break;
			}
		}
		pisanjeGostiju();
	}
	
	//metoda za pregled svojih rezervacija, treba da moze da se vidi i ukupan trosak za sve rezervacije
	public void pregledRezervacija(Menadzer menadzer, String korisnickoIme)
	{
		new PregledRezervacijaFrame(menadzer, korisnickoIme);
	}

	// ucitavanje gostiju iz fajla
	public void ucitavanjeGostiju(MenadzerRezervacija menadzerRezervacija) {
		String pathString="datoteke"+File.separator+"Gosti.txt";
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
				ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
				if(delovi.length==9)
                {
					String rezervacijeString=delovi[8];
					String[] rezervacijeNiz=rezervacijeString.split(", ");
					for(String sifra:rezervacijeNiz)
	                {
						rezervacije.add(menadzerRezervacija.rezervacije.get(Integer.parseInt(sifra)));
	                }
                }
				Gost gost=new Gost(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
				gosti.put(korisnickoIme, gost);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// upisivanje gostiju u fajl
	public void pisanjeGostiju() {
		String pathString="datoteke"+File.separator+"Gosti.txt";
		Path path = Path.of(pathString);
		
		if (gosti.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi = true;
		for (Gost gost : gosti.values()) {
			try {
				if (prvi) {
					Files.writeString(path, gost.upis()+"\n");
					prvi = false;
				} else {
					Files.writeString(path, gost.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud operacije nad gostima
	public void dodavanjeGosta(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, ArrayList<Rezervacija> rezervacije)
	{	
		Gost gost = new Gost(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
		gosti.put(korisnickoIme, gost);
		pisanjeGostiju();
	}
	public void pregledGostiju()
	{
		for (Gost gost : gosti.values()) {
			System.out.println(gost);
		}
	}
	public void izmenaGostiju(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, ArrayList<Rezervacija> rezervacije)
	{
		Gost gost = gosti.get(korisnickoIme);
		gost.setIme(ime);
		gost.setPrezime(prezime);
		gost.setPol(pol);
		gost.setDatumRodjenja(datumRodjenja);
		gost.setTelefon(telefon);
		gost.setAdresa(adresa);
		gost.setLozinka(lozinka);
		gost.setRezervacije(rezervacije);
		pisanjeGostiju();
	}
	public void brisanjeGostiju(String korisnickoIme)
	{
		gosti.remove(korisnickoIme);
		pisanjeGostiju();
	}
}
