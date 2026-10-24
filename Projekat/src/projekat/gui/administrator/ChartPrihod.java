package projekat.gui.administrator;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import projekat.menadzeri.Menadzer;

//klasa za prikaz grafika prihoda po tipovima soba zadnjih godinu dana
public class ChartPrihod {
	
	//ukupan prihod
	private double ukupanPrihod;
	
	public ChartPrihod(Menadzer menadzer) {
		//kreiranje mape za cuvanje prihoda po tipovima soba, za svaki tip sobe po 12 meseci
		HashMap<String, ArrayList<Double>> prihodi=new HashMap<String, ArrayList<Double>>();
		
		//lista meseci
		ArrayList<Integer> meseciInt=new ArrayList<Integer>();
		
		//popunjavanje mape
		menadzer.getMenadzerIzvestaja().racunChartPrihoda(prihodi, meseciInt, menadzer);
		
		//kreiranje grafika
		XYChart chart=new XYChartBuilder().width(800).height(600).title("Prihod po tipu sobe").xAxisTitle("Mesec").yAxisTitle("Prihod").build();
		
		//ukupan prihod za godinu dana
		ukupanPrihod=0;
		
		for(String tipSobe:prihodi.keySet())
		{
			ArrayList<Double> prihodiTipa=prihodi.get(tipSobe);
			for (int i = 0; i < prihodiTipa.size(); i++) {
				ukupanPrihod += prihodiTipa.get(i);
			}
			
			chart.addSeries(tipSobe, meseciInt, prihodiTipa);
		}
		
		// prikazivanje grafikona unutar EDT-a
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grafikon prihoda");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel chartPanel = new XChartPanel<>(chart);
            frame.add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            //ispis ukupnog prihoda
            JLabel labela=new JLabel("Ukupan prihod za godinu dana: "+ukupanPrihod);
            labela.setFont(labela.getFont().deriveFont(15.0f));
            frame.add(labela,BorderLayout.SOUTH);
        });
	}

}
