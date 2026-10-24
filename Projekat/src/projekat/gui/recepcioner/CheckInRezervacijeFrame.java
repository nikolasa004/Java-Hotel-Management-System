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

import projekat.enumi.StatusRezervacije;
import projekat.gui.administrator.TableModeli.RezervacijaModel;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.TipSobe;

//klasa koja predstavlja prozor za check in rezervacija
public class CheckInRezervacijeFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JTable tabelaRezervacija;
	private JButton dugmeCheckIn;
	private JFrame ovajProzor=this;
	
	public CheckInRezervacijeFrame(Menadzer menadzer) {
		this.menadzer = menadzer;
		setTitle("Check in rezervacije");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		
		checkInGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za check in rezervacija
	private void checkInGUI() {
		JToolBar toolbar = new JToolBar();
        add(toolbar, BorderLayout.NORTH);
        
        dugmeCheckIn = new JButton("Izaberi");
        toolbar.add(dugmeCheckIn);
        
        ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		for (Rezervacija rezervacija : menadzer.getMenadzerRezervacija().rezervacije.values()) {
			if(rezervacija.getStatusRezervacije()==StatusRezervacije.POTVRDJENA && rezervacija.getSoba().getBrojSobe()==-1)
                rezervacije.add(rezervacija);
		}
        tabelaRezervacija = new JTable(new RezervacijaModel(rezervacije));
        add(tabelaRezervacija);
        tabelaRezervacija.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaRezervacija.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrol
		JScrollPane scrollPane = new JScrollPane(tabelaRezervacija);
		add(scrollPane);
		
		//listener za dugme potvrdi
		dugmeCheckIn.addActionListener(new ActionListener() {
			    
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(tabelaRezervacija.getSelectedRow() == -1) {
            	    JOptionPane.showMessageDialog(CheckInRezervacijeFrame.this, "Morate izabrati rezervaciju.");
            	}
            	else {
            		int id=Integer.parseInt(tabelaRezervacija.getValueAt(tabelaRezervacija.getSelectedRow(), 0).toString());
            		Rezervacija rezervacija=menadzer.getMenadzerRezervacija().rezervacije.get(id);
            		TipSobe tipSobe=rezervacija.getTipSobe();
            		new PregledSobaFrame(menadzer,tipSobe.getTip(),id,ovajProzor);
				}
            }
		});
	}
}
