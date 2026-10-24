package projekat.gui.administrator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.menadzeri.Menadzer;
import projekat.sobe.TipSobe;
import projekat.usluge.Cenovnik;
import projekat.usluge.DodatnaUsluga;

//klasa za dodavanje i izmenu cenovnika
public class DodavanjeCenovnikaFrame extends JFrame{
	
private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private int kljuc;
	private JFrame roditelj;
	
	public DodavanjeCenovnikaFrame(JFrame roditelj, Menadzer menadzer, int kljuc) {
        this.roditelj = roditelj;
        this.menadzer = menadzer;
        this.kljuc = kljuc;
        
        setTitle("Dodavanje cenovnika");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        cenovnikGUI();
        
        setVisible(true);
	}
	
	//metoda koja definise izgled prozora za dodavanje cenovnika
	private void cenovnikGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][]30[]");
		setLayout(layout);
		
		//velicina fonta
		float velicinaFonta = 15f;
		
		//dodavanje komponenti
		//naslov
		JLabel naslov = new JLabel("Unesite podatke o cenovniku:");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
		add(naslov, "span,align center");
		
		//id
		JLabel id = new JLabel("ID:");
		id.setFont(id.getFont().deriveFont(velicinaFonta));
		add(id);
		JTextField idPolje = new JTextField(20);
		add(idPolje);
		
		//datum od
		JLabel datumOd = new JLabel("Datum od:");
		datumOd.setFont(datumOd.getFont().deriveFont(velicinaFonta));
		add(datumOd);
		JTextField datumOdPolje = new JTextField(20);
		add(datumOdPolje);
		
		//datum do
		JLabel datumDo = new JLabel("Datum do:");
		datumDo.setFont(datumDo.getFont().deriveFont(velicinaFonta));
		add(datumDo);
		JTextField datumDoPolje = new JTextField(20);
		add(datumDoPolje);
		
		//cene za tipove soba
		JLabel ceneTipovaSoba = new JLabel("Cene tipova soba:");
		ceneTipovaSoba.setFont(ceneTipovaSoba.getFont().deriveFont(velicinaFonta));
		add(ceneTipovaSoba, "span");
		
		JPanel panelCeneTipovaSoba = new JPanel();
		MigLayout layoutCeneTipovaSoba = new MigLayout("wrap 2", "[][]", "[]");
		panelCeneTipovaSoba.setLayout(layoutCeneTipovaSoba);
		ArrayList<JTextField> ceneTipova = new ArrayList<JTextField>();
		for (TipSobe tip : menadzer.getMenadzerTipovaSoba().tipoviSoba.values()) {
			JLabel labela = new JLabel(tip.getTip() + ":");
			panelCeneTipovaSoba.add(labela);
			JTextField polje = new JTextField(10);
			panelCeneTipovaSoba.add(polje);
			ceneTipova.add(polje);
		}
		JScrollPane scrollPane = new JScrollPane(panelCeneTipovaSoba);
		scrollPane.setPreferredSize(new Dimension(300,200));
		add(scrollPane, "span");
		
		//cene za usluge
		JLabel ceneUsluga = new JLabel("Cene usluga:");
		ceneUsluga.setFont(ceneUsluga.getFont().deriveFont(velicinaFonta));
		add(ceneUsluga, "span");
		
		JPanel panelCeneUsluga = new JPanel();
		MigLayout layoutCeneUsluga = new MigLayout("wrap 2", "[][]", "[]");
		panelCeneUsluga.setLayout(layoutCeneUsluga);
		ArrayList<JTextField> dodatneUsluge = new ArrayList<JTextField>();
		for (String usluga : menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.keySet()) {
			JLabel labela = new JLabel(usluga + ":");
			panelCeneUsluga.add(labela);
			JTextField polje = new JTextField(10);
			panelCeneUsluga.add(polje);
			dodatneUsluge.add(polje);
		}
		JScrollPane scrollPaneUsluge = new JScrollPane(panelCeneUsluga);
		scrollPaneUsluge.setPreferredSize(new Dimension(300,200));
		add(scrollPaneUsluge, "span");
		
		//dugme za potvrdu
		String recString="";
		if (kljuc == -1)
			recString = "Dodaj";
		else
			recString = "Izmeni";
		JButton dugmePotvrdi = new JButton(recString);
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta));
		add(dugmePotvrdi, "span, align center,split 2");
		
		//popunjava polja sa podacima o cenovniku ako se radi o izmeni
		if(kljuc!=-1)
		{
			Cenovnik cenovnik = menadzer.getMenadzerCenovnika().cenovnici.get(kljuc);
			idPolje.setText(String.valueOf(cenovnik.getId()));
			datumOdPolje.setText(cenovnik.getDatumOd().toString());
			datumDoPolje.setText(cenovnik.getDatumDo().toString());
			int i = 0;
			for (TipSobe tip : menadzer.getMenadzerCenovnika().cenovnici.get(kljuc).getCeneSoba().keySet()) {
				ceneTipova.get(i).setText(String.valueOf(cenovnik.getCeneSoba().get(tip)));
				i++;
			}
			i = 0;
			for (DodatnaUsluga usluga : menadzer.getMenadzerCenovnika().cenovnici.get(kljuc).getCeneUsluga().keySet()) {
				dodatneUsluge.get(i).setText(String.valueOf(cenovnik.getCeneUsluga().get(usluga)));
				i++;
			}
			
			idPolje.setEditable(false);
		}
		
		//akcija na dugme za potvrdu
		dugmePotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//provera da li su uneti podaci
				if (idPolje.getText().equals("") || datumOdPolje.getText().equals("")
						|| datumDoPolje.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke!", "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//provera da li su uneti podaci ispravni
				String greske = "";
				
				//id mora biti broj i ne sme vec da postoji
				boolean broj = true;
				try {
					Integer.parseInt(idPolje.getText());
				} catch (NumberFormatException e1) {
					greske += "ID mora biti broj.\n";
					broj = false;
				}
				
				if(broj && kljuc == -1)
				{
					for (Cenovnik cenovnik : menadzer.getMenadzerCenovnika().cenovnici.values()) {
						if (cenovnik.getId() == Integer.parseInt(idPolje.getText())) {
							greske += "Cenovnik sa unetim ID vec postoji.\n";
							break;
						}
					}
				}
				
				//provera da li su uneti datumi ispravni, moraju biti u formatu yyyy-MM-dd i datum do mora biti posle datuma od i oba moraju biti posle danasnjeg
				boolean datum1 = true;
				if (!datumOdPolje.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					greske += "Datum od mora biti u formatu yyyy-MM-dd.\n";
					datum1 = false;
				}
				if(datum1)
				{
					LocalDate datumOd = LocalDate.parse(datumOdPolje.getText());
					if (datumOd.isBefore(LocalDate.now())) {
						greske += "Datum od mora biti posle danasnjeg.\n";
					}
				}
				
				boolean datum2 = true;
				if (!datumDoPolje.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					greske += "Datum do mora biti u formatu yyyy-MM-dd.\n";
					datum2 = false;
				}
				if (datum2) {
					LocalDate datumDo = LocalDate.parse(datumDoPolje.getText());
					if (datumDo.isBefore(LocalDate.now())) {
						greske += "Datum do mora biti posle danasnjeg.\n";
					}
				}
				if (datum1 && datum2) {
					LocalDate datumOd = LocalDate.parse(datumOdPolje.getText());
					LocalDate datumDo = LocalDate.parse(datumDoPolje.getText());
					if (datumDo.isBefore(datumOd)) {
						greske += "Datum do mora biti posle datuma od.\n";
					}
				}
				
				//datum ovog cenovnika ne sme da se poklapa sa datumima drugih cenovnika
				if (datum1 && datum2) {
					LocalDate datumOd = LocalDate.parse(datumOdPolje.getText());
					LocalDate datumDo = LocalDate.parse(datumDoPolje.getText());
					for (Cenovnik cenovnik : menadzer.getMenadzerCenovnika().cenovnici.values()) {
						if (kljuc != cenovnik.getId()) {
							if ((datumOd.isAfter(cenovnik.getDatumOd()) && datumOd.isBefore(cenovnik.getDatumDo()))
									|| (datumDo.isAfter(cenovnik.getDatumOd())
											&& datumDo.isBefore(cenovnik.getDatumDo()))
									|| (datumOd.isBefore(cenovnik.getDatumOd())
											&& datumDo.isAfter(cenovnik.getDatumDo()))
									|| (datumOd.isEqual(cenovnik.getDatumOd())
											|| datumDo.isEqual(cenovnik.getDatumDo()))) {
								greske += "Datum ovog cenovnika se poklapa sa datumima drugih cenovnika.\n";
								break;
							}
						}
					}
				}
				
				//provera da li su unete cene ispravne, moraju biti brojevi i veci od 0
				for (JTextField polje : ceneTipova) {
					if (!polje.getText().equals("")) {
						try {
							Double.parseDouble(polje.getText());
							if (Double.parseDouble(polje.getText()) < 0) {
								greske += "Cene tipova soba moraju biti veci od 0.\n";
								break;
							}
						} catch (NumberFormatException e1) {
							greske += "Cene tipova soba moraju biti brojevi.\n";
							break;
						}
					}
					else {
						greske += "Cene tipova soba moraju biti unete.\n";
						break;
					}
				}
				
				for (JTextField polje : dodatneUsluge) {
					if (!polje.getText().equals("")) {
						try {
							Double.parseDouble(polje.getText());
							if (Double.parseDouble(polje.getText()) < 0) {
								greske += "Cene usluga moraju biti veci od 0.\n";
								break;
							}
						} catch (NumberFormatException e1) {
							greske += "Cene usluga moraju biti brojevi.\n";
							break;
						}
					}
					else {
						greske += "Cene usluga moraju biti unete.\n";
						break;
					}
				}
				
				//ako ima gresaka, obavestavamo korisnika
				if (!greske.equals("")) {
					JOptionPane.showMessageDialog(null, greske, "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//ako je sve u redu, dodajemo cenovnik
				int id = Integer.parseInt(idPolje.getText());
				LocalDate datumOd = LocalDate.parse(datumOdPolje.getText());
				LocalDate datumDo = LocalDate.parse(datumDoPolje.getText());
				int i = 0;
				HashMap<TipSobe, Double> ceneTipovaSoba = new HashMap<TipSobe, Double>();
				for (TipSobe tip : menadzer.getMenadzerTipovaSoba().tipoviSoba.values()) {
                    if (!ceneTipova.get(i).getText().equals("")) {
                        ceneTipovaSoba.put(tip, Double.parseDouble(ceneTipova.get(i).getText()));
                    }
                    i++;
                }
				i = 0;
				HashMap<DodatnaUsluga, Double> ceneUsluga = new HashMap<DodatnaUsluga, Double>();
				for (DodatnaUsluga dodatnaUsluga : menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.values()) {
					if (!dodatneUsluge.get(i).getText().equals("")) {
						ceneUsluga.put(dodatnaUsluga, Double.parseDouble(dodatneUsluge.get(i).getText()));
					}
					i++;
				}
				
				if (kljuc == -1) {
					menadzer.getMenadzerCenovnika().dodavanjeCenovnika(id, datumOd, datumDo, ceneTipovaSoba, ceneUsluga);
					((CRUDFrame)roditelj).osveziTabelu();
				} else {
					menadzer.getMenadzerCenovnika().izmenaCenovnika(id, datumOd, datumDo, ceneTipovaSoba, ceneUsluga);
					((CRUDFrame)roditelj).osveziTabelu();
				}
				
				if (kljuc == -1) {
					JOptionPane.showMessageDialog(null, "Cenovnik je uspesno dodat.", "Obavestenje",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Cenovnik je uspesno izmenjen.", "Obavestenje",
							JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
			}
		});
	}
}
