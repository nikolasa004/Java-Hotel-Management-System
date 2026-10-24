package projekat.korisnici;

import projekat.sobe.Soba;
import projekat.enumi.Pol;
import projekat.enumi.StrucnaSprema;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sobarica extends Zaposleni {
	//atribut
	private ArrayList<Soba> dodeljeneSobe;
	
	//get i set metode
	public ArrayList<Soba> getDodeljeneSobe() {
		return dodeljeneSobe;
	}
	public void setDodeljeneSobe(ArrayList<Soba> dodeljenjeSobe) {
		this.dodeljeneSobe = dodeljenjeSobe;
	}
	
	//konstruktori
	public Sobarica(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int staz,
			ArrayList<Soba> dodeljeneSobe) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
		this.dodeljeneSobe = dodeljeneSobe;
	}
	public Sobarica() {
		super();
	}
	
	//toString metoda
	@Override
	public String toString() {
		return "Sobarica [dodeljenjeSobe=" + dodeljeneSobe + ", strucnaSprema=" + strucnaSprema + ", staz=" + staz
				+ ", plata=" + plata + ", ime=" + ime + ", prezime=" + prezime + ", pol=" + pol + ", datumRodjenja="
				+ datumRodjenja + ", telefon=" + telefon + ", adresa=" + adresa + ", korisnickoIme=" + korisnickoIme
				+ ", lozinka=" + lozinka + "]";
	}
	
	//pretvaranje soba u string
	public String sobeString() {
		String sobe = "";
		for (Soba soba : dodeljeneSobe) {
			sobe += soba.getBrojSobe();
			if (dodeljeneSobe.indexOf(soba) != dodeljeneSobe.size() - 1) {
				sobe += ", ";
			}
		}
		return sobe;
	}
	
	//metoda za upis u fajl
	public String upis() {
		return ime + "|" + prezime + "|" + pol + "|" + datumRodjenja + "|" + telefon + "|" + adresa + "|"
				+ korisnickoIme + "|" + lozinka + "|" + strucnaSprema + "|" + staz + "|" + sobeString();
	}
}
