package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.menadzeri.MenadzerCenovnika;
import projekat.usluge.Cenovnik;

public class CenovnikModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

	private String[] koloneHeder = {"Id","Datum pocetka","Datum zavrsetka","Cene soba","Cene usluga"};
	private MenadzerCenovnika menadzerCenovnika;
	private ArrayList<Integer> keyList;
	
	public CenovnikModel(MenadzerCenovnika menadzerCenovnika) {
		this.menadzerCenovnika = menadzerCenovnika;
	}
	
	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}
	
	@Override
	public int getRowCount() {
		return menadzerCenovnika.cenovnici.size();
	}
	
	@Override
	public Object getValueAt(int redovi, int kolone) {
		Set<Integer> keys = menadzerCenovnika.cenovnici.keySet();
		keyList = new ArrayList<Integer>(keys);
		
		Cenovnik cenovnik = menadzerCenovnika.cenovnici.get(keyList.get(redovi));
		
		switch (kolone) {
		case 0:
			return String.valueOf(cenovnik.getId());
		case 1:
			return cenovnik.getDatumOd();
		case 2:
			return cenovnik.getDatumDo();
		case 3:
			return cenovnik.ceneSobaString();
		case 4:
			return cenovnik.ceneUslugaString();
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return koloneHeder[column];
	}
	
	@Override
	public Class<?> getColumnClass(int kolona)
	{
		return this.getValueAt(0, kolona).getClass();
	}
}
