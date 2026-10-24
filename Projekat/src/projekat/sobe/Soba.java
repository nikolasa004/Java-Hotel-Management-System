package projekat.sobe;

import projekat.enumi.StatusSobe;

public class Soba {
	//atributi
	private TipSobe tipSobe;
	private StatusSobe statusSobe;
	private int brojSobe;
	private boolean klima_uredjaj;
	private boolean balkon;
	private boolean tv;
	private boolean pusacka;
	
	//get i set metode
	public TipSobe getTipSobe() {
		return tipSobe;
	}
	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}
	public StatusSobe getStatusSobe() {
		return statusSobe;
	}
	public void setStatusSobe(StatusSobe statusSobe) {
		this.statusSobe = statusSobe;
	}
	public int getBrojSobe() {
		return brojSobe;
	}
	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}
	public boolean isKlima_uredjaj() {
		return klima_uredjaj;
	}
	public void setKlima_uredjaj(boolean klima_uredjaj) {
		this.klima_uredjaj = klima_uredjaj;
	}
	public boolean isBalkon() {
		return balkon;
	}
	public void setBalkon(boolean balkon) {
		this.balkon = balkon;
	}
	public boolean isTv() {
		return tv;
	}
	public void setTv(boolean tv) {
		this.tv = tv;
	}
	public boolean isPusacka() {
		return pusacka;
	}
	public void setPusacka(boolean pusacka) {
		this.pusacka = pusacka;
	}
	
	//konstruktori
	public Soba(TipSobe tipSobe, StatusSobe statusSobe, int brojSobe, boolean klima_uredjaj, boolean balkon, boolean tv, boolean pusacka) {
		this.tipSobe = tipSobe;
		this.statusSobe = statusSobe;
		this.brojSobe = brojSobe;
		this.klima_uredjaj = klima_uredjaj;
		this.balkon = balkon;
		this.tv = tv;
		this.pusacka = pusacka;
	}
	public Soba() {
	}
	
	//toString metoda
	@Override
	public String toString() {
		return "Soba [tipSobe=" + tipSobe + ", statusSobe=" + statusSobe + ", brojSobe=" + brojSobe + ", klima_uredjaj="
				+ klima_uredjaj + ", balkon=" + balkon + ", tv=" + tv + ", pusacka=" + pusacka + "]";
	}
	
	//upisivanje sobe u fajl
	public String upis() {
		return tipSobe.getTip() + "|" + statusSobe + "|" + brojSobe + "|" + klima_uredjaj + "|" + balkon + "|" + tv + "|"
				+ pusacka;
	}
}
