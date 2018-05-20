import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.net.*;

class StringDateTime
{
	String message;
	int timeStamp;
}
public class GroupParticipants
{
	public int id;
	public int port;
	public String ip;
	public Boolean isDisconnected;
	public Socket socket;
	public DataInputStream inputStream;
	public DataOutputStream outputStream;
	public List<StringDateTime> savedMessage;
	public Date disconnectTime;
	
	//Information of all Participants
	public GroupParticipants(int id, String ip, int port, Socket socket,DataInputStream input, DataOutputStream output)
	{
		this.id=id;
		this.port=port;
		this.ip = ip;
		this.isDisconnected=false;
		this.socket = socket;
		this.inputStream = input;
		this.outputStream = output;
		this.savedMessage = new ArrayList<StringDateTime>();
	}
	
	//Method to save multicast messages and time
	public void AddStringDateTime(GroupParticipants g, String message, int l)
	{
		StringDateTime s = new StringDateTime();
		s.message=message;
		s.timeStamp=l;
		g.savedMessage.add(s);
	}
}

