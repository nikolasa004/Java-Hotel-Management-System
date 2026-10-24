package projekat.testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projekat.enumi.Pol;
import projekat.enumi.StatusRezervacije;
import projekat.enumi.StatusSobe;
import projekat.enumi.StrucnaSprema;
import projekat.menadzeri.MenadzerCenovnika;
import projekat.menadzeri.MenadzerDodatnihUsluga;
import projekat.menadzeri.MenadzerGostiju;
import projekat.menadzeri.MenadzerRezervacija;
import projekat.menadzeri.MenadzerSoba;
import projekat.menadzeri.MenadzerSobarica;
import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.usluge.DodatnaUsluga;

class MenadzerTipovaSobaTest {
	private static MenadzerTipovaSoba menadzerTipovaSoba;
	private static MenadzerTipovaSoba menadzerTipovaSobaPravi;
	private static MenadzerRezervacija menadzerRezervacija;
	private static MenadzerRezervacija menadzerRezervacijaPravi;
	private static MenadzerSoba menadzerSoba;
	private static MenadzerSoba menadzerSobaPravi;
	private static MenadzerCenovnika menadzerCenovnika;
	private static MenadzerCenovnika menadzerCenovnikaPravi;
	private static MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private static MenadzerDodatnihUsluga menadzerDodatnihUslugaPravi;
	private static MenadzerSobarica menadzerSobarica;
	private static MenadzerSobarica menadzerSobaricaPravi;
	private static MenadzerGostiju menadzerGostiju;
	private static MenadzerGostiju menadzerGostijuPravi;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerTipovaSoba = new MenadzerTipovaSoba();
		menadzerTipovaSobaPravi = new MenadzerTipovaSoba();
		menadzerRezervacija = new MenadzerRezervacija();
		menadzerRezervacijaPravi = new MenadzerRezervacija();
		menadzerSoba = new MenadzerSoba();
		menadzerSobaPravi = new MenadzerSoba();
		menadzerCenovnika = new MenadzerCenovnika();
		menadzerCenovnikaPravi = new MenadzerCenovnika();
		menadzerDodatnihUsluga = new MenadzerDodatnihUsluga();
		menadzerDodatnihUslugaPravi = new MenadzerDodatnihUsluga();
		menadzerSobarica = new MenadzerSobarica();
		menadzerSobaricaPravi = new MenadzerSobarica();
		menadzerGostiju = new MenadzerGostiju();
		menadzerGostijuPravi = new MenadzerGostiju();
		menadzerTipovaSobaPravi.ucitavanjeTipovaSoba();
		menadzerDodatnihUslugaPravi.ucitavanjeDodatnihUsluga();
		menadzerSobaPravi.ucitavanjeSoba(menadzerTipovaSobaPravi);
		menadzerRezervacijaPravi.ucitavanjeRezervacija(menadzerTipovaSobaPravi,menadzerDodatnihUslugaPravi,menadzerSobaPravi);
		menadzerCenovnikaPravi.ucitavanjeCenovnika(menadzerTipovaSobaPravi,menadzerDodatnihUslugaPravi);
		menadzerSobaricaPravi.ucitavanjeSobarica(menadzerSobaPravi);
		menadzerGostijuPravi.ucitavanjeGostiju(menadzerRezervacijaPravi);
		
