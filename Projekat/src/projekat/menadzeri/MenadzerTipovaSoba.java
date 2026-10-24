package projekat.menadzeri;

import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.usluge.Cenovnik;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;

public class MenadzerTipovaSoba {
	//mapa svih tipova soba, kljuc je naziv tipa
	public HashMap<String, TipSobe> tipoviSoba = new HashMap<String, TipSobe>();
	
	//ucitavanje tipova soba iz fajla
	public void ucitavanjeTipovaSoba() {
		String pathString="datoteke"+File.separator+"TipoviSoba.txt";
		Path path=Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);
			for (String linija : linije) {
				String[] delovi = linija.split("\\|");
				
				String tip=delovi[0];
				TipSobe tipSobe=new TipSobe(tip);
				tipoviSoba.put(tip, tipSobe);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//upisivanje tipova soba u fajl
	public void pisanjeTipovaSoba() {
		String pathString="datoteke"+File.separator+"TipoviSoba.txt";
		Path path = Path.of(pathString);
		
		if (tipoviSoba.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi = true;
		for (TipSobe tipSobe : tipoviSoba.values()) {
			try {
				if (prvi) {
					Files.writeString(path, tipSobe.upis()+"\n");
					prvi = false;
				} else {
					Files.writeString(path, tipSobe.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud za tip sobe
	public void dodavanjeTipovaSoba(String tip)
	{
		TipSobe tipSobe = new TipSobe(tip);
		tipoviSoba.put(tip, tipSobe);
		pisanjeTipovaSoba();
	}
	public void pregledTipovaSoba()
	{
		for (TipSobe tipSobe : tipoviSoba.values()) {
			System.out.println(tipSobe);
		}
	}
	public void brisanjeTipovaSoba(String tip,MenadzerCenovnika menadzerCenovnika,MenadzerSoba menadzerSoba,MenadzerRezervacija menadzerRezervacija,MenadzerSobarica menadzerSobarica,MenadzerGostiju menadzerGostiju)
	{
		for (Cenovnik cenovnik : menadzerCenovnika.cenovnici.values()) {
			for (TipSobe tipSobe : cenovnik.getCeneSoba().keySet()) {
				if (tipSobe.getTip().equals(tip)) {
					cenovnik.getCeneSoba().remove(tipSobe);
					break;
				}
			}
		}
		menadzerCenovnika.pisanjeCenovnika();
		for (Soba soba : menadzerSoba.sobe.values()) {
			if (soba.getTipSobe().getTip().equals(tip)) {
				menadzerSoba.brisanjeSoba(soba.getBrojSobe(), menadzerRezervacija,menadzerSobarica);
			}
		}
		menadzerSoba.pisanjeSoba();
		for (Rezervacija rezervacija : menadzerRezervacija.rezervacije.values()) {
			if (rezervacija.getTipSobe().getTip().equals(tip)) {
				menadzerRezervacija.brisanjeRezervacija(rezervacija.getId(),menadzerGostiju);
			}
		}
		menadzerRezervacija.pisanjeRezervacija();
		tipoviSoba.remove(tip);
		pisanjeTipovaSoba();
	}
}
