package projekat.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import projekat.gui.administrator.AdministratorFrame;
import projekat.gui.gost.GostFrame;
import projekat.gui.recepcioner.RecepcionerFrame;
import projekat.gui.sobarica.SobaricaFrame;
import projekat.menadzeri.Menadzer;

//klasa koja predstavlja prozor za prijavu
public class LoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;

	public LoginFrame(Menadzer menadzer)
	{
		this.menadzer = menadzer;
		
		//definisanje prozora
		JDialog loginDijalog = new JDialog();
		loginDijalog.setTitle("Prijava");
		loginDijalog.setLocationRelativeTo(null);
		loginDijalog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		loginDijalog.setResizable(false);
		//definisanje GUI-a u okviru prozora
		loginGUI(loginDijalog);
		loginDijalog.pack();
		loginDijalog.setVisible(true);
	}
	
	//metoda koja definise izgled prozora za prijavu
	private void loginGUI(JDialog dijalog)
	{	
		//postavljanje layout-a
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[][]20[]");
		dijalog.setLayout(layout);
		
		//definisanje komponenti
		//polja za unos korisnickog imena i lozinke
		JTextField jKorisnickoIme = new JTextField(20);
		JPasswordField jLozinka = new JPasswordField(20);
		//dugme za prijavu
		JButton dugmePrijavi= new JButton("Prijavi se");
		dijalog.getRootPane().setDefaultButton(dugmePrijavi);
		//dugme za odustajanje
		JButton dugmeOdustani = new JButton("Odustani");
		//labele
		JLabel naslov = new JLabel("Unesite vase korisnicko ime i lozinku:");
		JLabel labelaKorisnickoIme = new JLabel("Korisnicko ime:");
		JLabel labelaLozinka = new JLabel("Lozinka:");
		
		//dodavanje komponenti u dijalog
		dijalog.add(naslov, "span");
		dijalog.add(labelaKorisnickoIme);
		dijalog.add(jKorisnickoIme);
		dijalog.add(labelaLozinka);
		dijalog.add(jLozinka);
		dijalog.add(dugmePrijavi, "split 2, span, align center");
		dijalog.add(dugmeOdustani);
		
		//dodavanje akcija na dugmad
		//prvo za dugme prijava
		dugmePrijavi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korisnickoIme = jKorisnickoIme.getText().trim();
				String lozinka = new String(jLozinka.getPassword());
				
				//provera da li su uneti podaci
				if (korisnickoIme.equals("") || lozinka.equals(""))
				{
					// ako nisu, obavestavamo korisnika preko dijaloga
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke!", "Greska", JOptionPane.WARNING_MESSAGE);
					
					return;
				}
				
				//provera da li korisnik postoji u sistemu
				Object[] podaci=menadzer.prijava(korisnickoIme, lozinka);
				boolean postoji = (boolean) podaci[0];
				String tipKorisnika = (String) podaci[1];
				
				if (!postoji) {
					// ako podaci nisu ispravni, obavestavamo korisnika
					JOptionPane.showMessageDialog(null, "Pogresno korisnicko ime ili lozinka!", "Greska", JOptionPane.ERROR_MESSAGE);
					
					return;
				}
				
				//prikaz glavnog prozora ako su podaci ispravni
				if (tipKorisnika.equals("Administrator"))
                {
					new AdministratorFrame(menadzer);
                }
                else if (tipKorisnika.equals("Recepcioner"))
                {
                	new RecepcionerFrame(menadzer);
                }
				else if (tipKorisnika.equals("Sobarica")) {
					new SobaricaFrame(menadzer);
				}
				else 
				{
					new GostFrame(menadzer);
				}
				
				dijalog.setVisible(false);
				dijalog.dispose();
			}
		});
		
		//za dugme odustani
		dugmeOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dijalog.setVisible(false);
				dijalog.dispose();
			}
		});
	}
}
