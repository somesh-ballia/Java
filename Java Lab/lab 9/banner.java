import java.awt.*;
import java.applet.*;

public class banner extends Applet implements Runnable
{

 Thread t = null;
 boolean flag;
 
 public String msg= "KRISHNA ENGINEERING COLLEGE "; 

 public void init()
	{
		setBackground(Color.RED);
		setForeground(Color.BLUE);
	}

 public void start()
  	{
		
		t= new Thread(this);
		flag = true;
		t.start();
	}

 public void run()
	{
		char ch;

		try
		{
			while(true)
			{
				repaint();
				Thread.sleep(500);
				ch = msg.charAt(0);
				msg=msg.substring(1,msg.length());
				msg=msg+ch;
				//if(flag)
					//break;

			}
		}
		catch(Exception e)
		{
			
		}

	}

  public void stop()
	{
		if(flag)
			t=null;
	}
  public void paint(Graphics g)
	{
		g.drawString(msg,20,30);
			
	}

}