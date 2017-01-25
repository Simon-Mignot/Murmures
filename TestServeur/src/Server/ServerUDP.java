/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import testserveur.DataHandler;

/**
 *
 * @author Simon
 */
public class ServerUDP extends Thread
{
	final private int DATA_SIZE = 1024;
	private DatagramSocket listener;
	
	ServerUDP(int port)
	{
		try
		{
			listener = new DatagramSocket(port);
		}
		catch(SocketException ex)
		{
			Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			byte[] data = new byte[DATA_SIZE];
			DatagramPacket packet = new DatagramPacket(data, DATA_SIZE);
			try
			{
				System.out.println("listen UDP");
				listener.receive(packet);
				System.out.println("received UDP");
			}
			catch(IOException ex)
			{
				Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println("UDP - " + new String(packet.getAddress().getHostAddress()) + ": " + new String(packet.getData()));
			DataHandler.networkMessage(DataHandler.ANNOUCEMENT_MSG, data, packet.getAddress().getHostAddress());
		}
		
	}
}
