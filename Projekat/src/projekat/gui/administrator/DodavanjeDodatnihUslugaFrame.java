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

public class DodavanjeDodatnihUslugaFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private JFrame roditelj;

	public DodavanjeDodatnihUslugaFrame(Menadzer menadzer, JFrame roditelj) {
		setTitle("Dodavanje tipa sobe");
		setSize(275, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.menadzer = menadzer;
		this.roditelj = roditelj;
		
		dodatnaUslugaGUI();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za dodavanje tipa sobe
	private void dodatnaUslugaGUI()
    {
		//definisanje layout-a prozora
		MigLayout layout = new MigLayout("wrap 2", "[][]", "5[]20[]30[]");
        setLayout(layout);
        
        //velicina fonta
        float velicinaFonta = 15f;
        
        //definisanje komponenti
        //naslov
        JLabel naslov = new JLabel("Unesite dodatnu uslugu:");
        naslov.setFont(naslov.getFont().deriveFont(velicinaFonta));
        add(naslov, "span, align center");
        
        //ime
        JLabel tip = new JLabel("Naziv:");
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
					JOptionPane.showMessageDialog(null, "Niste uneli naziv!", "Greska", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//naziv ne sme vec da postoji)
				for(String dodatnaUsluga:menadzer.getMenadzerDodatnihUsluga().dodatneUsluge.keySet())
				{
					if(jTip.getText().equals(dodatnaUsluga)){
                    JOptionPane.showMessageDialog(null, "Dodatna usluga sa tim nazivom vec postoji!", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
					}
				}
				
				//ako nema gresaka dodajemo tip sobe
				menadzer.getMenadzerDodatnihUsluga().dodavanjeDodatnihUsluga(jTip.getText());
				((CRUDFrame) roditelj).osveziTabelu();
				
				//obavestavamo korisnika o uspesnom dodavanju ili izmeni
                 JOptionPane.showMessageDialog(null, "Uspesno ste dodali dodatnu uslugu!", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		 });
    }
}