		menadzerDodatnihUsluga.dodavanjeDodatnihUsluga("usluga");
		menadzerTipovaSoba.dodavanjeTipovaSoba("tip");
        menadzerSoba.dodavanjeSoba(menadzerTipovaSoba.tipoviSoba.get("tip"),StatusSobe.SLOBODNA,1,true,true,true,true);
        ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
        usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
        menadzerRezervacija.dodavanjeRezervacija(1,StatusRezervacije.POTVRDJENA,LocalDate.now(),menadzerTipovaSoba.tipoviSoba.get("tip"),1,1,usluge,1000.0,"gost","napomena");
        HashMap<TipSobe, Double> ceneSoba = new HashMap<TipSobe, Double>();
        ceneSoba.put(menadzerTipovaSoba.tipoviSoba.get("tip"), 1000.0);
        menadzerCenovnika.dodavanjeCenovnika(1,LocalDate.now(), LocalDate.now().plusDays(1), ceneSoba, new HashMap<DodatnaUsluga, Double>());
        ArrayList<Soba> sobe = new ArrayList<Soba>();
        sobe.add(menadzerSoba.sobe.get(1));
        menadzerSobarica.dodavanjeSobarica("ime", "prezime",Pol.ZENSKI, LocalDate.now(), "telefon", "adresa","korisnickoIme","lozinka",StrucnaSprema.OSNOVNA, 1,sobe);
        ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        rezervacije.add(menadzerRezervacija.rezervacije.get(1));
        menadzerGostiju.dodavanjeGosta("ime", "prezime", Pol.MUSKI, LocalDate.now(), "telefon", "adresa","korisnickoIme", "lozinka",rezervacije);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerTipovaSoba.brisanjeTipovaSoba("tip",menadzerCenovnika,menadzerSoba,menadzerRezervacija,menadzerSobarica, menadzerGostiju);
		menadzerTipovaSoba.pisanjeTipovaSoba();
		
		menadzerTipovaSobaPravi.pisanjeTipovaSoba();
		menadzerDodatnihUslugaPravi.pisanjeDodatnihUsluga();
		menadzerSobaPravi.pisanjeSoba();
		menadzerRezervacijaPravi.pisanjeRezervacija();
		menadzerCenovnikaPravi.pisanjeCenovnika();
		menadzerSobaricaPravi.pisanjeSobarica();
		menadzerGostijuPravi.pisanjeGostiju();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerTipovaSoba.tipoviSoba.clear();
	}

	@Test
	void testUcitavanjeTipovaSoba() {
		String tip = "tip";
		menadzerTipovaSoba.dodavanjeTipovaSoba(tip);
		menadzerTipovaSoba.pisanjeTipovaSoba();
		
		menadzerTipovaSoba.ucitavanjeTipovaSoba();
		
		assertEquals(1, menadzerTipovaSoba.tipoviSoba.size());
	}

	@Test
	void testPisanjeTipovaSoba() {
		String tip = "tip";
		
		menadzerTipovaSoba.dodavanjeTipovaSoba(tip);
		
		menadzerTipovaSoba.pisanjeTipovaSoba();
		
		String pathString="datoteke"+File.separator+"TipoviSoba.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("tip", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDodavanjeTipovaSoba() {
		String tip = "tip";
		
		menadzerTipovaSoba.dodavanjeTipovaSoba(tip);
		
		assertEquals(1, menadzerTipovaSoba.tipoviSoba.size());
		assertEquals("tip", menadzerTipovaSoba.tipoviSoba.get("tip").getTip());
	}

	@Test
	void testBrisanjeTipovaSoba() {
		String tip = "tip";
        
        menadzerTipovaSoba.dodavanjeTipovaSoba(tip);
        
        menadzerTipovaSoba.brisanjeTipovaSoba(tip,menadzerCenovnika,menadzerSoba,menadzerRezervacija,menadzerSobarica, menadzerGostiju);
        
        assertEquals(0, menadzerTipovaSoba.tipoviSoba.size());
        assertEquals(null, menadzerCenovnika.cenovnici.get(1).getCeneSoba().get(menadzerTipovaSoba.tipoviSoba.get(tip)));
        assertEquals(0, menadzerSoba.sobe.size());
        assertEquals(0, menadzerRezervacija.rezervacije.size());
        assertEquals(0, menadzerSobarica.getSobarice().get("korisnickoIme").getDodeljeneSobe().size());
        assertEquals(0, menadzerGostiju.getGosti().get("korisnickoIme").getRezervacije().size());
	}

}
