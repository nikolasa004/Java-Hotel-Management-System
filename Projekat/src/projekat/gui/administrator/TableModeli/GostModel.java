package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.korisnici.Gost;
import projekat.menadzeri.MenadzerGostiju;

public class GostModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	private String[] koloneHeder ={ "Korisnicko ime", "Lozinka", "Ime", "Prezime", "Pol", "Datum rodjenja","Telefon", "Adresa", "Rezervacije" };
	private MenadzerGostiju menadzerGostiju;
	private ArrayList<String> keyList;
	
	public GostModel(MenadzerGostiju menadzerGostiju)
    {
        this.menadzerGostiju = menadzerGostiju;
    }

	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}

	@Override
	public int getRowCount() {
		return menadzerGostiju.getGosti().size();
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		Set<String> keys = menadzerGostiju.getGosti().keySet();
		keyList = new ArrayList<String>(keys);
		
		Gost gost = menadzerGostiju.getGosti().get(keyList.get(red));
		
		switch(kolona) {
		case 0:
			return gost.getKorisnickoIme();
		case 1:
			return gost.getLozinka();
		case 2:
			return gost.getIme();
		case 3:
			return gost.getPrezime();
		case 4:
			return gost.getPol();
		case 5:
			return gost.getDatumRodjenja();
		case 6:
			return gost.getTelefon();
		case 7:
			return gost.getAdresa();
		case 8:
			return gost.rezervacijeString();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int kolona) {
		return koloneHeder[kolona];
	}

	

}
