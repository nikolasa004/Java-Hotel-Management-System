package projekat.rezervacije;

import projekat.sobe.Soba;
import projekat.enumi.StatusRezervacije;
import projekat.sobe.TipSobe;
import projekat.usluge.DodatnaUsluga;
import java.time.LocalDate;
import java.util.ArrayList;

public class Rezervacija {
	//atributi
	private int id;
	private StatusRezervacije statusRezervacije;
	private LocalDate datumRezervacije;
	private TipSobe tipSobe;
	private int brojOsoba;
	private int brojNocenja;
	private ArrayList<DodatnaUsluga> dodatneUsluge;
	private double cena;
	private String imeRezervacije;
	private Soba soba;
	private String napomena;
	
	//get i set metode
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StatusRezervacije getStatusRezervacije() {
		return statusRezervacije;
	}
	public void setStatusRezervacije(StatusRezervacije tipRezervacije) {
		this.statusRezervacije = tipRezervacije;
	}
	public LocalDate getDatumRezervacije() {
		return datumRezervacije;
	}
	public void setDatumRezervacije(LocalDate datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}
	public TipSobe getTipSobe() {
		return tipSobe;
	}
	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}
	public int getBrojOsoba() {
		return brojOsoba;
	}
	public void setBrojOsoba(int brojOsoba) {
		this.brojOsoba = brojOsoba;
	}
	public int getBrojNocenja() {
		return brojNocenja;
	}
	public void setBrojNocenja(int brojNocenja) {
		this.brojNocenja = brojNocenja;
	}
	public ArrayList<DodatnaUsluga> getDodatneUsluge() {
		return dodatneUsluge;
	}
	public void setDodatneUsluge(ArrayList<DodatnaUsluga> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getImeRezervacije() {
		return imeRezervacije;
	}
	public void setImeRezervacije(String imeRezervacije) {
		this.imeRezervacije = imeRezervacije;
	}
	public Soba getSoba() {
		return soba;
	}
	public void setSoba(Soba soba) {
		this.soba = soba;
	}
	public String getNapomena() {
		return napomena;
	}
	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}
	
	//konstruktori
	public Rezervacija(int id,StatusRezervacije tipRezervacije, LocalDate datumRezervacije, TipSobe tipSobe, int brojOsoba,
			int brojNocenja, ArrayList<DodatnaUsluga> dodatneUsluge, double cena, String imeRezervacije) {
		this.id = id;
		this.statusRezervacije = tipRezervacije;
		this.datumRezervacije = datumRezervacije;
		this.tipSobe = tipSobe;
		this.brojOsoba = brojOsoba;
		this.brojNocenja = brojNocenja;
		this.dodatneUsluge = dodatneUsluge;
		this.cena = cena;
		this.imeRezervacije = imeRezervacije;
		this.soba = new Soba();
		soba.setBrojSobe(-1);
	}

	public Rezervacija(int id, StatusRezervacije tipRezervacije, LocalDate datumRezervacije, TipSobe tipSobe,
			int brojOsoba, int brojNocenja, ArrayList<DodatnaUsluga> dodatneUsluge, double cena, String imeRezervacije,
			String napomena) {
		this.id = id;
		this.statusRezervacije = tipRezervacije;
		this.datumRezervacije = datumRezervacije;
		this.tipSobe = tipSobe;
		this.brojOsoba = brojOsoba;
		this.brojNocenja = brojNocenja;
		this.dodatneUsluge = dodatneUsluge;
		this.cena = cena;
		this.imeRezervacije = imeRezervacije;
		this.soba = new Soba();
		soba.setBrojSobe(-1);
		this.napomena = napomena;
	}
	
	public Rezervacija(int id,StatusRezervacije tipRezervacije, LocalDate datumRezervacije, TipSobe tipSobe, int brojOsoba,
			int brojNocenja, ArrayList<DodatnaUsluga> dodatneUsluge, double cena, String imeRezervacije,Soba soba) {
		this.id = id;
		this.statusRezervacije = tipRezervacije;
		this.datumRezervacije = datumRezervacije;
		this.tipSobe = tipSobe;
		this.brojOsoba = brojOsoba;
		this.brojNocenja = brojNocenja;
		this.dodatneUsluge = dodatneUsluge;
		this.cena = cena;
		this.imeRezervacije = imeRezervacije;
		this.soba = soba;
	}

	public Rezervacija(int id, StatusRezervacije tipRezervacije, LocalDate datumRezervacije, TipSobe tipSobe,
			int brojOsoba, int brojNocenja, ArrayList<DodatnaUsluga> dodatneUsluge, double cena, String imeRezervacije,
			Soba soba, String napomena) {
		this.id = id;
		this.statusRezervacije = tipRezervacije;
		this.datumRezervacije = datumRezervacije;
		this.tipSobe = tipSobe;
		this.brojOsoba = brojOsoba;
		this.brojNocenja = brojNocenja;
		this.dodatneUsluge = dodatneUsluge;
		this.cena = cena;
		this.imeRezervacije = imeRezervacije;
		this.soba = soba;
		this.napomena = napomena;
	}
	public Rezervacija() {
	}
	
	//toString metoda
	@Override
	public String toString() {
		return "Rezervacija [id="+id+", tipRezervacije=" + statusRezervacije + ", datumRezervacije=" + datumRezervacije + ", tipSobe="
				+ tipSobe + ", brojOsoba=" + brojOsoba + ", brojNocenja=" + brojNocenja + ", dodatneUsluge="
				+ dodatneUsluge + ", cena=" + cena + ", imeRezervacije=" + imeRezervacije + "]";
	}
	
	//pretvaranje dodatnih usluga u string
	public String dodatneUslugeString() {
		String dodatneUslugeString = "";
		for (DodatnaUsluga dodatnaUsluga : dodatneUsluge) {
			dodatneUslugeString+=dodatnaUsluga.getNaziv();
			if (dodatneUsluge.indexOf(dodatnaUsluga) != dodatneUsluge.size() - 1) {
				dodatneUslugeString += ", ";
			}
		}
		return dodatneUslugeString;
	}
	
	//upisivanje rezervacije u fajl
	public String upis() {
		return id + "|" + statusRezervacije + "|" + datumRezervacije + "|" + tipSobe.getTip() + "|" + brojOsoba + "|"
				+ brojNocenja + "|" + dodatneUslugeString() + "|" + cena + "|" + imeRezervacije+"|"+soba.getBrojSobe()+"|"+napomena;
	}
}
