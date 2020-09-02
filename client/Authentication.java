import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


/*extends jframe as we provide a ui for input, as it is important for some security to be maintained on the server side*/
class Authentication extends JFrame implements ActionListener
{
	private Socket cSocket = null;
	DataOutputStream passchk = null; 
	DataInputStream verification = null;
	String verify="";
	
	/*dataoutputstream allows primitive datatype to be written to the outputstream in a machine indeoendent environment
	Java uses dataoutputstream to wite data which can later be read by datainput stream
	*/
	
	JButton submit;
	JPanel panel;
	JLabel label,label1;
	String width="", height="";
	final JTextField text1;


	//constructor
	Authentication(Socket cSocket)
	{
		label1 = new JLabel();
		label1.setText("Password");
		text1 = new JTextField(15);
		this.cSocket=cSocket; //client socket
		
		label = new JLabel();
		label.setText("");
		this.setLayout(new BorderLayout());
		
		submit = new JButton("Submit"); 
		
		panel = new JPanel(new GridLayout(2,1));//width : height = 2:1
		
		panel.add(label1);
		panel.add(text1);
		panel.add(label);
		panel.add(submit);
		
		add(panel, BorderLayout.CENTER);
		submit.addActionListener(this);
		setTitle("Login");
	
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		String value1 = text1.getText(); //get password
		
		try
		{
			passchk = new DataOutputStream(cSocket.getOutputStream()); 
			verification = new DataInputStream(cSocket.getInputStream()); 
			
			passchk.writeUTF(value1);//builtin method using it here for encryption of value1
			verify = verification.readUTF();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if(verify.equals("valid"))
		{
			try
			{
				width = verification.readUTF();
				height = verification.readUTF(); 
			}
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			CreateFrame abc = new CreateFrame(cSocket, width, height);
			dispose();//helps clear resource
		
		}
		
		else
		{
			System.out.println("Please enter valid password");
			JOptionPane.showMessageDialog(this, "password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
		
		}
	
	
	}


}







