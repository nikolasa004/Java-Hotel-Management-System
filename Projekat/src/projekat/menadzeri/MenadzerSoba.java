package projekat.menadzeri;

import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.enumi.StatusSobe;
import projekat.korisnici.Sobarica;
import projekat.rezervacije.Rezervacija;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;

public class MenadzerSoba {
	//mapa svih soba, kljuc je broj sobe
	public HashMap<Integer, Soba> sobe = new HashMap<Integer, Soba>();
	
	//ucitavanje soba iz fajla
	public void ucitavanjeSoba(MenadzerTipovaSoba menadzerTipovaSoba) {
		String pathString="datoteke"+File.separator+"Sobe.txt";
		Path path=Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);
			for (String linija : linije) {
				String[] delovi = linija.split("\\|");
				
				String tipSobeString=delovi[0];
				TipSobe tipSobe=new TipSobe();
				tipSobe=menadzerTipovaSoba.tipoviSoba.get(tipSobeString);
				StatusSobe statusSobe=StatusSobe.valueOf(delovi[1]);
				int brojSobe=Integer.parseInt(delovi[2]);
				boolean klima_uredjaj=Boolean.parseBoolean(delovi[3]);
				boolean balkon=Boolean.parseBoolean(delovi[4]);
				boolean tv=Boolean.parseBoolean(delovi[5]);
				boolean pusacka=Boolean.parseBoolean(delovi[6]);
				Soba soba = new Soba(tipSobe, statusSobe, brojSobe, klima_uredjaj, balkon, tv, pusacka);
				sobe.put(brojSobe, soba);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//upisivanje soba u fajl
	public void pisanjeSoba() {
		String pathString="datoteke"+File.separator+"Sobe.txt";
		Path path = Path.of(pathString);
		
		if (sobe.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi = true;
		for (Soba soba : sobe.values()) {
			try {
				if (prvi) {
					Files.writeString(path, soba.upis()+"\n");
					prvi = false;
				} else {
					Files.writeString(path, soba.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud metode za sobe
	public void dodavanjeSoba(TipSobe tipSobe, StatusSobe statusSobe, int brojSobe, boolean klima_uredjaj, boolean balkon, boolean tv, boolean pusacka)
	{
		Soba soba = new Soba(tipSobe, statusSobe, brojSobe, klima_uredjaj, balkon, tv, pusacka);
		sobe.put(brojSobe, soba);
		pisanjeSoba();
	}
	public void pregledSoba()
	{
		for (Soba soba : sobe.values()) {
			System.out.println(soba);
		}
	}
	public void izmenaSoba(int brojSobe,TipSobe tipSobe, StatusSobe statusSobe, boolean klima_uredjaj, boolean balkon, boolean tv, boolean pusacka)
	{	
		Soba soba = sobe.get(brojSobe);
        soba.setTipSobe(tipSobe);
        soba.setStatusSobe(statusSobe);
        soba.setKlima_uredjaj(klima_uredjaj);
        soba.setBalkon(balkon);
        soba.setTv(tv);
        soba.setPusacka(pusacka);
        pisanjeSoba();
	}
	public void brisanjeSoba(int brojSobe,MenadzerRezervacija menadzerRezervacija,MenadzerSobarica menadzerSobarica)
	{
		for (Rezervacija rezervacija : menadzerRezervacija.rezervacije.values()) {
			if (rezervacija.getSoba().getBrojSobe() == brojSobe) {
				rezervacija.getSoba().setBrojSobe(-1);
			}
		}
		menadzerRezervacija.pisanjeRezervacija();
		for(Sobarica sobarica:menadzerSobarica.getSobarice().values())
		{
			for (Soba soba : sobarica.getDodeljeneSobe()) {
				if (soba.getBrojSobe() == brojSobe) {
					sobarica.getDodeljeneSobe().remove(soba);
					break;
				}
			}
		}
		menadzerSobarica.pisanjeSobarica();
		sobe.remove(brojSobe);
		pisanjeSoba();
	}
}
