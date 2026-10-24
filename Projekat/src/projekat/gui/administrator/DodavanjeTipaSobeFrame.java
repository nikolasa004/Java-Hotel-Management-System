package projekat.gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import projekat.menadzeri.Menadzer;

//klasa za dodavanje tipa sobe
public class DodavanjeTipaSobeFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JFrame roditelj;

	public DodavanjeTipaSobeFrame(Menadzer menadzer, JFrame roditelj) {
		setTitle("Dodavanje tipa sobe");
		setSize(275, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.roditelj = roditelj;
		
		tipSobeGUI();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za dodavanje tipa sobe
	private void tipSobeGUI()
    {
		//definisanje layout-a prozora
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[]30[]");
        setLayout(layout);
        
        //velicina fonta
        float velicinaFonta = 15f;
        
        //definisanje komponenti
        //naslov
        JLabel naslov = new JLabel("Unesite tip sobe:");
        naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
        add(naslov, "span, align center");
        
        //ime
        JLabel tip = new JLabel("Tip:");
        tip.setFont(tip.getFont().deriveFont(velicinaFonta));
        add(tip);
        JTextField jTip = new JTextField(20);
        add(jTip);
 
		//dugme za potvrdu
		JButton dugmePotvrdi = new JButton("Dodaj");
		dugmePotvrdi.setFont(dugmePotvrdi.getFont().deriveFont(velicinaFonta));
		add(dugmePotvrdi, "split 2, align center, span");
		
		//akcija na dugme za potvrdu
		dugmePotvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //provera da li su uneti podaci
				if (jTip.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli tip!", "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//provera da li tip sobe vec postoji
				for (String tip : menadzer.getMenadzerTipovaSoba().tipoviSoba.keySet()) {
					if (tip.equals(jTip.getText())) {
						JOptionPane.showMessageDialog(null, "Tip sobe vec postoji!", "Greska",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				//tip sobe mora da bude ili samo broj ili u obliku broj+broj bez razmaka
				if (!jTip.getText().matches("^[0-9]+$") && !jTip.getText().matches("^[0-9]+[+][0-9]+$")) {
					JOptionPane.showMessageDialog(null, "Tip sobe mora biti broj ili broj+broj!", "Greska",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//ako nema gresaka dodajemo tip sobe
				menadzer.getMenadzerTipovaSoba().dodavanjeTipovaSoba(jTip.getText());
				((CRUDFrame) roditelj).osveziTabelu();
				
				//obavestavamo korisnika o uspesnom dodavanju ili izmeni
                 JOptionPane.showMessageDialog(null, "Uspesno ste dodali tip sobe!", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		 });
    }
}
