package epsi.projet.jicdsmdq.murmures.Classes;

import java.util.ArrayList;

/**
 * Created by Simon on 14/11/2016.
 */
public class Group
{
    private ArrayList<User> membres;
    private ArrayList<Message> channel;
    private String nom;
    public String getNom() {
        return nom;
    }



    public Group(String nomchannel){
        channel = new ArrayList<Message>();
        membres = new ArrayList<User>();
        nom=nomchannel;
    }

    public void addMessage( Message message) {
        channel.add(message);
    }

    public void removeMessage(Message message){
        channel.remove(message);
    }

    public ArrayList getChannel(){
        return channel;
    }

}
