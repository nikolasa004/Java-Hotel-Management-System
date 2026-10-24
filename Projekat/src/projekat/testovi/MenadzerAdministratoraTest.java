package projekat.testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projekat.enumi.Pol;
import projekat.enumi.StrucnaSprema;
import projekat.korisnici.Administrator;
import projekat.menadzeri.MenadzerAdministratora;

class MenadzerAdministratoraTest {
	private static MenadzerAdministratora menadzerAdministratora;
	private static MenadzerAdministratora menadzerAdministratoraPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerAdministratora = new MenadzerAdministratora();
		menadzerAdministratoraPravi = new MenadzerAdministratora();
		menadzerAdministratoraPravi.ucitavanjeAdministratora();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerAdministratora.brisanjeAdministratora("admin");
		menadzerAdministratora.pisanjeAdministratora();
		
		menadzerAdministratoraPravi.pisanjeAdministratora();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		menadzerAdministratora.getAdministratori().clear();
	}

	@Test
	void testDodavanjeAdministratora() {
		String korisnickoIme= "admin";
		String lozinka = "admin";
		String ime = "admin";
		String prezime = "admin";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "admin";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;
		int staz = 1;
		
		menadzerAdministratora.dodavanjeAdministratora(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme,lozinka, strucnaSprema, staz);
		
		assertEquals(1, menadzerAdministratora.getAdministratori().size());
		assertEquals(korisnickoIme, menadzerAdministratora.getAdministratori().get(korisnickoIme).getKorisnickoIme());
	}
	
	@Test
	void testPisanjeAdministratora()
	{
		String korisnickoIme = "admin";
		String lozinka = "admin";
		String ime = "admin";
		String prezime = "admin";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "admin";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;
		int staz = 1;
		
		menadzerAdministratora.dodavanjeAdministratora(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme,
				lozinka, strucnaSprema, staz);
		
		menadzerAdministratora.pisanjeAdministratora();
		
		String pathString="datoteke"+File.separator+"Administratori.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("admin|admin|MUSKI|"+datumRodjenja+"|123456789|admin|admin|admin|DOKTORSKA|1", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testCitanjeAdministratora() {
		String korisnickoIme = "admin";
		String lozinka = "admin";
		String ime = "admin";
		String prezime = "admin";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "admin";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;
		int staz = 1;
		menadzerAdministratora.dodavanjeAdministratora(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme,
				lozinka, strucnaSprema, staz);
		menadzerAdministratora.pisanjeAdministratora();
		
		menadzerAdministratora.ucitavanjeAdministratora();
		
		assertEquals(1, menadzerAdministratora.getAdministratori().size());
	}

	@Test
	void testIzmenaAdministratora() {
		String korisnickoIme = "admin";
		String lozinka = "admin";
		String ime = "admin";
		String prezime = "admin";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "admin";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;
		int staz = 1;

		menadzerAdministratora.dodavanjeAdministratora(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme,lozinka, strucnaSprema, staz);
		
		Administrator administrator = menadzerAdministratora.getAdministratori().get(korisnickoIme);
		String novoIme = "admin1";
		
		menadzerAdministratora.izmenaAdministratora(novoIme, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz);
		
		assertEquals(novoIme, administrator.getIme());
	}

	@Test
	void testBrisanjeAdministratora() {
		String korisnickoIme = "admin";
		String lozinka = "admin";
		String ime = "admin";
		String prezime = "admin";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "admin";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;
		int staz = 1;

		menadzerAdministratora.dodavanjeAdministratora(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme,
				lozinka, strucnaSprema, staz);

		menadzerAdministratora.brisanjeAdministratora(korisnickoIme);
		
		assertEquals(0, menadzerAdministratora.getAdministratori().size());
	}

}
