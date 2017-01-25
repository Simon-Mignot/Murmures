/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import testserveur.DataHandler;
import testserveur.Host;

/**
 *
 * @author Simon
 */
public class ClientTCP extends Thread
{
	private Socket socket;
	private Host parentHost;
	public ClientTCP(String ip, int port)
	{
		try
		{
			socket = new Socket(ip, port);
		}
		catch(IOException ex)
		{
			Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void setHost(Host host)
	{
		parentHost = host;
	}
	
	String readStream(InputStream in)
	{
		int character = 0;
		String result = "";
		try
		{
			while(true) // while break condition in if
			{
				character = in.read();
				if(character == '\n' || character == '\r' || character == -1)
					break;
				result += (char)character;
			}
		}
		catch(IOException ex)
		{
			Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
	public String getIP()
	{
		return socket.getInetAddress().getHostAddress();
	}
	
	public InetAddress getInetAddress()
	{
		return socket.getInetAddress();
	}
	
	@Override
	public void run()
	{
		System.out.println("Start thread " + this.getId());
		PrintStream out;
		InputStream in;
		try
		{
			out = new PrintStream(socket.getOutputStream());
			in = socket.getInputStream();
		}
		catch(IOException ex)
		{
			Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
			return;
		}
		
		out.print('\02' + "septimus");
		out.flush();
		while(!socket.isClosed())
		{
			String command = readStream(in);
			System.out.println(command);
			if(command.length() == 0)
				break;
		}
		
		try
		{
			socket.close();
		}
		catch(IOException ex)
		{
			Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
		}
		DataHandler.networkEvent(DataHandler.HOST_DISCONNECT_EVENT, parentHost);
		System.out.println("Stop thread " + this.getId());
	}
}
