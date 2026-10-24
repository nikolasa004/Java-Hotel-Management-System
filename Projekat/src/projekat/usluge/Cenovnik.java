package projekat.usluge;

import projekat.sobe.TipSobe;
import java.time.LocalDate;
import java.util.HashMap;

public class Cenovnik {
	//atributi
	private int id;
	private LocalDate datumOd;
	private LocalDate datumDo;
	private HashMap<TipSobe, Double> ceneSoba;
	private HashMap<DodatnaUsluga, Double> ceneUsluga;
	
	//get i set metode
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(LocalDate datumOd) {
		this.datumOd = datumOd;
	}
	public LocalDate getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(LocalDate datumDo) {
		this.datumDo = datumDo;
	}
	public HashMap<TipSobe, Double> getCeneSoba() {
		return ceneSoba;
	}
	public void setCeneSoba(HashMap<TipSobe, Double> ceneSoba) {
		this.ceneSoba = ceneSoba;
	}
	public HashMap<DodatnaUsluga, Double> getCeneUsluga() {
		return ceneUsluga;
	}
	public void setCeneUsluga(HashMap<DodatnaUsluga, Double> ceneUsluga) {
		this.ceneUsluga = ceneUsluga;
	}
	
	//konstruktori
	public Cenovnik(int id,LocalDate datumOd, LocalDate datumDo, HashMap<TipSobe, Double> ceneSoba, HashMap<DodatnaUsluga, Double> ceneUsluga)
	{
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.ceneSoba = ceneSoba;
		this.ceneUsluga = ceneUsluga;
	}
	public Cenovnik() {
	}
	
	//toString metoda
	@Override
	public String toString() {
		return "Cenovnik [id="+id+", datumOd=" + datumOd + ", datumDo=" + datumDo + ", ceneSoba=" + ceneSoba + ", ceneUsluga="
				+ ceneUsluga + "]";
	}
	
	//pretvaranje cena soba u string
	public String ceneSobaString() {
		String ceneSobaString = "";
		for (TipSobe tipSobe : ceneSoba.keySet()) {
			ceneSobaString += tipSobe.getTip() + "-" + ceneSoba.get(tipSobe);
			if (tipSobe != ceneSoba.keySet().toArray()[ceneSoba.size() - 1]) {
				ceneSobaString += ", ";
			}
		}
		return ceneSobaString;
	}
	
	//pretvaranje cena usluga u string
	public String ceneUslugaString() {
		String ceneUslugaString = "";
		for (DodatnaUsluga usluga : ceneUsluga.keySet()) {
			ceneUslugaString += usluga.getNaziv() + "-" + ceneUsluga.get(usluga);
			if (usluga != ceneUsluga.keySet().toArray()[ceneUsluga.size() - 1]) {
				ceneUslugaString += ", ";
			}
		}
		return ceneUslugaString;
	}
	
	//upisivanje cenovnika u fajl
	public String upis() {
		return id + "|" + datumOd + "|" + datumDo + "|" + ceneSobaString() + "|" + ceneUslugaString();
	}
}
