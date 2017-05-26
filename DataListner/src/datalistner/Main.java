/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package datalistner;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

    try
    {
        Socket s = new Socket("172.16.1.3",8989);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.flush();
        String line;
        while((line = in.readLine()) != null)
        {
            System.out.println(line);
        }

    }
    catch(Exception e)
     {
          e.printStackTrace();
     }
}

}
