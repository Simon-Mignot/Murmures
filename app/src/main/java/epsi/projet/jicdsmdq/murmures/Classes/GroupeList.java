package epsi.projet.jicdsmdq.murmures.Classes;


import java.util.HashMap;


/**
 * Created by damien on 12/12/16.
 */

public class GroupeList {


    private HashMap<String,Group> groupelist ;
    private static GroupeList instance;

    private GroupeList(){
        groupelist = new HashMap<String, Group>();
    }

    public  static GroupeList getInstance()
    {
        if(instance == null){
            instance = new GroupeList();
        }
        return  instance;
    }


    public void addGroup(Group channel){
        groupelist.put(channel.getNom(),channel);
    }

    public void removeGroup(Group channel){
        groupelist.remove(channel);
    }

    public Group getGroup(String nomChannel){
        return  groupelist.get(nomChannel);
    }

}
