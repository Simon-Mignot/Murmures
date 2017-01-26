/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import Server.ClientTCP;
import java.net.InetAddress;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Simon
 */
public class Host
{
	public String name;
	public ClientTCP tcp;
	public Date lastKeepalive;
	
	public Host()
	{
		
	}
	public Host(String name)
	{
		this.name = name;
		tcp = null;
	}
	public Host(String _name, ClientTCP _tcp)
	{
		System.out.println("new host : " + _name);
		name = _name;
		tcp = _tcp;
		tcp.setHost(this);
		tcp.start();
		lastKeepalive = new Date();
	}
	public boolean isLocalhost()
	{
		return tcp == null;
	}
	
	public void resetKeepalive()
	{
		System.out.println(name + " : " + (new Date().getTime() - lastKeepalive.getTime())/1000.);
		lastKeepalive = new Date();
	}
	
	public byte[] toBytesArray()
	{
		String firstPart = new String(name + '\0');
		int firstPartLength = firstPart.length();
		byte[] out = new byte[firstPartLength + 4];
		System.arraycopy(firstPart.getBytes(), 0, out, 0, firstPartLength);
		System.arraycopy(tcp.getInetAddress().getAddress(), 0, out, firstPartLength, 4);
		return out;
	}
	
	@Override
	public String toString()
	{
		return (name + '\n' + tcp.getIP());
	}
	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof Host)
			return (name.equals(((Host)o).name)
				 && tcp.getIP().equals(((Host)o).tcp.getIP()));
		return false;
	}
}