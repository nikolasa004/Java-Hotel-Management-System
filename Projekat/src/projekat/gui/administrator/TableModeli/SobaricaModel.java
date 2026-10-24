package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.korisnici.Sobarica;
import projekat.menadzeri.MenadzerSobarica;

public class SobaricaModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;

	private String[] koloneHeder ={ "Korisnicko ime", "Lozinka", "Ime", "Prezime", "Pol", "Datum rodjenja","Telefon", "Adresa", "Strucna sprema", "Staz","Plata","Sobe" };
	private MenadzerSobarica menadzerSobarica;
	private ArrayList<String> keyList;
	private String[] koloneHeder2 = { "Korisnicko ime","Broj odradjenih soba"};
	private HashMap<String, Integer> sobarice;
	private String tip;
	
	public SobaricaModel(MenadzerSobarica menadzerSobarica) {
        this.menadzerSobarica = menadzerSobarica;
        tip = "menadzer";
	}
	
	public SobaricaModel(HashMap<String, Integer> sobarice)
	{
		this.sobarice = sobarice;
		tip = "sobarice";
	}

	@Override
	public int getColumnCount() {
		if (tip.equals("sobarice"))
			return koloneHeder2.length;
		return koloneHeder.length;
	}

	@Override
	public int getRowCount() {
		if (tip.equals("sobarice"))
			return sobarice.size();
		return menadzerSobarica.getSobarice().size();
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		if (!tip.equals("sobarice"))
        {
			Set<String> keys = menadzerSobarica.getSobarice().keySet();
			keyList = new ArrayList<String>(keys);
			
			Sobarica sobarica = menadzerSobarica.getSobarice().get(keyList.get(red));
			
			switch (kolona) {
			case 0:
				return sobarica.getKorisnickoIme();
		    case 1:
		        return sobarica.getLozinka();
		    case 2:
		        return sobarica.getIme();
		    case 3:
		        return sobarica.getPrezime();
		    case 4:
		        return sobarica.getPol();
		    case 5:
		        return sobarica.getDatumRodjenja();
		    case 6:
		        return sobarica.getTelefon();
		    case 7:
		        return sobarica.getAdresa();
		    case 8:
		        return sobarica.getStrucnaSprema();
		    case 9:
		        return String.valueOf(sobarica.getStaz());
		    case 10:
		        return String.valueOf(sobarica.getPlata());
		    case 11:
				return String.valueOf(sobarica.sobeString());
			default:
				return null;
			}
        }
		else
		{
			Set<String> keys=sobarice.keySet();
			keyList=new ArrayList<String>(keys);
			
			String korisnickoIme = keyList.get(red);
			
			switch (kolona) {
			case 0:
				return korisnickoIme;
			case 1:
				return String.valueOf(sobarice.get(korisnickoIme));
			default:
					return null;
			}
		}
	}

	@Override
	public String getColumnName(int kolona) {
		if (tip.equals("sobarice"))
			return koloneHeder2[kolona];
		return koloneHeder[kolona];
	}

	@Override
	public Class<?> getColumnClass(int kolona)
	{
		return this.getValueAt(0, kolona).getClass();
	}

}
