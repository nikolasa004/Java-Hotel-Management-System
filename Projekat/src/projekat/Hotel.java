package projekat;

import projekat.gui.LoginFrame;
import projekat.menadzeri.Menadzer;

public class Hotel {
	public static void main(String[] args)
	{
		//kreiranje glavnog menadzera
		Menadzer menadzer = new Menadzer();
		//ucitavanje podataka iz datoteka
		menadzer.ucitajPodatke();
		
		//prikaz login prozora
		LoginFrame login = new LoginFrame(menadzer);
		login.toString();
	}
}
