import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit; //used to bind various components
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

public class InitConnection
{
	ServerSocket socket = null;
	DataInputStream password = null;
	DataOutputStream verify =  null;
	String width = "";
	String height = "";
	
	InitConnection(int port, String value1)
	{
		//robot class is from java.awt package
		//used to generate native system input event
		//for test automation and self running demos
		
		Robot robot = null;
		Rectangle rectangle = null;
		
		try
		{
			System.out.println("waiting for client to get connected");
			socket = new ServerSocket(port);
			
			//consists of no of graphic devices object and font object
			//which might be in local or remote machine
			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			
			//this class describes graphic devices
			//that might be available in a graphic enviroment
			//this includes screen, printer etc
			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
			
			//Dimension contans height and width of component in integer as well as double precision
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			String width = ""+dim.getWidth();
			String height = ""+dim.getHeight();
			rectangle = new Rectangle(dim);
			robot = new Robot(gDev);
			
			drawGUI();
			
			
			while(true)
			{
				Socket sc = socket.accept();
				password = new DataInputStream(sc.getInputStream());
				verify = new DataOutputStream(sc.getOutputStream());
				
				String psword = password.readUTF();//encrypted password
				if(psword.equals(value1))
				{
					verify.writeUTF("valid");
					verify.writeUTF(width);
					verify.writeUTF(height);
					new SendScreen(sc,robot,rectangle);//allowing screenshare
					new ReceiveEvents(sc, robot);//receive events from the server end
				}
				
				else
				{
					verify.writeUTF("Invalid");
				}
				
			}	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void drawGUI()
	{
	
	}


}
