/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.projet.jicdsmdq.murmures.Server;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
}
