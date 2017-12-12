/**
 *
 */
package local.projects.betting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.net.TelnetAppender;

/**
 * @author aricciardiello
 *
 */
public class Generator2 {
	public static int COUNTER = 0;

	// http://www.vincicasa.it/estrazioni/archivio-concorsi
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		boolean contains = false;
		do {
			COUNTER++;
			int numeri[] = new int[5];
			int numero;
			StringBuilder combinazione = new StringBuilder();

			for (int i = 0; i < 5; i++) {
				numero = (int) (Math.random() * 40);
				if (numero == 0) {
					numero++;
				} else {
					numeri[i] = numero;
					combinazione.append(numero + " ");
				}
			}

			System.out.print(combinazione.toString().trim());
			Set estrazioni = getEstrazioni();

			if (COUNTER > 744475) {
				contains = estrazioni.contains(combinazione.toString().trim());
				if (contains) {
					System.out.println("Tentativo: " + COUNTER);
					System.out.println(contains);
					System.out.println(combinazione.toString().trim());
				}
			}

		} while (!contains);

	}

	public static Set getEstrazioni() {
		Set<String> estrazioni = new HashSet<String>();

		estrazioni.add("2 9 20 28 31");
		estrazioni.add("3 19 24 27 28");
		estrazioni.add("12 15 16 23 38");
		estrazioni.add("1 6 10 14 22");
		estrazioni.add("1 2 7 29 30");
		estrazioni.add("3 7 22 31 36");
		estrazioni.add("12 13 23 35 40");
		estrazioni.add("9 12 15 19 25");
		estrazioni.add("7 13 30 33 39");
		estrazioni.add("3 5 6 20 28");
		estrazioni.add("9 20 21 22 39");
		estrazioni.add("23 26 33 34 38");
		estrazioni.add("9 11 17 26 30");
		estrazioni.add("2 5 8 16 27");
		estrazioni.add("7 11 18 24 30");
		estrazioni.add("6 15 19 20 32");
		estrazioni.add("5 22 26 30 38");
		estrazioni.add("10 14 17 28 39");
		estrazioni.add("11 19 21 26 29");
		estrazioni.add("16 17 30 31 35");
		estrazioni.add("4 13 26 35 39");
		estrazioni.add("3 12 19 34 38");
		estrazioni.add("2 6 33 34 38");
		estrazioni.add("2 3 11 15 23");
		estrazioni.add("3 5 7 16 33");
		estrazioni.add("4 13 23 32 36");
		estrazioni.add("7 22 28 39 40");
		estrazioni.add("3 11 18 23 28");
		estrazioni.add("12 16 20 31 32");
		estrazioni.add("10 12 23 37 39");
		estrazioni.add("3 15 19 26 31");
		estrazioni.add("3 4 14 18 37");
		estrazioni.add("5 14 19 22 40");
		estrazioni.add("22 24 26 31 35");
		estrazioni.add("7 22 26 32 33");
		estrazioni.add("21 29 30 32 40");
		estrazioni.add("21 22 25 32 34");
		estrazioni.add("3 6 27 36 38");
		estrazioni.add("15 29 31 38 40");
		estrazioni.add("4 17 22 27 30");
		estrazioni.add("7 10 17 22 36");
		estrazioni.add("9 14 17 21 32");
		estrazioni.add("1 11 17 19 23");
		estrazioni.add("1 5 8 14 39");
		estrazioni.add("11 17 21 30 40");
		estrazioni.add("9 12 18 27 37");
		estrazioni.add("6 9 10 21 22");
		estrazioni.add("17 26 27 30 36");
		estrazioni.add("11 13 19 25 38");
		estrazioni.add("13 26 28 32 35");
		estrazioni.add("1 2 22 28 34");
		estrazioni.add("6 15 19 31 35");
		estrazioni.add("20 27 33 35 37");
		estrazioni.add("5 11 14 20 38");
		estrazioni.add("9 12 24 27 35");
		estrazioni.add("9 18 19 23 27");
		estrazioni.add("5 16 21 29 40");
		estrazioni.add("2 8 12 37 39");
		estrazioni.add("22 23 25 30 33");
		estrazioni.add("1 2 17 19 37");
		estrazioni.add("18 19 28 29 37");
		estrazioni.add("4 17 25 26 40");
		estrazioni.add("5 9 21 28 37");
		estrazioni.add("8 15 19 26 34");
		estrazioni.add("2 6 10 32 34");
		estrazioni.add("5 8 21 25 31");
		estrazioni.add("1 2 8 9 17");
		estrazioni.add("4 24 29 31 39");
		estrazioni.add("14 19 25 26 34");
		estrazioni.add("8 10 11 15 24");
		estrazioni.add("7 11 23 29 40");
		estrazioni.add("2 13 28 34 39");
		estrazioni.add("2 3 7 11 33");
		estrazioni.add("12 15 19 21 32");
		estrazioni.add("13 15 17 23 24");
		estrazioni.add("9 11 15 21 34");
		estrazioni.add("1 4 7 30 40");
		estrazioni.add("9 26 29 31 32");
		estrazioni.add("3 12 17 26 33");
		estrazioni.add("6 14 22 33 36");
		estrazioni.add("1 5 10 13 39");
		estrazioni.add("12 14 18 19 33");
		estrazioni.add("9 13 31 38 40");
		estrazioni.add("11 14 17 27 40");
		estrazioni.add("7 9 10 22 34");
		estrazioni.add("22 23 28 29 30");
		estrazioni.add("6 15 28 31 34");
		estrazioni.add("7 11 25 26 37");
		estrazioni.add("12 15 16 18 33");
		estrazioni.add("15 18 31 34 35");
		estrazioni.add("7 23 32 34 37");
		estrazioni.add("9 13 32 37 40");
		estrazioni.add("5 27 28 31 39");
		estrazioni.add("5 22 27 32 39");
		estrazioni.add("14 15 25 29 37");
		estrazioni.add("3 6 7 29 39");
		estrazioni.add("11 12 25 34 38");
		estrazioni.add("13 14 16 30 32");
		estrazioni.add("6 11 12 21 24");
		estrazioni.add("8 23 30 35 38");
		estrazioni.add("6 15 23 37 38");
		estrazioni.add("5 11 18 25 37");
		estrazioni.add("9 18 22 23 34");
		estrazioni.add("5 7 13 25 35");
		estrazioni.add("4 25 27 32 35");
		estrazioni.add("7 26 28 36 40");
		estrazioni.add("3 10 25 26 31");
		estrazioni.add("16 18 23 24 38");
		estrazioni.add("6 9 12 22 39");
		estrazioni.add("4 5 28 33 35");
		estrazioni.add("2 4 18 19 29");
		estrazioni.add("6 9 19 34 36");
		estrazioni.add("3 4 17 19 23");
		estrazioni.add("3 8 16 24 36");
		estrazioni.add("1 14 15 31 32");
		estrazioni.add("14 23 28 29 33");
		estrazioni.add("10 17 29 37 39");
		estrazioni.add("1 6 21 24 40");
		estrazioni.add("10 24 27 29 40");
		estrazioni.add("9 16 29 32 37");
		estrazioni.add("1 8 16 22 37");
		estrazioni.add("6 13 14 17 21");
		estrazioni.add("1 8 10 18 36");
		estrazioni.add("5 17 20 29 39");
		estrazioni.add("3 12 22 23 33");
		estrazioni.add("5 7 10 18 28");
		estrazioni.add("13 18 28 35 40");
		estrazioni.add("13 16 19 29 38");
		estrazioni.add("12 21 25 33 38");
		estrazioni.add("9 10 13 15 26");
		estrazioni.add("7 10 12 13 20");
		estrazioni.add("4 33 36 39 40");
		estrazioni.add("2 4 6 17 26");
		estrazioni.add("5 18 29 30 31");
		estrazioni.add("17 25 27 29 32");
		estrazioni.add("3 11 15 18 23");
		estrazioni.add("7 15 20 34 39");
		estrazioni.add("2 13 19 21 35");
		estrazioni.add("9 15 32 39 40");

		return estrazioni;
	}

}
