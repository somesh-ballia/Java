import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Radio extends Applet implements ItemListener
{

Checkbox forest,flower,air;
CheckboxGroup group;
int count =0;
 public void init()
	{
		setBackground(Color.RED);
		
		group = new CheckboxGroup();
		forest= new Checkbox("FOREST",group,true);
		flower= new Checkbox("FLOWER",group,false);
		air = new Checkbox("AIR",group,false);
		
		add(forest);
		add(flower);
		add(air);
		
		forest.addItemListener(this);
		flower.addItemListener(this);
		air.addItemListener(this);
		
	}

 public void itemStateChanged(ItemEvent e)
	{
		count++;
		repaint();
	}  

  public void paint(Graphics g)
	{
		g.drawString("state change count = "+count,20,30);			
	}

}