package epsi.projet.jicdsmdq.murmures.Network;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import epsi.projet.jicdsmdq.murmures.Handlers.NetworkHandler;

/**
 *
 * @author Simon
 */
public class ServerUDP extends Thread
{
	private DatagramSocket listener;
	
	ServerUDP(int port)
	{
		try
		{
			listener = new DatagramSocket(port);
		}
		catch(SocketException ex)
		{
			Log.d("ServerTCP", port + "");
			Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private byte[] removeNullBytes(byte[] data)
	{
		int dataLength = 0;
		for(int i = 0; i < data.length; ++i)
		{
			if(data[i] == 0)
			{
				dataLength = i;
				break;
			}
		}
		byte[] result = new byte[dataLength];

		for(int i = 0; i < dataLength; ++i)
			result[i] = data[i];

		return result;
	}


	@Override
	public void run()
	{
		while(true)
		{
			int DATA_SIZE = 1024;
			byte[] data = new byte[DATA_SIZE];
			DatagramPacket packet = new DatagramPacket(data, DATA_SIZE);
			try
			{
				listener.receive(packet);
			}
			catch(IOException ex)
			{
				Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
			}
			data = removeNullBytes(data);
			Log.d("NETWORK", "IN - UDP - (ANNOUNCEMENT_MSG) " + (int) data[0] + " " + packet.getAddress().getHostAddress() + ": " + new String(data));
			if(!Server.localAddresses.contains(packet.getAddress().getHostAddress()))
				NetworkHandler.networkMessage(NetworkConstants.ANNOUNCEMENT_MSG, data, packet.getAddress());
		}
		
	}
}
