import java.util.HashMap;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
public class Main {
	public static void main(String[] args) {
	

		String fileName = "ERBB2.fasta";
		try {
			
			/*Getting ready to read files*/
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);
			boolean header = true;
			String allLines = "";
			String line = bufferedReader.readLine();
			
			
			ArrayList<String> sequences= new ArrayList<String>(); /*For sequence strings*/
			
			ArrayList<String> headers = new ArrayList<String>(); /*For header strings*/
			
			int counter = 0;
			String lines = "";
			
			/*Extracting sequence strings and header strings aligned in 2 arrayLists*/
			while (line != null) {	
				header = false;
				if (line.equals("")) {
					header = true;
					if (lines != "") {
						sequences.add(lines);
					}
					lines = "";
				}
				else {
					if (line.charAt(0) == '>') {
						header = true;
						lines = "";
						headers.add(line);
					}
				}
				if (header != true) {
					lines += line;
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();


			ArrayList<String> patents= new ArrayList<String>(); /*Sequences of patented genomes*/
			ArrayList<String> cancers= new ArrayList<String>(); /*Sequences of cancer genome */
			ArrayList<String> normal = new ArrayList<String>(); /*Sequences of normal genomes*/
			
			/*Moving sequences into patents,cancers or normal array lists BASED ON THEIR HEADER*/
			for (int x = 0; x < headers.size() ; x++) {		
				line = headers.get(x);
				String seq = sequences.get(x);
			
				if (line.contains("patent") || line.contains("Patent")) {
					patents.add(seq);
				}
				else if (line.contains("onco")) {
					cancers.add(seq);
				}
				else {
					normal.add(seq);
				}
			}

			/*Converting each FASTA DNA sequence into mRNA but ONLY including [start codon ; stop codon] 
			 * Uses startToStop function!
			 */
			for (int z = 0; z < patents.size(); z++) {
				String seq = patents.get(z);
				seq = startToStop(seq);
				patents.set(z,seq);
				
			}
			for (int z = 0; z < normal.size(); z++) {
				String seq = normal.get(z);
				seq = startToStop(seq);
				normal.set(z,seq);
		
			}
			for (int z = 0; z < cancers.size(); z++) {
				String seq = cancers.get(z);
				seq = startToStop(seq);
				cancers.set(z,seq);
			
			}
			
			/*Next few for loops find 'mutation indices' where the oncogenic sequence is different THAN EVERY 'normal human' sequence */
			ArrayList<ArrayList<Integer>> potentialIndices = new ArrayList<ArrayList<Integer>>();
			for (int n = 0; n < normal.size(); n++) {
				String x = cancers.get(0);
				String y = normal.get(n);
				potentialIndices.add(compareOncoToNormal(x,y)); 
			}
			/* Works with infinite normal sequences*/
			int[] array = new int[10000];
			int counter2 = 0;
			for (int n = 0; n < potentialIndices.size(); n++) {
				ArrayList<Integer> l = potentialIndices.get(n);
				for (int z = 0; z < l.size(); z++) {
					array[counter2] = l.get(z);
				    counter2++;
				}
			}
			
			/*numbers hashmap is how often the mutation happens across the patented sequences..
			 * If an oncogenic mutation is also nowhere in normal or patented sequences.. it is very likely to be unique to cancer and bad.
			 */
			HashMap<Integer,Integer> numbers = new HashMap<Integer,Integer>();
			for (int r = 0; r < array.length; r++) {
				if (numbers.containsKey(array[r])) {
					numbers.put(array[r], numbers.get(array[r]) + 1);
				}
				else {
					numbers.put(array[r], 1);
				}
			}
			ArrayList<Integer> mutations = new ArrayList<Integer>();
			for (int x : numbers.keySet()) {
				if (numbers.get(x) >= normal.size()) {
					mutations.add(x);
				}
			}
			
			
			
			
			
			HashMap<Integer,Integer> scores = new HashMap<Integer, Integer>();
			for (int x = 0; x < mutations.size(); x++) {
				scores.put(mutations.get(x), 0);
			}
			
			/*Getting Scores
			 * Each i-th codon that is a mutation will be assigned a score based on how cancerous it is. 
			 */
			
			for (int c = 0; c < patents.size(); c++) {
				String seq = patents.get(c);
				String onco = cancers.get(0);
				String sub;
				for (int d = 0; d < mutations.size(); d++) {
					int i = mutations.get(d);
					try {
					sub = seq.substring(i*3,i*3+3);
					}
					catch (Exception e) {
						sub = onco.substring(i*3, i*3+3);
					}
					String subOnco = onco.substring(i*3, i*3+3);
					if (sub.equals(subOnco)) {
						scores.put(mutations.get(d), scores.get(mutations.get(d)) + 1);
					}
					
				}
				
			}
			
			
			/*Score Hierarchy*/
			
			int[] topFive = {0,0,0,0,0};
			int[] indexOfTop = new int[5];
			for (int x : scores.keySet()) {
				int value = scores.get(x);
				int minTopFive = getMinValue(topFive);
				for (int y = 0; y < 5; y++) {
					if (topFive[y] == minTopFive && minTopFive < value) {
						topFive[y] = value;
						indexOfTop[y] = x;
						break;
					}
				}
			}
		
			
			double[] powerOfScores = new double[5];
			for (int x = 0; x < 5; x++) {
				powerOfScores[x] = Math.round(topFive[x]/9.0*100.0*100.0)/100.0;
			}
			
			
			


		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file " + fileName + "");
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");                  
		}
	}
	public static String startToStop(String x) {
		String newString = "";
		boolean foundStart = false;
		for (int i = 0; i < x.length()-3; i++) {
			String codon = x.substring(i,i+3);
			if (codon.equals("CAT")) {
				newString += codon;
				foundStart = true;
				i = i + 2;
			}
			else if (foundStart == true) {
				if (codon.equals("TTA") || codon.equals("TCA") || codon.equals("CTA")) {
					newString += codon;
					break;
				}
				else
				{ 
					newString += codon;
				}
				i = i + 2;
			}
		}
		return newString;
	}
	public static ArrayList<Integer> compareOncoToNormal(String a , String b) {
		/*a is cancer, b is normal*/
		ArrayList<Integer> indices = new ArrayList<Integer>();
		int index = 0;
		
		if (a.length() > b.length()) {
			if( !a.equals("") && !b.equals("")) {
				int count;
				String codonA ; 
				String codonB ; 
				int i = 0;
				while (i < b.length()) {
					codonA = 	a.substring(i,i+3);
					codonB =    b.substring(i,i+3);
					if (isSame(codonA,codonB) == false ) { 
						indices.add(index);
					}
					index++;
					i = i + 3;
				}
				while (i < a.length()) {
					indices.add(index);
					index++;
					i = i + 3;
				}
			} 
		}
		else {
			if( !a.equals("") && !b.equals("")) {
				int count;
				String codonA ; 
				String codonB ; 
				int i = 0;
				while (i < a.length()) {
					codonA = 	a.substring(i,i+3);
					codonB =    b.substring(i,i+3);
					if (isSame(codonA,codonB) == false ) { 
						indices.add(index);
					}
					index++;
					i = i + 3;
				}
				indices.add(index);
			} 
		}
		return indices;
	}

	public static boolean isSame (String a, String b) {
		if ((a.equals("UUU") || a.equals("UUC")) && (b.equals("UUU") || b.equals("UUC"))) {
			return true;
		}
		else if ((a.equals("UUA") || a.equals("UUG")) && (b.equals("UUA") || b.equals("UUG"))) {
			return true;
		}
		else if ((a.equals("UAU") || a.equals("UAC")) && (b.equals("UAU") || b.equals("UAC"))) {
			return true;
		}
		else if ((a.equals("CAU") || a.equals("CAC")) && (b.equals("CAU") || b.equals("CAC"))) {
			return true;
		}
		else if ((a.equals("CAA") || a.equals("CAG")) && (b.equals("CAA") || b.equals("CAG"))) {
			return true;
		}
		else if ((a.equals("AAU") || a.equals("AAC")) && (b.equals("AAU") || b.equals("AAC"))) {
			return true;
		}
		else if ((a.equals("AAA") || a.equals("AAG")) && (b.equals("AAA") || b.equals("AAG"))) {
			return true;
		}
		else if ((a.equals("GAU") || a.equals("GAC")) && (b.equals("GAU") || b.equals("GAC"))) {
			return true;
		}
		else if ((a.equals("GAA") || a.equals("GAG")) && (b.equals("GAA") || b.equals("GAG"))) {
			return true;
		}
		else if ((a.equals("UGU") || a.equals("UGC")) && (b.equals("UGU") || b.equals("UGC"))) {
			return true;
		}
		else if ((a.equals("AGU") || a.equals("AGC")) && (b.equals("AGU") || b.equals("AGC"))) {
			return true;
		}
		else if ((a.equals("AGA") || a.equals("AGG")) && (b.equals("AGA") || b.equals("AGG"))) {
			return true;
		}
		else if ((a.equals("CUU") || a.equals("CUC") || a.equals("CUA") || a.equals("CUG")) && (b.equals("CUU") || b.equals("CUC") || b.equals("CUA") || b.equals("CUG"))) {
			return true;
		}
		else if ((a.equals("GUU") || a.equals("GUC") || a.equals("GUA") || a.equals("GUG")) && (b.equals("GUU") || b.equals("GUC") || b.equals("GUA") || b.equals("GUG"))) {
			return true;
		}
		else if ((a.equals("UCU") || a.equals("UCC") || a.equals("UCA") || a.equals("UCG")) && (b.equals("UCU") || b.equals("UCC") || b.equals("UCA") || b.equals("UCG"))) {
			return true;
		}
		else if ((a.equals("CCU") || a.equals("CCC") || a.equals("CCA") || a.equals("CCG")) && (b.equals("CCU") || b.equals("CCC") || b.equals("CCA") || b.equals("CCG"))) {
			return true;
		}
		else if ((a.equals("ACU") || a.equals("ACC") || a.equals("ACA") || a.equals("ACG")) && (b.equals("ACU") || b.equals("ACC") || b.equals("ACA") || b.equals("ACG"))) {
			return true;
		}
		else if ((a.equals("GCU") || a.equals("GCC") || a.equals("GCA") || a.equals("GCG")) && (b.equals("GCU") || b.equals("GCC") || b.equals("GCA") || b.equals("GCG"))) {
			return true;
		}
		else if ((a.equals("CGU") || a.equals("CGC") || a.equals("CGA") || a.equals("CGG")) && (b.equals("CGU") || b.equals("CGC") || b.equals("CGA") || b.equals("CGG"))) {
			return true;
		}
		else if ((a.equals("GGU") || a.equals("GGC") || a.equals("GGA") || a.equals("GGG")) && (b.equals("GGU") || b.equals("GGC") || b.equals("GGA") || b.equals("GGG"))) {
			return true;
		}
		else {
			return false;
		}
	}
	public static int getMinValue(int[] array) {
	    int minValue = array[0];
	    for (int i = 1; i < array.length; i++) {
	        if (array[i] < minValue) {
	            minValue = array[i];
	        }
	    }
	    return minValue;
	}
	public static int getMaxValue(int[] array) {
	    int maxValue = array[0];
	    for (int i = 1; i < array.length; i++) {
	        if (array[i] > maxValue) {
	            maxValue = array[i];
	        }
	    }
	    return maxValue;
	}
}