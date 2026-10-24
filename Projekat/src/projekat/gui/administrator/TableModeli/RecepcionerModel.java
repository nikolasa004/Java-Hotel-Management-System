package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.korisnici.Recepcioner;
import projekat.menadzeri.MenadzerRecepcionera;

//klasa za model tabele koja prikazuje recepcionere
public class RecepcionerModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

	String[] koloneHeder={"Korisnicko ime","Lozinka","Ime","Prezime","Pol","Datum rodjenja","Telefon","Adresa","Strucna sprema","Staz","Plata"};
	private MenadzerRecepcionera menadzerRecepcionera;
	private ArrayList<String> keyList;
	
	public RecepcionerModel(MenadzerRecepcionera menadzerRecepcionera)
	{
        this.menadzerRecepcionera = menadzerRecepcionera;
	}
	
	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}

	@Override
	public int getRowCount() {
		return menadzerRecepcionera.getRecepcioneri().size();
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		Set<String> keySet = menadzerRecepcionera.getRecepcioneri().keySet();
		keyList = new ArrayList<String>(keySet);
		
		Recepcioner recepcioner = menadzerRecepcionera.getRecepcioneri().get(keyList.get(red));

		switch (kolona) {
		case 0:
			return recepcioner.getKorisnickoIme();
		case 1:
			return recepcioner.getLozinka();
		case 2:
		    return recepcioner.getIme();
		case 3:
		    return recepcioner.getPrezime();
		case 4:
		    return recepcioner.getPol();
		case 5:
		    return recepcioner.getDatumRodjenja();
		case 6:
		    return recepcioner.getTelefon();
		case 7:
		    return recepcioner.getAdresa();
		case 8:
		    return recepcioner.getStrucnaSprema();
		case 9:
		    return String.valueOf(recepcioner.getStaz());
		case 10:
		    return String.valueOf(recepcioner.getPlata());
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int kolona) {
		return koloneHeder[kolona];
	}

	@Override
	public Class<?> getColumnClass(int kolona) {
        return this.getValueAt(0, kolona).getClass();
    }
}
