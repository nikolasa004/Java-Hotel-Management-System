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

import projekat.enumi.StatusRezervacije;
import projekat.enumi.StatusSobe;
import projekat.menadzeri.MenadzerCenovnika;
import projekat.menadzeri.MenadzerDodatnihUsluga;
import projekat.menadzeri.MenadzerRezervacija;
import projekat.menadzeri.MenadzerSoba;
import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.sobe.TipSobe;
import projekat.usluge.DodatnaUsluga;

class MenadzerDodatnihUslugaTest {
	private static MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private static MenadzerDodatnihUsluga menadzerDodatnihUslugaPravi;
	private static MenadzerRezervacija menadzerRezervacija;
	private static MenadzerRezervacija menadzerRezervacijaPravi;
	private static MenadzerTipovaSoba menadzerTipovaSoba;
	private static MenadzerTipovaSoba menadzerTipovaSobaPravi;
	private static MenadzerSoba menadzerSoba;
	private static MenadzerSoba menadzerSobaPravi;
	private static MenadzerCenovnika menadzerCenovnika;
	private static MenadzerCenovnika menadzerCenovnikaPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerDodatnihUsluga = new MenadzerDodatnihUsluga();
        menadzerDodatnihUslugaPravi = new MenadzerDodatnihUsluga();
        menadzerRezervacija = new MenadzerRezervacija();
        menadzerRezervacijaPravi = new MenadzerRezervacija();
        menadzerTipovaSoba = new MenadzerTipovaSoba();
        menadzerTipovaSobaPravi = new MenadzerTipovaSoba();
        menadzerSoba = new MenadzerSoba();
        menadzerSobaPravi = new MenadzerSoba();
        menadzerCenovnika = new MenadzerCenovnika();
        menadzerCenovnikaPravi = new MenadzerCenovnika();
        menadzerTipovaSobaPravi.ucitavanjeTipovaSoba();
        menadzerSobaPravi.ucitavanjeSoba(menadzerTipovaSobaPravi);
        menadzerDodatnihUslugaPravi.ucitavanjeDodatnihUsluga();
        menadzerRezervacijaPravi.ucitavanjeRezervacija(menadzerTipovaSobaPravi,menadzerDodatnihUslugaPravi,menadzerSobaPravi);
        menadzerCenovnikaPravi.ucitavanjeCenovnika(menadzerTipovaSobaPravi,menadzerDodatnihUslugaPravi);
        
		menadzerDodatnihUsluga.dodavanjeDodatnihUsluga("usluga");
		menadzerTipovaSoba.dodavanjeTipovaSoba("tip");
        menadzerSoba.dodavanjeSoba(menadzerTipovaSoba.tipoviSoba.get("tip"),StatusSobe.SLOBODNA,1,true,true,true,true);
        ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
        usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
        menadzerRezervacija.dodavanjeRezervacija(1,StatusRezervacije.POTVRDJENA,LocalDate.now(),menadzerTipovaSoba.tipoviSoba.get("tip"),1,1,usluge,1000.0,"gost","napomena");
        HashMap<DodatnaUsluga, Double> ceneUsluga = new HashMap<DodatnaUsluga, Double>();
        ceneUsluga.put(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"), 100.0);
        menadzerCenovnika.dodavanjeCenovnika(1,LocalDate.now(), LocalDate.now().plusDays(1),new HashMap<TipSobe, Double>(), ceneUsluga);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerDodatnihUsluga.brisanjeDodatnihUsluga("usluga", menadzerCenovnika, menadzerRezervacija);
		menadzerDodatnihUsluga.pisanjeDodatnihUsluga();
		
		menadzerCenovnikaPravi.pisanjeCenovnika();
		menadzerRezervacijaPravi.pisanjeRezervacija();
		menadzerSobaPravi.pisanjeSoba();
		menadzerTipovaSobaPravi.pisanjeTipovaSoba();
		menadzerDodatnihUslugaPravi.pisanjeDodatnihUsluga();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerDodatnihUsluga.dodatneUsluge.clear();
	}

	@Test
	void testUcitavanjeDodatnihUsluga() {
		String usluga = "usluga";
		menadzerDodatnihUsluga.dodavanjeDodatnihUsluga(usluga);
		menadzerDodatnihUsluga.pisanjeDodatnihUsluga();
		
		menadzerDodatnihUsluga.ucitavanjeDodatnihUsluga();
		
		assertEquals(1, menadzerDodatnihUsluga.dodatneUsluge.size());
	}

	@Test
	void testPisanjeDodatnihUsluga() {
		String usluga = "usluga";
		
		menadzerDodatnihUsluga.dodavanjeDodatnihUsluga(usluga);
		
		menadzerDodatnihUsluga.pisanjeDodatnihUsluga();
		
		String pathString="datoteke"+File.separator+"DodatneUsluge.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("usluga", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDodavanjeDodatnihUsluga() {
		String usluga = "usluga";
		
		menadzerDodatnihUsluga.dodavanjeDodatnihUsluga(usluga);
		
		assertEquals(1, menadzerDodatnihUsluga.dodatneUsluge.size());
		assertEquals("usluga", menadzerDodatnihUsluga.dodatneUsluge.get(usluga).getNaziv());
	}

	@Test
	void testBrisanjeDodatnihUsluga() {
		String usluga = "usluga";
		menadzerDodatnihUsluga.dodavanjeDodatnihUsluga(usluga);
		
		menadzerDodatnihUsluga.brisanjeDodatnihUsluga(usluga, menadzerCenovnika, menadzerRezervacija);
		
		assertEquals(0, menadzerDodatnihUsluga.dodatneUsluge.size());
		assertEquals(0, menadzerCenovnika.cenovnici.get(1).getCeneUsluga().size());
		assertEquals(0, menadzerRezervacija.rezervacije.get(1).getDodatneUsluge().size());
	}

}
