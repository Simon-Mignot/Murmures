/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.projet.jicdsmdq.murmures.Classes;

import java.util.Date;

/**
 *
 * @author Simon
 */
public class Message
{
	private String message;
	private Date date;
	boolean read;
	protected Host host;
	
	public Message(Host host, String message)
	{
		this.host = host;
		this.message = message;
		read = false;
		date = new Date();
	}
	
	public String toString()
	{
		return host.name + ":\n" + message;
	}
	
    public String getMessage()
	{
        return message;
    }

    public Date getDate()
	{
        return date;
    }
}
