import java.awt.AWTException; 
import java.awt.Dimension; 
import java.awt.Rectangle; 
import java.awt.Robot; 
import java.awt.Toolkit; 
import java.awt.image.RenderedImage; 
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO;
import java.io.*;
import java.net.*;
import java.util.*;

public class ImageServer {

    public static void main(String[] args) {
	
	long FileLength;	
	// working with file
	try
	{
		getImage();
		
                File file = new File("default.png");
		//File file = new File(args[0]);
		FileInputStream inputData = new FileInputStream(file);
		FileLength = file.length(); 

		byte data [] = new byte[(int)FileLength];

		inputData.read(data);
	
	
    // working with network	
    
        ServerSocket s = new ServerSocket(8989);
        Socket incoming = s.accept();

        try
        {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();

            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter (outStream,true);           
	    	    
               for(int i=0;i<FileLength;i++)
		{
			out.println(data[i]);			
		}

       }
       catch(InterruptedException e)
        {
           e.printStackTrace();
        }
        finally
        {
            incoming.close();
        }
    }
    catch(IOException e)
     {
          e.printStackTrace();
     }

    }
public static void getImage()
   {
	  
	 String saveFileName;
         saveFileName = "default.png"; 
         String saveFileFormat = "png";  
  	
		try
		{
         	 // get current sceen size for snapshot. 
        	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        	 Rectangle screenRect = new Rectangle(screenSize); 
  
        	 // create current screen snapshot 
        	

		 RenderedImage image = (new Robot()).createScreenCapture(screenRect); 
 		
        	ImageIO.write(image, saveFileFormat, new File(saveFileName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		 
	
     } 

}
