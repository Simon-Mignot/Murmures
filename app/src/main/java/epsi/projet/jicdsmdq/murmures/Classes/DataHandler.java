/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.projet.jicdsmdq.murmures.Classes;

import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.RunnableFuture;

import epsi.projet.jicdsmdq.murmures.Activities.HomeActivity;
import epsi.projet.jicdsmdq.murmures.Server.ClientTCP;
import epsi.projet.jicdsmdq.murmures.Server.Server;

/**
 *
 * @author Simon
 */
public class DataHandler
{
	static final public int ANNOUCEMENT_MSG = 0x01;
	static final public int HELLO_MSG = 0x02;
	static final public int GLOBAL_MESSAGE_MSG = 0x04;
	
	static final public int HOST_DISCONNECT_EVENT = 0x01;

	static public Host localhost;
	static public LinkedList<Host> knownHostList = new LinkedList<Host>();
	static public LinkedList<Message> globalMessage = new LinkedList<Message>();
	static public ArrayAdapter list;
    static public HomeActivity homeActivity;

	static final Handler handler = new Handler();
	static final Runnable updateUI = new Runnable()
	{
		@Override
		public void run()
		{
			if(homeActivity != null)
				homeActivity.refresh();
		}
	};


	static public void setList(ArrayAdapter _list)
	{
		list = _list;
	}

	static public void init(Host _localhost, HomeActivity _homeActivity)
	{
		localhost = _localhost;
        homeActivity = _homeActivity;
		knownHostList.add(localhost);
	}
	static public void networkMessage(int eventType, byte[] data, Object ip)
	{
		networkMessage(eventType, new String(data).substring(1), ip);
	}
	static public void networkMessage(int eventType, String data)
	{
		networkMessage(eventType, data, "");
	}
	static public void networkMessage(int eventType, String data, Object host)
	{
		switch(eventType)
		{
			case ANNOUCEMENT_MSG:
				//System.out.println("Annoucement from: " + data);
				receivedAnnoucementMessage(data, (InetAddress)host);
				//globalMessage.add(new Message(localhost, "NetworkEvent " + data));
				break;

			case HELLO_MSG:
                ((Host)host).annoucementName = data;
                setNameHelloMsg(((Host)host), data);
				break;
			
			case GLOBAL_MESSAGE_MSG:
				globalMessage.add(new Message((Host)host, data));
				for(Message m : globalMessage)
					System.out.println((m.host.name == localhost.name ? ">" : "<") + m.toString() + '\n');
				break;
		}
		handler.post(updateUI);
	}

	static public void networkSend(Message message)
	{
		//if(message.host == localhost)
		globalMessage.add(message);
		for(Host host : knownHostList)
		{
			if(host.tcp != null)
				host.tcp.sendMessage(message);
		}

	}
	
	static public void networkEvent(int eventType, Object data)
	{
		switch(eventType)
		{
			case HOST_DISCONNECT_EVENT:
				hostDisconnectEvent((Host)data);
				break;
		}
	}
	
	
	static private String getNewHostname(String name, byte[] ip)
	{
		int id = 0;
		for(int i = 0; i < 4; ++i)
			id += (/*Byte.toUnsignedInt*/(ip[i]) * (i + 1));
		for(char c : name.toCharArray())
			id += (int)c;
		return name + '-' + String.format("%08X", id);
	}

	static private boolean hostIsWaitingHello(Host host, String ip)
	{
		Date before = new Date();
		before.setTime(before.getTime() - Server.HELLO_WAITING_TIME_MS);

		return (host.tcp.getIP().equals(ip)
 			 && host.name.length() == 0
			 );
	}

	/*
        behavior when the host name is known
            return true if keepalive,
            false if a new name have to be generated

	 */

    static public void setNameHelloMsg(Host host, String name)
    {
        for(Host h : knownHostList)
        {
            if(h.annoucementName.equals(name) && h.name.length() > 0)
            {
                host.name = getNewHostname(name, host.tcp.getInetAddress().getAddress());
                return;
            }
        }
        host.name = name;
    }
	
	static private void receivedAnnoucementMessage(String data, InetAddress ip)
	{
		String str_ip = ip.getHostAddress();
        String altName = getNewHostname(data, ip.getAddress());
        boolean isAltName = false;
		for(Host host : knownHostList)
		{
            Log.e("DEBUG", host.annoucementName + " == " + data + " ? " + host.annoucementName.equals(data) + "(" + (host.tcp == null ? "null" : host.tcp.getIP()) + ")");
            Log.e("DEBUG", host.annoucementName + " " + host.name);
            Log.e("DEBUG", Integer.toString(host.annoucementName.length()));
            Log.e("DEBUG", " ");
            if(host.annoucementName.equals(data))
            {
                if(host.tcp != null && host.tcp.getIP().equals(str_ip))
                {
                    host.resetKeepalive();
                    return;
                }
                else// if(host.tcp != null)
                {
                    isAltName = true;
                }
            }
            else if(host == localhost)
                continue;
            else if(hostIsWaitingHello(host, str_ip))
                return;
		}

		try
		{
            Host newHost;
            ClientTCP tcpClient = new ClientTCP(str_ip);
            if(isAltName)
                newHost = new Host(data, altName, tcpClient);
            else
                newHost = new Host(data, tcpClient);
			knownHostList.add(newHost);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	static private void hostDisconnectEvent(Host host)
	{
		System.out.println("Remove : " + host.name);
		knownHostList.remove(host);
	}




}


/*







            if(host.tcp != null)
                Log.e("IP DEBUG", host.tcp.getIP());

            if(host.tcp == null && host != localhost)
            {
                knownHostList.remove(host);
                continue;
            }
            else if(host.name.equals(data))
            {
                if(host.tcp != null && host.tcp.getIP().equals(str_ip) && host.tcp.getIP().length() > 0)
                {
                    host.resetKeepalive();
                    return;
                }
                data = altName;
                break;
            }
            else if(host.name.equals(altName))
            {
                // altName can't be localhost, so tcp is never null
                if(host.tcp.getIP().equals(str_ip))
                    host.resetKeepalive();
                //altName with a different IP is theoretically not possible
                return;
            }
            else if(host == localhost)
                continue;
            else if(hostIsWaitingHello(host, str_ip))
                return;

 */