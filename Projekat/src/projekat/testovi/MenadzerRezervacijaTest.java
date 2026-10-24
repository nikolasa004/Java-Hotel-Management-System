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
import projekat.menadzeri.MenadzerDodatnihUsluga;
import projekat.menadzeri.MenadzerGostiju;
import projekat.menadzeri.MenadzerRezervacija;
import projekat.menadzeri.MenadzerSoba;
import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.usluge.DodatnaUsluga;

class MenadzerRezervacijaTest {
	
	private static MenadzerRezervacija menadzerRezervacija;
	private static MenadzerRezervacija menadzerRezervacijaPravi;
	private static MenadzerTipovaSoba menadzerTipSobe;
	private static MenadzerTipovaSoba menadzerTipSobePravi;
	private static MenadzerSoba menadzerSoba;
	private static MenadzerSoba menadzerSobaPravi;
	private static MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private static MenadzerDodatnihUsluga menadzerDodatnihUslugaPravi;
	private static MenadzerGostiju menadzerGostiju;
	private static MenadzerGostiju menadzerGostijuPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerRezervacija = new MenadzerRezervacija();
		menadzerRezervacijaPravi = new MenadzerRezervacija();
		menadzerTipSobe = new MenadzerTipovaSoba();
		menadzerTipSobePravi = new MenadzerTipovaSoba();
		menadzerSoba = new MenadzerSoba();
		menadzerSobaPravi = new MenadzerSoba();
		menadzerDodatnihUsluga = new MenadzerDodatnihUsluga();
		menadzerDodatnihUslugaPravi = new MenadzerDodatnihUsluga();
		menadzerGostiju = new MenadzerGostiju();
		menadzerGostijuPravi = new MenadzerGostiju();
		menadzerTipSobePravi.ucitavanjeTipovaSoba();
		menadzerSobaPravi.ucitavanjeSoba(menadzerTipSobePravi);
		menadzerDodatnihUslugaPravi.ucitavanjeDodatnihUsluga();
		menadzerRezervacijaPravi.ucitavanjeRezervacija(menadzerTipSobePravi,menadzerDodatnihUslugaPravi,menadzerSobaPravi);
		menadzerGostijuPravi.ucitavanjeGostiju(menadzerRezervacijaPravi);
		
		menadzerDodatnihUsluga.dodavanjeDodatnihUsluga("usluga");
		menadzerTipSobe.dodavanjeTipovaSoba("tip");
        menadzerSoba.dodavanjeSoba(menadzerTipSobe.tipoviSoba.get("tip"),StatusSobe.SLOBODNA,1,true,true,true,true);
        ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
        usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
        menadzerRezervacija.dodavanjeRezervacija(1,StatusRezervacije.POTVRDJENA,LocalDate.now(),menadzerTipSobe.tipoviSoba.get("tip"),1,1,usluge,1000.0,"gost","napomena");
        ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        rezervacije.add(menadzerRezervacija.rezervacije.get(1));
        menadzerGostiju.dodavanjeGosta("ime", "prezime", Pol.MUSKI, LocalDate.now(), "telefon", "adresa","korisnickoIme", "lozinka",rezervacije);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerRezervacija.brisanjeRezervacija(1,menadzerGostiju);
		menadzerRezervacija.pisanjeRezervacija();
		
