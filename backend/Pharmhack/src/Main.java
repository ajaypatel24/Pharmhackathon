import java.util.HashMap;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.*;
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");

		String fileName = "ERBB2.fasta";
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);
			boolean header = true;
			String allLines = "";
			String line = bufferedReader.readLine();
			ArrayList<String> sequences= new ArrayList<String>();
			ArrayList<String> headers = new ArrayList<String>();
			int counter = 0;
			String lines = "";
			while (line != null) {	
				/*System.out.println(line);*/
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


			ArrayList<String> patents= new ArrayList<String>();
			ArrayList<String> cancers= new ArrayList<String>();
			ArrayList<String> normal = new ArrayList<String>();

			for (int x = 0; x < headers.size() ; x++) {		
				line = headers.get(x);
				String seq = sequences.get(x);
				System.out.println(line);
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

			/*Only start-stop codons now*/
			for (int z = 0; z < patents.size(); z++) {
				String seq = patents.get(z);
				seq = startToStop(seq);
				patents.set(z,seq);
				System.out.println(seq);
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
				System.out.println(seq);
				
			}
			/*System.out.println(compareStrings(cancers, normal));*/

			/*Normal vs. Onco*/

			ArrayList<String> mutations = new ArrayList<String>();

			/* Start codons will be AUG, STP are UAA, UAG and UGA */




			/*
			normalSequence = normal.get(0);
			for (int x = 0; x < cancers.size();x++) {
				cancerSequence = cancers.get(x);
				for (int y = 0; y < cancerSequence.length; y++) {
					codon = 
				}
			} */








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
	
	
	public static void g (String human, String cancer) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static int compareStrings( ArrayList<String> a , ArrayList<String> b ) {
		int index = 0;
		while( (a != null) && (b != null)) {
			double maxLength;
			if( a.size() >= b.size() ){
				maxLength = b.size();
			} else {
				maxLength = a.size();
			} 

			int count;

			for(int i = 0; i< maxLength; i++ ) {



				if ((a.get(i)) == (b.get(i)) ) {
					index = i;
					break;
				}

			} 


		}
		return index;
	}
}