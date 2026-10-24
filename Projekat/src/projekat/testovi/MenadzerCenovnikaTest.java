package projekat.testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projekat.menadzeri.MenadzerCenovnika;
import projekat.menadzeri.MenadzerDodatnihUsluga;
import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.sobe.TipSobe;
import projekat.usluge.Cenovnik;
import projekat.usluge.DodatnaUsluga;

class MenadzerCenovnikaTest {
	
	private static MenadzerCenovnika menadzerCenovnika;
	private static MenadzerCenovnika menadzerCenovnikaPravi;
	private static MenadzerTipovaSoba menadzerTipovaSoba;
	private static MenadzerTipovaSoba menadzerTipovaSobaPravi;
	private static MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private static MenadzerDodatnihUsluga menadzerDodatnihUslugaPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerCenovnika = new MenadzerCenovnika();
        menadzerCenovnikaPravi = new MenadzerCenovnika();
        menadzerTipovaSoba = new MenadzerTipovaSoba();
        menadzerTipovaSobaPravi = new MenadzerTipovaSoba();
        menadzerDodatnihUsluga = new MenadzerDodatnihUsluga();
        menadzerDodatnihUslugaPravi = new MenadzerDodatnihUsluga();
        menadzerTipovaSobaPravi.ucitavanjeTipovaSoba();
        menadzerDodatnihUslugaPravi.ucitavanjeDodatnihUsluga();
        menadzerCenovnikaPravi.ucitavanjeCenovnika(menadzerTipovaSobaPravi,menadzerDodatnihUslugaPravi);
        
        menadzerDodatnihUsluga.dodavanjeDodatnihUsluga("usluga");
		menadzerTipovaSoba.dodavanjeTipovaSoba("tip");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerCenovnika.brisanjeCenovnika(1);
		menadzerCenovnika.pisanjeCenovnika();
		
		menadzerCenovnikaPravi.pisanjeCenovnika();
		menadzerTipovaSobaPravi.pisanjeTipovaSoba();
		menadzerDodatnihUslugaPravi.pisanjeDodatnihUsluga();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerCenovnika.cenovnici.clear();
	}
	
	@Test
	void testPronalazakCenovnika()
	{
		int id=1;
		LocalDate datumOd = LocalDate.now();
		LocalDate datumDo = LocalDate.now().plusDays(10);
		HashMap<TipSobe,Double> ceneSoba = new HashMap<TipSobe,Double>();
		HashMap<DodatnaUsluga,Double> ceneUsluga = new HashMap<DodatnaUsluga,Double>();
		ceneSoba.put(menadzerTipovaSoba.tipoviSoba.get("tip"), 100.0);
		ceneUsluga.put(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"), 50.0);
		menadzerCenovnika.dodavanjeCenovnika(id, datumOd, datumDo, ceneSoba, ceneUsluga);
		menadzerCenovnika.pisanjeCenovnika();
		
		LocalDate datum = LocalDate.now();
		
		Cenovnik cenovnik = menadzerCenovnika.pronalazakCenovnika(datum);
		
		assertEquals(id, cenovnik.getId());
	}

	@Test
	void testUcitavanjeCenovnika() {
		int id=1;
		LocalDate datumOd = LocalDate.now();
		LocalDate datumDo = LocalDate.now().plusDays(10);
		HashMap<TipSobe,Double> ceneSoba = new HashMap<TipSobe,Double>();
		HashMap<DodatnaUsluga,Double> ceneUsluga = new HashMap<DodatnaUsluga,Double>();
		ceneSoba.put(menadzerTipovaSoba.tipoviSoba.get("tip"), 100.0);
		ceneUsluga.put(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"), 50.0);
		menadzerCenovnika.dodavanjeCenovnika(id, datumOd, datumDo, ceneSoba, ceneUsluga);
		menadzerCenovnika.pisanjeCenovnika();
		
		menadzerCenovnika.ucitavanjeCenovnika(menadzerTipovaSoba, menadzerDodatnihUsluga);
		
		assertEquals(1, menadzerCenovnika.cenovnici.size());
	}

	@Test
	void testPisanjeCenovnika() {
		int id = 1;
		LocalDate datumOd = LocalDate.now();
		LocalDate datumDo = LocalDate.now().plusDays(10);
		HashMap<TipSobe, Double> ceneSoba = new HashMap<TipSobe, Double>();
		HashMap<DodatnaUsluga, Double> ceneUsluga = new HashMap<DodatnaUsluga, Double>();
		ceneSoba.put(menadzerTipovaSoba.tipoviSoba.get("tip"), 100.0);
		ceneUsluga.put(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"), 50.0);
		menadzerCenovnika.dodavanjeCenovnika(id, datumOd, datumDo, ceneSoba, ceneUsluga);
		
		menadzerCenovnika.pisanjeCenovnika();
		
		String pathString="datoteke"+File.separator+"Cenovnici.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("1|"+datumOd+"|"+datumDo+"|tip-100.0|usluga-50.0", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDodavanjeCenovnika() {
		int id = 1;
		LocalDate datumOd = LocalDate.now();
		LocalDate datumDo = LocalDate.now().plusDays(10);
		HashMap<TipSobe, Double> ceneSoba = new HashMap<TipSobe, Double>();
		HashMap<DodatnaUsluga, Double> ceneUsluga = new HashMap<DodatnaUsluga, Double>();
		ceneSoba.put(menadzerTipovaSoba.tipoviSoba.get("tip"), 100.0);
		ceneUsluga.put(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"), 50.0);
		menadzerCenovnika.dodavanjeCenovnika(id, datumOd, datumDo, ceneSoba, ceneUsluga);

		assertEquals(1, menadzerCenovnika.cenovnici.size());
	}

	@Test
	void testIzmenaCenovnika() {
		int id = 1;
        LocalDate datumOd = LocalDate.now();
        LocalDate datumDo = LocalDate.now().plusDays(10);
        HashMap<TipSobe, Double> ceneSoba = new HashMap<TipSobe, Double>();
        HashMap<DodatnaUsluga, Double> ceneUsluga = new HashMap<DodatnaUsluga, Double>();
        ceneSoba.put(menadzerTipovaSoba.tipoviSoba.get("tip"), 100.0);
        ceneUsluga.put(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"), 50.0);
        menadzerCenovnika.dodavanjeCenovnika(id, datumOd, datumDo, ceneSoba, ceneUsluga);
        
        LocalDate datumOdIzmena = LocalDate.now().plusDays(1);
        Cenovnik cenovnik = menadzerCenovnika.cenovnici.get(1);
        
        menadzerCenovnika.izmenaCenovnika(id, datumOdIzmena, datumDo, ceneSoba, ceneUsluga);
        
        assertEquals(datumOdIzmena, cenovnik.getDatumOd());
	}

	@Test
	void testBrisanjeCenovnika() {
		int id = 1;
        LocalDate datumOd = LocalDate.now();
        LocalDate datumDo = LocalDate.now().plusDays(10);
        HashMap<TipSobe, Double> ceneSoba = new HashMap<TipSobe, Double>();
        HashMap<DodatnaUsluga, Double> ceneUsluga = new HashMap<DodatnaUsluga, Double>();
        ceneSoba.put(menadzerTipovaSoba.tipoviSoba.get("tip"), 100.0);
        ceneUsluga.put(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"), 50.0);
        menadzerCenovnika.dodavanjeCenovnika(id, datumOd, datumDo, ceneSoba, ceneUsluga);
        
        menadzerCenovnika.brisanjeCenovnika(id);
        
        assertEquals(0, menadzerCenovnika.cenovnici.size());
	}

}
