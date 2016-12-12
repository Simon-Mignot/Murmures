/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

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
	public Client(String _name, String _ip)
	{
		name = _name;
		ip = _ip;
	}
	
	
	@Override
	public String toString()
	{
		return (name + ";" + ip);
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
	public String ip;
}