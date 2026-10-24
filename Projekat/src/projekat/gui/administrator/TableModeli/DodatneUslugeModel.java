package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.menadzeri.MenadzerDodatnihUsluga;
import projekat.usluge.DodatnaUsluga;

public class DodatneUslugeModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

	private String[] koloneHeder ={ "Naziv"};
	private MenadzerDodatnihUsluga menadzerDodatnihUsluga;
	private ArrayList<String> keyList;
	
	public DodatneUslugeModel(MenadzerDodatnihUsluga menadzerDodatnihUsluga) {
		this.menadzerDodatnihUsluga = menadzerDodatnihUsluga;
	}

	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}

	@Override
	public int getRowCount() {
		return menadzerDodatnihUsluga.dodatneUsluge.size();
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		Set<String> keys = menadzerDodatnihUsluga.dodatneUsluge.keySet();
		keyList = new ArrayList<String>(keys);
		DodatnaUsluga dodatnaUsluga = menadzerDodatnihUsluga.dodatneUsluge.get(keyList.get(red));
		
		switch(kolona) {
		case 0:
			return dodatnaUsluga.getNaziv();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int index) {
		return koloneHeder[index];
	}
	
	@Override
	public Class<?> getColumnClass(int kolona)
	{
		return this.getValueAt(0, kolona).getClass();
	}
}
