package projekat.gui.administrator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import projekat.gui.administrator.TableModeli.AdministratorModel;
import projekat.gui.administrator.TableModeli.CenovnikModel;
import projekat.gui.administrator.TableModeli.DodatneUslugeModel;
import projekat.gui.administrator.TableModeli.GostModel;
import projekat.gui.administrator.TableModeli.RecepcionerModel;
import projekat.gui.administrator.TableModeli.RezervacijaModel;
import projekat.gui.administrator.TableModeli.SobaModel;
import projekat.gui.administrator.TableModeli.SobaricaModel;
import projekat.gui.administrator.TableModeli.TipSobeModel;
import projekat.menadzeri.Menadzer;

//klasa koja sluzi za prikazivanje tabele sa podacima o entitetima
public class CRUDFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	private String entitet;
	private JTable tabela;
	private JButton dodaj;
	private JButton izmeni;
	private JButton obrisi;

	public CRUDFrame(Menadzer menadzer, String entitet) {
		setTitle("CRUD");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		this.menadzer = menadzer;
		this.entitet = entitet;
		
		entitetiGui();

		setVisible(true);
	}
	
	//metoda koja definise izgled prozora za prikazivanje entiteta
	private void entitetiGui()
    {
		//definisanje komponenti
		//tool bar sa dugmicima za dodavanje, izmenu i brisanje entiteta
		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		//dugme za dodavanje entiteta
		dodaj = new JButton("Dodaj");
		toolBar.add(dodaj);
		
		//dugme za izmenu entiteta
		izmeni = new JButton("Izmeni");
		toolBar.add(izmeni);
		
		//dugme za brisanje entiteta
		obrisi = new JButton("Obrisi");
		toolBar.add(obrisi);
		
		//namestanje modela tabele u zavisnosti od entiteta i dodavanje akcija na dugmad
		if (entitet.equals("administrator")) {
			dodajListenerZaAdministratore();
		}
		else if(entitet.equals("recepcioner"))
        {
            dodajListenerZaRecepcionere();
		}
		else if(entitet.equals("sobarica"))
		{
			dodajListenerZaSobarice();
		}
		else if (entitet.equals("gost"))
		{
			dodajListenerZaGoste();
		}
		else if (entitet.equals("tipSobe"))
		{
			dodajListenerZaTipoveSoba();
		}
		else if (entitet.equals("soba"))
		{
			dodajListenerZaSobe();
		}
		else if (entitet.equals("rezervacija"))
		{
			dodajListenerZaRezervacije();
		}
		else if (entitet.equals("dodatnaUsluga"))
		{
			dodajListenerZaDodatneUsluge();
		} else if (entitet.equals("cenovnik")) 
		{
			dodajListenerZaCenovnik();
		}

		add(tabela);
		tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		
		//panel za skrolovanje tabele
		JScrollPane skrol = new JScrollPane(tabela);
		add(skrol);
    }
	
	//metoda za dodavanje listenera za administatore
	public void dodajListenerZaAdministratore() {
		tabela = new JTable(new AdministratorModel(menadzer.getMenadzerAdministratora()));
		
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeZaposlenihFrame(entitet, menadzer,"", CRUDFrame.this);
			}
		});
		
		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null,"Morate selektovati red u tabeli!","Greska",JOptionPane.WARNING_MESSAGE);
				}
				else {
					new DodavanjeZaposlenihFrame(entitet, menadzer, tabela.getValueAt(tabela.getSelectedRow(), 0).toString(), CRUDFrame.this);
				}
			}
		});
		
		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete administratora?", "Brisanje administratora", JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION)
					{
						menadzer.getMenadzerAdministratora().brisanjeAdministratora(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda za dodavanje listenera za recepcionere
	public void dodajListenerZaRecepcionere() {
		tabela = new JTable(new RecepcionerModel(menadzer.getMenadzerRecepcionera()));
		
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeZaposlenihFrame(entitet, menadzer,"", CRUDFrame.this);
			}
		});
		
		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null,"Morate selektovati red u tabeli!","Greska",JOptionPane.WARNING_MESSAGE);
				}
				else {
					new DodavanjeZaposlenihFrame(entitet, menadzer, tabela.getValueAt(tabela.getSelectedRow(), 0).toString(), CRUDFrame.this);
				}
			}
		});
		
		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete recepcionera?", "Brisanje recepcionera", JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION)
					{
						menadzer.getMenadzerRecepcionera().brisanjeRecepcionera(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda za dodavanje listenera za sobarice
	public void dodajListenerZaSobarice() {
		tabela = new JTable(new SobaricaModel(menadzer.getMenadzerSobarica()));
		
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeZaposlenihFrame(entitet, menadzer,"", CRUDFrame.this);
			}
		});
		
		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null,"Morate selektovati red u tabeli!","Greska",JOptionPane.WARNING_MESSAGE);
				}
				else {
					new DodavanjeZaposlenihFrame(entitet, menadzer, tabela.getValueAt(tabela.getSelectedRow(), 0).toString(), CRUDFrame.this);
				}
			}
		});
		
		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete sobaricu?", "Brisanje sobarice", JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION)
					{
						menadzer.getMenadzerSobarica().brisanjeSobarica(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
						osveziTabelu();
					}
				}
			}
		});
    }
	
	//metoda za dodavanje listenera za goste
	public void dodajListenerZaGoste() {
		tabela = new JTable(new GostModel(menadzer.getMenadzerGostiju()));

		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeGostaFrame(menadzer, "", CRUDFrame.this);
			}
		});

		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					new DodavanjeGostaFrame(menadzer,tabela.getValueAt(tabela.getSelectedRow(), 0).toString(), CRUDFrame.this);
				}
			}
		});

		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete gosta?",
							"Brisanje gosta", JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
						menadzer.getMenadzerGostiju().brisanjeGostiju(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda za dodavanje listenera za tipove soba
	public void dodajListenerZaTipoveSoba() {
		tabela = new JTable(new TipSobeModel(menadzer.getMenadzerTipovaSoba()));
		
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeTipaSobeFrame(menadzer,CRUDFrame.this);
			}
		});
		
		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Tipovi soba ne mogu da se menjaju!", "Greska",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		
		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da obrisete tip sobe?", "Brisanje tipa sobe",
							JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
						menadzer.getMenadzerTipovaSoba()
								.brisanjeTipovaSoba(tabela.getValueAt(tabela.getSelectedRow(), 0).toString(),menadzer.getMenadzerCenovnika(),menadzer.getMenadzerSoba(),menadzer.getMenadzerRezervacija(),menadzer.getMenadzerSobarica(),menadzer.getMenadzerGostiju());
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda za dodavanje listenera za sobe
	public void dodajListenerZaSobe() {
		tabela = new JTable(new SobaModel(menadzer.getMenadzerSoba()));

		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeSobeFrame(menadzer, 0, CRUDFrame.this);
			}
		});

		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					new DodavanjeSobeFrame(menadzer,Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()),CRUDFrame.this);
				}
			}
		});

		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete sobu?",
							"Brisanje sobe", JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
						menadzer.getMenadzerSoba()
								.brisanjeSoba(Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()),menadzer.getMenadzerRezervacija(),menadzer.getMenadzerSobarica());
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda za dodavanje listenera za rezervacije
	public void dodajListenerZaRezervacije() {
		tabela = new JTable(new RezervacijaModel(menadzer.getMenadzerRezervacija()));

		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeRezervacijeFrame(menadzer, -1, CRUDFrame.this);
			}
		});

		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					new DodavanjeRezervacijeFrame(menadzer,Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()),CRUDFrame.this);
				}
			}
		});

		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da obrisete rezervaciju?", "Brisanje rezervacije",
							JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
						menadzer.getMenadzerRezervacija().brisanjeRezervacija(Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()),menadzer.getMenadzerGostiju());
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda za dodavanje listenera za dodatne usluge
	public void dodajListenerZaDodatneUsluge() {
		tabela = new JTable(new DodatneUslugeModel(menadzer.getMenadzerDodatnihUsluga()));

		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeDodatnihUslugaFrame(menadzer, CRUDFrame.this);
			}
		});

		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Dodatne usluge ne mogu da se menjaju!", "Greska",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da obrisete dodatnu uslugu?", "Brisanje dodatne usluge",
							JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
						menadzer.getMenadzerDodatnihUsluga().brisanjeDodatnihUsluga(
								tabela.getValueAt(tabela.getSelectedRow(), 0).toString(),menadzer.getMenadzerCenovnika(),menadzer.getMenadzerRezervacija());
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda za dodavanje listenera za cenovnik
	public void dodajListenerZaCenovnik() {
		tabela = new JTable(new CenovnikModel(menadzer.getMenadzerCenovnika()));

		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodavanjeCenovnikaFrame(CRUDFrame.this,menadzer,-1);
			}
		});

		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    new DodavanjeCenovnikaFrame(CRUDFrame.this,menadzer,Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()));
			}
			}
		});

		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int opcija = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da obrisete cenovnik?", "Brisanje cenovnika",
							JOptionPane.YES_NO_OPTION);
					if (opcija == JOptionPane.YES_OPTION) {
						menadzer.getMenadzerCenovnika().brisanjeCenovnika(
								Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()));
						osveziTabelu();
					}
				}
			}
		});
	}
	
	//metoda koja osvezava podatke u tabeli
	public void osveziTabelu() {
		if(entitet.equals("administrator"))
		{
			((AdministratorModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("recepcioner")) {
			((RecepcionerModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("sobarica")) {
			((SobaricaModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("gost")) {
			((GostModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("tipSobe")) {
			((TipSobeModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("soba")) {
			((SobaModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("rezervacija")) {
			((RezervacijaModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("dodatnaUsluga")) {
			((DodatneUslugeModel) this.tabela.getModel()).fireTableDataChanged();
		}
		else if (entitet.equals("cenovnik")) {
			((CenovnikModel) this.tabela.getModel()).fireTableDataChanged();
		}
	}
}
