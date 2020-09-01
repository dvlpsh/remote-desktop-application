import java.io.ObjectInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ByteArrayInputStream;


class ReceivingScreen extends Thread
{
	//we must recieve screen of our server or second computer
	//so we must provide a stream for receiving
	//ObjectInputStream is provided
	//ObjectInputStream can be used to convertInputStream to Object
	/*
	ObjectInputStream reads object from file
	so we need to convert Inputstream which we will get from our server side to an object and display it in our screen	
	*/
	
	
	private ObjectInputStream cObjectInputStream=null;
	private JPanel cPanel = null;//jpanel to view the server screen
	private boolean continueLoop = true;
	InputStream oin = null;
	Image image1 = null;//take a snapshot of the image which we shall pass from server side to client side
	
	
	public ReceivingScreen(InputStream in, JPanel p)
	{
		oin = in;
		cPanel = p;
		start(); // since we use run we must provide run method	
	}
	
	
	public void run()
	{
		/*
		here we continously recieve screen from the server side so we read the screenshot of the server, so we have to continously read the screen of server
		*/
		try
		{
			while(true)
			{
				//we read the screenshots in the form of byte array
				byte bytes[] = new byte[1024*1024];
				int count = 0;
				
				do
				{
					count+=oin.read(bytes, count, bytes.length-count);
					/*
					count is the offset at which the data is written in the byte array
					length-count is the no of bytes of data read by our inputStream, if inpit string is EOF then value returned is -1
					*/
				
				}while(!(count>4 && bytes[count-2]==(byte)-1 && bytes[count-1]==(byte)-39)); //EOF condition
				
				image1 = ImageIO.read(new ByteArrayInputStream(bytes));
				image1 = image1.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_FAST);
				
				Graphics graphics = cPanel.getGraphics();
				graphics.drawImage(image1, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
				
			}
		
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
	}
	
	
	
	
	
	
}
