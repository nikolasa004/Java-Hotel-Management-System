package projekat.menadzeri;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import projekat.enumi.StatusRezervacije;
import projekat.korisnici.Administrator;
import projekat.korisnici.Recepcioner;
import projekat.korisnici.Sobarica;
import projekat.rezervacije.Rezervacija;

//klasa koja cuva izvestaje o rashodima i ociscenim sobama
public class MenadzerIzvestaja {
	
	public ArrayList<String[]> rashodi=new ArrayList<>();
	public ArrayList<String[]> ocisceneSobe=new ArrayList<>();
	
	public void dodajRashode(Menadzer menadzer)
	{
		//gleda se kad je bila poslednja plata, treba posle svakog 20. u mesecu da se isplati
		//ako je danas 20. ili posle 20. u mesecu, onda se isplacuju plate
		LocalDate danas = LocalDate.now();
		if(danas.getDayOfMonth()>=20)
		{
			double plate=0;
			for (Administrator administrator : menadzer.getMenadzerAdministratora().getAdministratori().values()) {
				plate += administrator.getPlata();
			}
			for (Recepcioner recepcioner : menadzer.getMenadzerRecepcionera().getRecepcioneri().values()) {
				plate += recepcioner.getPlata();
			}
			for (Sobarica sobarica : menadzer.getMenadzerSobarica().getSobarice().values()) {
				plate += sobarica.getPlata();
			}
			
			if (rashodi.isEmpty()) {
				String[] rashod = new String[2];
				rashod[0] = danas.toString();
				rashod[1] = String.valueOf(plate);
				rashodi.add(rashod);
				pisanjeIzvestajaRashoda();
			}
			else {
				String[] poslednjiRashod = rashodi.get(rashodi.size() - 1);
				LocalDate datumRashoda = LocalDate.parse(poslednjiRashod[0]);
				if (danas.getMonthValue() != datumRashoda.getMonthValue() || danas.getYear() != datumRashoda.getYear()) {
					String[] rashod = new String[2];
					rashod[0] = danas.toString();
					rashod[1] = String.valueOf(plate);
					rashodi.add(rashod);
					pisanjeIzvestajaRashoda();
				}
			}
		}
	}
	
	//metoda za racun prometa za uneti interval
	public double[] racunPrometa(ArrayList<Rezervacija> prihodi,ArrayList<String[]> rashodi, LocalDate datumOd, LocalDate datumDo,Menadzer menadzer) {
		//racun prihoda
		double prihod=0;
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if (rezervacija.getStatusRezervacije() == StatusRezervacije.POTVRDJENA || rezervacija.getStatusRezervacije() == StatusRezervacije.OTKAZANA) {
				if (rezervacija.getDatumRezervacije().isAfter(datumOd)
						&& rezervacija.getDatumRezervacije().isBefore(datumDo)) {
					prihod += rezervacija.getCena();
					prihodi.add(rezervacija);
				}
			}
		}
		
		//racun rashoda
		double rashod=0;
		for(String[] rashodNiz:menadzer.getMenadzerIzvestaja().rashodi)
		{
			LocalDate datum=LocalDate.parse(rashodNiz[0]);
            if(datum.isAfter(datumOd) && datum.isBefore(datumDo))
            {
                rashod+=Double.parseDouble(rashodNiz[1]);
                rashodi.add(rashodNiz);
            }
		}
		
