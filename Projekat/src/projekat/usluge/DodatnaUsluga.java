package projekat.usluge;

public class DodatnaUsluga {
	//atribut
	private String naziv;
	
	//get i set metode
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	//konstruktori
	public DodatnaUsluga(String naziv) {
		this.naziv = naziv;
	}
	public DodatnaUsluga() {
	}
	
	//toString metoda
	@Override
	public String toString() {
		return "DodatnaUsluga [naziv=" + naziv + "]";
	}
}
