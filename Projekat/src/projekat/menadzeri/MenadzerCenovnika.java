package projekat.menadzeri;

import projekat.usluge.Cenovnik;
import projekat.usluge.DodatnaUsluga;
import projekat.sobe.TipSobe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class MenadzerCenovnika {
	//mapa cenovnika sa kljucem naziv cenovnika
	public HashMap<Integer, Cenovnik> cenovnici = new HashMap<Integer,Cenovnik>();
	
	//metoda za pronalazak cenovnika koji vazi na odredjeni datum
	public Cenovnik pronalazakCenovnika(LocalDate datum)
	{
		for (Cenovnik cenovnik : cenovnici.values()) {
			if ((datum.isAfter(cenovnik.getDatumOd()) || datum.equals(cenovnik.getDatumOd())) && (datum.isBefore(cenovnik.getDatumDo()) || datum.equals(cenovnik.getDatumDo()))) {
				return cenovnik;
			}
		}
		return null;
	}
	
	//ucitavanje cenovnika iz fajla
	public void ucitavanjeCenovnika(MenadzerTipovaSoba menadzerTipovaSoba, MenadzerDodatnihUsluga menadzerDodatnihUsluga) {
		String pathString="datoteke"+File.separator+"Cenovnici.txt";
		Path path=Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);
			for (String linija : linije) {
				String[] delovi = linija.split("\\|");
				int id=Integer.parseInt(delovi[0]);
				LocalDate datumOd=LocalDate.parse(delovi[1]);
				LocalDate datumDo=LocalDate.parse(delovi[2]);
				HashMap<TipSobe, Double> ceneSoba = new HashMap<TipSobe, Double>();
				HashMap<DodatnaUsluga, Double> ceneUsluga = new HashMap<DodatnaUsluga, Double>();
				String[] ceneSobaString = delovi[3].split(", ");
				for (String cenaSoba : ceneSobaString) {
					String[] deloviCenaSoba = cenaSoba.split("-");
					TipSobe tipSobe = menadzerTipovaSoba.tipoviSoba.get(deloviCenaSoba[0]);
					Double cena = Double.parseDouble(deloviCenaSoba[1]);
					ceneSoba.put(tipSobe, cena);
				}
				String[] ceneUslugaString = delovi[4].split(", ");
				for (String cenaUsluga : ceneUslugaString) {
                    String[] deloviCenaUsluga = cenaUsluga.split("-");
                    DodatnaUsluga dodatnaUsluga = menadzerDodatnihUsluga.dodatneUsluge.get(deloviCenaUsluga[0]);
                    Double cena = Double.parseDouble(deloviCenaUsluga[1]);
                    ceneUsluga.put(dodatnaUsluga, cena);
                }
				Cenovnik cenovnik = new Cenovnik(id, datumOd, datumDo, ceneSoba, ceneUsluga);
				cenovnici.put(id, cenovnik);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//upisivanje cenovnika u fajl
	public void pisanjeCenovnika() {
		String pathString = "datoteke" + File.separator + "Cenovnici.txt";
		Path path = Path.of(pathString);

		if (cenovnici.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean prvi = true;
		for (Cenovnik cenovnik : cenovnici.values()) {
			try {
				if (prvi) {
					Files.writeString(path, cenovnik.upis() + "\n");
					prvi = false;
				} else {
					Files.writeString(path, cenovnik.upis() + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//crud metode za cenovnik
	public void dodavanjeCenovnika(int id,LocalDate datumOd, LocalDate datumDo, HashMap<TipSobe, Double> ceneSoba, HashMap<DodatnaUsluga, Double> ceneUsluga)
	{
		Cenovnik cenovnik = new Cenovnik(id, datumOd, datumDo, ceneSoba, ceneUsluga);
		this.cenovnici.put(id, cenovnik);
		pisanjeCenovnika();
	}
	public void pregledCenovnika()
	{
		for (Cenovnik cenovnik : cenovnici.values()) {
			System.out.println(cenovnik);
		}
	}
	public void izmenaCenovnika(int id,LocalDate datumOd, LocalDate datumDo, HashMap<TipSobe, Double> ceneSoba, HashMap<DodatnaUsluga, Double> ceneUsluga)
	{
		Cenovnik cenovnik = cenovnici.get(id);
		cenovnik.setDatumOd(datumOd);
		cenovnik.setDatumDo(datumDo);
		cenovnik.setCeneSoba(ceneSoba);
		cenovnik.setCeneUsluga(ceneUsluga);
		pisanjeCenovnika();
	}

	public void brisanjeCenovnika(int id)
	{
		cenovnici.remove(id);
		pisanjeCenovnika();
	}
}
