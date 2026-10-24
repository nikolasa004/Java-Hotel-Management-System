package projekat.gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.menadzeri.Menadzer;

public class IzvestajFrame extends JFrame{
	
private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private String tip;

	public IzvestajFrame(Menadzer menadzer,String tip) {
		setTitle("Izvestaj "+tip);
		setSize(275, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		this.menadzer = menadzer;
		this.tip = tip;
		
		izvestajGUI();
		
		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za unos datuma
	private void izvestajGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][]30[]");
		setLayout(layout);
		
		//velicina fonta
		float velicinaFonta = 15f;
		
		//naslov
		JLabel naslov = new JLabel("Unesite period:");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
		add(naslov, "span, align center");
		
		//od
		JLabel od = new JLabel("Od:");
		od.setFont(od.getFont().deriveFont(velicinaFonta));
		add(od);
		JTextField datumOd = new JTextField(20);
		add(datumOd, "wrap");
		
		//do
		JLabel doo = new JLabel("Do:");
		doo.setFont(doo.getFont().deriveFont(velicinaFonta));
		add(doo);
		JTextField datumDo = new JTextField(20);
		add(datumDo, "wrap");
		
		//dugme za izvestaj
		JButton izvestaj = new JButton("Izvestaj");
		izvestaj.setFont(izvestaj.getFont().deriveFont(velicinaFonta));
		add(izvestaj, "span, align center");
		
		//listener za dugme za izvestaj
		izvestaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String greska="";
				//provera da li su uneti datumi
				if (datumOd.getText().equals("") || datumDo.getText().equals("")) {
					greska += "Morate uneti oba datuma!";
				}
				
				//datumi moraju biti u dobrom formatu yyyy-MM-dd i datumDo mora biti posle datumaOd
				boolean dobarFormat1 = true;
				if (!datumOd.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					greska += "Datum mora biti u formatu yyyy-MM-dd!";
					dobarFormat1 = false;
				}
				
				boolean dobarFormat2 = true;
				if (!datumDo.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					greska += "Datum mora biti u formatu yyyy-MM-dd!";
					dobarFormat2 = false;
				}
				
				if(dobarFormat1 && dobarFormat2)
				{
					LocalDate datumOd1 = LocalDate.parse(datumOd.getText());
					LocalDate datumDo1 = LocalDate.parse(datumDo.getText());
					
					if (datumDo1.isBefore(datumOd1)) {
						greska += "Datum do mora biti posle datuma od!";
					}
				}
				
				if (!greska.equals("")) {
					JOptionPane.showMessageDialog(null, greska, "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (tip.equals("sobarica"))
					new TabelaIzvestajSobaricaFrame(menadzer, datumOd.getText(), datumDo.getText());
				else if(tip.equals("prometa"))
				{
					new TabelaIzvestajPrometaFrame(menadzer,LocalDate.parse(datumOd.getText()),LocalDate.parse(datumDo.getText())); 
				}
				else if (tip.equals("rezervacija")) {
					int[] podaci=menadzer.getMenadzerIzvestaja().racunRezervacija(LocalDate.parse(datumOd.getText()), LocalDate.parse(datumDo.getText()), menadzer);
					int brojPotvrdjenih=podaci[0];
					int brojOdbijenih=podaci[1];
					int brojOtkazanih=podaci[2];
					
					JOptionPane.showMessageDialog(null, "Broj potvrdjenih rezervacija: " + brojPotvrdjenih + "\nBroj odbijenih rezervacija: " + brojOdbijenih + "\nBroj otkazanih rezervacija: " + brojOtkazanih, "Izvestaj rezervacija", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					new TabelaIzvestajSobaFrame(menadzer, datumOd.getText(), datumDo.getText());
				}
			}
		});
	}

}
