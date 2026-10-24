package projekat.gui.administrator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.enumi.StatusRezervacije;
import projekat.korisnici.Gost;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;
import projekat.usluge.DodatnaUsluga;

//klasa za dodavanje i izmenu rezervacije
public class DodavanjeRezervacijeFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private int kljuc;
	private JFrame roditelj;
	
	public DodavanjeRezervacijeFrame(Menadzer menadzer, int kljuc, JFrame roditelj) {
		this.menadzer = menadzer;
		this.kljuc = kljuc;
		this.roditelj = roditelj;

		setTitle("Dodavanje rezervacije");
        setSize(600, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		rezervacijaGUI();

		setVisible(true);
	}

	//klasa koja definise izgled prozora za dodavanje rezervacije
	private void rezervacijaGUI() {
		//postavljanje layout-a
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][][][][][][][]30[]");
        setLayout(layout);
        
        //velicina fonta
        float velicinaFonta = 15f;
        
        //definisanje komponenti
        //naslov
        JLabel naslov = new JLabel("Unesite podatke o rezervaciji:");
        naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
        add(naslov, "span, align center");
        
        //id
        JLabel idLabela = new JLabel("ID:");
        idLabela.setFont(idLabela.getFont().deriveFont(velicinaFonta));
        add(idLabela);
        JTextField idPolje = new JTextField(20);
        add(idPolje);
        
        //status
        JLabel statusLabela = new JLabel("Status:");
        statusLabela.setFont(statusLabela.getFont().deriveFont(velicinaFonta));
        add(statusLabela);
        ButtonGroup statusGrupa = new ButtonGroup();
        JRadioButton status1 = new JRadioButton("Na cekanju");
        status1.setFont(status1.getFont().deriveFont(velicinaFonta));
        status1.setSelected(true);
        statusGrupa.add(status1);
        add(status1,"split 4");
        JRadioButton status2 = new JRadioButton("Potvrdjena");
        status2.setFont(status2.getFont().deriveFont(velicinaFonta));
        statusGrupa.add(status2);
        add(status2);
        JRadioButton status3 = new JRadioButton("Odbijena");
        status3.setFont(status3.getFont().deriveFont(velicinaFonta));
        statusGrupa.add(status3);
        add(status3);
        JRadioButton status4 = new JRadioButton("Otkazana");
        status4.setFont(status4.getFont().deriveFont(velicinaFonta));
        statusGrupa.add(status4);
        add(status4);
        
        //datum rezervacije
        JLabel datumLabela = new JLabel("Datum rezervacije:");
        datumLabela.setFont(datumLabela.getFont().deriveFont(velicinaFonta));
        add(datumLabela);
        JTextField datumPolje = new JTextField(20);
        add(datumPolje);
        
        //broj nocenja
        JLabel nocenjaLabela = new JLabel("Broj nocenja:");
        nocenjaLabela.setFont(nocenjaLabela.getFont().deriveFont(velicinaFonta));
        add(nocenjaLabela);
        JTextField nocenjaPolje = new JTextField(20);
        add(nocenjaPolje);
        
        //tip  sobe
        JLabel tipLabela = new JLabel("Tip sobe:");
        tipLabela.setFont(tipLabela.getFont().deriveFont(velicinaFonta));
        add(tipLabela);
        String[] tipoviSoba = menadzer.getMenadzerTipovaSoba().tipoviSoba.keySet().toArray(new String[0]);
        JComboBox<String> tipPolje = new JComboBox<String>(tipoviSoba);
        add(tipPolje);
        
        //broj osoba
        JLabel osobaLabela = new JLabel("Broj osoba:");
        osobaLabela.setFont(osobaLabela.getFont().deriveFont(velicinaFonta));
        add(osobaLabela);
        JTextField osobaPolje = new JTextField(20);
        add(osobaPolje);
        
        //dodatne usluge
        JLabel uslugeLabela = new JLabel("Dodatne usluge:");
        uslugeLabela.setFont(uslugeLabela.getFont().deriveFont(velicinaFonta));
        add(uslugeLabela,"wrap");
        HashMap<String, JCheckBox> usluge = new HashMap<String, JCheckBox>();
        JPanel panelUsluga = new JPanel();
        panelUsluga.setLayout(new MigLayout("wrap 1", "[]", "[]"));
		for (String usluga : menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.keySet())
		{
			JCheckBox uslugaPolje = new JCheckBox(usluga);
			usluge.put(usluga, uslugaPolje);
			panelUsluga.add(uslugaPolje);
		}
		JScrollPane scrollPane = new JScrollPane(panelUsluga);
		scrollPane.setPreferredSize(new Dimension(300, 200));
		add(scrollPane, "span");
		
		//cena
		JLabel cenaLabela = new JLabel("Cena:");
		cenaLabela.setFont(cenaLabela.getFont().deriveFont(velicinaFonta));
		add(cenaLabela);
		JTextField cenaPolje = new JTextField(20);
		add(cenaPolje);
		
		//ime rezeracije
		JLabel imeLabela = new JLabel("Ime gosta:");
		imeLabela.setFont(imeLabela.getFont().deriveFont(velicinaFonta));
		add(imeLabela);
		JTextField imePolje = new JTextField(20);
		add(imePolje);
		
		//soba
		JLabel sobaLabela = new JLabel("Soba:");
		sobaLabela.setFont(sobaLabela.getFont().deriveFont(velicinaFonta));
		add(sobaLabela);
		JTextField sobaPolje = new JTextField(20);
		add(sobaPolje);
		
		//dodaci vezani za sobe
		JLabel osobineSobe = new JLabel("Osobine sobe:");
		osobineSobe.setFont(osobineSobe.getFont().deriveFont(velicinaFonta));
		add(osobineSobe, "span");
		HashMap<String, JCheckBox> osobine = new HashMap<String, JCheckBox>();
		JPanel panelOsobina = new JPanel();
		panelOsobina.setLayout(new MigLayout("wrap 1", "[]", "[]"));
		JCheckBox klima = new JCheckBox("Klima");
		osobine.put("Klima", klima);
		panelOsobina.add(klima);
		JCheckBox tv = new JCheckBox("TV");
		osobine.put("TV", tv);
		panelOsobina.add(tv);
		JCheckBox balkon = new JCheckBox("Balkon");
		osobine.put("Balkon", balkon);
		panelOsobina.add(balkon);
		JCheckBox pusacka = new JCheckBox("Pusacka");
		osobine.put("Pusacka", pusacka);
		panelOsobina.add(pusacka);
		JScrollPane scrollPaneOsobine = new JScrollPane(panelOsobina);
		scrollPaneOsobine.setPreferredSize(new Dimension(300, 200));
		add(scrollPaneOsobine, "span");
		
		//dugme za potvrdu
		JButton dugmePotvrdi = new JButton("Potvrdi");
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta));
		add(dugmePotvrdi, "span, align center");
		
		//popunjavanje polja ako je izmena
		if(!(kljuc == -1))
        {
			Rezervacija rezervacija = menadzer.getMenadzerRezervacija().rezervacije.get(kljuc);
			
			idPolje.setText(String.valueOf(rezervacija.getId()));
			StatusRezervacije statusRezervacije = rezervacija.getStatusRezervacije();
			if(String.valueOf(statusRezervacije).equals("NA_CEKANJU"))
				status1.setSelected(true);
			else if(String.valueOf(statusRezervacije).equals("POTVRDJENA"))
				status2.setSelected(true);
			else if(String.valueOf(statusRezervacije).equals("ODBIJENA"))
				status3.setSelected(true);
			else
				status4.setSelected(true);
			datumPolje.setText(rezervacija.getDatumRezervacije().toString());
			nocenjaPolje.setText(String.valueOf(rezervacija.getBrojNocenja()));
			tipPolje.setSelectedItem(rezervacija.getTipSobe().getTip());
			osobaPolje.setText(String.valueOf(rezervacija.getBrojOsoba()));
			for (DodatnaUsluga usluga : rezervacija.getDodatneUsluge())
			{
			    usluge.get(usluga.getNaziv()).setSelected(true);
			}
			cenaPolje.setText(String.valueOf(rezervacija.getCena()));
			imePolje.setText(rezervacija.getImeRezervacije());
			if(rezervacija.getSoba()!=null)
				sobaPolje.setText(String.valueOf(rezervacija.getSoba().getBrojSobe()));
			else
				sobaPolje.setText("-1");
			String napomenaString=rezervacija.getNapomena();
			String[] napomena=napomenaString.split(", ");
			for (String osobina : napomena)
			{
				if(osobine.containsKey(osobina))
				{
					osobine.get(osobina).setSelected(true);
				}
			}
			
			idPolje.setEditable(false);
			imePolje.setEditable(false);
        }
		
		//akcija na dugme potvrdi
		dugmePotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//provera da li je sve uneto
				if (idPolje.getText().equals("") || datumPolje.getText().equals("") || nocenjaPolje.getText().equals("")
						|| tipPolje.getSelectedItem() .toString().equals("") || osobaPolje.getText().equals("")
					 || imePolje.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke!", "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//provera ispravnosti unetih podataka
				String greske = "";
				
				//id mora biti broj i jedinstven
				int id = -1;
				try {
					id = Integer.parseInt(idPolje.getText());
				} catch (NumberFormatException e1) {
					greske += "ID mora biti broj.\n";
				}
				if(kljuc==-1)
				{
					for (int id1 : menadzer.getMenadzerRezervacija().rezervacije.keySet())
                    {
                        if (id1 == id)
                        {
                            greske += "ID mora biti jedinstven.\n";
                            break;
                        }
                    }
				}
				
				//datum mora biti u formatu yyyy-MM-dd i mora biti posle danasnjeg
				String datum = datumPolje.getText();
				boolean ispravanDatum = true;
				if (!datum.matches("\\d{4}-\\d{2}-\\d{2}")) {
					greske += "Datum mora biti u formatu yyyy-MM-dd.\n";
					ispravanDatum = false;
				}
				if (ispravanDatum) {
					LocalDate danas = LocalDate.now();
					LocalDate unetiDatum = LocalDate.parse(datum);
					if (unetiDatum.isBefore(danas)) {
						greske += "Datum mora biti posle danasnjeg.\n";
					}
				}
				
				//broj nocenja mora biti broj
				int nocenja = -1;
				try {
					nocenja = Integer.parseInt(nocenjaPolje.getText());
				} catch (NumberFormatException e1) {
					greske += "Broj nocenja mora biti broj.\n";
				}
				
				//broj osoba mora biti broj
				int osoba = -1;
				try {
					osoba = Integer.parseInt(osobaPolje.getText());
				} catch (NumberFormatException e1) {
					greske += "Broj osoba mora biti broj.\n";
				}
				
				//cena mora biti broj ili nista
				double cena=0;
				if (!cenaPolje.getText().equals("")) {
					try {
						cena=Double.parseDouble(cenaPolje.getText());
					} catch (NumberFormatException e1) {
						greske += "Cena mora biti broj.\n";
					}
				}
				
				//tip sobe
				TipSobe tipSobe=menadzer.getMenadzerTipovaSoba().tipoviSoba.get(tipPolje.getSelectedItem().toString());
				
				//broj osoba mora biti manji ili jednak kapacitetu sobe
				if(osoba!=-1)
				{
					String tipString=tipPolje.getSelectedItem().toString();
					String[] delovi=tipString.split("\\+");
					int brojKreveta=0;
					for (int i = 0; i < delovi.length; i++) {
						brojKreveta += Integer.parseInt(delovi[i]);
					}
					if (osoba > brojKreveta) {
						greske += "Soba tog tipa ne moze da primi toliko osoba\n";
					}
				}
				
				//soba mora postojati
				Soba soba = null;
				if(sobaPolje.getText().equals("-1"))
				{	soba=new Soba();
					soba.setBrojSobe(-1);
				}
				else{
					soba=menadzer.getMenadzerSoba().sobe.get(Integer.parseInt(sobaPolje.getText()));
					if(soba==null) {
						greske += "Soba mora postojati.\n";
					}
					else {
						if (soba.getTipSobe().getTip().equals(tipSobe.getTip()) == false)
							greske += "Soba mora biti unetog tipa.\n";
					}
				}
				
				//gost mora postojati
				Gost gost=menadzer.getMenadzerGostiju().getGosti().get(imePolje .getText());
				if (gost == null) {
					greske += "Gost mora postojati.\n";
				}
				
				//ako ima gresaka, obavesti korisnika
				if (!greske.equals("")) {
					JOptionPane.showMessageDialog(null, greske, "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//dodavanje rezervacije
				StatusRezervacije status = StatusRezervacije.NA_CEKANJU;
				if (status2.isSelected())
					status = StatusRezervacije.POTVRDJENA;
				else if (status3.isSelected())
					status = StatusRezervacije.ODBIJENA;
				else if (status4.isSelected())
					status = StatusRezervacije.OTKAZANA;
				LocalDate datumRezervacije = LocalDate.parse(datum);
				ArrayList<DodatnaUsluga> dodatneUsluge = new ArrayList<DodatnaUsluga>();
				for (String usluga : usluge.keySet()) {
					if (usluge.get(usluga).isSelected()) {
						dodatneUsluge.add(menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.get(usluga));
					}
				}
				String imeRezervacije=imePolje.getText();
				String napomenaString="";
				for (String osobina : osobine.keySet()) {
					if (osobine.get(osobina).isSelected()) {
						napomenaString += osobina + ", ";
					}
				}
				if(napomenaString.length()>2)
					napomenaString=napomenaString.substring(0, napomenaString.length()-2);
				
				if(kljuc==-1)
				{
					menadzer.getMenadzerRezervacija().dodavanjeRezervacija(id, status, datumRezervacije, tipSobe,osoba, nocenja, dodatneUsluge, cena, imeRezervacije,napomenaString);
					gost.getRezervacije().add(menadzer.getMenadzerRezervacija().rezervacije.get(id));
					((CRUDFrame)roditelj).osveziTabelu();
				}
				else {
					menadzer.getMenadzerRezervacija().izmenaRezervacija(id, status, datumRezervacije, tipSobe,
							osoba,nocenja, dodatneUsluge, cena, imeRezervacije,soba,napomenaString);
					((CRUDFrame) roditelj).osveziTabelu();
				}
				
				if (kljuc == -1)
					JOptionPane.showMessageDialog(null, "Rezervacija je uspesno dodata.", "Obavestenje",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Rezervacija je uspesno izmenjena.", "Obavestenje",
							JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
	}
}
