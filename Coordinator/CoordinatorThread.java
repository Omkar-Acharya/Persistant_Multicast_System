import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorThread extends Thread {
	Socket socket;
	DataInputStream input;
	DataOutputStream output;
	String inputString = null;
	CoordinatorProcess mycommand = new CoordinatorProcess();
	int td=0;

	CoordinatorThread(ServerSocket sersocket, int td)
	{
		//Initialize the socket, DataInputStream, DataOutputStream for object
		try
		{
			this.socket = sersocket.accept();
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
			this.td=td;
		}
		catch (IOException ex)
		{
			Logger.getLogger(CoordinatorThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//Method to split the input command
	public String[] splitCommand(String command)
	{
		return command.split(" ");
	}

	public void run()
	{
		try
		{
			// read the command
			while(true)
			{
				//Thread sleep if no input
				while (input.available() == 0)
				{
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
				inputString = input.readUTF();
				
				//Method call to Register function
				if (splitCommand(inputString)[0].equalsIgnoreCase("register"))
				{
					if(mycommand.register(Integer.parseInt(splitCommand(inputString)[2]), splitCommand(inputString)[3], Integer.parseInt(splitCommand(inputString)[1])))
					{
						output.writeUTF("register is received at Coordinator");
					}
				}
				
				//Method call to Deregister function
				if (splitCommand(inputString)[0].equalsIgnoreCase("deregister"))
				{
					boolean result = mycommand.deregister(Integer.parseInt(splitCommand(inputString)[1]));
					output.writeUTF("Deregistration is complete");
				}
				
				//Method call to Msend function
				if (splitCommand(inputString)[0].equalsIgnoreCase("msend"))
				{
					output.writeUTF("multicast message received at Coordinator");
					String msendMessage = splitCommand(inputString)[1];
					mycommand.msend(msendMessage);
				}
				
				//Method call to Disconnect function
				if(splitCommand(inputString)[0].equalsIgnoreCase("disconnect"))
				{
					output.writeUTF("Disconnect message received at Coordinator");
					mycommand.disconnect(Integer.parseInt(splitCommand(inputString)[1]));
				}
				
				//Method call to Reconnect function
				if(splitCommand(inputString)[0].equalsIgnoreCase("reconnect"))
				{
					output.writeUTF("Reconnect message received at Coordinator");
					mycommand.reconnect(Integer.parseInt(splitCommand(inputString)[2]), splitCommand(inputString)[3], Integer.parseInt(splitCommand(inputString)[1]),td);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("exception:  "+e.getMessage());
		}
	}

}
