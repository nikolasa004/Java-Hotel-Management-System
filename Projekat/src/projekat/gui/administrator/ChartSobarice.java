package projekat.gui.administrator;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import projekat.menadzeri.Menadzer;

public class ChartSobarice {
	
	//klasa za prikaz grafika rada sobarica zadnjih mesec dana
	public ChartSobarice(Menadzer menadzer) {
		//kreiranje mape za cuvanje radara sobarica u zadnjih mesec dana
		HashMap<String, Integer> sobarice=new HashMap<String,Integer>();
		
		//popunjavanje mape
		menadzer.getMenadzerIzvestaja().racunChartSobarica(sobarice, menadzer);
		
		//kreiranje grafika
		PieChart chart=new PieChartBuilder().width(800).height(600).title("Opterecenje sobarica zadnjih mesec dana").build();
	
		for (String sobarica : sobarice.keySet()) {
			chart.addSeries(sobarica, sobarice.get(sobarica));
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
        });
	}
}
