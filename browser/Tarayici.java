import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

public class Tarayici extends JFrame implements ActionListener,HyperlinkListener
{
	private JEditorPane webs;
	private JScrollPane kay;
	private JMenuBar menubar;
	private JMenu menu,hakkimda;
	private JMenuItem menukapa;
	private Button kapat;
	private Button yazdir;
	private Button dugme;
	private TextField kutu;
	private URL url;

	public Tarayici()
	{

		super("Tarayici");
		setSize(950,700);

		Container c=getContentPane();

		dugme=new Button("Git");
		dugme.addActionListener(this);

		menubar=new JMenuBar();
		menu=new JMenu("Dosya");
		hakkimda=new JMenu("Hakkýmda");
		hakkimda.addActionListener(this);
		menukapa=new JMenuItem("Çýkýþ");
		menukapa.addActionListener(this);

		menu.add(menukapa);
		menubar.add(menu);
		menubar.add(hakkimda);

		kapat=new Button("Kapat");
		kapat.addActionListener(this);

		yazdir=new Button("Geri");
		yazdir.addActionListener(this);


		kutu=new TextField("http://www.");
		kutu.addActionListener(this);

		webs=new JEditorPane();
		webs.setEditable(false);
		webs.addHyperlinkListener(this);

		setJMenuBar(menubar);
		c.setLayout(null);

		kay=new JScrollPane(webs);

		kay.setBounds(10,50,940,650);
		kutu.setBounds(10,20,740,25);
		dugme.setBounds(751,20,50,25);
		kapat.setBounds(803,20,50,25);
		yazdir.setBounds(855,20,50,25);

		c.add(kapat);
		c.add(kay);
		c.add(dugme);
		c.add(yazdir);
		c.add(kutu);
		show();
		}

	public void actionPerformed(ActionEvent e)
	{
		setTitle("Kachak Web Tarayici - Site Açýlýyor...");
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Object kaynak=e.getSource();
		String satir=kutu.getText();

		if (kaynak==dugme || kaynak==kutu)
		{
			try {
				webs.setPage(satir);
				setTitle("Kachak Web Tarayici - Açýldý");

			}
			catch (IOException ei)
			{
				try {
				webs.setPage("http://www.cmaeal.com/hata/hata.html");
				kutu.setText("hata: sayfaYok");

				}
				catch (IOException se) {
					System.out.print("Hata oldu..");
				}
			}
		}
		else if (kaynak==kapat)
		{
			System.exit(0);
		}

		else if (kaynak==hakkimda)
		{
		JOptionPane.showMessageDialog( this,"Sürüm 1.0","Yazan: CanÖKÇELIK",JOptionPane.INFORMATION_MESSAGE );

		}

		else if (kaynak==menukapa)
		{
			System.exit(0);
		}

		else if (kaynak==yazdir)
		{
			webs.setPage((String)yazdir.getEditor().getItem());
		}
	}

	public void hyperlinkUpdate( HyperlinkEvent ea )
	{
		if ( ea.getEventType() ==
		HyperlinkEvent.EventType.ACTIVATED )
	{
	     try {
			webs.setPage( ea.getURL().toString() );
			kutu.setText(ea.getURL().toString());
		}
		catch (IOException ei)
		{
				try {
				webs.setPage("http://www.cmaeal.com/hata/hata.html");
				kutu.setText("hata: sayfaYok");
				}
				catch (IOException se) {
					System.out.print("Hata oldu..");
				}

			}
		}
	}


	public static void main(String[] args)
	{
		Tarayici t=new Tarayici();
		t.setVisible(true);
	}


}


