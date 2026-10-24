package projekat.gui.administrator.TableModeli;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class RashodModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;

	private String[] koloneHeder = { "Datum", "Iznos" };
	private ArrayList<String[]> rashodi;

	public RashodModel(ArrayList<String[]> rashodi) {
		this.rashodi = rashodi;
	}

	@Override
	public int getColumnCount() {
		return koloneHeder.length;
	}

	@Override
	public int getRowCount() {
		return rashodi.size();
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		String[] redRashoda = rashodi.get(red);
		switch (kolona) {
		case 0:
			return redRashoda[0];
		case 1:
			return redRashoda[1];
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
