package epsi.projet.jicdsmdq.murmures.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import epsi.projet.jicdsmdq.murmures.Classes.Host;
import epsi.projet.jicdsmdq.murmures.Classes.SectionsPagerAdapter;
import epsi.projet.jicdsmdq.murmures.Handlers.Handler;
import epsi.projet.jicdsmdq.murmures.Handlers.NetworkHandler;
import epsi.projet.jicdsmdq.murmures.Network.Server;
import epsi.projet.jicdsmdq.murmures.R;

public class HomeActivity extends AppCompatActivity
{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Permissions
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        Log.d("Permission check", permissionCheck != 0 ? "true" : "false");
        Log.d("permission internet",Manifest.permission.INTERNET);

        //Layout HOME
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();

        //Ajout Hôte Local
        String pseudo = intent.getStringExtra("pseudo");
        intent = new Intent(this, SectionsPagerAdapter.class);
        intent.putExtra("pseudo", pseudo);

        Handler.init(new Host(pseudo), this);
        Server.startNetwork();

        // Ajout Bar Menu
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Ajout des différentes Tabs
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void refresh()
    {
        mSectionsPagerAdapter.refreshAdapters();
    }

    // Permet de creer le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Permet de creer les boutons du menu avec leurs differentes actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_options:
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_deconnexion:
                NetworkHandler.disconnect();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


