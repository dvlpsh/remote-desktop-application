import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;


public class SendScreen extends Thread //send events in terms of thread object
{
	Socket socket = null;
	Robot robot = null;
	Rectangle rectangle = null;
	boolean continueLoop = true;
	OutputStream oos = null;
	
	public SendScreen(Socket socket, Robot robot, Rectangle rectangle)
	{
		this.socket = socket;
		this.robot = robot;
		this.rectangle = rectangle;
		start();	
	}
	
	public void run()
	{
		try
		{
			oos = socket.getOutputStream();		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
		while(continueLoop)
		{
			//subclass of image class
			//used to handle and manipulate the image data sent by server to client
			BufferedImage image = robot.createScreenCapture(rectangle);
			
			try
			{
				ImageIO.write(image, "jpeg", oos); //write image as jpeg
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				/*
				When 1 thread is in waiting or sleep state then 
				another thread will interrupt this thread
				which is in waiting or sleeping state
				so interrupted exception should be caught
				*/
				e.printStackTrace();
			}
			
			
		
		}
	
	
	}

}