		return new double[]{prihod,rashod};
	}
	
	//metoda za racun ociscenih soba za uneti interval za svaku sobaricu
	public void racunSobarica(LocalDate datumOd, LocalDate  datumDo,HashMap<String, Integer> sobarice)
	{
		for(String[] izvestaj:ocisceneSobe)
		{
			LocalDate datum=LocalDate.parse(izvestaj[0]);
			if(datum.isAfter(datumOd) && datum.isBefore(datumDo))
            {
				if(sobarice.containsKey(izvestaj[1]))
				{
					sobarice.put(izvestaj[1], sobarice.get(izvestaj[1]) + 1);
				} else {
					sobarice.put(izvestaj[1], 1);
				}
            }
		}
	}
	
	//metoda za racun izvestaja o rezervacijama za uneti interval
	public int[] racunRezervacija(LocalDate datumOd, LocalDate datumDo,Menadzer menadzer) {
		int brojPotvrdjenih = 0;
		int brojOdbijenih = 0;
		int brojOtkazanih = 0;
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if (rezervacija.getDatumRezervacije().isAfter(datumOd)
					&& rezervacija.getDatumRezervacije().isBefore(datumDo)) {
				if (rezervacija.getStatusRezervacije() == StatusRezervacije.POTVRDJENA) {
					brojPotvrdjenih++;
				} else if (rezervacija.getStatusRezervacije() == StatusRezervacije.ODBIJENA) {
					brojOdbijenih++;
				} else if (rezervacija.getStatusRezervacije() == StatusRezervacije.OTKAZANA) {
					brojOtkazanih++;
				}
			}
		}
		
		return new int[]{brojPotvrdjenih,brojOdbijenih,brojOtkazanih};
	}
	
	//metoda za racun izvestaja o sobama, broj nocenja i prihod za uneti interval
	public void racunSoba(LocalDate datumOd, LocalDate datumDo, Menadzer menadzer, HashMap<Integer, Integer> sobeBroj, HashMap<Integer, Double> sobePrihod) {
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if(rezervacija.getStatusRezervacije()==StatusRezervacije.POTVRDJENA && rezervacija.getSoba().getBrojSobe()!=-1)
			{
				if (rezervacija.getDatumRezervacije().isAfter(datumOd)&& rezervacija.getDatumRezervacije().isBefore(datumDo)) {
					if (sobeBroj.containsKey(rezervacija.getSoba().getBrojSobe())) {
						int broj_nocenja=0;
						if(rezervacija.getDatumRezervacije().plusDays(rezervacija.getBrojNocenja()).isAfter(datumDo))
						{
							broj_nocenja = datumDo.getDayOfYear()-rezervacija.getDatumRezervacije().getDayOfYear();
							sobeBroj.put(rezervacija.getSoba().getBrojSobe(), sobeBroj.get(rezervacija.getSoba().getBrojSobe())+broj_nocenja);
							sobePrihod.put(rezervacija.getSoba().getBrojSobe(), sobePrihod.get(rezervacija.getSoba().getBrojSobe())+rezervacija.getCena()/rezervacija.getBrojNocenja()*broj_nocenja);
						}
						else {
							broj_nocenja = rezervacija.getBrojNocenja();
							sobeBroj.put(rezervacija.getSoba().getBrojSobe(), sobeBroj.get(rezervacija.getSoba().getBrojSobe())+broj_nocenja);
							sobePrihod.put(rezervacija.getSoba().getBrojSobe(), sobePrihod.get(rezervacija.getSoba().getBrojSobe())+rezervacija.getCena());
						}
					} else {
						if (rezervacija.getDatumRezervacije().plusDays(rezervacija.getBrojNocenja()).isAfter(datumDo)) {
							int broj_nocenja = datumDo.getDayOfYear()
									- rezervacija.getDatumRezervacije().getDayOfYear();
							sobeBroj.put(rezervacija.getSoba().getBrojSobe(), broj_nocenja);
						} else {
							sobeBroj.put(rezervacija.getSoba().getBrojSobe(), rezervacija.getBrojNocenja());
							sobePrihod.put(rezervacija.getSoba().getBrojSobe(), rezervacija.getCena());
						}
					}
				}
			}
		}
	}
	
	//metoda koja pravi mapu za chart prihoda po tipu soba u poslednjih godinu dana
	public void racunChartPrihoda(HashMap<String,ArrayList<Double>> prihodi,ArrayList<Integer> meseci,Menadzer menadzer)
	{
		for (String tipSobe : menadzer.getMenadzerTipovaSoba().tipoviSoba.keySet()) {
			ArrayList<Double> prihodiTipa = new ArrayList<Double>();
			for (int i = 0; i < 12; i++) {
				prihodiTipa.add(0.0);
			}
			prihodi.put(tipSobe, prihodiTipa);
		}
		
		LocalDate datum=LocalDate.now();
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if (rezervacija.getDatumRezervacije().isAfter(datum.minusMonths(12)) && rezervacija.getDatumRezervacije().isBefore(datum)) {
				    String tipSobe = rezervacija.getTipSobe().getTip();
					int mesec = rezervacija.getDatumRezervacije().getMonthValue() - 1;
					double prihod = prihodi.get(tipSobe).get(mesec) + rezervacija.getCena();
					prihodi.get(tipSobe).set(mesec, prihod);
			}
		}

		for (int i = 1; i <= 12; i++)
			meseci.add(i);
	}
	
	//metoda koja pravi mapu za chart o stanju rezervacija u poslednjih mesec dana
	public void racunChartRezervacija(HashMap<String,Integer> rezervacije,Menadzer menadzer)
	{
		rezervacije.put("POTVRDJENA", 0);
		rezervacije.put("ODBIJENA", 0);
		rezervacije.put("NA_CEKANJU", 0);
		rezervacije.put("OTKAZANA", 0);
		
		LocalDate datum=LocalDate.now();
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			LocalDate datumRezervacije=rezervacija.getDatumRezervacije();
			if (datumRezervacije.isAfter(datum.minusMonths(1)) && datumRezervacije.isBefore(datum)) {
				switch (rezervacija.getStatusRezervacije()) {
				case POTVRDJENA:
					rezervacije.put("POTVRDJENA", rezervacije.get("POTVRDJENA") + 1);
					break;
				case ODBIJENA:
					rezervacije.put("ODBIJENA", rezervacije.get("ODBIJENA") + 1);
					break;
				case NA_CEKANJU:
					rezervacije.put("NA_CEKANJU", rezervacije.get("NA_CEKANJU") + 1);
					break;
				case OTKAZANA:
					rezervacije.put("OTKAZANA", rezervacije.get("OTKAZANA") + 1);
					break;
				}
			}
		}
	}
	
	//metoda za popunjavanje mape o radu sobarica za chart
	public void racunChartSobarica(HashMap<String,Integer> sobarice,Menadzer menadzer)
	{
		for (Sobarica sobarica : menadzer.getMenadzerSobarica().getSobarice().values()) {
			sobarice.put(sobarica.getKorisnickoIme(), 0);
		}
		
		LocalDate datum=LocalDate.now();
		for (String[] izvestaj:ocisceneSobe) {
			if (datum.minusDays(30).isBefore(LocalDate.parse(izvestaj[0])) && datum.isAfter(LocalDate.parse(izvestaj[0]))) {
			   sobarice.put(izvestaj[1], sobarice.get(izvestaj[1])+1);
			}
		}
	}
	
	public void ucitavanjeIzvestaja(Menadzer menadzer) {
		String pathString="datoteke"+File.separator+"IzvestajiRashodi.txt";
		Path path=Path.of(pathString);
		try {
			List<String> linije = Files.readAllLines(path);
			for (String linija : linije) {
				String[] splitovanaLinija = linija.split("\\|");
				rashodi.add(splitovanaLinija);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String pathString2="datoteke"+File.separator+"IzvestajiSobarice.txt";
		Path path2=Path.of(pathString2);
		try {
			List<String> linije = Files.readAllLines(path2);
			for (String linija : linije) {
				String[] splitovanaLinija = linija.split("\\|");
				ocisceneSobe.add(splitovanaLinija);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dodajRashode(menadzer);
	}
	
	public void pisanjeIzvestajaRashoda() {
		String pathString="datoteke"+File.separator+"IzvestajiRashodi.txt";
		Path path=Path.of(pathString);
		
		if (rashodi.isEmpty()) {
			try {
				Files.writeString(path,"");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi=true;
		for (String[] rashod : rashodi) {
			try {
				if (prvi) {
					Files.writeString(path, rashod[0]+"|"+rashod[1]+"\n");
					prvi = false;
				} else {
					Files.writeString(path, rashod[0]+"|"+rashod[1] + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pisanjeIzvestajaSobarice()
	{
		String pathString="datoteke"+File.separator+"IzvestajiSobarice.txt";
		Path path=Path.of(pathString);
		
		if (ocisceneSobe.isEmpty()) {
			try {
				Files.writeString(path, "");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean prvi2=true;
		for (String[] soba : ocisceneSobe) {
			try {
				if (prvi2) {
					Files.writeString(path, soba[0] + "|" + soba[1] + "\n");
					prvi2 = false;
				} else {
					Files.writeString(path, soba[0] + "|" + soba[1] + "\n", StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
