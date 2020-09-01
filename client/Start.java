import java.net.Socket;
import javax.swing.JOptionPane;


public class Start
{

	/*give port number and the ip address for that. Can fix any port no*/
	static String port="4907"; //port no, static because we want to keep it fixed
	
	public void initialize(String ip, int port)
	{
		//using try catch because this is server socket programming
		
		try
		{
			Socket sc = new Socket(ip, port);
			System.out.println("Connecting to server");
			
			//provide password authetication
			/*
			because if someone else knows my ip and there's no authentication he can easily access my desktop
			*/
			
			Authentication frame1 = new Authentication(sc);//user defined class
			frame1.setSize(300,80); //width and height
			frame1.setLocation(500,300);
			
			frame1.setVisible(true);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();		
		}
		
	
	}
	
	
	public static void main(String args[])
	{
		//get server ip, or the remote desktop we wish to use
		String ip=JOptionPane.showInputDialog("Enter Server IP:"); //take input ip
		new Start().initialize(ip, Integer.parseInt(port)); //initialise is a non-stattic method
	
	}
}
