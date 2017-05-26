import java.io.*;
import java.util.*;


 class Command {

  static String line;
  public static void issueCommand(String COMMAND) {
        try {
      
      Process p = Runtime.getRuntime().exec(COMMAND + "/fo csv /nh");
      BufferedReader input = new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
          if (!line.trim().equals("")) {
        	System.out.println(line);
          }

      }
      input.close();
    }
    catch (Exception err) {
      err.printStackTrace();
    }    
  }

  public static void main(String[] args){
      
      if(args[0].length()>0)
	{
		issueCommand(args[0]);
	}
	else
	{
		System.out.println("No Command Found");
	}
  }

}