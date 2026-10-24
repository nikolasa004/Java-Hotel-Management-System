package projekat.gui.recepcioner;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import projekat.menadzeri.Menadzer;
import projekat.rezervacije.Rezervacija;
import projekat.usluge.Cenovnik;
import projekat.usluge.DodatnaUsluga;

//klasa koja predstavlja prozor za potvrdu check-in-a
public class PotvrdaCheckInFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private int idRezervacije;
	private int idSobe;
	private JFrame roditeljRoditelj;
	private JFrame roditelj;
	
	public PotvrdaCheckInFrame(Menadzer menadzer, int idRezervacije, int idSobe, JFrame roditeljRoditelj,JFrame roditelj) {
		this.menadzer = menadzer;
		this.idRezervacije = idRezervacije;
		this.idSobe = idSobe;
		this.roditeljRoditelj = roditeljRoditelj;
		this.roditelj = roditelj;
		setTitle("Potvrda check-in-a");
		setSize(300, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		potvrdaCheckInGUI();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za potvrdu check-in-a
	private void potvrdaCheckInGUI() {
		MigLayout layout = new MigLayout("wrap", "[]", "5[]20[][]30");
		setLayout(layout);
		
		//velicina fonta
		float velicinaFonta = 15f;
		
		//naslov
		JLabel naslov = new JLabel("Da li ste zavrsili check-in?");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
		add(naslov, "align center");
		
		//dodatne usluge ako treba da se dodaju
		JLabel dodatneUsluge = new JLabel("Dodadne usluge:");
		dodatneUsluge.setFont(dodatneUsluge.getFont().deriveFont(velicinaFonta));
		add(dodatneUsluge, "align center");
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
		
		//dugme
		JButton dugmePotvrdi = new JButton("Potvrdi");
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta));
		add(dugmePotvrdi, "align center");
		
		//popunjava se polje za dodatne usluge koje su vec dodate
		HashMap<String, DodatnaUsluga> dodatneUslugeGosta = new HashMap<String, DodatnaUsluga>();
        Rezervacija rezervacija = menadzer.getMenadzerRezervacija().rezervacije.get(idRezervacije);
		for (DodatnaUsluga usluga : rezervacija.getDodatneUsluge()) {
			dodatneUslugeGosta.put(usluga.getNaziv(), usluga);
		}
		for (String usluga : dodatneUslugeGosta.keySet()) {
			usluge.get(usluga).setSelected(true);
		}
		
		//akcija na dugme
		dugmePotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//dodavanje dodatnih usluga i racunanje cene
				Rezervacija rezervacija = menadzer.getMenadzerRezervacija().rezervacije.get(idRezervacije);
				LocalDate datumRezervacije = rezervacija.getDatumRezervacije();
				double cena = rezervacija.getCena();
				Cenovnik cenovnik=menadzer.getMenadzerCenovnika().pronalazakCenovnika(datumRezervacije);
				if (cenovnik == null) {
					JOptionPane.showMessageDialog(null, "Nema cenovnika za uneti datum.", "Greska",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				LocalDate datumNocenja=datumRezervacije;
				int nocenja=rezervacija.getBrojNocenja();
				int brojOsoba=rezervacija.getBrojOsoba();
				for (String usluga : usluge.keySet()) {
					if (usluge.get(usluga).isSelected()) {
						if (!dodatneUslugeGosta.containsKey(usluga)) {
							rezervacija.getDodatneUsluge().add(menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.get(usluga));
							while (datumNocenja.isBefore(datumRezervacije.plusDays(nocenja))) {
								if (datumNocenja.isAfter(cenovnik.getDatumOd()) && datumNocenja.isBefore(cenovnik.getDatumDo())) {
									cena += cenovnik.getCeneUsluga().get(menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.get(usluga))*brojOsoba;
									datumNocenja = datumNocenja.plusDays(1);
								}
								else {
									cenovnik =menadzer.getMenadzerCenovnika().pronalazakCenovnika(datumNocenja);
									if (cenovnik == null) {
										JOptionPane.showMessageDialog(null, "Nema cenovnika za uneti datum.", "Greska",
												JOptionPane.WARNING_MESSAGE);
										return;
									}
								}
							}
							rezervacija.setCena(cena);
						}
					} else {
						if (dodatneUslugeGosta.containsKey(usluga)) {
							rezervacija.getDodatneUsluge().remove(dodatneUslugeGosta.get(usluga));
							while (datumNocenja.isBefore(datumRezervacije.plusDays(nocenja))) {
								if (datumNocenja.isAfter(cenovnik.getDatumOd())
										&& datumNocenja.isBefore(cenovnik.getDatumDo())) {
									cena -= cenovnik.getCeneUsluga().get(
											menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.get(usluga)) * brojOsoba;
									datumNocenja = datumNocenja.plusDays(1);
								} else {
									cenovnik =menadzer.getMenadzerCenovnika().pronalazakCenovnika(datumNocenja);
									if (cenovnik == null) {
										JOptionPane.showMessageDialog(null, "Nema cenovnika za uneti datum.", "Greska",
												JOptionPane.WARNING_MESSAGE);
										return;
									}
								}
							}
						}
					}
				}
				
				//check-in
				menadzer.getMenadzerRecepcionera().checkIn(menadzer,idRezervacije, idSobe);
				
				JOptionPane.showMessageDialog(null, "Check-in je uspesno zavrsen.", "Check-in", JOptionPane.INFORMATION_MESSAGE);
				roditeljRoditelj.dispose();
				roditelj.dispose();
				dispose();
			}
		});
	}
}
