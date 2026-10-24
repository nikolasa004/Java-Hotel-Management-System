package projekat.testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import projekat.enumi.Pol;
import projekat.enumi.StrucnaSprema;
import projekat.menadzeri.Menadzer;
import projekat.menadzeri.MenadzerAdministratora;

class MenadzerTest {
	private static Menadzer menadzer;
	private static MenadzerAdministratora menadzerAdministratora;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzer = new Menadzer();
		menadzerAdministratora = new MenadzerAdministratora();
		menadzerAdministratora.ucitavanjeAdministratora();
		
		menadzer.getMenadzerAdministratora().dodavanjeAdministratora("admin", "admin", Pol.MUSKI, LocalDate.now(),"0111111","adresa","admin","123", StrucnaSprema.DOKTORSKA, 0);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzer.getMenadzerAdministratora().getAdministratori().clear();
		menadzer.getMenadzerAdministratora().pisanjeAdministratora();
		
		menadzerAdministratora.pisanjeAdministratora();
	}

	@Test
	void testPrijava() {
		Object[] rezultat = menadzer.prijava("admin", "admin");
		boolean rez = (boolean) rezultat[0];
		String tip = (String) rezultat[1];
		assertEquals(false, rez);
		assertEquals("", tip);
		
		rezultat = menadzer.prijava("admin", "123");
		rez = (boolean) rezultat[0];
		tip = (String) rezultat[1];
		assertEquals(true, rez);
		assertEquals("Administrator", tip);
	}

}
