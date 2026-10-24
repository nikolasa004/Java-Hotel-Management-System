package projekat.korisnici;

import projekat.enumi.StrucnaSprema;
import projekat.enumi.Pol;
import java.time.LocalDate;

public class Zaposleni extends Korisnik {
	//dodatni atributi
	protected StrucnaSprema strucnaSprema;
	protected int staz;
	protected double plata;
	
	//geteri i seteri
	public StrucnaSprema getStrucnaSprema() {
		return strucnaSprema;
	}
	public void setStrucnaSprema(StrucnaSprema strucnaSprema) {
		this.strucnaSprema = strucnaSprema;
	}
	public int getStaz() {
		return staz;
	}
	public void setStaz(int staz) {
		this.staz = staz;
	}
	public double getPlata() {
		return plata;
	}
	public void setPlata() {
		if (strucnaSprema == StrucnaSprema.OSNOVNA)
			this.plata = 30000;
		else if (strucnaSprema == StrucnaSprema.SREDNJA)
			this.plata = 40000;
		else if (strucnaSprema == StrucnaSprema.VISOKA)
			this.plata = 50000;
		else if (strucnaSprema == StrucnaSprema.DOKTORSKA)
			this.plata = 60000;
		
		this.plata = this.plata + (this.plata * (this.staz * 0.01));
	}
	//konstruktori
	public Zaposleni(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa, String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.strucnaSprema = strucnaSprema;
		this.staz = staz;
		setPlata();
	}
	public Zaposleni() {
		super();
	}
	
	//toString
	@Override
	public String toString() {
		return "Zaposleni [strucnaSprema=" + strucnaSprema + ", staz=" + staz + ", plata=" + plata + ", ime=" + ime
				+ ", prezime=" + prezime + ", pol=" + pol + ", datumRodjenja=" + datumRodjenja + ", telefon=" + telefon
				+ ", adresa=" + adresa + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + "]";
	}
}
