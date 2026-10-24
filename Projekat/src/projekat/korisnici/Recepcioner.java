package projekat.korisnici;

import projekat.enumi.StrucnaSprema;
import projekat.enumi.Pol;
import java.time.LocalDate;

public class Recepcioner extends Zaposleni {
	
	//konstruktori
	public Recepcioner(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
	}
	public Recepcioner() {
		super();
	}
	
	//toString
	@Override
	public String toString() {
		return "Recepcioner [strucnaSprema=" + strucnaSprema + ", staz=" + staz + ", plata=" + plata + ", ime=" + ime
				+ ", prezime=" + prezime + ", pol=" + pol + ", datumRodjenja=" + datumRodjenja + ", telefon=" + telefon
				+ ", adresa=" + adresa + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + "]";
	}
	
	//string za upis u fajl
	public String upis() {
		return ime + "|" + prezime + "|" + pol + "|" + datumRodjenja + "|" + telefon + "|" + adresa + "|"
				+ korisnickoIme + "|" + lozinka + "|" + strucnaSprema + "|" + staz;
	}
}
