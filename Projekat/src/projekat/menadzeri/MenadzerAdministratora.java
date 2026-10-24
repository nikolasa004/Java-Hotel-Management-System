package projekat.menadzeri;

import projekat.korisnici.Administrator;
import projekat.enumi.Pol;
import projekat.enumi.StrucnaSprema;
import projekat.gui.administrator.ChartPrihod;
import projekat.gui.administrator.ChartRezervacije;
import projekat.gui.administrator.ChartSobarice;
import projekat.gui.administrator.IzvestajFrame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class MenadzerAdministratora {
	//mapa svih kreiranih administratora, kljuc je korisnicko ime
	private HashMap<String, Administrator> administratori = new HashMap<String, Administrator>();
	
	//geter
	public HashMap<String, Administrator> getAdministratori() {
		return administratori;
	}
	
	//metoda za pregled izvestaja
	public void izvestaj(Menadzer menadzer,String tip)
    {
       new IzvestajFrame(menadzer,tip);
    }
	
	//metoda za chart o prikazu prihoda zadnjih 12 meseci, ukupan kao i po tipu sobe
	public void chartPrihoda(Menadzer menadzer)
	{
		new ChartPrihod(menadzer);
	}
	
	//metoda za chart o tome koliko su sobarice sredile soba za 30 dana
	public void chartSobarica(Menadzer menadzer)
	{
		new ChartSobarice(menadzer);
	}
	
	//metoda za chart o tome statusu rezervacija za 30 dana
	public void chartRezervacija(Menadzer menadzer)
	{
		new ChartRezervacije(menadzer);
	}
	
	//ucitavanje administratora iz fajla
	public void ucitavanjeAdministratora() {
		String pathString="datoteke"+File.separator+"Administratori.txt";
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
				Administrator administrator=new Administrator(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
				administratori.put(korisnickoIme, administrator);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//upisivanje administratora u fajl
	public void pisanjeAdministratora() {
		String pathString="datoteke"+File.separator+"Administratori.txt";
		Path path = Path.of(pathString);
		
		if (administratori.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi = true;
		for (Administrator administrator : administratori.values()) {
			try {
				if (prvi) {
					Files.writeString(path, administrator.upis()+"\n");
					prvi = false;
				} else {
					Files.writeString(path, administrator.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud operacije nad administratorima
	public void dodavanjeAdministratora(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz)
	{
		Administrator administrator=new Administrator(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
		administratori.put(korisnickoIme, administrator);
		pisanjeAdministratora();
	}
	public void pregledAdministratora()
	{
		for (Administrator administrator : administratori.values()) {
			System.out.println(administrator);
		}
	}
	public void izmenaAdministratora(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz)
	{
		Administrator administrator = administratori.get(korisnickoIme);
		administrator.setIme(ime);
		administrator.setPrezime(prezime);
		administrator.setPol(pol);
		administrator.setDatumRodjenja(datumRodjenja);
		administrator.setTelefon(telefon);
		administrator.setAdresa(adresa);
		administrator.setKorisnickoIme(korisnickoIme);
		administrator.setLozinka(lozinka);
		administrator.setStrucnaSprema(strucnaSprema);
		administrator.setStaz(staz);
		administrator.setPlata();
		pisanjeAdministratora();
	}
	public void brisanjeAdministratora(String korisnickoIme)
	{
		administratori.remove(korisnickoIme);
		pisanjeAdministratora();
	}
}
