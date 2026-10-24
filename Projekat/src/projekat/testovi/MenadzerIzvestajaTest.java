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
import projekat.menadzeri.Menadzer;
import projekat.menadzeri.MenadzerAdministratora;
import projekat.menadzeri.MenadzerIzvestaja;

class MenadzerIzvestajaTest {
	
	private static MenadzerIzvestaja menadzerIzvestaja;
	private static MenadzerIzvestaja menadzerIzvestajaPravi;
	private static Menadzer menadzer;
	private static MenadzerAdministratora menadzerAdministratora;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		menadzer = new Menadzer();
        menadzerIzvestaja=new MenadzerIzvestaja();
        menadzerIzvestajaPravi=new MenadzerIzvestaja();
        menadzerAdministratora=new MenadzerAdministratora();
        menadzerIzvestajaPravi.ucitavanjeIzvestaja(menadzer);
        menadzerAdministratora.ucitavanjeAdministratora();
        
        menadzer.getMenadzerAdministratora().dodavanjeAdministratora("admin", "admin", Pol.MUSKI, LocalDate.now(),"admin","admin","admin","admin", StrucnaSprema.DOKTORSKA, 1000);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		menadzerIzvestaja.rashodi.clear();
		menadzerIzvestaja.pisanjeIzvestajaRashoda();
		
		menadzerIzvestajaPravi.pisanjeIzvestajaRashoda();
		menadzerAdministratora.pisanjeAdministratora();
	}

	@BeforeEach
	void setUp() throws Exception {
		menadzerIzvestaja.rashodi.clear();
	}

	@Test
	void testDodajRashode() {
		menadzerIzvestaja.dodajRashode(menadzer);
		
		LocalDate datum=LocalDate.now();
		if(datum.getDayOfMonth()>=20)
		{
			String suma=menadzerIzvestaja.rashodi.get(0)[1];
			assertEquals("1000", suma);
		}
		else {
			assertEquals(0, menadzerIzvestaja.rashodi.size());
		}
		
	}

	@Test
	void testUcitavanjeIzvestaja() {
		menadzerIzvestaja.dodajRashode(menadzer);
		menadzerIzvestaja.pisanjeIzvestajaRashoda();
		menadzerIzvestaja.rashodi.clear();
		menadzerIzvestaja.ucitavanjeIzvestaja(menadzer);
		
		LocalDate datum=LocalDate.now();
		if (datum.getDayOfMonth() >= 20) {
			String suma = menadzerIzvestaja.rashodi.get(0)[1];
			assertEquals("1000", suma);
		} else {
			assertEquals(0, menadzerIzvestaja.rashodi.size());
		}
	}

	@Test
	void testPisanjeIzvestajaRashoda() {
		menadzerIzvestaja.dodajRashode(menadzer);
		menadzerIzvestaja.pisanjeIzvestajaRashoda();
		
		String pathString="datoteke"+File.separator+"IzvestajiRashodi.txt";
		Path path = Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);
			LocalDate datum=LocalDate.now();
			if (datum.getDayOfMonth() >= 20) {
				assertEquals("1000", linije.get(0).split("|")[1]);
			} else {
				assertEquals(0, linije.size());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
