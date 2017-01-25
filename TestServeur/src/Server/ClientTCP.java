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
	
	private PrintStream out;
	private InputStream in;
	
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
		initStreams();
		sayHello();
		
		listenSocket();
	
		close();
		System.out.println("Stop thread " + this.getId());
	}
	
	private void initStreams()
	{
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
	}
	
	private void sayHello()
	{
		out.print((char)DataHandler.HELLO_MSG + DataHandler.name);
		out.flush();
	}
	
	private void listenSocket()
	{
		while(!socket.isClosed())
		{
			String command = readStream(in);
			if(command.length() == 0)
				break;
			//System.out.println(command);
			DataHandler.networkMessage((int)(command.charAt(0)), command, parentHost);
		}
	}
	private String readStream(InputStream in)
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
	
	private void close()
	{
		try
		{
			socket.close();
		}
		catch(IOException ex)
		{
			Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
		}
		DataHandler.networkEvent(DataHandler.HOST_DISCONNECT_EVENT, parentHost);
	}
}
