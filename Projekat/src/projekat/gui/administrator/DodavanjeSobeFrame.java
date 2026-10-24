package projekat.gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.enumi.StatusSobe;
import projekat.menadzeri.Menadzer;
import projekat.sobe.Soba;
import projekat.sobe.TipSobe;

//klasa za dodavanje i izmenu sobe
public class DodavanjeSobeFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private int kljuc;
	private JFrame roditelj;

	public DodavanjeSobeFrame(Menadzer menadzer,int kljuc, JFrame roditelj) {
		setTitle("Dodavanje sobe");
		setSize(400, 350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.kljuc = kljuc;
		this.roditelj = roditelj;
		
		sobaGUI();

		setVisible(true);
	}

	//metoda koja definise izgled prozora za dodavanje i izmenu sobe
	private void sobaGUI() {
		// postavljanje layout-a
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[][][][][][][]30[]");
		setLayout(layout);
		
		//velicina fonta
		float velicinaFonta = 15f;

		// definisanje komponenti
		//naslov
		JLabel naslov = new JLabel("Unesite podatke o sobi:");
		naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
		add(naslov, "span, align center");
		
		//broj sobe
		JLabel brojSobe = new JLabel("Broj sobe:");
		brojSobe.setFont(brojSobe.getFont().deriveFont(velicinaFonta));
		add(brojSobe);
		JTextField jBrojSobe = new JTextField(20);
		add(jBrojSobe);
		
		//tip sobe
		JLabel tipSobe = new JLabel("Tip sobe:");
		tipSobe.setFont(tipSobe.getFont().deriveFont(velicinaFonta));
		add(tipSobe);
		String[] tipoviSoba = menadzer.getMenadzerTipovaSoba().tipoviSoba.keySet().toArray(new String[0]);
		JComboBox<String> jTipSobe = new JComboBox<String>(tipoviSoba);
		add(jTipSobe,"align center");
		
		//status sobe
		JLabel statusSobe = new JLabel("Status sobe:");
		statusSobe.setFont(statusSobe.getFont().deriveFont(velicinaFonta));
		add(statusSobe);
		ButtonGroup grupa = new ButtonGroup();
		JRadioButton jStatusSobe1 = new JRadioButton("Slobodna");
		jStatusSobe1.setSelected(true);
		add(jStatusSobe1, "split 3");
		JRadioButton jStatusSobe2 = new JRadioButton("Zauzeta");
		add(jStatusSobe2);
		JRadioButton jStatusSobe3 = new JRadioButton("Spremanje");
		add(jStatusSobe3, "wrap");
		grupa.add(jStatusSobe1);
		grupa.add(jStatusSobe2);
		grupa.add(jStatusSobe3);
		
		//klima
		JLabel klima = new JLabel("Klima:");
		klima.setFont(klima.getFont().deriveFont(velicinaFonta));
		add(klima);
		JCheckBox jKlima = new JCheckBox();
		add(jKlima);
		
		//tv
		JLabel tv = new JLabel("TV:");
		tv.setFont(tv.getFont().deriveFont(velicinaFonta));
		add(tv);
		JCheckBox jTv = new JCheckBox();
		add(jTv);
		
		//balkon
		JLabel balkon = new JLabel("Balkon:");
		balkon.setFont(balkon.getFont().deriveFont(velicinaFonta));
		add(balkon);
		JCheckBox jBalkon = new JCheckBox();
		add(jBalkon);
		
		//pusacka
		JLabel pusacka = new JLabel("Pusacka:");
		pusacka.setFont(pusacka.getFont().deriveFont(velicinaFonta));
		add(pusacka);
		JCheckBox jPusacka = new JCheckBox();
		add(jPusacka);
		
		//dugme za potvrdu
		String recString="";
		if(kljuc==0){
            recString="Dodaj";
        }else{
            recString="Izmeni";
        }
		JButton dugmePotvrdi = new JButton(recString);
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta));
		add(dugmePotvrdi, "split 2, align center, span");
		
		//popunjavanje polja ako se radi o izmeni
		if(kljuc!=0) {
            
			Soba soba=menadzer.getMenadzerSoba().sobe.get(kljuc);
			
			jBrojSobe.setText(String.valueOf(soba.getBrojSobe()));
			jTipSobe.setSelectedItem(soba.getTipSobe().getTip());
			if(String.valueOf(soba.getStatusSobe()).equals("SLOBODNA"))
			{
                jStatusSobe1.setSelected(true);
            }
			else if(String.valueOf(soba.getStatusSobe()).equals("ZAUZETA")) 
			{
            	jStatusSobe2.setSelected(true);
            }
			else {
				jStatusSobe3.setSelected(true);
			}
			jKlima.setSelected(soba.isKlima_uredjaj());
			jTv.setSelected(soba.isTv());
			jBalkon.setSelected(soba.isBalkon());
			jPusacka.setSelected(soba.isPusacka());
			
			jBrojSobe.setEditable(false);
		}
		
		//akcija na dugme za potvrdu
		dugmePotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//provera da li su uneti podaci
				if (jBrojSobe.getText().equals("") || jTipSobe.getSelectedItem().toString().equals("")
						|| (!jStatusSobe1.isSelected() && !jStatusSobe2.isSelected() && !jStatusSobe3.isSelected())) 
				{
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke!", "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//provera da li su uneti podaci ispravni
				String greske = "";
				
			    //broj sobe mora biti broj i mora biti jedinstven i pozitivan
				int brojSobe = 0;
				try {
                    brojSobe = Integer.parseInt(jBrojSobe.getText());
					if (brojSobe < 0) {
						greske += "Broj sobe mora biti pozitivan\n";
					}
                } catch (NumberFormatException e1) {
                    greske +="Broj sobe mora biti broj\n";
                }
				if(kljuc==0) {
					for (Soba soba : menadzer.getMenadzerSoba().sobe.values()) {
						if (soba.getBrojSobe() == brojSobe) {
							greske += "Soba sa unetim brojem vec postoji\n";
							break;
						}
					}
				}
				
				//tip sobe mora da postoji
				TipSobe tipSobe = menadzer.getMenadzerTipovaSoba().tipoviSoba.get(jTipSobe.getSelectedItem().toString());
				
				if(!greske.equals(""))
				{
					JOptionPane.showMessageDialog(null, greske, "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//kreiranje sobe
				StatusSobe statusSobe = StatusSobe.SLOBODNA;
				if(jStatusSobe2.isSelected()) {
                    statusSobe = StatusSobe.ZAUZETA;
                    }
				else if(jStatusSobe3.isSelected()) {
	                    statusSobe = StatusSobe.SPREMANJE;
				}
				
				boolean klima = jKlima.isSelected();
				boolean tv = jTv.isSelected();
				boolean balkon = jBalkon.isSelected();
				boolean pusacka = jPusacka.isSelected();
				
				
				if(kljuc==0) {
                    //dodavanje sobe
                    menadzer.getMenadzerSoba().dodavanjeSoba(tipSobe, statusSobe, brojSobe,klima,balkon,tv,pusacka);
                    ((CRUDFrame) roditelj).osveziTabelu();
				}
				else {
					// izmena sobe
					menadzer.getMenadzerSoba().izmenaSoba(brojSobe,tipSobe, statusSobe,klima,balkon,tv,pusacka);
					((CRUDFrame) roditelj).osveziTabelu();
				}
				
				if (kljuc == 0) {
					JOptionPane.showMessageDialog(null, "Soba je uspesno dodata!", "Obavestenje",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Soba je uspesno izmenjena!", "Obavestenje",
							JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
			}
		});
	}
}
