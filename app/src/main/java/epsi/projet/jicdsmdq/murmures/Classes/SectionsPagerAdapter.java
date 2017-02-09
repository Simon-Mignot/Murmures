package epsi.projet.jicdsmdq.murmures.Classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ArrayAdapter;

import java.util.Map;

/**
 * Created by C.DUMORTIER on 09/02/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    public void refreshAdapters()
    {
        for(Map.Entry<String, Object> entry : PlaceholderFragment.adaptersList.entrySet())
        {
            Object obj = entry.getValue();

            if(obj instanceof MyAdapter)
                ((MyAdapter)obj).notifyDataSetChanged();
            else if(obj instanceof ArrayAdapter)
                ((ArrayAdapter)obj).notifyDataSetChanged();
        }
    }

    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "Salon";
            case 1:
                return "Liste Connectés";
        }
        return null;
    }
}
