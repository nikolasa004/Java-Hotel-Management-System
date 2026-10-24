package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.korisnici.Administrator;
import projekat.menadzeri.MenadzerAdministratora;

//klasa za tabelu administratora
public class AdministratorModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	
	private String[] koloneHeder={"Korisnicko ime","Lozinka","Ime","Prezime","Pol","Datum rodjenja","Telefon","Adresa","Strucna sprema","Staz","Plata"};
	private MenadzerAdministratora menadzer;
	private ArrayList<String> keyList;
	
	public AdministratorModel(MenadzerAdministratora menadzer) {
		this.menadzer = menadzer;
	}
	
	@Override
	public int getRowCount() {
		return menadzer.getAdministratori().size();
	}
	
	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}
	
	@Override
	public Object getValueAt(int red, int kolona) {
		 // Dobijanje skupa ključeva
        Set<String> keySet = menadzer.getAdministratori().keySet();
        // Pretvaranje skupa ključeva u ArrayList
        keyList = new ArrayList<>(keySet);
        
        // Pronalazak administratora na datom indeksu
        Administrator administrator = menadzer.getAdministratori().get(keyList.get(red));
        
		switch(kolona)
        {
			case 0:
				return administrator.getKorisnickoIme();
			case 1:
				return administrator.getLozinka();
			case 2:
				return administrator.getIme();
			case 3:
				return administrator.getPrezime();
			case 4:
				return administrator.getPol();
			case 5:
				return administrator.getDatumRodjenja();
			case 6:
				return administrator.getTelefon();
			case 7:
				return administrator.getAdresa();
			case 8:
				return administrator.getStrucnaSprema();
			case 9:
				return String.valueOf(administrator.getStaz());
			case 10:
				return String.valueOf(administrator.getPlata());
			default:
				return null;
        }
	}
	
	@Override
	public String getColumnName(int kolona) {
		return koloneHeder[kolona];
	}
	
	@Override
	public Class<?> getColumnClass(int kolona)
	{
		return this.getValueAt(0, kolona).getClass();
	}
}
