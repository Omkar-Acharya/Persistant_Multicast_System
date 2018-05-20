import java.net.ServerSocket;
import java.util.*;
import java.io.*;

public class Coordinator {

	public static void main(String[] args)
	{
		int nport = 0, td = 0;

		//Taking File from command-line parameters
		try
		{
			Scanner inputFile = new Scanner(new File(args[0]));
			String input = inputFile.nextLine();

			Scanner myscanner = new Scanner(input);

			if(myscanner.hasNext())
			{
				nport = Integer.parseInt(myscanner.next());
			}

			input = inputFile.nextLine();
			myscanner = new Scanner(input);

			if(myscanner.hasNext()){
				td = Integer.parseInt(myscanner.next());
			}

		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found!");
		}

		// create object of ServerProcess that executes the command
		System.out.println("Coordinator Started");

		try
		{
			// Create nport, sockets
			ServerSocket nserSocket = new ServerSocket(nport);
			while (true)
			{
				//Creating thread object for Socket
				CoordinatorThread coordinatorthread = new CoordinatorThread(nserSocket,td);
				//starting the thread
				coordinatorthread.start();
			}
		}
		catch (Exception ex) {
			System.out.println("exceptionnn" + ex + " exception " + ex.getMessage());
		}
	}

}
