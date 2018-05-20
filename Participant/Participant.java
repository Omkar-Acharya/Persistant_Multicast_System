import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class Participant
{
	//Method to take input from User
	public static String takeInput() throws Exception
	{
		System.out.print("Participant> ");
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(reader);
		return buffer.readLine();
	}

	@SuppressWarnings("SleepWhileInLoop")
	public static void main(String[] args) throws Exception
	{
		InetAddress localhost = InetAddress.getLocalHost();
		Socket nclientSocket=null;
		int participantId = 0, port = 0;
		String messageLogFile = null, coordinatorip=null, participantIp = localhost.getHostAddress();

		//Taking input from File as Command Line parameters
		try {
			Scanner inputFile = new Scanner(new File(args[0]));
			String input = inputFile.nextLine();

			Scanner myscanner = new Scanner(input);

			if(myscanner.hasNext())
			{
				participantId = Integer.parseInt(myscanner.next());
			}

			input = inputFile.nextLine();
			myscanner = new Scanner(input);

			if(myscanner.hasNext()){
				messageLogFile = myscanner.next().toString();

			}

			input = inputFile.nextLine();
			myscanner = new Scanner(input);

			if(myscanner.hasNext()){
				coordinatorip = myscanner.next().toString();
			}

			if(myscanner.hasNext()){
				port = Integer.parseInt(myscanner.next());
			}

		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
		}

		//configure normal port
		nclientSocket = new Socket(coordinatorip, port);
		ParticipantThread participantthread = new ParticipantThread(nclientSocket);
		participantthread.start();

		//Main Participant Loop
		while (true)
		{
			Thread.sleep(1000);
			String command = takeInput();

			//If command is Register or Reconnect- Start Thread B
			if(command.split(" ")[0].equalsIgnoreCase("register") || command.split(" ")[0].equalsIgnoreCase("reconnect"))
			{
				int portB =Integer.parseInt(command.split(" ")[1]);
				//ServerSocket serSocketB = new ServerSocket(portB);
				ParticipantThread myparticipant = new ParticipantThread(portB, command+" "+participantId+" "+participantIp);
				myparticipant.messageLogFileName = messageLogFile;
				myparticipant.start();
				//myparticipant.sendDataToCoordinator(command+" "+participantId+" "+participantIp);
			}

			participantthread.sendDataToCoordinator(command+" "+participantId+" "+participantIp);
		}
	}

}
