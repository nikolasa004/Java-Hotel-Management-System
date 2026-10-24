package projekat.gui.gost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import net.miginfocom.swing.MigLayout;
import projekat.menadzeri.Menadzer;

//klasa koja predstavlja prozor za gosta
public class GostFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private String korisnickoIme;
	private Menadzer menadzer;

	public GostFrame(Menadzer menadzer) {
		setTitle("Gost");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		MigLayout layout = new MigLayout("wrap", "[grow]", "40[][]");
		setLayout(layout);
		this.korisnickoIme = menadzer.getTrenutnoUlogovan();
		this.menadzer = menadzer;
		
		gostGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za gosta
	private void gostGUI() {
        //tekst koji se ispisuje u prozoru
        JLabel labela = new JLabel("Dobrodosli, " + korisnickoIme + "!");
        //povecavanje fonta
        labela.setFont(labela.getFont().deriveFont(15.0f));
        add(labela, "align center");
        JLabel labela1 = new JLabel("Izaberite opciju iz menija.");
        labela1.setFont(labela1.getFont().deriveFont(20.0f));
        add(labela1, "align center");
        //dodavanje menija
        JToolBar meni = new JToolBar();
        add(meni, "dock north");
        
        //dodavanje delova menija
        //napravi rezervaciju
        JButton napraviRezervaciju = new JButton("Napravi rezervaciju");
        meni.add(napraviRezervaciju);
        
        //pregledaj rezervacije
        JButton pregledajRezervacije = new JButton("Pregledaj rezervacije");
        meni.add(pregledajRezervacije);
        
        //listener za dugme za napravu rezervacije
        napraviRezervaciju.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NapraviRezervacijuFrame(menadzer, korisnickoIme);
			}
        });
        
        //listener za dugme za pregled rezervacija
        pregledajRezervacije.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	menadzer.getMenadzerGostiju().pregledRezervacija(menadzer, korisnickoIme);
            }
        });
	}
}
