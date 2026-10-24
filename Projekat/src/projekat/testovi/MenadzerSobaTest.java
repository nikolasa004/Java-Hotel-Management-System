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
import projekat.enumi.StrucnaSprema;
import projekat.menadzeri.MenadzerDodatnihUsluga;
import projekat.menadzeri.MenadzerRezervacija;
import projekat.menadzeri.MenadzerSoba;
import projekat.menadzeri.MenadzerSobarica;
import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.usluge.DodatnaUsluga;

class MenadzerSobaTest {
	
	private static MenadzerSoba menadzerSoba;
	private static MenadzerSoba menadzerSobaPravi;
	private static MenadzerTipovaSoba menadzerTipovaSoba;
	private static MenadzerTipovaSoba menadzerTipovaSobaPravi;
	private static MenadzerRezervacija menadzerRezervacija;
	private static MenadzerRezervacija menadzerRezervacijaPravi;
	private static MenadzerSobarica menadzerSobarica;
	private static MenadzerSobarica menadzerSobaricaPravi;
	private static MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private static MenadzerDodatnihUsluga menadzerDodatnihUslugaPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerSoba = new MenadzerSoba();
        menadzerSobaPravi = new MenadzerSoba();
        menadzerTipovaSoba = new MenadzerTipovaSoba();
        menadzerTipovaSobaPravi = new MenadzerTipovaSoba();
        menadzerRezervacija = new MenadzerRezervacija();
        menadzerRezervacijaPravi = new MenadzerRezervacija();
        menadzerSobarica = new MenadzerSobarica();
        menadzerSobaricaPravi = new MenadzerSobarica();
        menadzerDodatnihUsluga = new MenadzerDodatnihUsluga();
        menadzerDodatnihUslugaPravi = new MenadzerDodatnihUsluga();
        menadzerTipovaSobaPravi.ucitavanjeTipovaSoba();
        menadzerSobaPravi.ucitavanjeSoba(menadzerTipovaSobaPravi);
        menadzerDodatnihUslugaPravi.ucitavanjeDodatnihUsluga();
        menadzerRezervacijaPravi.ucitavanjeRezervacija(menadzerTipovaSobaPravi, menadzerDodatnihUslugaPravi, menadzerSobaPravi);
        menadzerSobaricaPravi.ucitavanjeSobarica(menadzerSobaPravi);
        
        menadzerDodatnihUsluga.dodavanjeDodatnihUsluga("usluga");
		menadzerTipovaSoba.dodavanjeTipovaSoba("tip");
        menadzerSoba.dodavanjeSoba(menadzerTipovaSoba.tipoviSoba.get("tip"),StatusSobe.SLOBODNA,1,true,true,true,true);
        ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
        usluge.add(menadzerDodatnihUsluga.dodatneUsluge.get("usluga"));
        menadzerRezervacija.dodavanjeRezervacija(1,StatusRezervacije.POTVRDJENA,LocalDate.now(),menadzerTipovaSoba.tipoviSoba.get("tip"),1,1,usluge,1000.0,"gost","napomena");
        ArrayList<Soba> sobe = new ArrayList<Soba>();
        sobe.add(menadzerSoba.sobe.get(1));
        menadzerSobarica.dodavanjeSobarica("ime", "prezime",Pol.ZENSKI, LocalDate.now(), "telefon", "adresa","korisnickoIme","lozinka",StrucnaSprema.OSNOVNA, 1,sobe);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerSoba.brisanjeSoba(1,menadzerRezervacija,menadzerSobarica);
		menadzerSoba.pisanjeSoba();
		
		menadzerDodatnihUslugaPravi.pisanjeDodatnihUsluga();
		menadzerTipovaSobaPravi.pisanjeTipovaSoba();
		menadzerSobaPravi.pisanjeSoba();
		menadzerRezervacijaPravi.pisanjeRezervacija();
		menadzerSobaricaPravi.pisanjeSobarica();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerSoba.sobe.clear();
	}

	@Test
	void testUcitavanjeSoba() {
		TipSobe tipSobe = menadzerTipovaSoba.tipoviSoba.get("tip");
		StatusSobe statusSobe = StatusSobe.SLOBODNA;
		int brojSobe = 1;
		boolean klima = true;
		boolean tv = true;
		boolean balkon = true;
		boolean pusacka = true;
		menadzerSoba.dodavanjeSoba(tipSobe, statusSobe, brojSobe, klima, balkon, tv, pusacka);
		menadzerSoba.pisanjeSoba();
		
		menadzerSoba.ucitavanjeSoba(menadzerTipovaSoba);
		
		assertEquals(1, menadzerSoba.sobe.size());
	}

	@Test
	void testPisanjeSoba() {
		TipSobe tipSobe = menadzerTipovaSoba.tipoviSoba.get("tip");
		StatusSobe statusSobe = StatusSobe.SLOBODNA;
		int brojSobe = 1;
		boolean klima = true;
		boolean tv = true;
		boolean balkon = true;
		boolean pusacka = true;
		menadzerSoba.dodavanjeSoba(tipSobe, statusSobe, brojSobe, klima, balkon, tv, pusacka);
		
		menadzerSoba.pisanjeSoba();
		
		String pathString="datoteke"+File.separator+"Sobe.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("tip|SLOBODNA|1|true|true|true|true", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDodavanjeSoba() {
		TipSobe tipSobe = menadzerTipovaSoba.tipoviSoba.get("tip");
		StatusSobe statusSobe = StatusSobe.SLOBODNA;
		int brojSobe = 1;
		boolean klima = true;
		boolean tv = true;
		boolean balkon = true;
		boolean pusacka = true;
		
		menadzerSoba.dodavanjeSoba(tipSobe, statusSobe, brojSobe, klima, balkon, tv, pusacka);
		
		assertEquals(1, menadzerSoba.sobe.size());
		assertEquals(1, menadzerSoba.sobe.get(1).getBrojSobe());
	}

	@Test
	void testIzmenaSoba() {
		TipSobe tipSobe = menadzerTipovaSoba.tipoviSoba.get("tip");
		StatusSobe statusSobe = StatusSobe.SLOBODNA;
		int brojSobe = 1;
		boolean klima = true;
		boolean tv = true;
		boolean balkon = true;
		boolean pusacka = true;
		menadzerSoba.dodavanjeSoba(tipSobe, statusSobe, brojSobe, klima, balkon, tv, pusacka);
		
		Soba soba=menadzerSoba.sobe.get(1);
		boolean klima1 = false;
		menadzerSoba.izmenaSoba(brojSobe, tipSobe, statusSobe, klima1, balkon, tv, pusacka);
		
		assertEquals(1, menadzerSoba.sobe.size());
		assertEquals(false,soba.isKlima_uredjaj());
	}

	@Test
	void testBrisanjeSoba() {
		TipSobe tipSobe = menadzerTipovaSoba.tipoviSoba.get("tip");
		StatusSobe statusSobe = StatusSobe.SLOBODNA;
		int brojSobe = 1;
		boolean klima = true;
		boolean tv = true;
		boolean balkon = true;
		boolean pusacka = true;
		menadzerSoba.dodavanjeSoba(tipSobe, statusSobe, brojSobe, klima, balkon, tv, pusacka);
		
		menadzerSoba.brisanjeSoba(1,menadzerRezervacija,menadzerSobarica);
		
		assertEquals(0, menadzerSoba.sobe.size());
		assertEquals(-1, menadzerRezervacija.rezervacije.get(1).getSoba().getBrojSobe());
		assertEquals(0, menadzerSobarica.getSobarice().get("korisnickoIme").getDodeljeneSobe().size());
	}

}
