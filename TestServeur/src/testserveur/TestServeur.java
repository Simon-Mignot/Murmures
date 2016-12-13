/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import static com.oracle.jrockit.jfr.ContentType.Bytes;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

/**
 *
 * @author Simon
 */
public class TestServeur
{
	static private HashMap<String, Client> clients;
	static private final int LOGIN          = 0x01; // 0000 0001
	static private final int KEEP_ALIVE     = 0x02; // 0000 0010
	static private final int USER_LIST      = 0x04; // 0000 0100
	static private final int GLOBAL_MESSAGE = 0x08; // 0000 1000
	
	public static void addClient(Client client)
	{
		if(!clients.containsKey(client))
			clients.put(client.name, client);
		Iterator it = clients.entrySet().iterator();
		while(it.hasNext())
		{
			Client c = (Client)(((Entry)(it.next())).getValue());
			System.out.println("Client " + c.ip + " : " + c.name);
		}
	}
	
	public static byte[] getClientsList()
	{
		ArrayList<Byte> data = new ArrayList<Byte>();
		Iterator it = clients.entrySet().iterator();
		while(it.hasNext())
		{
			Client c = (Client)(((Entry)(it.next())).getValue());
			byte[] bytesArray = c.toBytesArray();
			for(byte b : bytesArray)
				data.add(b);
			data.add(new String("\n").getBytes()[0]);
		}
		byte[] out = new byte[data.size()];
		for(int i = 0; i < data.size(); ++i)
			out[i] = data.get(i);
		return out;
	}
	
	public static String extractPseudo(byte[] data)
	{
		String pseudo = new String(data).substring(1);
		return pseudo;
	}
	
	public static void resetKeepalive(String pseudo)
	{
		try
		{
			clients.get(pseudo).resetKeepalive();
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	public static void handleDatagram(DatagramPacket packet)
	{
		byte[] data = packet.getData();
		System.out.println((int)data[0]);
		switch(data[0])
		{
			case LOGIN:
				System.out.println("LOGIN");
				addClient(new Client(extractPseudo(data), packet.getAddress()));
				break;
			case KEEP_ALIVE:
				System.out.println("KEEP_ALIVE");
				resetKeepalive(extractPseudo(data));
				break;
			case USER_LIST:
				
				break;
			case GLOBAL_MESSAGE:
				
				break;
		};
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws SocketException, IOException
	{
		clients = new HashMap<String, Client>();
		DatagramSocket serverSocket = new DatagramSocket(65500);
		while(true)
		{
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			handleDatagram(receivePacket);
			String pseudo = new String(receivePacket.getData());
			String ip = receivePacket.getAddress().getHostAddress();
			System.out.println("RECEIVED from " + ip + " : " + pseudo);
			
			
			
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			
			sendData = getClientsList();
			System.out.println(new String(sendData));
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
}
