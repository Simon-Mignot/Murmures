package epsi.projet.jicdsmdq.murmures.Handlers;

import android.os.Vibrator;

/**
 * Created by Simon on 09/02/2017.
 */
public class MiscHandler
{
    static final public String version = "alpha2.0";

    static protected Vibrator vibrator;

    static public void vibrate()
    {
        if(OptionsHandler.options_vibrator)
            vibrator.vibrate(getLongArrayPattern(OptionsHandler.options_vibratorPattern), -1);
    }

    static private long[] getLongArrayPattern(String pattern)
    {
        String[] array = pattern.split(",");
        long[] result = new long[array.length + 1];
        result[0] = 0;
        for(int i = 0; i < array.length; ++i)
            result[i + 1] = Long.valueOf(array[i]);
        return result;
    }
}
