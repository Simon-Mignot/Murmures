/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import Server.Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class TestServeur
{
	static private HashMap<String, Host> clients;
	static private final int NETWORK_PORT = 65500;
	static private final int LOGIN           = 0x01; // 0000 0001
	static private final int KEEP_ALIVE      = 0x02; // 0000 0010
	static private final int USER_LIST       = 0x04; // 0000 0100
	static private final int GLOBAL_MESSAGE  = 0x08; // 0000 1000
	static private final int PRIVATE_MESSAGE = 0x16; // 0001 0000
	static private final int GROUP_MESSAGE   = 0x32; // 0010 0000
	
	public static void addClient(Host client)
	{
		if(!clients.containsKey(client))
			clients.put(client.name, client);
		Iterator it = clients.entrySet().iterator();
		while(it.hasNext())
		{
			Host c = (Host)(((Entry)(it.next())).getValue());
			System.out.println("Client " + c.tcp + " : " + c.name);
		}
	}
	
	public static byte[] getClientsList()
	{
		ArrayList<Byte> data = new ArrayList<Byte>();
		data.add((byte)USER_LIST);
		Iterator it = clients.entrySet().iterator();
		while(it.hasNext())
		{
			Host c = (Host)(((Entry)(it.next())).getValue());
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
	
	private static void sendMessage(String message)
	{
		byte[] data = new byte[message.length() + 1];
		System.arraycopy(message, 0, data, 1, message.length());
		data[0] = GLOBAL_MESSAGE;
		send(message.getBytes(), new Host());
	}
	
	private static void sendMessage(String message, Host client)
	{
		byte[] data = new byte[message.length() + 1];
		System.arraycopy(message, 0, data, 1, message.length());
		data[0] = PRIVATE_MESSAGE;
		send(message.getBytes(), client);
	}
	
	private static void send(byte[] data, Host client)
	{
		InetAddress IPAddress = client.tcp.getInetAddress();
		int port = NETWORK_PORT;
		DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
		
		System.out.println(new String(data));
		try
		{
			DatagramSocket s = new DatagramSocket();
			s.send(sendPacket);
		}
		catch(SocketException ex)
		{
			Logger.getLogger(TestServeur.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch(IOException ex)
		{
			Logger.getLogger(TestServeur.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void handleDatagram(DatagramPacket packet)
	{
		/*byte[] data = packet.getData();
		System.out.println((int)data[0]);
		switch(data[0])
		{
			case LOGIN:
				System.out.println("LOGIN");
				Client c = new Client(extractPseudo(data), packet.getAddress());
				addClient(c);
				send(getClientsList(), c);
				break;
			case KEEP_ALIVE:
				System.out.println("KEEP_ALIVE");
				resetKeepalive(extractPseudo(data));
				break;
			case USER_LIST:
				
				break;
			case GLOBAL_MESSAGE:
				String msg = new String(data);
				msg = msg.substring(1);
				DataHandler.networkEvent(DataHandler.NEW_GLOBAL_MESSAGE, msg);
				break;
		}*/
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws SocketException, IOException
	{
		Server server = new Server();
	}
}


/*

clients = new HashMap<String, Client>();
		DatagramSocket serverSocket = new DatagramSocket(NETWORK_PORT);
		while(true)
		{
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			
			// receive
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			
			handleDatagram(receivePacket);
			
			System.out.println("RECEIVED from " + receivePacket.getAddress().getHostAddress() + " : " + new String(receivePacket.getData()));
			
			// send
			InetAddress IPAddress = receivePacket.getAddress();
			NetworkInterface ni = NetworkInterface.getByInetAddress(IPAddress);
			
			int port = receivePacket.getPort();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			
			sendData = getClientsList();
			System.out.println(new String(sendData));
			serverSocket.send(sendPacket);

*/
