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
import projekat.gui.administrator.TableModeli.SobaModel;
import projekat.menadzeri.Menadzer;
import projekat.sobe.Soba;

//klasa koja predstavlja prozor za pregled soba
public class PregledSobaFrame extends JFrame{

	private static final long serialVersionUID=1L;
	
	private Menadzer menadzer;
	private JTable tabela;
	private JButton dugmeIzaberi;
	private String tipSobe;
	private int idRezervacije;
	private JFrame roditelj;
	private JFrame ovajFrame=this;
	
	public PregledSobaFrame(Menadzer menadzer, String tipSobe, int idRezervacije,JFrame roditelj)
    {
        setTitle("Pregled soba");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        this.menadzer = menadzer;
        this.tipSobe = tipSobe;
        this.idRezervacije = idRezervacije;
        this.roditelj = roditelj;
        
        sobeGUI();
        
        setVisible(true);
    }
	
	//metoda koja definise izgled prozora za recepcionera
	private void sobeGUI()
    {
		JToolBar toolbar = new JToolBar();
        add(toolbar, BorderLayout.NORTH);
        
        dugmeIzaberi = new JButton("Izaberi");
        toolbar.add(dugmeIzaberi);
        
        ArrayList<Soba> sobe = new ArrayList<Soba>();
		//popunjavanje liste soba
        menadzer.getMenadzerRecepcionera().pregledSoba(menadzer, sobe, tipSobe);
        
        tabela = new JTable(new SobaModel(sobe));
        add(tabela);
        tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane);
		
		//listener za dugme potvrdi
		dugmeIzaberi.addActionListener(new ActionListener() {
			    
            @Override
            public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate izabrati sobu.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int brojSobe=Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
					new PotvrdaCheckInFrame(menadzer,idRezervacije,brojSobe,roditelj,ovajFrame);
				}
            }
		});
    }
}
