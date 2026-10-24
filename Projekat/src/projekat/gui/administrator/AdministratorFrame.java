package projekat.gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.miginfocom.swing.MigLayout;
import projekat.menadzeri.Menadzer;

//klasa koja predstavlja prozor za administratora
public class AdministratorFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private String korisnickoIme;
	private Menadzer menadzer;

	public AdministratorFrame(Menadzer menadzer)
	{
		setTitle("Administrator");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		MigLayout layout = new MigLayout("wrap", "[grow]", "40[][]");
		setLayout(layout);
		this.korisnickoIme = menadzer.getTrenutnoUlogovan();
		this.menadzer = menadzer;
		
		adminGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za administratora
	private void adminGUI()
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
		JMenuBar meni = new JMenuBar();
		
		//dodavanje delova menija
		//pregled entiteta
		JMenu crudMeni = new JMenu("Pregled entiteta");
		JMenuItem administratori = new JMenuItem("Administratori");
		crudMeni.add(administratori);
		JMenuItem recepcioneri = new JMenuItem("Recepcioneri");
		crudMeni.add(recepcioneri);
		JMenuItem sobarice = new JMenuItem("Sobarice");
		crudMeni.add(sobarice);
		JMenuItem gosti = new JMenuItem("Gosti");
		crudMeni.add(gosti);
		JMenuItem tipoviSobe = new JMenuItem("Tipovi soba");
		crudMeni.add(tipoviSobe);
		JMenuItem sobe = new JMenuItem("Sobe");
		crudMeni.add(sobe);
		JMenuItem rezervacije = new JMenuItem("Rezervacije");
		crudMeni.add(rezervacije);
		JMenuItem usluge = new JMenuItem("Dodatne usluge");
		crudMeni.add(usluge);
		JMenuItem cenovnik = new JMenuItem("Cenovnici");
		crudMeni.add(cenovnik);
		
		meni.add(crudMeni);
		
		//dodavanje akcija na meni za pregled entiteta
		administratori.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer,"administrator");
			}
		});
		
		recepcioneri.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "recepcioner");
			}
		});
		
		sobarice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "sobarica");
			}
		});
		
		gosti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "gost");
			}
		});
		
		tipoviSobe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "tipSobe");
			}
		});
		
		sobe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "soba");
			}
		});
		
		rezervacije.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "rezervacija");
			}
		});
		
		usluge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "dodatnaUsluga");
			}
		});
		
		cenovnik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CRUDFrame(menadzer, "cenovnik");
			}
		});
		
		//izvestaji
		JMenu izvestajiMeni = new JMenu("Izvestaji");
		JMenuItem promet = new JMenuItem("Prometa");
		izvestajiMeni.add(promet);
		JMenuItem sobarica = new JMenuItem("Sobarica");
		izvestajiMeni.add(sobarica);
		JMenuItem rezeracija = new JMenuItem("Rezervacija");
		izvestajiMeni.add(rezeracija);
		JMenuItem soba = new JMenuItem("Soba");
		izvestajiMeni.add(soba);
		JMenuItem grafik1 = new JMenuItem("Grafikon prometa");
		izvestajiMeni.add(grafik1);
		JMenuItem grafik2 = new JMenuItem("Grafikon sobarica");
		izvestajiMeni.add(grafik2);
		JMenuItem grafik3 = new JMenuItem("Grafikon rezervacija");
		izvestajiMeni.add(grafik3);
		
		meni.add(izvestajiMeni);
		
		//dodavanje akcija na meni za izvestaje
		promet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerAdministratora().izvestaj(menadzer, "prometa");
			}
		});
		
		sobarica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerAdministratora().izvestaj(menadzer,"sobarica");
			}
		});
		
		rezeracija.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerAdministratora().izvestaj(menadzer, "rezervacija");
			}
		});
		
		soba.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerAdministratora().izvestaj(menadzer, "soba");
			}
		});
		
		grafik1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerAdministratora().chartPrihoda(menadzer);
			}
		});
		
		grafik2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerAdministratora().chartSobarica(menadzer);
			}
		});
		
		grafik3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menadzer.getMenadzerAdministratora().chartRezervacija(menadzer);
			}
		});
		
		//dodavanje zaposlenih
		JMenu dodavanje= new JMenu("Dodavanje zaposlenih");
		JMenuItem dodajRecepcionera = new JMenuItem("Recepcionera");
		dodavanje.add(dodajRecepcionera);
		JMenuItem dodajSobaricu = new JMenuItem("Sobarica");
		dodavanje.add(dodajSobaricu);
		
		meni.add(dodavanje);
		
		//dodavanje akcija na meni za dodavanje zaposlenih
		dodajRecepcionera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// poziv forme za dodavanje recepcionera
				new DodavanjeZaposlenihFrame("recepcioner",menadzer,"",null);
			}
		});
		
		dodajSobaricu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // poziv forme za dodavanje sobarice
                new DodavanjeZaposlenihFrame("sobarica",menadzer,"",null);
                }
            });
		
		setJMenuBar(meni);
	}

}
