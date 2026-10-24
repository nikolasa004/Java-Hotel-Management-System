package projekat.korisnici;

import projekat.enumi.Pol;
import projekat.enumi.StrucnaSprema;
import java.time.LocalDate;

public class Administrator extends Zaposleni {
	
	//konstruktori
	public Administrator(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
	}
	public Administrator() {
		super();
	}
	
	//toString
	@Override
	public String toString() {
		return "Administrator [strucnaSprema=" + strucnaSprema + ", staz=" + staz + ", plata=" + plata + ", ime=" + ime
				+ ", prezime=" + prezime + ", pol=" + pol + ", datumRodjenja=" + datumRodjenja + ", telefon=" + telefon
				+ ", adresa=" + adresa + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + "]";
	}
	
	//upisivanje administratora u fajl
	public String upis() {
		return ime + "|" + prezime + "|" + pol + "|" + datumRodjenja + "|" + telefon + "|" + adresa + "|"
				+ korisnickoIme + "|" + lozinka + "|" + strucnaSprema + "|" + staz;
	}
}
