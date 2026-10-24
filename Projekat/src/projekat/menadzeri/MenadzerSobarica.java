package projekat.menadzeri;

import projekat.enumi.Pol;
import projekat.enumi.StrucnaSprema;
import projekat.sobe.Soba;
import projekat.korisnici.Sobarica;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenadzerSobarica {
	// mapa svih sobarica, kljuc je korisnicko ime
	private HashMap<String, Sobarica> sobarice = new HashMap<String, Sobarica>();
	
	//geter
	public HashMap<String, Sobarica> getSobarice() {
		return sobarice;
	}
	
	//metoda za unos da je soba sredjena
	public void sobaSredjena(int id, String korisnickoImeSobarice)
	{
		Sobarica sobarica=sobarice.get(korisnickoImeSobarice);
		int i=0;
		for (Soba soba : sobarica.getDodeljeneSobe()) {
			if (soba.getBrojSobe() == id) {
				break;
			}
			i++;
		}
		sobarica.getDodeljeneSobe().remove(i);
		pisanjeSobarica();
	}
	
	//metoda za dodelu sobe sobarici pri check-outu
	public void dodelaSobe(Soba soba) {
		Sobarica sobarica = null;
		int min = Integer.MAX_VALUE;
		for (Sobarica sobarica1 : sobarice.values()) {
			if (sobarica1.getDodeljeneSobe().size() < min) {
				min = sobarica1.getDodeljeneSobe().size();
				sobarica = sobarica1;
			}
		}
		sobarica.getDodeljeneSobe().add(soba);
		pisanjeSobarica();
	}

	// ucitavanje sobarica iz fajla
	public void ucitavanjeSobarica(MenadzerSoba menadzerSoba)
	{
		String pathString="datoteke"+File.separator+"Sobarice.txt";
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
				String dodeljeneSobeString="";
				ArrayList<Soba> dodeljeneSobe=new ArrayList<Soba>();
				if(delovi.length==11)
				{
					dodeljeneSobeString=delovi[10];
					String[] dodeljeneSobeNiz=dodeljeneSobeString.split(", ");
					for (String dodeljenaSoba : dodeljeneSobeNiz) {
						dodeljeneSobe.add(menadzerSoba.sobe.get(Integer.parseInt(dodeljenaSoba)));
					}
				}
				Sobarica sobarica=new Sobarica(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz,dodeljeneSobe);
				sobarice.put(korisnickoIme, sobarica);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// upisivanje sobarica u fajl
	public void pisanjeSobarica() {
		String pathString="datoteke"+File.separator+"Sobarice.txt";
		Path path = Path.of(pathString);
		
		if (sobarice.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi = true;
		for (Sobarica sobarica : sobarice.values()) {
			try {
				if (prvi) {
					Files.writeString(path, sobarica.upis()+"\n");
					prvi = false;
				} else {
					Files.writeString(path, sobarica.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud operacije za sobarice
	public void dodavanjeSobarica(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz,ArrayList<Soba> dodeljeneSobe)
	{
		Sobarica sobarica = new Sobarica(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz,dodeljeneSobe);
		sobarice.put(korisnickoIme, sobarica);
		pisanjeSobarica();
	}
	public void pregledSobarica()
	{
		for (Sobarica sobarica : sobarice.values()) {
			System.out.println(sobarica);
		}
	}
	public void izmenaSobarica(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz,ArrayList<Soba> dodeljeneSobe)
	{
		Sobarica sobarica = sobarice.get(korisnickoIme);
		sobarica.setIme(ime);
		sobarica.setPrezime(prezime);
		sobarica.setPol(pol);
		sobarica.setDatumRodjenja(datumRodjenja);
		sobarica.setTelefon(telefon);
		sobarica.setAdresa(adresa);
		sobarica.setLozinka(lozinka);
		sobarica.setStrucnaSprema(strucnaSprema);
		sobarica.setStaz(staz);
		sobarica.setDodeljeneSobe(dodeljeneSobe);
		sobarica.setPlata();
		pisanjeSobarica();
	}
	public void brisanjeSobarica(String korisnickoIme)
	{
		sobarice.remove(korisnickoIme);
		pisanjeSobarica();
	}
}
