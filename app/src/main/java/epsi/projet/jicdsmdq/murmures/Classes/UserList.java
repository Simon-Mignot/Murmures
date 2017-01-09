package epsi.projet.jicdsmdq.murmures.Classes;


import java.util.HashMap;


/**
 * Created by damien on 12/12/16.
 */

public class UserList {


    private HashMap<String,User> userlist ;
    private static UserList instance;

    private UserList(){
        userlist = new HashMap<String, User>();
    }

    public  static  UserList getInstance()
    {
        if(instance == null){
            instance = new UserList();
        }
        return  instance;
    }


    public void addUser(User user){
        userlist.put(user.getPseudo(),user);
    }

    public void removeUser(User user){
        userlist.remove(user);
    }

    public User getUser(String pseudo){
        return  userlist.get(pseudo);
    }

}
