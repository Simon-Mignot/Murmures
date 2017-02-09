package epsi.projet.jicdsmdq.murmures.Handlers;

import android.content.Context;
import android.os.Vibrator;

import epsi.projet.jicdsmdq.murmures.Activities.HomeActivity;
import epsi.projet.jicdsmdq.murmures.Classes.Host;

/**
 * Created by Simon on 09/02/2017.
 */
public class Handler
{
    static public void init(Host localhost, HomeActivity homeActivity)
    {
        DataHandler.init(localhost, homeActivity);

        MiscHandler.vibrator = (Vibrator)homeActivity.getSystemService(Context.VIBRATOR_SERVICE);

    }
}
