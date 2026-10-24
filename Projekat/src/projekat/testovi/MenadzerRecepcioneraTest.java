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
import projekat.korisnici.Recepcioner;
import projekat.menadzeri.MenadzerRecepcionera;

class MenadzerRecepcioneraTest {
	private static MenadzerRecepcionera menadzerRecepcionera;
	private static MenadzerRecepcionera menadzerRecepcioneraPravi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzerRecepcionera = new MenadzerRecepcionera();
		menadzerRecepcioneraPravi = new MenadzerRecepcionera();
		menadzerRecepcioneraPravi.ucitavanjeRecepcionera();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerRecepcionera.brisanjeRecepcionera("recepcioner");
		menadzerRecepcionera.pisanjeRecepcionera();
		
		menadzerRecepcioneraPravi.pisanjeRecepcionera();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		menadzerRecepcionera.getRecepcioneri().clear();
	}

	@Test
	void testDodavanjeRecepcionera() {
		String korisnickoIme = "recepcioner";
		String lozinka = "recepcioner";
		String ime = "recepcioner";
		String prezime = "recepcioner";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "recepcioner";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;	
		int staz = 0;
		
		menadzerRecepcionera.dodavanjeRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,  strucnaSprema, staz);
		
		assertEquals(1, menadzerRecepcionera.getRecepcioneri().size());
		assertEquals(korisnickoIme, menadzerRecepcionera.getRecepcioneri().get(korisnickoIme).getKorisnickoIme());
	}

	@Test
	void testPisanjeRecepcionera() {
		String korisnickoIme = "recepcioner";
		String lozinka = "recepcioner";
		String ime = "recepcioner";
		String prezime = "recepcioner";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "recepcioner";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;	
		int staz = 0;
		
		menadzerRecepcionera.dodavanjeRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,  strucnaSprema, staz);
		
		menadzerRecepcionera.pisanjeRecepcionera();
		
		String pathString="datoteke"+File.separator+"Recepcioneri.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);

			assertEquals(1, linije.size());
			assertEquals("recepcioner|recepcioner|MUSKI|"+datumRodjenja+"|123456789|recepcioner|recepcioner|recepcioner|DOKTORSKA|0", linije.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testUcitavanjeRecepcionera() {
		String korisnickoIme = "recepcioner";
		String lozinka = "recepcioner";
		String ime = "recepcioner";
		String prezime = "recepcioner";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "recepcioner";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;	
		int staz = 0;
		menadzerRecepcionera.dodavanjeRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,  strucnaSprema, staz);
		menadzerRecepcionera.pisanjeRecepcionera();
		
		menadzerRecepcionera.ucitavanjeRecepcionera();
		
		assertEquals(1, menadzerRecepcionera.getRecepcioneri().size());
	}
	
	@Test
	void testIzmenaRecepcionera() {
		String korisnickoIme = "recepcioner";
		String lozinka = "recepcioner";
		String ime = "recepcioner";
		String prezime = "recepcioner";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "recepcioner";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;	
		int staz = 0;
		
		menadzerRecepcionera.dodavanjeRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,  strucnaSprema, staz);
		
		Recepcioner recepcioner = menadzerRecepcionera.getRecepcioneri().get(korisnickoIme);
		String ime1 = "recepcioner1";
		
		menadzerRecepcionera.izmenaRecepcionera(ime1, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka, strucnaSprema, staz);
		
		assertEquals(ime1, recepcioner.getIme());
	}

	@Test
	void testBrisanjeRecepcionera() {
		String korisnickoIme = "recepcioner";
		String lozinka = "recepcioner";
		String ime = "recepcioner";
		String prezime = "recepcioner";
		Pol pol = Pol.MUSKI;
		LocalDate datumRodjenja = LocalDate.now();
		String telefon = "123456789";
		String adresa = "recepcioner";
		StrucnaSprema strucnaSprema = StrucnaSprema.DOKTORSKA;	
		int staz = 0;
		
		menadzerRecepcionera.dodavanjeRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa,korisnickoIme, lozinka,  strucnaSprema, staz);
		
		menadzerRecepcionera.brisanjeRecepcionera(korisnickoIme);
		
		assertEquals(0, menadzerRecepcionera.getRecepcioneri().size());
	}

}
