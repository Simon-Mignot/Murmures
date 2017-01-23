package epsi.projet.jicdsmdq.murmures.Classes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import epsi.projet.jicdsmdq.murmures.Activities.HomeActivity;

/**
 * Created by Corentin on 17/01/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public Context mContext;
    private String pseudo;

    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return HomeActivity.PlaceholderFragment.newInstance(position + 1, pseudo);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0:
                return "All";
            case 1:
                return "Groupes";
            case 2:
                return "Individuel";
        }
        return null;
    }
}
