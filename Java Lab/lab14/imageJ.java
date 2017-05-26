import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class imageJ extends Applet 
{
 Image i;
 
 public void init()
	{
		setBackground(Color.RED);		
		i= getImage(getCodeBase(),"abc.jpg");		
		
	}

  public void paint(Graphics g)
	{
		g.drawImage(i,10,10,500,500,this);			
	}


}