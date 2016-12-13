/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Simon
 */
class Client
{
	public Client()
	{

	}
	public Client(String _name, InetAddress _ip)
	{
		name = _name;
		ip = _ip;
		lastKeepalive = new Date();
	}
	
	public void resetKeepalive()
	{
		System.out.println((new Date().getTime() - lastKeepalive.getTime())/1000.);
		lastKeepalive = new Date();
	}
	
	public byte[] toBytesArray()
	{
		String firstPart = new String(name + '\0');
		int firstPartLength = firstPart.length();
		byte[] out = new byte[firstPartLength + 4];
		System.arraycopy(firstPart.getBytes(), 0, out, 0, firstPartLength);
		System.arraycopy(ip.getAddress(), 0, out, firstPartLength, 4);
		return out;
	}
	
	@Override
	public String toString()
	{
		return (name + '\n' + ip.getHostAddress());
	}
	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof Client)
			return (name.equals(((Client)o).name) && ip.equals(((Client)o).ip));
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + Objects.hashCode(this.name);
		hash = 43 * hash + Objects.hashCode(this.ip);
		return hash;
	}
	public String name;
	public InetAddress ip;
	public Date lastKeepalive;
}