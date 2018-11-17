import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class SequenceUI extends JFrame implements ActionListener {

    JFileChooser fc;
    JButton b, b1;
    JTextField tf;
    FileInputStream in;
    Socket s;
    DataOutputStream dout;
    DataInputStream din;
    int i;
    private final JPanel panel = new JPanel();
    private final JLabel lblSequencer = new JLabel("SEQUENCER");
    private final JPanel panel_1 = new JPanel();
    private final JTable table = new JTable();

    SequenceUI() {
        super("client");
        tf = new JTextField();
        tf.setBounds(26, 311, 190, 30);
        getContentPane().add(tf);

        b = new JButton("Browse");
        b.setBounds(231, 301, 152, 50);
        getContentPane().add(b);
        b.addActionListener(this);
        b1 = new JButton("Upload");
        b1.setBounds(231, 367, 152, 50);
        getContentPane().add(b1);
        b1.addActionListener(this);
        fc = new JFileChooser();
        getContentPane().setLayout(null);
        panel.setBounds(325, 16, 165, 50);
        
        getContentPane().add(panel);
        
        panel.add(lblSequencer);
        panel_1.setBounds(451, 145, 362, 258);
        
        getContentPane().add(panel_1);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        panel_1.add(table);
        setSize(850, 486);
        setVisible(true);
        try {
            s = new Socket("localhost", 10);
            dout = new DataOutputStream(s.getOutputStream());
            din = new DataInputStream(s.getInputStream());
            send();
        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b) {
                int x = fc.showOpenDialog(null);
                if (x == JFileChooser.APPROVE_OPTION) {
                    copy();
                }
            }
            if (e.getSource() == b1) {
                send();
            }
        } catch (Exception ex) {
        }
    }

    public void copy() throws IOException {
        File f1 = fc.getSelectedFile();
        tf.setText(f1.getAbsolutePath());
        in = new FileInputStream(f1.getAbsolutePath());
        while ((i = in.read()) != -1) {
            System.out.print(i);
        }
    }

    public void send() throws IOException {
        dout.write(i);
        dout.flush();

    }

    public static void main(String... d) {
        new SequenceUI();
    }
}