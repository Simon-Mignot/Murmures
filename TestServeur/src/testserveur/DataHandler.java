/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

/**
 *
 * @author Simon
 */
public class DataHandler
{
	static final public int NEW_CLIENT = 0x01;
	static final public int NEW_GLOBAL_MESSAGE = 0x02;
	//static public ArrayList
	
	static public void networkEvent(int eventType, byte[] data)
	{
		networkEvent(eventType, new String(data));
	}
	
	static public void networkEvent(int eventType, String data)
	{
		switch(eventType)
		{
			case NEW_CLIENT:
				System.out.println("NetworkEvent: " + data);
				break;
		}
	}
}
