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
import projekat.enumi.StatusSobe;
import projekat.enumi.StrucnaSprema;
import projekat.korisnici.Sobarica;
import projekat.menadzeri.MenadzerSoba;
import projekat.menadzeri.MenadzerSobarica;
import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.sobe.Soba;

class MenadzerSobaricaTest {
	private static MenadzerSobarica menadzerSobarica;
	private static MenadzerSobarica menadzerSobaricaPravi;
	private static MenadzerSoba menadzerSoba;
	private static MenadzerSoba menadzerSobaPravi;
	private static MenadzerTipovaSoba menadzerTipovaSoba;
	private static MenadzerTipovaSoba menadzerTipovaSobaPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerSobarica=new MenadzerSobarica();
		menadzerSobaricaPravi=new MenadzerSobarica();
		menadzerSoba=new MenadzerSoba();
		menadzerSobaPravi=new MenadzerSoba();
		menadzerTipovaSoba=new MenadzerTipovaSoba();
		menadzerTipovaSobaPravi=new MenadzerTipovaSoba();
		menadzerTipovaSobaPravi.ucitavanjeTipovaSoba();
		menadzerSobaPravi.ucitavanjeSoba(menadzerTipovaSobaPravi);
		menadzerSobaricaPravi.ucitavanjeSobarica(menadzerSobaPravi);
		
		menadzerTipovaSoba.dodavanjeTipovaSoba("tip");
		menadzerSoba.dodavanjeSoba(menadzerTipovaSoba.tipoviSoba.get("tip"),StatusSobe.SLOBODNA,1,true,true,true,true);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerSobarica.brisanjeSobarica("sobarica");
		menadzerSobarica.pisanjeSobarica();
		
		menadzerSobaricaPravi.pisanjeSobarica();
		menadzerTipovaSobaPravi.pisanjeTipovaSoba();
		menadzerSobaPravi.pisanjeSoba();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerSobarica.getSobarice().clear();
	}

	@Test
	void testUcitavanjeSobarica() {
		String korisnickoIme="sobarica";
		String lozinka="sobarica";
		String ime="sobarica";
		String prezime="sobarica";
		Pol pol=Pol.ZENSKI;
		LocalDate datumRodjenja=LocalDate.now();
		String telefon="0600000000";
		String adresa="adresa";
		StrucnaSprema strucnaSprema=StrucnaSprema.DOKTORSKA;
		int staz=0;
		ArrayList<Soba> dodeljeneSobe=new ArrayList<Soba>();
		Soba soba=menadzerSoba.sobe.get(1);
		dodeljeneSobe.add(soba);
		menadzerSobarica.dodavanjeSobarica(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,strucnaSprema, staz, dodeljeneSobe);
		menadzerSobarica.pisanjeSobarica();
		
		menadzerSobarica.ucitavanjeSobarica(menadzerSoba);
		
		assertEquals(1, menadzerSobarica.getSobarice().size());
	}

	@Test
	void testPisanjeSobarica() {
		String korisnickoIme="sobarica";
		String lozinka="sobarica";
		String ime="sobarica";
		String prezime="sobarica";
		Pol pol=Pol.ZENSKI;
		LocalDate datumRodjenja=LocalDate.now();
		String telefon="0600000000";
		String adresa="adresa";
		StrucnaSprema strucnaSprema=StrucnaSprema.DOKTORSKA;
		int staz=0;
		ArrayList<Soba> dodeljeneSobe=new ArrayList<Soba>();
		Soba soba=menadzerSoba.sobe.get(1);
		dodeljeneSobe.add(soba);
		
		menadzerSobarica.dodavanjeSobarica(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,strucnaSprema, staz, dodeljeneSobe);
		
		menadzerSobarica.pisanjeSobarica();
		
		String pathString="datoteke"+File.separator+"Sobarice.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("sobarica|sobarica|ZENSKI|"+LocalDate.now()+"|0600000000|adresa|sobarica|sobarica|DOKTORSKA|0|1", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDodavanjeSobarica() {
		String korisnickoIme="sobarica";
		String lozinka="sobarica";
		String ime="sobarica";
		String prezime="sobarica";
		Pol pol=Pol.ZENSKI;
		LocalDate datumRodjenja=LocalDate.now();
		String telefon="0600000000";
		String adresa="adresa";
		StrucnaSprema strucnaSprema=StrucnaSprema.DOKTORSKA;
		int staz=0;
		ArrayList<Soba> dodeljeneSobe=new ArrayList<Soba>();
		Soba soba=menadzerSoba.sobe.get(1);
		dodeljeneSobe.add(soba);
		
		menadzerSobarica.dodavanjeSobarica(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,strucnaSprema, staz, dodeljeneSobe);
		
		assertEquals(1, menadzerSobarica.getSobarice().size());
		assertEquals(korisnickoIme, menadzerSobarica.getSobarice().get(korisnickoIme).getKorisnickoIme());
		assertEquals(1, menadzerSobarica.getSobarice().get(korisnickoIme).getDodeljeneSobe().size());
	}

	@Test
	void testIzmenaSobarica() {
		String korisnickoIme="sobarica";
		String lozinka="sobarica";
		String ime="sobarica";
		String prezime="sobarica";
		Pol pol=Pol.ZENSKI;
		LocalDate datumRodjenja=LocalDate.now();
		String telefon="0600000000";
		String adresa="adresa";
		StrucnaSprema strucnaSprema=StrucnaSprema.DOKTORSKA;
		int staz=0;
		ArrayList<Soba> dodeljeneSobe=new ArrayList<Soba>();
		Soba soba=menadzerSoba.sobe.get(1);
		dodeljeneSobe.add(soba);
		
		menadzerSobarica.dodavanjeSobarica(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,strucnaSprema, staz, dodeljeneSobe);
		
		Sobarica sobarica=menadzerSobarica.getSobarice().get(korisnickoIme);
		String imeIzmena="sobaricaIzmena";
		
		menadzerSobarica.izmenaSobarica(imeIzmena, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,strucnaSprema, staz, dodeljeneSobe);
		
		assertEquals(imeIzmena, sobarica.getIme());
	}

	@Test
	void testBrisanjeSobarica() {
		String korisnickoIme="sobarica";
		String lozinka="sobarica";
		String ime="sobarica";
		String prezime="sobarica";
		Pol pol=Pol.ZENSKI;
		LocalDate datumRodjenja=LocalDate.now();
		String telefon="0600000000";
		String adresa="adresa";
		StrucnaSprema strucnaSprema=StrucnaSprema.DOKTORSKA;
		int staz=0;
		ArrayList<Soba> dodeljeneSobe=new ArrayList<Soba>();
		Soba soba=menadzerSoba.sobe.get(1);
		dodeljeneSobe.add(soba);
		
		menadzerSobarica.dodavanjeSobarica(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,strucnaSprema, staz, dodeljeneSobe);
		
		menadzerSobarica.brisanjeSobarica(korisnickoIme);
		
		assertEquals(0, menadzerSobarica.getSobarice().size());
	}

}
