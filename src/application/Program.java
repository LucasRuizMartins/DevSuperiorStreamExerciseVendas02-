package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {
	public static void main(String[] args) throws Exception {

		Scanner sc = new Scanner(System.in);
		List<Sale> list = new ArrayList<>();
		
		
		System.out.println("Entre o caminho do arquivo:");
		String path =  sc.nextLine();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String fields[] = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);

				list.add(new Sale(month, year, items, seller, total));
				line = br.readLine();
			}
			/*
			 * METODO SEM USAR STREAM Map<String, Double> sellersMap = new TreeMap<>(); for
			 * (Sale s : list) { String aux = s.getSeller(); Double amount = s.getTotal();
			 * 
			 * if (sellersMap.containsKey(s.getSeller())) { amount = amount +
			 * sellersMap.get(s.getSeller()); sellersMap.put(aux, amount); } else {
			 * sellersMap.put(aux, amount); }
			 * 
			 * }
			 * 
			 */

			Map<String, Double> sellersMap = list.stream()
					.collect(Collectors.groupingBy(Sale::getSeller, Collectors.summingDouble(Sale::getTotal)));

			System.out.println();
			System.out.println();
			System.out.println("Total de vendas por vendedor:");

			for (String key : sellersMap.keySet()) {
				System.out.println(key + " : " + String.format(": %.2f", sellersMap.get(key)));
			}

		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		sc.close();
	}
}
