/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mailtest;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
/**
 *
 * @author Wavejumper
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new MailTestFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

}

// for GUI

class MailTestFrame extends JFrame
{
    public MailTestFrame()
    {
        SetSize(DEFAULT_WIDTH,DEFAULT_HIGHT);
        setTitle("MailTest");
    }

}