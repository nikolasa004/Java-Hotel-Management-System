package projekat.gui.sobarica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import projekat.enumi.StatusSobe;
import projekat.gui.administrator.TableModeli.SobaModel;
import projekat.menadzeri.Menadzer;

//klasa za sobaricu
public class SobaricaFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JTable tabelaSoba;
	private JButton zavrseno;

	public SobaricaFrame(Menadzer menadzer) {
		setTitle("Sobarice");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		
		tabelaGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za sobaricu
	private void tabelaGUI() {
		//tulbar sa dugmetom za zavrseno
		JToolBar toolbar = new JToolBar();
		add(toolbar, BorderLayout.NORTH);
		
		zavrseno = new JButton("Zavrseno");
		toolbar.add(zavrseno);
		
		//tabela sa sobama
		tabelaSoba = new JTable(new SobaModel(menadzer.getMenadzerSobarica().getSobarice().get(menadzer.getTrenutnoUlogovan()).getDodeljeneSobe()));
		add(tabelaSoba);
		tabelaSoba.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaSoba.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabelaSoba);
		add(scrollPane);
		
		//akcioni listener za dugme zavrseno
		zavrseno.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabelaSoba.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati sobu!", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id=Integer.parseInt(tabelaSoba.getValueAt(tabelaSoba.getSelectedRow(), 0).toString());
					int opcija=JOptionPane.showConfirmDialog(null, "Da li ste sigurni da je soba broj "+id+" spremna?", "Zavrsena soba", JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
						menadzer.getMenadzerSoba().sobe.get(id).setStatusSobe(StatusSobe.SLOBODNA);
						menadzer.getMenadzerSoba().pisanjeSoba();
						menadzer.getMenadzerSobarica().sobaSredjena(id,menadzer.getTrenutnoUlogovan());
						String[] izvestaj=new String[2];
						izvestaj[0]=LocalDate.now().toString();
						izvestaj[1]=menadzer.getTrenutnoUlogovan();
						menadzer.getMenadzerIzvestaja().ocisceneSobe.add(izvestaj);
						menadzer.getMenadzerIzvestaja().pisanjeIzvestajaSobarice();
						((SobaModel) tabelaSoba.getModel()).fireTableDataChanged();
					}
				}
			}
		});
	}
}
