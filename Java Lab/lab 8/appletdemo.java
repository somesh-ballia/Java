import java.awt.*;
import java.applet.*;

public class appletdemo extends Applet
{

  public void init()
	{
		setBackground(Color.RED);
		setForeground(Color.BLUE);
	}
  public void paint(Graphics g)
	{
		g.drawString("HELLO",20,30);
			
	}

}