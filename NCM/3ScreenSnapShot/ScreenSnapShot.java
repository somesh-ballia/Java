import java.awt.AWTException; 
import java.awt.Dimension; 
import java.awt.Rectangle; 
import java.awt.Robot; 
import java.awt.Toolkit; 
import java.awt.image.RenderedImage; 
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO; 
  
public class ScreenSnapShot { 
  
     public static void main(String[] args) throws IOException,AWTException { 

	try
	{
		Thread.sleep(2000);
	}
	catch(InterruptedException e)
	{

	}
  
	 String saveFileName;
         saveFileName = "default.png"; 
         String saveFileFormat = "png"; 	  
  	
         if (args.length > 0) 
             saveFileName = args[0]; 
  
         if (saveFileName.toLowerCase().endsWith(".png"))
	 { 
             saveFileFormat = "png"; 
         } 
	else if (saveFileName.toLowerCase().endsWith(".jpg") ||saveFileName.toLowerCase().endsWith(".jpeg")) 
	 { 
             saveFileFormat = "jpg"; 
         } 
	else
	 { 
             System.err.println("Only png and jpg formats are supported."); 
             System.exit(1); 
         } 
	
         	 // get current sceen size for snapshot. 
        	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        	 Rectangle screenRect = new Rectangle(screenSize); 
  
        	 // create current screen snapshot 
        	

		 RenderedImage image = (new Robot()).createScreenCapture(screenRect); 
 		
        	 if (ImageIO.write(image, saveFileFormat, new File(saveFileName)))
		 { 
         	   System.out.println("Screen snap shot is saved as :" + saveFileName); 
        	 } 
		 else 
		 { 
         	    System.out.println("Unable to save snap shot"); 
        	 } 
	
     } 
} 