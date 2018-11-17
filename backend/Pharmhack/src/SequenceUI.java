import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;


public class SequenceUI {
	private static final JTextField txtUploadAFile = new JTextField();
	private static final JButton upload = new JButton("Upload");
	private static final JButton ChooseFile = new JButton("Choose File");
	private static final JPanel panel = new JPanel();
	private static final JLabel lblSequencer = new JLabel("SEQUENCER");
	
	private static final JButton b = new JButton();
	
	
	private static final JLabel pic = new JLabel();
	private static final ImageIcon image = new ImageIcon("logoGenome.png");
	private static final JPanel picpanel = new JPanel();
	
	static JFileChooser fc;
   
    JTextField tf;
    FileInputStream in;
    Socket s;
    DataOutputStream dout;
    DataInputStream din;
    int i;
	

   public static void main(String[] args) {
   	txtUploadAFile.setText("Upload a File");
   	txtUploadAFile.setBounds(45, 315, 146, 26);
   	txtUploadAFile.setColumns(10);
   	
   
      JFrame f = new JFrame("A JFrame");
      f.setSize(1000, 520);
      f.setLocation(300,200);
      f.getContentPane().setLayout(null);
      
      Image g = getScaledImage(image.getImage(),195,185);
      ImageIcon image = new ImageIcon(g);
      b.setIcon(image);
      
      
      
      f.getContentPane().add(txtUploadAFile);
      upload.setFont(new Font("Verdana", Font.PLAIN, 16));
      upload.setBounds(230, 377, 146, 48);
      
      upload.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent arg0) {
    		  
    	  }
      });
      
      
      
      f.getContentPane().add(upload);
      ChooseFile.setFont(new Font("Verdana", Font.PLAIN, 16));
      ChooseFile.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    	        try {
    	            if (e.getSource() == ChooseFile) {
    	                int x = fc.showOpenDialog(null);
    	                if (x == JFileChooser.APPROVE_OPTION) {
    	                    copy();
    	                }
    	            }
    	            
    	        } catch (Exception ex) {
    	        }
    	    }
      });
      
      ChooseFile.setBounds(230, 304, 146, 48);
      
      f.getContentPane().add(ChooseFile);
      panel.setBounds(355, 15, 298, 66);
      
      f.getContentPane().add(panel);
      panel.add(lblSequencer);
      lblSequencer.setFont(new Font("Verdana", Font.PLAIN, 45));
      
      b.setBounds(15, 15, 195, 185);
      f.getContentPane().add(b);
      
     
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
   
   public static void copy() throws IOException {
       File f1 = fc.getSelectedFile();
       tf.setText(f1.getAbsolutePath());
       in = new FileInputStream(f1.getAbsolutePath());
       while ((i = in.read()) != -1) {
           System.out.print(i);
       }
   }
}
