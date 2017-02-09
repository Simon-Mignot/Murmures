package epsi.projet.jicdsmdq.murmures.Network;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import epsi.projet.jicdsmdq.murmures.Handlers.DataHandler;
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

			Log.d("NETWORK", "IN - TCP - " + socket.getRemoteSocketAddress() + " : " + socket.getLocalPort() + " - " + socket.getPort());
			try
			{
				DataHandler.knownHostList.add(new Host("", new ClientTCP(socket)));
			}
			catch(SocketException e)
			{
				e.printStackTrace();
			}
		}
	}
}
