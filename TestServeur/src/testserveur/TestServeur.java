/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Simon
 */
public class TestServeur
{
	static private List<Client> clients;
	
	public static void addClient(Client client)
	{
		if(!clients.contains(client))
			clients.add(client);
		for(int i = 0; i < clients.size(); ++i)
			System.out.println("Client " + i + " : " + clients.get(i).name);
	}
	
	public static String getClientsString()
	{
		String data = "";
		for(int i = 0; i < clients.size(); ++i)
			data += (clients.get(i).toString() + '\n');
		return data;
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws SocketException, IOException
	{
		clients = new ArrayList<Client>();
		DatagramSocket serverSocket = new DatagramSocket(65500);
		while(true)
		{
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String pseudo = new String(receivePacket.getData());
			String ip = receivePacket.getAddress().getHostAddress();
			System.out.println("RECEIVED from " + ip + " : " + pseudo);
			
			
			
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			addClient(new Client(pseudo, ip));
			
			String d = getClientsString();
			sendData = d.getBytes();
			System.out.println(d);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
}
