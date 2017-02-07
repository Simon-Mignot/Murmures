/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.projet.jicdsmdq.murmures.Server;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import epsi.projet.jicdsmdq.murmures.Classes.DataHandler;
import epsi.projet.jicdsmdq.murmures.Classes.Host;

/**
 *
 * @author Simon
 */
public class ServerTCP extends Thread
{
	private ServerSocket listener;
	ServerTCP(int port)
	{
		try
		{
			listener = new ServerSocket(port);
		}
		catch(IOException ex)
		{
			Log.d("ServerTCP", port + "");
			Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void run()
	{
		while(true)
		{
			Socket socket = null;
			try
			{
				socket = listener.accept();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if(socket == null)
				continue;

			System.out.println("TCP - " + new String(socket.getRemoteSocketAddress() + " : " + socket.getLocalPort() + " - " + socket.getPort()));
			DataHandler.knownHostList.add(new Host("", new ClientTCP(socket)));
			/*if(!Server.localAddresses.contains(packet.getAddress().getHostAddress()))
				DataHandler.networkMessage(DataHandler.ANNOUCEMENT_MSG, data, packet.getAddress());*/
		}
	}
}
