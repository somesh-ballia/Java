import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class mouseClick extends Applet implements MouseListener
{
 public void init()
	{
		setBackground(Color.RED);
		addMouseListener(this);
	}

 public void mouseClicked(MouseEvent e)
	{
		setBackground(Color.BLUE);
	}  
 
 public void mouseExited(MouseEvent e)
	{
	}
 public void mousePressed(MouseEvent e)
	{
	}
 public void mouseReleased(MouseEvent e)
	{
	}
 public void mouseEntered(MouseEvent e)
	{
	}

}