package projekat.sobe;

public class TipSobe {
	//atribut
	private String tip;
	
	//get i set metode
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	//konstruktor
	public TipSobe(String tip) {
		this.tip = tip;
	}
	public TipSobe() {
	}
	
	//toString metoda
	@Override
	public String toString() {
		return "TipSobe [tip=" + tip + "]";
	}
	
	//upisivanje tipa sobe u fajl
	public String upis() {
		return tip;
	}
}
