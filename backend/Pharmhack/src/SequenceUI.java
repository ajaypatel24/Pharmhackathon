
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Panel;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Button;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Insets;


public class SequenceUI {
	private static final JPanel panel = new JPanel();
	private static final JTable table = new JTable();
	private static final JLabel lblGenomeSequenceDetection = new JLabel("Genome Sequence Detection");
	private static final JPanel panel_1 = new JPanel();
	private static final JFrame frame = new JFrame();
	private static final JButton btnNewButton = new JButton("New button");
	private static final Button button = new Button("New button");
	private static final Button button_1 = new Button("New button");
	private static final JLabel lblSequence = new JLabel("sequence 1");
	private static final JLabel lblSequence_1 = new JLabel("sequence 2");
	private static final Panel panel_2 = new Panel();
	private static final JLabel label = new JLabel("");
	private static final JLabel lblSequencer = new JLabel("SEQUENCER");
	private static final JPanel panel_3 = new JPanel();
	private static final JTable table_1 = new JTable();

   public static void main(String[] args) {
   	
   	frame.getContentPane().add(panel_1);
   	GridBagLayout gbl_panel_1 = new GridBagLayout();
   	gbl_panel_1.columnWidths = new int[]{103, 115, 109, 227, 77, 101, 45, 91, 0};
   	gbl_panel_1.rowHeights = new int[]{31, 62, 70, 20, 182, 29, 134, 27, 0};
   	gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
   	gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
   	panel_1.setLayout(gbl_panel_1);
   	btnNewButton.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent arg0) {
   		}
   	});
   	
   	GridBagConstraints gbc_panel_2 = new GridBagConstraints();
   	gbc_panel_2.fill = GridBagConstraints.BOTH;
   	gbc_panel_2.insets = new Insets(0, 0, 5, 5);
   	gbc_panel_2.gridx = 3;
   	gbc_panel_2.gridy = 1;
   	panel_1.add(panel_2, gbc_panel_2);
   	panel_2.setLayout(new GridLayout(1, 0, 0, 0));
   	
   	panel_2.add(lblSequencer);
   	
   	panel_2.add(label);
   	
   	GridBagConstraints gbc_lblSequence = new GridBagConstraints();
   	gbc_lblSequence.anchor = GridBagConstraints.NORTH;
   	gbc_lblSequence.fill = GridBagConstraints.HORIZONTAL;
   	gbc_lblSequence.insets = new Insets(0, 0, 5, 5);
   	gbc_lblSequence.gridx = 1;
   	gbc_lblSequence.gridy = 3;
   	panel_1.add(lblSequence, gbc_lblSequence);
   	
   	GridBagConstraints gbc_lblSequence_1 = new GridBagConstraints();
   	gbc_lblSequence_1.anchor = GridBagConstraints.NORTH;
   	gbc_lblSequence_1.fill = GridBagConstraints.HORIZONTAL;
   	gbc_lblSequence_1.insets = new Insets(0, 0, 5, 5);
   	gbc_lblSequence_1.gridx = 5;
   	gbc_lblSequence_1.gridy = 3;
   	panel_1.add(lblSequence_1, gbc_lblSequence_1);
   	
   	GridBagConstraints gbc_panel_3 = new GridBagConstraints();
   	gbc_panel_3.insets = new Insets(0, 0, 5, 5);
   	gbc_panel_3.fill = GridBagConstraints.BOTH;
   	gbc_panel_3.gridx = 3;
   	gbc_panel_3.gridy = 4;
   	panel_1.add(panel_3, gbc_panel_3);
   	
   	panel_3.add(table_1);
   	btnNewButton.setText("upload");
   	
   	GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
   	gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
   	gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
   	gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
   	gbc_btnNewButton.gridx = 1;
   	gbc_btnNewButton.gridy = 5;
   	panel_1.add(btnNewButton, gbc_btnNewButton);
   	
   	GridBagConstraints gbc_button = new GridBagConstraints();
   	gbc_button.anchor = GridBagConstraints.NORTHWEST;
   	gbc_button.insets = new Insets(0, 0, 5, 5);
   	gbc_button.gridx = 5;
   	gbc_button.gridy = 5;
   	panel_1.add(button, gbc_button);
   	
   	GridBagConstraints gbc_button_1 = new GridBagConstraints();
   	gbc_button_1.insets = new Insets(0, 0, 0, 5);
   	gbc_button_1.anchor = GridBagConstraints.NORTHWEST;
   	gbc_button_1.gridx = 5;
   	gbc_button_1.gridy = 7;
   	panel_1.add(button_1, gbc_button_1);
   	frame.setSize(920, 650);
   	frame.setVisible(true);
      
     
      
      
    }
}