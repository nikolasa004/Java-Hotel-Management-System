package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.menadzeri.MenadzerSoba;
import projekat.sobe.Soba;

public class SobaModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

	private String[] koloneHeder ={ "Broj","Tip","Status","Klima","TV","Balkon","Pusacka"};
	private String[] koloneHeder2 = { "Broj","Tip","Status","Klima","TV","Balkon","Pusacka","Broj nocenja","Ukupan prihod"};
	private MenadzerSoba menadzerSoba;
	private ArrayList<Integer> keyList;
	private ArrayList<Soba> sobe;
	private HashMap<Integer, Integer> sobeBroj;
	private HashMap<Integer, Double> sobePrihod;
	private String tip;
	
	public SobaModel(MenadzerSoba menadzerSoba) {
		this.menadzerSoba = menadzerSoba;
		tip = "menadzer";
	}

	public SobaModel(ArrayList<Soba> sobe) {
		this.sobe = sobe;
		tip = "sobe";
	}
	
	public SobaModel(HashMap<Integer, Integer> sobeBroj, HashMap<Integer, Double> sobePrihod, MenadzerSoba menadzerSoba) {
		this.sobeBroj = sobeBroj;
		this.sobePrihod = sobePrihod;
		this.menadzerSoba = menadzerSoba;
		tip = "sobeIzvestaj";
	}
	
	@Override
	public int getColumnCount() {
		if (tip.equals("sobeIzvestaj"))
			return koloneHeder2.length;
		return koloneHeder.length;
	}

	@Override
	public int getRowCount() {
		if (tip.equals("sobe")) {
			return sobe.size();
		}
        else if (tip.equals("sobeIzvestaj")) {
            return sobeBroj.size();
        }
		return menadzerSoba.sobe.size();
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		if (!tip.equals("sobeIzvestaj")) {
			Soba soba = null;
			if (tip.equals("sobe")) {
				soba = sobe.get(red);
			}
			else {
				Set<Integer> keys = menadzerSoba.sobe.keySet();
				keyList = new ArrayList<Integer>(keys);
				soba = menadzerSoba.sobe.get(keyList.get(red));
			}
			
			switch(kolona) {
			case 0:
				return String.valueOf(soba.getBrojSobe());
			case 1:
				return soba.getTipSobe().getTip();
			case 2:
				return soba.getStatusSobe();
			case 3:
				return soba.isKlima_uredjaj();
			case 4:
				return soba.isTv();
			case 5:
				return soba.isBalkon();
			case 6:
				return soba.isPusacka();
			default:
				return null;
			}
		}
        else {
            Set<Integer> keys = sobeBroj.keySet();
            keyList = new ArrayList<Integer>(keys);
            Soba soba = menadzerSoba.sobe.get(keyList.get(red));
            switch(kolona) {
			case 0:
				return String.valueOf(soba.getBrojSobe());
			case 1:
				return soba.getTipSobe().getTip();
			case 2:
				return soba.getStatusSobe();
			case 3:
				return soba.isKlima_uredjaj();
		    case 4:
		        return soba.isTv();
		    case 5:
		        return soba.isBalkon();
		    case 6:
		        return soba.isPusacka();
		    case 7:
		        return String.valueOf(sobeBroj.get(soba.getBrojSobe()));
		    case 8:
		        return String.valueOf(sobePrihod.get(soba.getBrojSobe()));
		    default:
		        return null;
            }
        }
	}

	@Override
	public String getColumnName(int index) {
		if (tip.equals("sobeIzvestaj"))
			return koloneHeder2[index];
		return koloneHeder[index];
	}
	
	@Override
	public Class<?> getColumnClass(int kolona)
	{
		return this.getValueAt(0, kolona).getClass();
	}
	
}
