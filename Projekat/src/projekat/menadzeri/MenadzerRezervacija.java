package projekat.menadzeri;

import projekat.enumi.StatusRezervacije;
import projekat.korisnici.Gost;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.rezervacije.Rezervacija;
import projekat.usluge.DodatnaUsluga;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenadzerRezervacija {
	// mapa svih rezervacija, kljuc je naziv rezervacije
	public HashMap<Integer, Rezervacija> rezervacije = new HashMap<Integer, Rezervacija>();

	// ucitavanje rezervacija iz fajla
	public void ucitavanjeRezervacija(MenadzerTipovaSoba menadzerTipovaSoba, MenadzerDodatnihUsluga menadzerDodatnihUsluga, MenadzerSoba menadzerSoba) {
		String pathString="datoteke"+File.separator+"Rezervacije.txt";
		Path path=Path.of(pathString);
		boolean trebaPisanje = false;
		try {
			List<String> linije = Files.readAllLines(path);
			for (String linija : linije) {
				String[] delovi = linija.split("\\|");
				
				int id=Integer.parseInt(delovi[0]);
				StatusRezervacije tipRezervacije=StatusRezervacije.valueOf(delovi[1]);
				LocalDate datumRezervacije=LocalDate.parse(delovi[2]);
				//ako je status na cekanju a prosao je datum rezervacije, menja se status na odbijeno jer je rezervacija istekla
				if (tipRezervacije == StatusRezervacije.NA_CEKANJU && datumRezervacije.isBefore(LocalDate.now())) {
					tipRezervacije = StatusRezervacije.ODBIJENA;
					trebaPisanje = true;
				}
				TipSobe tipSobe=menadzerTipovaSoba.tipoviSoba.get(delovi[3]);
				int brojOsoba=Integer.parseInt(delovi[4]);
				int brojNocenja=Integer.parseInt(delovi[5]);
				ArrayList<DodatnaUsluga> dodatneUsluge=new ArrayList<DodatnaUsluga>();
				if(delovi[6].length()>0)
				{
					String[] dodatneUslugeNiz=delovi[6].split(", ");
					for (String dodatnaUsluga : dodatneUslugeNiz) {
						dodatneUsluge.add(menadzerDodatnihUsluga.dodatneUsluge.get(dodatnaUsluga));
					}
				}
				double cena=Double.parseDouble(delovi[7]);
				if (trebaPisanje) {
					cena = 0;
				}
				String imeRezervacije=delovi[8];
				Soba soba=new Soba();
				soba.setBrojSobe(-1);
				if(!delovi[9].equals("-1"))
					soba=menadzerSoba.sobe.get(Integer.parseInt(delovi[9]));
				String napomena="";
                if(delovi.length==11)
                {
                	napomena=delovi[10];
                }
				
				Rezervacija rezervacija=new Rezervacija(id,tipRezervacije, datumRezervacije, tipSobe, brojOsoba, brojNocenja, dodatneUsluge, cena, imeRezervacije,soba,napomena);
				rezervacije.put(id, rezervacija);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (trebaPisanje) {
			pisanjeRezervacija();
		}
	}

	// upisivanje rezervacija u fajl
	public void pisanjeRezervacija() {
		String pathString="datoteke"+File.separator+"Rezervacije.txt";
		Path path = Path.of(pathString);
		
		if (rezervacije.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi = true;
		for (Rezervacija rezervacija : rezervacije.values()) {
			try {
				if (prvi) {
					Files.writeString(path, rezervacija.upis()+"\n");
					prvi = false;
				} else {
					Files.writeString(path, rezervacija.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud metode za rezervacije
	public void dodavanjeRezervacija(int id,StatusRezervacije tipRezervacije, LocalDate datumRezervacije, TipSobe tipSobe, int brojOsoba,
			int brojNocenja, ArrayList<DodatnaUsluga> dodatneUsluge, double cena, String imeRezervacije,String napomena)
	{
		Rezervacija rezervacija = new Rezervacija(id,tipRezervacije, datumRezervacije, tipSobe, brojOsoba, brojNocenja, dodatneUsluge, cena, imeRezervacije,napomena);
		rezervacije.put(id, rezervacija);
		pisanjeRezervacija();
	}
	public void pregledRezervacija()
	{
		for (Rezervacija rezervacija : rezervacije.values()) {
			System.out.println(rezervacija);
		}
	}
	public void izmenaRezervacija(int id,StatusRezervacije tipRezervacije, LocalDate datumRezervacije, TipSobe tipSobe, int brojOsoba,
			int brojNocenja, ArrayList<DodatnaUsluga> dodatneUsluge, double cena, String imeRezervacije,Soba soba,String napomena)
	{
		Rezervacija rezervacija = rezervacije.get(id);
		rezervacija.setStatusRezervacije(tipRezervacije);
		rezervacija.setDatumRezervacije(datumRezervacije);
		rezervacija.setTipSobe(tipSobe);
		rezervacija.setBrojOsoba(brojOsoba);
		rezervacija.setBrojNocenja(brojNocenja);
		rezervacija.setDodatneUsluge(dodatneUsluge);
		rezervacija.setCena(cena);
		rezervacija.setImeRezervacije(imeRezervacije);
		rezervacija.setSoba(soba);
		rezervacija.setNapomena(napomena);
		pisanjeRezervacija();
	}
	public void brisanjeRezervacija(int id,MenadzerGostiju menadzerGostiju)
	{
		for (Gost gost : menadzerGostiju.getGosti().values()) {
			for (Rezervacija rezervacija : gost.getRezervacije()) {
				if (rezervacija.getId() == id) {
					gost.getRezervacije().remove(rezervacija);
					break;
				}
			}
		}
		menadzerGostiju.pisanjeGostiju();
		rezervacije.remove(id);
		pisanjeRezervacija();
	}
}
