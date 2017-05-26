import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class TextButton extends Applet implements ActionListener
{

TextField DATA;

 public void init()
	{
		setBackground(Color.RED);
		
		DATA = new TextField(20);
		Label l1 = new Label("Name", Label.RIGHT);
		Button clickMe = new Button("ClickMe");
		
		add(clickMe);
		add(l1);
		add(DATA);
		
		clickMe.addActionListener(this);
		
	}

 public void actionPerformed(ActionEvent e)
	{
		repaint();
	}  

  public void paint(Graphics g)
	{
		g.drawString(DATA.getText(),20,30);			
	}

}