import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JTable;
import javax.swing.JScrollPane;


public class SequenceUI {
	private static final JTextField fileName = new JTextField();
	private static final JButton upload = new JButton("Upload");
	private static final JButton ChooseFile = new JButton("Choose File");
	
	private static final JButton b = new JButton();
	
	
	private static final JLabel pic = new JLabel();
	private static final ImageIcon image = new ImageIcon("logoGenome.png");
	private static final JPanel picpanel = new JPanel();
	
	
	
	//static JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
   
    static JTextField tf;
    static FileInputStream in;
    Socket s;
    DataOutputStream dout;
    DataInputStream din;
    static int i;
    private static final JScrollPane scrollPane = new JScrollPane();
    private static final JTable table = new JTable();
    
   
	

   public static void main(String[] args) {
	   
	   Object[] columns = {"ith codon", "mutation", "Likelihood"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		System.out.println(model.isCellEditable(100,100));
		
		
		
   	fileName.setText("Upload a File");
   	fileName.setBounds(45, 315, 146, 26);
   	fileName.setColumns(10);
   	
  
   	
   
      JFrame f = new JFrame("Genome Parser");
      f.setSize(1000, 520);
      f.setLocation(300,200);
      f.getContentPane().setLayout(null);
      
      Image g = getScaledImage(image.getImage(),195,185);
      ImageIcon image = new ImageIcon(g);
      b.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		fileName.setText(" ");
      		JOptionPane.showMessageDialog(f, "GenomeAI\n" +
      				"Health Is Wealth\n" +

      				"GenomeAI uses FASTA databases to compare a oncogenic sequence to patented and normal sequences to identify the regions of the oncogenic sequence that are most likely to cause cancer.\n" + 

      				"These algorithms can be used for any number of genes in an effort to direct research in a user-friendly and quick fashion.\n" +

      				"Simply upload a FASTA file with an oncogenic sequence, normal sequences and patented sequences and click 'Run' \n" +

      				"Of course, neural networks, chaos theory, lossless compression, machine learning and intelligent AI are implemented to run our algorithms.\n " + 

      				"How does this get accomplished?" +
      				"- Every sequence is determined to be either an oncogenic sequence, patented sequence, or normal sequence based on keywords in the header \n" +
      				"- Every sequence is shortened to just to codons of interest (ie between start and stop codons)\n" +
      				"- Every codon of oncogenic sequence is compared to each codon of normal sequences. If there is a mutation in oncogenic sequence that never appears in the normal sequences.. \n" +
      				"it is considered a potential mutation of interest and it's index (ie i-th codon of oncogonic sequence) is saved. \n" +
      				"- Each potential mutation (codon) of interest from oncogenic sequence is compared to that codon of the patented sequence. \n" +
      				"- If a mutation in oncogenic sequence never occurs in patented sequence.. it is assigned a higher danger score than if an oncogenic mutation appears in multiple places \n" +
      				"- Top 5 codon mutations are shown to user in a beatiful GUI\n");
      	
      		
      	}
      });
      b.setIcon(image);
      
      
      
      f.getContentPane().add(fileName);
      upload.setFont(new Font("Verdana", Font.PLAIN, 16));
      upload.setBounds(230, 377, 146, 48);
      
      
      
      
      
      
      f.getContentPane().add(upload);
      ChooseFile.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    		int returnValue = jfc.showOpenDialog(null);
    		// int returnValue = jfc.showSaveDialog(null);

    		if (returnValue == JFileChooser.APPROVE_OPTION) {
    			File selectedFile = jfc.getSelectedFile();
    			fileName.setText(selectedFile.getAbsolutePath());
    			try {
					FileReader fileReader = new FileReader(selectedFile.getAbsolutePath());
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String line = bufferedReader.readLine();
					
					
					ArrayList<String> sequences= new ArrayList<String>(); /*For sequence strings*/
					
					ArrayList<String> headers = new ArrayList<String>();
					boolean header = true;
					int counter = 0;
					String lines = "";
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
					ArrayList<String> normal = new ArrayList<String>();
					
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
					
					for (int c = 0; c < patents.size(); c++) {
						String seq = patents.get(c);
						String onco = cancers.get(0);
						String sub;
						for (int d = 0; d < mutations.size(); d++) {
							int i = mutations.get(d);
							try {
							sub = seq.substring(i*3,i*3+3);
							}
							catch (Exception e2) {
								sub = onco.substring(i*3, i*3+3);
							}
							String subOnco = onco.substring(i*3, i*3+3);
							if (sub.equals(subOnco)) {
								scores.put(mutations.get(d), scores.get(mutations.get(d)) + 1);
							}
							
						}
						
					}
					int[] topFive = {0,0,0,0,0};
					int[] indexOfTop = new int[5];
					for (int x : scores.keySet()) {
						int value = scores.get(x);
						int minTopFive = getMinValue(topFive);
						for (int y = 0; y < 5; y++) {
							if (topFive[y] == minTopFive && minTopFive < value) {
								topFive[y] = value;
								indexOfTop[y] = x; //2nd column
								break;
							}
						}
					}
					double[] powerOfScores = new double[5];
					for (int x = 0; x < 5; x++) {
						powerOfScores[x] = Math.round(topFive[x]/9.0*100.0*100.0)/100.0;
					}
					table.setFont(new Font("Serif", Font.BOLD, 40));
					table.setModel(new DefaultTableModel(
			    	      	new Object[][] {
			    	      		{1, indexOfTop[0], powerOfScores[0]},
			    	      		{2, indexOfTop[4], powerOfScores[4]},
			    	      		{3, indexOfTop[2], powerOfScores[2]},
			    	      		{4, indexOfTop[3], powerOfScores[3]},
			    	      		{5, indexOfTop[1], powerOfScores[1]},
			    	      	},
			    	      	new String[] {
			    	      		"RANK", "ON xth CODON", "SCORE"
			    	      	}
			    	      ));
    			
    			
					
					
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    			
    			
    		}

      	}
      });
      ChooseFile.setFont(new Font("Verdana", Font.PLAIN, 16));
      ChooseFile.setBounds(230, 304, 146, 48);
      
      

      
      f.getContentPane().add(ChooseFile);
      
      b.setBounds(15, 15, 195, 185);
      f.getContentPane().add(b);
      scrollPane.setBounds(452, 36, 378, 387);
      
      f.getContentPane().add(scrollPane);
      table.setModel(new DefaultTableModel(
    	      	new Object[][] {
    	      		{null, null, null},
    	      		{null, null, null},
    	      		{null, null, null},
    	      		{null, null, null},
    	      		{null, null, null},
    	      	},
    	      	new String[] {
    	      		"RANK", "ith CODON", "SCORE"
    	      	}
    	      ));
      
      
      
      table.getColumnModel().getColumn(0).setPreferredWidth(111);
      table.getColumnModel().getColumn(1).setPreferredWidth(125);
      table.getColumnModel().getColumn(2).setPreferredWidth(118);
      table.setRowHeight(70);
      
      scrollPane.setViewportView(table);
      
      
     
      f.setVisible(true);
      f.setResizable(false);
      
    }
   
   /*helper method used to resize image*/
   private static Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
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

