package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import projekat.menadzeri.MenadzerTipovaSoba;
import projekat.sobe.TipSobe;

public class TipSobeModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	String[] koloneHeder=new String[]{"Tip"};
	private MenadzerTipovaSoba menadzerTipovaSoba;
	private ArrayList<String> keyList;
	
	public TipSobeModel(MenadzerTipovaSoba menadzerTipovaSoba){
        this.menadzerTipovaSoba=menadzerTipovaSoba;
	}

	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}
	
	@Override
	public int getRowCount() {
		return menadzerTipovaSoba.tipoviSoba.size();
	}
	
	@Override
	public Object getValueAt(int red, int kolona) {
		Set<String> keys = menadzerTipovaSoba.tipoviSoba.keySet();
		keyList = new ArrayList<String>(keys);
		
		TipSobe tipSobe = menadzerTipovaSoba.tipoviSoba.get(keyList.get(red));
		
		switch(kolona){
		case 0:
			return tipSobe.getTip();
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
