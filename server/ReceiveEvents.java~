import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvents extends Thread
{
	//receive server command and execute them on client side
	
	Socket socket = null;
	Robot robot = null;
	boolean continueLoop = true;
	
	public ReceiveEvents(Socket socket, Robot robot)
	{
		this.socket = socket;
		this.robot = robot;
		start();	
	}
	
	public void run()
	{
		Scanner scanner = null;
		
		try
		{
			//get input from user on server side
			scanner = new Scanner(socket.getInputStream());
			
			int command = scanner.nextInt();
			switch(command)
			{
				case -1:
					robot.mousePress(scanner.nextInt());
					break;
				case -2:
					robot.mouseRelease(scanner.nextInt());
					break;
				case -3:
					robot.keyPress(scanner.nextInt());
					break;
				case -4:
					robot.keyRelease(scanner.nextInt());
					break;
				case -5:
					robot.mouseMove(scanner.nextInt(), scanner.nextInt());
					break;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();		
		}
	}
}
