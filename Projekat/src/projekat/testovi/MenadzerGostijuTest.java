package projekat.testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projekat.enumi.Pol;
import projekat.enumi.StatusRezervacije;
import projekat.enumi.StatusSobe;
import projekat.korisnici.Gost;
import projekat.menadzeri.MenadzerDodatnihUsluga;
import projekat.menadzeri.MenadzerGostiju;
import projekat.menadzeri.MenadzerRezervacija;
import projekat.menadzeri.MenadzerSoba;
import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.rezervacije.Rezervacija;
import projekat.usluge.DodatnaUsluga;

class MenadzerGostijuTest {
	private static MenadzerGostiju menadzerGostiju;
	private static MenadzerGostiju menadzerGostijuPravi;
	private static MenadzerRezervacija menadzerRezervacija;
	private static MenadzerRezervacija menadzerRezervacijaPravi;
	private static MenadzerSoba menadzerSoba;
	private static MenadzerSoba menadzerSobaPravi;
	private static MenadzerTipovaSoba menadzerTipovaSoba;
	private static MenadzerTipovaSoba menadzerTipovaSobaPravi;
	private static MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private static MenadzerDodatnihUsluga menadzerDodatnihUslugaPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerGostiju = new MenadzerGostiju();
        menadzerGostijuPravi = new MenadzerGostiju();
        menadzerRezervacija = new MenadzerRezervacija();
        menadzerRezervacijaPravi= new MenadzerRezervacija();
        menadzerSoba = new MenadzerSoba();
        menadzerSobaPravi= new MenadzerSoba();
        menadzerTipovaSoba = new MenadzerTipovaSoba();
        menadzerTipovaSobaPravi= new MenadzerTipovaSoba();
        menadzerDodatnihUsluga = new MenadzerDodatnihUsluga();
        menadzerDodatnihUslugaPravi = new MenadzerDodatnihUsluga();
        menadzerDodatnihUslugaPravi.ucitavanjeDodatnihUsluga();
        menadzerTipovaSobaPravi.ucitavanjeTipovaSoba();
		menadzerSobaPravi.ucitavanjeSoba(menadzerTipovaSobaPravi);
        menadzerRezervacijaPravi.ucitavanjeRezervacija(menadzerTipovaSobaPravi,menadzerDodatnihUslugaPravi,menadzerSobaPravi);
        menadzerGostijuPravi.ucitavanjeGostiju(menadzerRezervacijaPravi);
        
        menadzerDodatnihUsluga.dodavanjeDodatnihUsluga("usluga");
        menadzerTipovaSoba.dodavanjeTipovaSoba("tip");
        menadzerSoba.dodavanjeSoba(menadzerTipovaSoba.tipoviSoba.get("tip"),StatusSobe.SLOBODNA,1,true,true,true,true);
        ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
        usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
        menadzerRezervacija.dodavanjeRezervacija(1,StatusRezervacije.POTVRDJENA,LocalDate.now(),menadzerTipovaSoba.tipoviSoba.get("tip"),1,1,usluge,1000.0,"gost","napomena");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerGostiju.brisanjeGostiju("gost");
		menadzerGostiju.pisanjeGostiju();
		
		menadzerGostijuPravi.pisanjeGostiju();
		menadzerRezervacijaPravi.pisanjeRezervacija();
		menadzerSobaPravi.pisanjeSoba();
		menadzerTipovaSobaPravi.pisanjeTipovaSoba();
		menadzerDodatnihUslugaPravi.pisanjeDodatnihUsluga();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerGostiju.getGosti().clear();
	}
	
	@Test
	void testNapraviRezervaciju()
	{
		String ime = "gost";
		String prezime = "gost";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "gost";
		String adresa = "gost";
		String korisnickoIme = "gost";
		String lozinka = "gost";
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(1);
		rezervacije.add(rezervacija);
		menadzerGostiju.dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka,rezervacije);

		menadzerGostiju.napraviRezervaciju("gost",1,menadzerRezervacija);
		
		assertEquals(2,menadzerGostiju.getGosti().get("gost").getRezervacije().size());
	}
	
	@Test
	void testOtkaziRezervaciju()
	{
		String ime = "gost";
		String prezime = "gost";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "gost";
		String adresa = "gost";
		String korisnickoIme = "gost";
		String lozinka = "gost";
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(1);
		rezervacije.add(rezervacija);
		menadzerGostiju.dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka,
				rezervacije);

		menadzerGostiju.otkaziRezervaciju(1, "gost");

		assertEquals(0, menadzerGostiju.getGosti().get("gost").getRezervacije().size());
	}

	@Test
	void testUcitavanjeGostiju() {
		String ime = "gost";
		String prezime = "gost";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "gost";
		String adresa = "gost";
		String korisnickoIme = "gost";
		String lozinka = "gost";
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(1);
		rezervacije.add(rezervacija);
		menadzerGostiju.dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
		menadzerGostiju.pisanjeGostiju();
		
		menadzerGostiju.ucitavanjeGostiju(menadzerRezervacija);
		
		assertEquals(1,menadzerGostiju.getGosti().size());
	}

	@Test
	void testPisanjeGostiju() {
		String ime = "gost";
		String prezime = "gost";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "gost";
		String adresa = "gost";
		String korisnickoIme = "gost";
		String lozinka = "gost";
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(1);
		rezervacije.add(rezervacija);
		
		menadzerGostiju.dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
		
		menadzerGostiju.pisanjeGostiju();
		
		String pathString="datoteke"+File.separator+"Gosti.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("gost|gost|MUSKI|"+LocalDate.now()+"|gost|gost|gost|gost|1", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDodavanjeGosta() {
		String ime = "gost";
		String prezime = "gost";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "gost";
		String adresa = "gost";
		String korisnickoIme = "gost";
		String lozinka = "gost";
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(1);
		rezervacije.add(rezervacija);
		
		menadzerGostiju.dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
		
		assertEquals(1, menadzerGostiju.getGosti().size());
		assertEquals(korisnickoIme, menadzerGostiju.getGosti().get(korisnickoIme).getKorisnickoIme());
		assertEquals(1, menadzerGostiju.getGosti().get(korisnickoIme).getRezervacije().size());
	}

	@Test
	void testIzmenaGostiju() {
		String ime = "gost";
		String prezime = "gost";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "gost";
		String adresa = "gost";
		String korisnickoIme = "gost";
		String lozinka = "gost";
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(1);
		rezervacije.add(rezervacija);
		
		menadzerGostiju.dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
		
		Gost gost = menadzerGostiju.getGosti().get(korisnickoIme);
		String ime1 = "gost1";
		
		menadzerGostiju.izmenaGostiju(ime1, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
		
		assertEquals(ime1, gost.getIme());
	}

	@Test
	void testBrisanjeGostiju() {
		String ime = "gost";
        String prezime = "gost";
        Pol pol = Pol.MUSKI;
        LocalDate datumRodjenja = LocalDate.now();
        String telefon = "gost";
        String adresa = "gost";
        String korisnickoIme = "gost";
        String lozinka = "gost";
        ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(1);
        rezervacije.add(rezervacija);
        
        menadzerGostiju.dodavanjeGosta(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, rezervacije);
        
        menadzerGostiju.brisanjeGostiju(korisnickoIme);
        
        assertEquals(0, menadzerGostiju.getGosti().size());
	}

}
