import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class combo extends JApplet
{

 String names [] = {"NAME","DOB","AGE"};

 public void init()
	{
		setBackground(Color.RED);
		
		JComboBox box;
		setLayout(new FlowLayout());
		box = new JComboBox(names);
		add(box);
		
	}

}