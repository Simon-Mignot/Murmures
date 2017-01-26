/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import java.util.Date;

/**
 *
 * @author Simon
 */
public class Message
{
	String message;
    Date date;
	public Host host;
	
	public Message(Host host, String message)
	{
		this.host = host;
		this.message = message;
		date = new Date();
	}
	
	public String toString()
	{
		return host.name + '(' + date.toString() + ") : " + message;
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
