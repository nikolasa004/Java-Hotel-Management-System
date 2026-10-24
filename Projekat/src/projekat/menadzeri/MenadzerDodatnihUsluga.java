package projekat.menadzeri;

import projekat.rezervacije.Rezervacija;
import projekat.usluge.Cenovnik;
import projekat.usluge.DodatnaUsluga;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;

public class MenadzerDodatnihUsluga {
	// mapa svih dodatnih usluga, kljuc je naziv usluge
	public HashMap<String, DodatnaUsluga> dodatneUsluge = new HashMap<String, DodatnaUsluga>();

	// ucitavanje dodatnih usluga iz fajla
	public void ucitavanjeDodatnihUsluga() {
		String pathString="datoteke"+File.separator+"DodatneUsluge.txt";
		Path path=Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);
			for (String linija : linije) {
				String[] delovi = linija.split("\\|");
				String naziv = delovi[0];
				DodatnaUsluga usluga = new DodatnaUsluga(naziv);
				dodatneUsluge.put(naziv, usluga);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// upisivanje dodatnih usluga u fajl
	public void pisanjeDodatnihUsluga() {
		String pathString = "datoteke" + File.separator + "DodatneUsluge.txt";
		Path path = Path.of(pathString);

		if (dodatneUsluge.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean prvi = true;
		for (DodatnaUsluga usluga : dodatneUsluge.values()) {
			try {
				if (prvi) {
					Files.writeString(path, usluga.getNaziv() + "\n");
					prvi = false;
				} else {
					Files.writeString(path, usluga.getNaziv() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud metode za dodatne usluge
	public void dodavanjeDodatnihUsluga(String naziv) {
		DodatnaUsluga usluga = new DodatnaUsluga(naziv);
		dodatneUsluge.put(naziv, usluga);
		pisanjeDodatnihUsluga();
	}
	public void pregledDodatnihUsluga()
	{
		for (DodatnaUsluga usluga : dodatneUsluge.values()) {
			System.out.println(usluga);
		}
	}
	public void brisanjeDodatnihUsluga(String naziv,MenadzerCenovnika menadzerCenovnika,MenadzerRezervacija menadzerRezervacija)
	{
		for (Cenovnik cenovnik : menadzerCenovnika.cenovnici.values()) {
			for (DodatnaUsluga usluga : cenovnik.getCeneUsluga().keySet()) {
				if (usluga.getNaziv().equals(naziv)) {
					cenovnik.getCeneUsluga().remove(usluga);
					break;
				}
			}
		}
		menadzerCenovnika.pisanjeCenovnika();
		for(Rezervacija rezervacija:menadzerRezervacija.rezervacije.values())
        {
			for(DodatnaUsluga usluga:rezervacija.getDodatneUsluge())
            {
				if(usluga.getNaziv().equals(naziv))
                {
					rezervacija.getDodatneUsluge().remove(usluga);
					break;
                }
            }
        }
		menadzerRezervacija.pisanjeRezervacija();
		dodatneUsluge.remove(naziv);
		pisanjeDodatnihUsluga();
	}
}