		menadzerRezervacijaPravi.pisanjeRezervacija();
		menadzerTipSobePravi.pisanjeTipovaSoba();
		menadzerSobaPravi.pisanjeSoba();
		menadzerDodatnihUslugaPravi.pisanjeDodatnihUsluga();
		menadzerGostijuPravi.pisanjeGostiju();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerRezervacija.rezervacije.clear();
	}

	@Test
	void testUcitavanjeRezervacija() {
		int id = 1;
		StatusRezervacije status = StatusRezervacije.POTVRDJENA;
		LocalDate datum = LocalDate.now();
		TipSobe tipSobe = menadzerTipSobe.tipoviSoba.get("tip");
		int brojOsoba = 1;
		int brojNocenja = 1;
		ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
		usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
		double cena = 1000.0;
		String imeRezervacije = "gost";
		String napomena = "napomena";
		menadzerRezervacija.dodavanjeRezervacija(id, status, datum, tipSobe, brojOsoba, brojNocenja, usluge, cena, imeRezervacije, napomena);
		menadzerRezervacija.pisanjeRezervacija();
		
		menadzerRezervacija.ucitavanjeRezervacija(menadzerTipSobe, menadzerDodatnihUsluga, menadzerSoba);
		
		assertEquals(1, menadzerRezervacija.rezervacije.size());
	}

	@Test
	void testPisanjeRezervacija() {
		int id = 1;
        StatusRezervacije status = StatusRezervacije.POTVRDJENA;
        LocalDate datum = LocalDate.now();
        TipSobe tipSobe = menadzerTipSobe.tipoviSoba.get("tip");
        int brojOsoba = 1;
        int brojNocenja = 1;
        ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
        usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
        double cena = 1000.0;
        String imeRezervacije = "gost";
        String napomena = "napomena";
        menadzerRezervacija.dodavanjeRezervacija(id, status, datum, tipSobe, brojOsoba, brojNocenja, usluge, cena, imeRezervacije, napomena);
        
        menadzerRezervacija.pisanjeRezervacija();
        
        String pathString="datoteke"+File.separator+"Rezervacije.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("1|POTVRDJENA|"+LocalDate.now()+"|tip|1|1|usluga|1000.0|gost|-1|napomena", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDodavanjeRezervacija() {
		int id = 1;
		StatusRezervacije status = StatusRezervacije.POTVRDJENA;
		LocalDate datum = LocalDate.now();
		TipSobe tipSobe = menadzerTipSobe.tipoviSoba.get("tip");
		int brojOsoba = 1;
		int brojNocenja = 1;
		ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
		usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
		double cena = 1000.0;
		String imeRezervacije = "gost";
		String napomena = "napomena";
		
		menadzerRezervacija.dodavanjeRezervacija(id, status, datum, tipSobe, brojOsoba, brojNocenja, usluge, cena, imeRezervacije, napomena);
		
		assertEquals(1, menadzerRezervacija.rezervacije.size());
		assertEquals(id, menadzerRezervacija.rezervacije.get(id).getId());
	}

	@Test
	void testIzmenaRezervacija() {
		int id = 1;
		StatusRezervacije status = StatusRezervacije.POTVRDJENA;
		LocalDate datum = LocalDate.now();
		TipSobe tipSobe = menadzerTipSobe.tipoviSoba.get("tip");
		int brojOsoba = 1;
		int brojNocenja = 1;
		ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
		usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
		double cena = 1000.0;
		String imeRezervacije = "gost";
		String napomena = "napomena";
		menadzerRezervacija.dodavanjeRezervacija(id, status, datum, tipSobe, brojOsoba, brojNocenja, usluge, cena,
				imeRezervacije, napomena);

		int brojNocenjaIzmena = 2;
		Rezervacija rezervacija = menadzerRezervacija.rezervacije.get(id);
		menadzerRezervacija.izmenaRezervacija(id, status, datum, tipSobe, brojOsoba, brojNocenjaIzmena, usluge, cena,
				imeRezervacije,new Soba(), napomena);
		
		assertEquals(brojNocenjaIzmena, rezervacija.getBrojNocenja());
	}

	@Test
	void testBrisanjeRezervacija() {
		int id = 1;
        StatusRezervacije status = StatusRezervacije.POTVRDJENA;
        LocalDate datum = LocalDate.now();
        TipSobe tipSobe = menadzerTipSobe.tipoviSoba.get("tip");
        int brojOsoba = 1;
        int brojNocenja = 1;
        ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
        usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
        double cena = 1000.0;
        String imeRezervacije = "gost";
        String napomena = "napomena";
        menadzerRezervacija.dodavanjeRezervacija(id, status, datum, tipSobe, brojOsoba, brojNocenja, usluge, cena, imeRezervacije, napomena);
        
        menadzerRezervacija.brisanjeRezervacija(id,menadzerGostiju);
        
        assertEquals(0, menadzerRezervacija.rezervacije.size());
        assertEquals(0, menadzerGostiju.getGosti().get("korisnickoIme").getRezervacije().size());
	}

}
