package projekat.gui.recepcioner;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;
import projekat.sobe.Soba;
import projekat.usluge.DodatnaUsluga;

//klasa za filtriranje rezervacija kod recepcionera
public class FiltriranjeRezervacijaFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private ArrayList<Rezervacija> pocetneRezervacije= new ArrayList<Rezervacija>();
	private ArrayList<Rezervacija> rezervacije= new ArrayList<Rezervacija>();
	private JFrame roditelj;

	public FiltriranjeRezervacijaFrame(Menadzer menadzer,ArrayList<Rezervacija> rezervacije,JFrame roditelj) {
		setTitle("Filtriranje rezervacija");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.pocetneRezervacije=rezervacije;
		this.roditelj = roditelj;
		
		filterGUI();

		setVisible(true);
	}

	//metoda koja definise izgled prozora za filtriranje rezervacija
	private void filterGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][]30[]");
		setLayout(layout);
		
		//velicina fonta
		float velicinaFonta = 15f;
		
		//naslov
		JLabel naslov = new JLabel("Filtriranje rezervacija:");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
		add(naslov, "span, align center");
		
		//filtriranje po tipu sobe
		JLabel tipSobe = new JLabel("Tip sobe:");
		tipSobe.setFont(tipSobe.getFont().deriveFont(velicinaFonta));
		add(tipSobe);
		ArrayList<String> tipoviSoba = new ArrayList<String>();
		for (String tip : menadzer.getMenadzerTipovaSoba().tipoviSoba.keySet()) {
			tipoviSoba.add(tip);
		}
		tipoviSoba.add("");
		JComboBox<String> jTipSobe = new JComboBox<String>(tipoviSoba.toArray(new String[0]));
		jTipSobe.setSelectedItem("");
		add(jTipSobe,"align center");
		
		//filtriranje po sobi
		JLabel soba = new JLabel("Soba:");
		soba.setFont(soba.getFont().deriveFont(velicinaFonta));
		add(soba);
		JTextField jSoba = new JTextField(20);
		jSoba.setFont(jSoba.getFont().deriveFont(velicinaFonta));
		add(jSoba);
		
		//filtriranje po ceni
		JLabel cena = new JLabel("Min:");
		cena.setFont(cena.getFont().deriveFont(velicinaFonta));
		add(cena);
		JTextField jCena = new JTextField(20);
		jCena.setFont(jCena.getFont().deriveFont(velicinaFonta));
		add(jCena);
		JLabel cena1 = new JLabel("Max:");
		cena1.setFont(cena1.getFont().deriveFont(velicinaFonta));
		add(cena1);
		JTextField jCena1 = new JTextField(20);
		jCena1.setFont(jCena1.getFont().deriveFont(velicinaFonta));
		add(jCena1);
		
		//filtriranje po dodatnim uslugama
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
		
		//checkbox da i da se proveravaju usluge jer ako nijedna nije cekirana onda se filtrira za tako
		JLabel uslugeLabela1 = new JLabel("Proveri usluge:");
		uslugeLabela1.setFont(uslugeLabela1.getFont().deriveFont(velicinaFonta));
		add(uslugeLabela1);
		JCheckBox proveraUsluga = new JCheckBox();
		add(proveraUsluga);
		
		//dugme za filtriranje
		JButton filtriraj = new JButton("Filtriraj");
		filtriraj.setFont(filtriraj.getFont().deriveFont(velicinaFonta));
		add(filtriraj, "span, align center");
		
		//listener za dugme za filtriranje
		filtriraj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String tipSobe = (String) jTipSobe.getSelectedItem();
				String soba = jSoba.getText();
				String cena = jCena.getText();
				String cena1 = jCena1.getText();
				HashMap<String, Boolean> uslugeMap = new HashMap<String, Boolean>();
				for (String usluga : usluge.keySet()) {
					uslugeMap.put(usluga, usluge.get(usluga).isSelected());
				}
				
				String greske = "";
				//soba mora da bude da bude broj i da postoji
				if(!soba.equals("")) {
					boolean sobaOk = true;
					try {
						Integer.parseInt(soba);
                    } catch (NumberFormatException e1) {
                        greske +="Broj sobe mora biti broj.\n";
                        sobaOk = false;
					}
					
					if(sobaOk)
					{
	                    boolean postoji = false;
	                    for (Soba soba1 : menadzer.getMenadzerSoba().sobe.values()) {
	                        if (soba1.getBrojSobe() == Integer.parseInt(soba)) {
	                            postoji = true;
	                            break;
	                        }
	                    }
						if (!postoji) {
							greske += "Soba sa unetim brojem ne postoji.\n";
						}
					}
				}
				
				//cena mora biti broj i pozitivan i max mora biti veci od min
				boolean cenaOk = true;
				if (!cena.equals("")) {
					try {
						double cenaDouble = Double.parseDouble(cena);
						if (cenaDouble < 0) {
							greske += "Cena mora biti pozitivan broj.\n";
							cenaOk = false;
						}
					} catch (NumberFormatException e1) {
						greske += "Cena mora biti broj.\n";
						cenaOk = false;
					}
				}
				boolean cena1Ok = true;
				if (!cena1.equals("")) {
					try {
						double cenaDouble = Double.parseDouble(cena1);
						if (cenaDouble < 0) {
							greske += "Cena mora biti pozitivan broj.\n";
							cena1Ok = false;
						}
					} catch (NumberFormatException e1) {
						greske += "Cena mora biti broj.\n";
						cena1Ok = false;
					}
				}
				if (cenaOk && cena1Ok && !cena.equals("") && !cena1.equals("")
						&& Double.parseDouble(cena) > Double.parseDouble(cena1)) {
					greske += "Minimalna cena mora biti manja od maksimalne.\n";
				}
				
				if (!greske.equals("")) {
					JOptionPane.showMessageDialog(null, greske, "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				for (Rezervacija rezervacija : pocetneRezervacije) {
					if (!tipSobe.equals("") && !tipSobe.equals(rezervacija.getTipSobe().getTip())) {
						continue;
					}
					if (!soba.equals("") && !soba.equals(String.valueOf(rezervacija.getSoba().getBrojSobe()))) {
						continue;
					}
					if (!cena.equals("") && rezervacija.getCena() < Double.parseDouble(cena)) {
						continue;
					}
					if (!cena1.equals("") && rezervacija.getCena() > Double.parseDouble(cena1)) {
						continue;
					}
					if (proveraUsluga.isSelected()) {
						boolean uslugeOk = true;
						for (String usluga : uslugeMap.keySet()) {
							for (DodatnaUsluga uslugaRez : rezervacija.getDodatneUsluge()) {
								if (usluga.equals(uslugaRez.getNaziv()) && !uslugeMap.get(usluga)) {
									uslugeOk = false;
									break;
								}
							}
						}
						if (!uslugeOk) {
							continue;
						}
					}
					rezervacije.add(rezervacija);
					
				}
				JOptionPane.showMessageDialog(null, "Rezervacije su uspesno filtrirane.", "Filtriranje", JOptionPane.INFORMATION_MESSAGE);
				((PregledRezervacijaRecepcionerFrame) roditelj).osveziTabelu(rezervacije);
				dispose();
			}
		});
	}
}
