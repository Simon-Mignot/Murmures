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

	private boolean noHello;
	
	public ClientTCP(String ip) throws IOException
	{
		socket = new Socket(ip, Server.TCP_PORT);
		socket.setSoTimeout(0);
		noHello = false;
		if (socket == null)
			System.out.println("socket null");
	}

	public ClientTCP(Socket socket) throws SocketException
	{
		this.socket = socket;
		this.socket.setSoTimeout(0);
		noHello = true;
	}
	
	public void setHost(Host host)
	{
		parentHost = host;
	}
	public String getIP()
	{
		if(socket.getInetAddress() != null)
			return socket.getInetAddress().getHostAddress();
		return "0.0.0.0";
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

		if(!noHello)
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
		Log.i("NETWORK", "OUT - TCP - GLOBAL_MSG " + (char)DataHandler.GLOBAL_MESSAGE_MSG + msg.toString());
		out.print(Character.toString((char)DataHandler.GLOBAL_MESSAGE_MSG) + msg.getMessage() + '\n');
		out.flush();
	}
	
	private void sayHello()
	{
		Log.i("NETWORK", "OUT - TCP - HELLO_MSG " + (char)DataHandler.HELLO_MSG + DataHandler.localhost.name);
		Log.w("sayHello", out.toString());
		out.print(Character.toString((char)DataHandler.HELLO_MSG) + DataHandler.localhost.name + '\n');
		//out.flush();
	}
	
	private void listenSocket()
	{
		while(!socket.isClosed())
		{
			String trame = readStream(in);
			if(trame.length() == 0)
				break;
			String data = trame.substring(1);
			Log.d("NETWORK", "IN - TCP - " + trame.charAt(0) + " " + data);
			DataHandler.networkMessage((int)(trame.charAt(0)), data, parentHost);
		}
		close();
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
			System.out.println("readStream() : IOException");
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
