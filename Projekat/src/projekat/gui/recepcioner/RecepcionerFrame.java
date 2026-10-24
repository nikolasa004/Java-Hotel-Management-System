package projekat.gui.recepcioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import net.miginfocom.swing.MigLayout;
import projekat.gui.gost.NapraviRezervacijuFrame;
import projekat.menadzeri.Menadzer;

//klasa koja predstavlja prozor za recepcionera
public class RecepcionerFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private String korisnickoIme;
	private Menadzer menadzer;

	public RecepcionerFrame(Menadzer menadzer) {
		setTitle("Recepcioner");
		setSize(550, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		MigLayout layout = new MigLayout("wrap", "[grow]", "40[][]");
		setLayout(layout);
		this.korisnickoIme = menadzer.getTrenutnoUlogovan();
		this.menadzer = menadzer;
		
		recepcionerGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za recepcionera
	private void recepcionerGUI()
	{
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
        //dodaj gosta
        JButton dodajGosta = new JButton("Dodaj gosta");
        meni.add(dodajGosta);
        
        //pravljenje rezervacije za novog gosta
        JButton napraviRezervaciju = new JButton("Napravi rezervaciju");
        meni.add(napraviRezervaciju);
        
        //pregled rezervacija
        JButton pregledRezervacija = new JButton("Pregled rezervacija");
        meni.add(pregledRezervacija);
        
        //check-in
        JButton checkIn = new JButton("Check-in");
        meni.add(checkIn);
        
        //check-out
        JButton checkOut = new JButton("Check-out");
        meni.add(checkOut);
        
        //izvestaj
        JMenuBar meniBar = new JMenuBar();
        JMenu izvestaji = new JMenu("Izvestaji");
        JMenuItem izvestajGost= new JMenuItem("Gosti");
        izvestaji.add(izvestajGost);
        JMenuItem izvestajZauzetost = new JMenuItem("Zauzetost soba");
        izvestaji.add(izvestajZauzetost);
        JMenuItem izvestajDolasciOdlasci = new JMenuItem("Dolasci i odlasci");
        izvestaji.add(izvestajDolasciOdlasci);
        meniBar.add(izvestaji);
        meni.add(meniBar);
        
        //listeneri za dugmad
        dodajGosta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerRecepcionera().dodavanjeGosta(menadzer);
			}
		});
        
		napraviRezervaciju.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new NapraviRezervacijuFrame(menadzer, "") ;
			}
		});

		pregledRezervacija.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PregledRezervacijaRecepcionerFrame(menadzer, korisnickoIme);
			}
		});

		checkIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CheckInRezervacijeFrame(menadzer);
			}
		});

		checkOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CheckOutFrame(menadzer);
			}
		});
		
		izvestajGost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new IzvestajGostijuFrame(menadzer);
			}
		});
		
		izvestajZauzetost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new IzvestajSobaFrame(menadzer);
			}
		});
		
		izvestajDolasciOdlasci.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menadzer.getMenadzerRecepcionera().dnevniIzvestaj(menadzer);
            }
		});
	}
}
