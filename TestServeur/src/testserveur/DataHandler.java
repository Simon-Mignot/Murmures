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
	static final public int NEW_GLOBAL_MESSAGE = 0x1;
	//static public ArrayList
	
	static public void networkEvent(int eventType, String data)
	{
		switch(eventType)
		{
			case NEW_GLOBAL_MESSAGE:
				
				break;
		}
	}
}
