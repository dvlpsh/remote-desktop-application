import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.util.zip.*;
/* zip is used for compressing and decompressing the data used from the server side, and reading and writing of zip files*/


//class to access the screen of the server computer, or the other computer
class CreateFrame extends Thread //thread is connected to server
{
	String width="", height=""; //width and height of the frame
	private JFrame frame = new JFrame();
	
	/* 
		main container that contains all connected client screen
	*/
	private JDesktopPane desktop = new JDesktopPane();
	
	private Socket cSocket = null;
	private JInternalFrame interFrame = new JInternalFrame("Server Screen", true, true, true);
	/* internal frame to access smaller frames inside the server screen
	1st parameter is title of window, the next few parameters are of minimise, maximise and close button handling
	*/
	private JPanel cPanel = new JPanel();
	
	
	
	public CreateFrame(Socket cSocket, String width, String height)
	{
		this.width = width;
		this.height =  height;
		this.cSocket = cSocket;
		
		start();
	} 
	
	
	//GUI Screen for each connected client
	public void drawGUI()
	{
		frame.add(desktop, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH); //to view the server screen in maximised state
		frame.setVisible(true);
		interFrame.setLayout(new BorderLayout());//should be inside mainframe so set the layout
		interFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
		/*show server screen in the center */
		interFrame.setSize(100,100);
		desktop.add(interFrame);
		
		
		//show internal frame as maximised version
		try
		{
			interFrame.setMaximum(true);
		}
		catch(PropertyVetoException e)//might cause errors if we try to make changes in the internal windows
		{
			e.printStackTrace();
		}
		
		//handling keylistener event
		cPanel.setFocusable(true);
		interFrame.setVisible(true);	
	}
	
	
	
	public void run()
	{
		//read server screen
		InputStream in = null;
		drawGUI();
		
		try
		{
			in = cSocket.getInputStream();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		new ReceivingScreen(in, cPanel); //class for receiving screen
		new SendEvents(cSocket, cPanel, width, height);
		/*if client does any activity it has to be recieved by server, or 2nd computer
		
		activity happening in client screen is going to be monitored in server screen
		*/
	}
	
	

}
