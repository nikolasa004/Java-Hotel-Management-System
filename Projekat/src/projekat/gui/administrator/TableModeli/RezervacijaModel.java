package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.menadzeri.MenadzerRezervacija;
import projekat.rezervacije.Rezervacija;

public class RezervacijaModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

	private String[] koloneHeder={"Id","Status","Datum pocetka","Broj nocenja","Broj osoba","Tip sobe","Dodatne usluge","Cena","Gost","Soba","Napomena"};
	private MenadzerRezervacija menadzerRezervacija;
	private ArrayList<Integer> keyList;
	private ArrayList<Rezervacija> rezervacije;
	private String tip;
	
	public RezervacijaModel(MenadzerRezervacija menadzerRezervacija) {
        this.menadzerRezervacija=menadzerRezervacija;
        tip="menadzer";
	}

	public RezervacijaModel(ArrayList<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
		tip="gost";
	}
	
	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}

	@Override
	public int getRowCount() {
		if (tip.equals("gost"))
			return rezervacije.size();
		else
			return menadzerRezervacija.rezervacije.size();
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		Rezervacija rezervacija=null;
		if(tip.equals("gost"))
            rezervacija=rezervacije.get(red);
        else
        {
			Set<Integer> keys=menadzerRezervacija.rezervacije.keySet();
			keyList=new ArrayList<Integer>(keys);
			rezervacija=menadzerRezervacija.rezervacije.get(keyList.get(red));
        }
		
		switch(kolona) {
		case 0:
			return String.valueOf(rezervacija.getId());
		case 1:
			return rezervacija.getStatusRezervacije();
		case 2:
			return rezervacija.getDatumRezervacije();
		case 3:
			return String.valueOf(rezervacija.getBrojNocenja());
		case 4:
			return String.valueOf(rezervacija.getBrojOsoba());
		case 5:
			return rezervacija.getTipSobe().getTip();
		case 6:
			return rezervacija.dodatneUslugeString();
		case 7:
			return String.valueOf(rezervacija.getCena());
		case 8:
			return rezervacija.getImeRezervacije();
		case 9:
			if (rezervacija.getSoba() == null)
				return "-1";
			else
				return String.valueOf(rezervacija.getSoba().getBrojSobe());
		case 10:
				return rezervacija.getNapomena();
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
	
	public void removeRow(int row) {
		rezervacije.remove(row);
		fireTableDataChanged();
	}
}
