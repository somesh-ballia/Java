import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.Vector;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
/*
 * Created on Jul 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author marimuthup  - pmarimuthu@gmail.com
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */



public class ComConsole extends JFrame implements ActionListener {

    Container g = null;
    JMenuBar mb = null;
    JMenu file, settings, help = null;
    JMenuItem save, filter, exit = null;

    JTabbedPane tab = null;

    JTextArea debug = null;

    JEditorPane myDebug = null;

    JLabel status = null;
    HTMLDocument doc = null;
    StyleSheet styleSheet = null;

    JButton clearDebug, saveMyDebug = null;
    JPanel debugPanel, myDebugPanel = null;

    JComboBox combo = null;
    Vector filterVector = new Vector();
    String filters[] = {""};
    String filterText = "MMP";

    FileOutputStream fout = null;
    FileChannel        fc = null;

    public ComConsole() throws Exception {
        super("Com Console");
        g = this.getContentPane();
        g.setLayout(new BorderLayout(5,5));
        mb = new JMenuBar();
        file = new JMenu("File");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        file.add(save);
        file.addSeparator();
        file.add(exit);
        mb.add(file);

        settings = new JMenu("Settings");
        filter = new JMenuItem("Filter");
        filter.addActionListener(this);
        settings.add(filter);
        mb.add(settings);

        help = new JMenu("Help");
        mb.add(help);

        this.setJMenuBar(mb);

        tab = new JTabbedPane();

        debugPanel = new JPanel(new BorderLayout(3,3));
        debug = new JTextArea("");
        debugPanel.add("Center", new JScrollPane(debug));
        tab.addTab("Debug", debugPanel);
        clearDebug = (JButton) debugPanel.add("South", newJButton("Clear"));
        System.out.println("middle");
        //doc = new HTMLDocument();
        myDebugPanel = new JPanel(new BorderLayout(3,3));
        myDebug = new JEditorPane("text/html", "<bodybgcolor=#ccffcc><h3>My Debug</h3><hr><Br><B>        </html>");
        doc = (HTMLDocument) myDebug.getDocument();
        myDebugPanel.add("Center", new JScrollPane(myDebug));
        saveMyDebug = (JButton) myDebugPanel.add("South", newJButton("Save"));

        tab.addTab("My Output", myDebugPanel);
        g.add(tab, "Center");
        status = new JLabel("Welcome");
        g.add(status, "South");

        clearDebug.addActionListener(this);
        saveMyDebug.addActionListener(this);

        System.out.println("GUI Constructed.");
    }
    public void readAndWrite() throws Exception
    {
        System.out.println("Read And Write ...");
        Enumeration en = CommPortIdentifier.getPortIdentifiers();
        while(en.hasMoreElements())
        {
            CommPortIdentifier com = (CommPortIdentifier)
en.nextElement();
            System.out.println("com - nextElement.");
            if(com.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                System.out.println("SERIAL_PORT Found ...");
                SerialPort sPort = (SerialPort) com.open("COM1",115200);
                System.out.println("Serial Port Opened.");
                JOptionPane.showMessageDialog(this, "COM1 OpenedSuccessfully!");
                sPort.setSerialPortParams(115200, 8, 1,SerialPort.PARITY_NONE);
                sPort.notifyOnDataAvailable(true);
                //sPort.addEventListener(this);

                SimpleAttributeSet attb = new SimpleAttributeSet();
                StyleConstants.setForeground(attb, Color.BLUE);
                BufferedReader br = new BufferedReader(newInputStreamReader(sPort.getInputStream()));

                fout = new FileOutputStream("C:/Documents andSettings/marimuthup/Desktop/out.html");
                fc = fout.getChannel();

                while(true) {
                    String str = br.readLine().trim();
                    if(str.equals("")) { continue; }
                    if(str.indexOf(filterText) != -1) {
                        //myDebug.setText("<br><fontcolor=red>"+myDebug.getText()+str+"</font>");
                        String line = " "+str;
                        doc.insertString(doc.getLength(), line, attb);
                       //myDebug.setCaretPosition(myDebug.getText().length());
                        fc.write(ByteBuffer.wrap(line.getBytes()));
                    }
                    if(!str.startsWith("Test1") &&!str.startsWith("Test2") && !str.startsWith("Test3")) {
                        debug.append(""+str);
                    }
                   // debug.setCaretPosition(debug.getText().length());
                }

       	    }
            System.out.println("Done.");
  	    }
     }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == filter) {
            doFilter();
        }
        else if(e.getSource() == clearDebug) {
            debug.setText("");
        }
        else if(e.getSource() == saveMyDebug) {
            String text = myDebug.getText();
            try {
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void doFilter() {
        String ftext = (String) JOptionPane.showInputDialog(this,"EnterFilter-By text : ", "Input", JOptionPane.QUESTION_MESSAGE,null, filters, filters[0]);
        if(!filterVector.contains(ftext)) {
            filterVector.add(ftext);
            filters = (String[])  filterVector.toArray();
        }
        filterText = (ftext == null) ? filterText : ftext;
    }

    public static void main(String[] args) {
        ComConsole z = null;
        try {
            	z = new ComConsole();
            	System.out.println("ComConsole ...");
            	z.setLocation(100, 50);
            	z.setSize(630, 630);
            	z.setVisible(true);
                z.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                z.readAndWrite();
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "COM1Opened Successfully!", JOptionPane.ERROR_MESSAGE);
            if(z != null) {
                z.status.setText("Ex: "+ex.getMessage());
            }
            try {
                	if(z.fc != null)        z.fc.close();
                	if(z.fout != null)		z.fout.close();
                	System.out.println("Saved.");
            }
            catch(Exception ex2) {
            }

        }
    }
}
