package projekat.gui.recepcioner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import projekat.sobe.Soba;

//klasa koja predstavlja prozor za check out
public class CheckOutFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JTable tabela;
	private JButton checkOut;
	
	public CheckOutFrame(Menadzer menadzer) {
		setTitle("Check out");
		setSize(800,500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		
		sobeGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za check out
	private void sobeGUI() {
		JToolBar toolbar = new JToolBar();
        add(toolbar, BorderLayout.NORTH);
        
        checkOut = new JButton("Check out");
        toolbar.add(checkOut);
        
        ArrayList<Soba> sobe = new ArrayList<Soba>();
		for (Soba soba : menadzer.getMenadzerSoba().sobe.values()) {
			if (soba.getStatusSobe() == StatusSobe.ZAUZETA) {
				sobe.add(soba);
			}
		}
        tabela = new JTable(new SobaModel(sobe));
        add(tabela);
        tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane);
		
		//listener za dugme potvrdi
		checkOut.addActionListener(new ActionListener() {
			    
            @Override
            public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate izabrati sobu.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int brojSobe=Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
                    menadzer.getMenadzerRecepcionera().checkOut(menadzer ,brojSobe);
					JOptionPane.showMessageDialog(null, "Check out uspesno obavljen.", "Check out", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
            }
		});
	}
}
