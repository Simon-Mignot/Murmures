/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.projet.jicdsmdq.murmures.Server;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import epsi.projet.jicdsmdq.murmures.Classes.DataHandler;
import epsi.projet.jicdsmdq.murmures.Classes.Host;
import epsi.projet.jicdsmdq.murmures.Classes.Message;

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
	
	public ClientTCP(String ip) throws IOException
	{
		socket = new Socket(ip, Server.TCP_PORT);
		socket.setSoTimeout(0);
		if (socket == null)
			System.out.println("socket null");
	}

	public ClientTCP(Socket socket)
	{
		this.socket = socket;
	}
	
	public void setHost(Host host)
	{
		parentHost = host;
	}
	public String getIP()
	{
		Log.d("getIP()", socket.toString());
		Log.d("getIP()", socket.getInetAddress().toString());
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
			Log.e("initStreams()", "IOException");
		}
		catch(NullPointerException ex)
		{
			Log.e("initStreams()", "NullPointerException");
		}
	}

	public void sendMessage(Message msg)
	{
		out.print((char)DataHandler.GLOBAL_MESSAGE_MSG + msg.toString());
		out.flush();
	}
	
	private void sayHello()
	{
		System.out.println("Say Hello!");
		out.print((char)DataHandler.HELLO_MSG + DataHandler.localhost.name);
		out.flush();
	}
	
	private void listenSocket()
	{
		while(!socket.isClosed())
		{
			String trame = readStream(in);
			if(trame.length() == 0)
				break;
			String data = trame.substring(1);
			DataHandler.networkMessage((int)(trame.charAt(0)), data, parentHost);
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
			System.out.println("readStram() : IOException");
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
