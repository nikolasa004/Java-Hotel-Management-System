package projekat.menadzeri;

import projekat.korisnici.Administrator;
import projekat.korisnici.Gost;
import projekat.korisnici.Recepcioner;
import projekat.korisnici.Sobarica;

//menadzer klasa koja kontrolise sve menadzere
public class Menadzer {
	private MenadzerAdministratora menadzerAdministratora;
	private MenadzerRecepcionera menadzerRecepcionera;
	private MenadzerSobarica menadzerSobarica;
	private MenadzerGostiju menadzerGostiju;
	private MenadzerTipovaSoba menadzerTipovaSoba;
	private MenadzerSoba menadzerSoba;
	private MenadzerRezervacija menadzerRezervacija;
	private MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private MenadzerCenovnika menadzerCenovnika;
	private MenadzerIzvestaja menadzerIzvestaja;
	
	private String trenutnoUlogovan;
	
	//geteri
	public MenadzerAdministratora getMenadzerAdministratora() {
		return menadzerAdministratora;
	}
	public MenadzerRecepcionera getMenadzerRecepcionera() {
		return menadzerRecepcionera;
	}
	public MenadzerSobarica getMenadzerSobarica() {
		return menadzerSobarica;
	}
	public MenadzerGostiju getMenadzerGostiju() {
		return menadzerGostiju;
	}
	public MenadzerTipovaSoba getMenadzerTipovaSoba() {
		return menadzerTipovaSoba;
	}
	public MenadzerSoba getMenadzerSoba() {
		return menadzerSoba;
	}
	public MenadzerRezervacija getMenadzerRezervacija() {
		return menadzerRezervacija;
	}
	public MenadzerDodatnihUsluga getMenadzerDodatnihUsluga() {
		return menadzerDodatnihUsluga;
	}
	public MenadzerCenovnika getMenadzerCenovnika() {
		return menadzerCenovnika;
	}
	public MenadzerIzvestaja getMenadzerIzvestaja() {
		return menadzerIzvestaja;
	}

	public String getTrenutnoUlogovan() {
		return trenutnoUlogovan;
	}
	public void setTrenutnoUlogovan(String trenutnoUlogovan) {
		this.trenutnoUlogovan = trenutnoUlogovan;
	}
	
	//metoda za prijavu korisnika
	public Object[] prijava(String korisnickoIme, String lozinka) {
		//provera da li su uneti podaci ispravni, tacnije da li postoje u bazi
		boolean postoji = false;
		String tipKorisnika = "";

		Administrator administrator=menadzerAdministratora.getAdministratori().get(korisnickoIme);
		if (administrator != null) {
			if (administrator.getLozinka().equals(lozinka)) {
				postoji = true;
				tipKorisnika = "Administrator";
				trenutnoUlogovan = korisnickoIme;
				return new Object[]{postoji, tipKorisnika};
			}
		}
		
		Recepcioner recepcioner=menadzerRecepcionera.getRecepcioneri().get(korisnickoIme);
		if (recepcioner != null) {
			if (recepcioner.getLozinka().equals(lozinka)) {
				postoji = true;
				tipKorisnika = "Recepcioner";
				trenutnoUlogovan = korisnickoIme;
				return new Object[]{postoji, tipKorisnika};
			}
		}
		
		Sobarica  sobarica=menadzerSobarica.getSobarice().get(korisnickoIme);
		if (sobarica != null) {
			if (sobarica.getLozinka().equals(lozinka)) {
				postoji = true;
				tipKorisnika = "Sobarica";
				trenutnoUlogovan = korisnickoIme;
				return new Object[]{postoji, tipKorisnika};
			}
		}
		
		Gost gost=menadzerGostiju.getGosti().get(korisnickoIme);
		if (gost != null) {
			if (gost.getLozinka().equals(lozinka)) {
				postoji = true;
				tipKorisnika = "Gost";
				trenutnoUlogovan = korisnickoIme;
				return new Object[]{postoji, tipKorisnika};
			}
		}
		
		return new Object[]{postoji, tipKorisnika};
	}
	
	//konstruktor
	public Menadzer() {
        menadzerAdministratora = new MenadzerAdministratora();
        menadzerRecepcionera = new MenadzerRecepcionera();
        menadzerSobarica = new MenadzerSobarica();
        menadzerGostiju = new MenadzerGostiju();
        menadzerTipovaSoba = new MenadzerTipovaSoba();
        menadzerSoba = new MenadzerSoba();
        menadzerRezervacija = new MenadzerRezervacija();
        menadzerDodatnihUsluga = new MenadzerDodatnihUsluga();
        menadzerCenovnika = new MenadzerCenovnika();
        menadzerIzvestaja = new MenadzerIzvestaja();
    }
	
	//ucitavanje podataka
	public void ucitajPodatke() {
		menadzerTipovaSoba.ucitavanjeTipovaSoba();
        menadzerAdministratora.ucitavanjeAdministratora();
        menadzerRecepcionera.ucitavanjeRecepcionera();
        menadzerDodatnihUsluga.ucitavanjeDodatnihUsluga();
        menadzerSoba.ucitavanjeSoba(menadzerTipovaSoba);
        menadzerSobarica.ucitavanjeSobarica(menadzerSoba);
        menadzerRezervacija.ucitavanjeRezervacija(menadzerTipovaSoba,menadzerDodatnihUsluga,menadzerSoba);
        menadzerGostiju.ucitavanjeGostiju(menadzerRezervacija);
        menadzerCenovnika.ucitavanjeCenovnika(menadzerTipovaSoba, menadzerDodatnihUsluga);
        menadzerIzvestaja.ucitavanjeIzvestaja(this);
	}
}
