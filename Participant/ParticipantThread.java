import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantThread extends Thread
{

	Socket socket,bsocket = null;
	ServerSocket bserSocket;
	DataInputStream input,binput;
	DataOutputStream output,boutput;
	String command = "";
	boolean shouldrun = true;
	String port;
	String messageLogFileName, inputString = "", threadType;
	boolean threadBDisconnected = false;

	ParticipantThread(int portB, String mycommand)
	{

		try
		{
			this.bserSocket = new ServerSocket(portB);
			this.threadType = "B";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	ParticipantThread(Socket socket)
	{
		try
		{
			this.socket = socket;
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			this.threadType="A";
		}
		catch (IOException ex)
		{
			Logger.getLogger(ParticipantThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	//Method to send Data to Coordinator
	public void sendDataToCoordinator(String mycommand)
	{
		try
		{
			//use appropriate ports
			this.command = mycommand;
			output.writeUTF(command);
			output.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	//Run method for multithreading
	public void run()
	{
		try
		{
			while (shouldrun) {
				if(input!=null)
				{
					while ((input.available() == 0))
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
				}
				
				//Logic for Implementing the Functionality of Thread B
				if(this.threadType.equals("B"))
				{
					if(!this.threadBDisconnected )
					{
						if(this.bsocket==null)
						{
							this.bsocket = this.bserSocket.accept();
							this.binput = new DataInputStream(this.bsocket.getInputStream());
							this.boutput = new DataOutputStream(this.bsocket.getOutputStream());
						}
						else
						{
							if(this.binput.available()!=0)
							{
								String recv = this.binput.readUTF();
								if(recv.equals("deregister"))
								{
									this.binput.close();
									this.boutput.close();
									this.bsocket.close();
									this.bserSocket.close();
									this.threadBDisconnected=true;
									this.currentThread().interrupt();
								}
								else
								{
									receivemessage(this.binput, this.boutput, recv);
								}
							}

						}
					}
				}
				
				//Taking Input/Ack messages from Coordinator
				if(command!="")
				{
					//String inputString = "";
					if (shouldrun)
					{
						inputString = input.readUTF();
					}
					System.out.println(inputString);
					this.command="";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Method to save Multicast messages to MessageLogFile
	public void receivemessage(DataInputStream binput, DataOutputStream boutput, String recv)
	{
		try
		{
			File mymessagefile = new File(messageLogFileName);
			
			if(mymessagefile.exists())
			{
				BufferedWriter fileoutput = new BufferedWriter(new FileWriter(mymessagefile.getAbsoluteFile(), true));
				fileoutput.write("\n"+recv);
				fileoutput.close();
			}
			else
			{
				BufferedWriter fileoutput = new BufferedWriter(new FileWriter(messageLogFileName));
				fileoutput.write(recv);
				fileoutput.close();
			}

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
