package projekat.korisnici;

import projekat.rezervacije.Rezervacija;
import projekat.enumi.Pol;

import java.time.LocalDate;
import java.util.ArrayList;

public class Gost extends Korisnik {
	//atribut
	private ArrayList<Rezervacija> rezervacije;
	
	//get i set metode
	public ArrayList<Rezervacija> getRezervacije()
	{
		return rezervacije;
	}
	public void setRezervacije(ArrayList<Rezervacija> rezervacije)
	{
		this.rezervacije = rezervacije;
	}
	
	//konstruktori
	public Gost(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, ArrayList<Rezervacija> rezervacije) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.rezervacije = rezervacije;
	}
	public Gost() {
		super();
	}
	
	//toString metoda
	@Override
	public String toString() {
		return "Gost [rezervacije=" + rezervacije + ", ime=" + ime + ", prezime=" + prezime + ", pol=" + pol
				+ ", datumRodjenja=" + datumRodjenja + ", telefon=" + telefon + ", adresa=" + adresa
				+ ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + "]";
	}
	
	//pretvaranje rezeravacija u string
	public String rezervacijeString()
	{
		String rezervacijeString="";
		for (Rezervacija rezervacija:rezervacije)
		{
			rezervacijeString+=rezervacija.getId();
			if (rezervacije.indexOf(rezervacija) != rezervacije.size() - 1) {
				rezervacijeString += ", ";
			}
		}
		return rezervacijeString;
	}
	
	//upisivanje gosta u fajl
	public String upis() {
		return ime + "|" + prezime + "|" + pol + "|" + datumRodjenja + "|" + telefon + "|" + adresa + "|"
				+ korisnickoIme + "|" + lozinka + "|" + rezervacijeString();
	}
}
