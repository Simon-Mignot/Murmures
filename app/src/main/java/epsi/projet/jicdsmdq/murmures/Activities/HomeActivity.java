package epsi.projet.jicdsmdq.murmures.Activities;

import android.Manifest;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import epsi.projet.jicdsmdq.murmures.Handlers.DataHandler;
import epsi.projet.jicdsmdq.murmures.Classes.Message;
import epsi.projet.jicdsmdq.murmures.Classes.Host;
import epsi.projet.jicdsmdq.murmures.Handlers.NetworkHandler;
import epsi.projet.jicdsmdq.murmures.R;
import epsi.projet.jicdsmdq.murmures.Network.Server;

public class HomeActivity extends AppCompatActivity
{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private static RecyclerView recyclerView;
    private static MyAdapter adapter;
    private static RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        Log.d("Permission check", permissionCheck != 0 ? "true" : "false");
        Log.d("permission internet",Manifest.permission.INTERNET);

        setContentView(R.layout.activity_home);
        Intent intent = getIntent();

        String pseudo = intent.getStringExtra("pseudo");
        intent = new Intent(this, SectionsPagerAdapter.class);
        intent.putExtra("pseudo", pseudo);

        DataHandler.init(new Host(pseudo), this);
        Server.startNetwork();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
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

    public static class PlaceholderFragment extends Fragment
    {
        private static final String ARG_SECTION_NUMBER = "section_number";

        private static HashMap<String, Object> adaptersList;

        public PlaceholderFragment()
        {

        }

        public static PlaceholderFragment newInstance(int sectionNumber, String pseudo)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            adaptersList = new HashMap<>();

            args.putString("pseudo", pseudo);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {

            View rootView = null;

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1)
            {
                //final Group general = new Group("General");
                //groupeList.addGroup(general);

                rootView = inflater.inflate(R.layout.activity_chatall, container, false);

                recyclerView = (RecyclerView) rootView.findViewById(R.id.textchatall);
                if(adaptersList.containsKey("globalChat"))
                    adapter = (MyAdapter)adaptersList.get("globalChat");
                else
                {
                    adapter = new MyAdapter(this.getContext(), DataHandler.globalMessage);
                    adaptersList.put("globalChat", adapter);
                }

                GridLayoutManager myGridLayoutManager = new GridLayoutManager(this.getContext(), 1);

                final RecyclerView.LayoutManager mLayoutManager = myGridLayoutManager;

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);

                DataHandler.setGlobalMessagesRead();

                final View sendView=rootView;
                final ImageButton sendbutton = (ImageButton) rootView.findViewById(R.id.buttonSend);
                final EditText message = (EditText) sendView.findViewById(R.id.messageText);
                sendbutton.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        if (message.getText().toString().length() > 0)
                        {
                            NetworkHandler.networkSend(new Message(DataHandler.localhost, message.getText().toString()));
                            message.setText("");
                            //list.setSelection(list.getAdapter().getCount() - 1);
                            recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
                            adapter.notifyDataSetChanged();
                        }
                    }});
                message.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if(s.length() != 0) {
                            sendbutton.setImageResource(R.drawable.ic_send_blue_48dp);
                            sendbutton.setClickable(true);
                        }
                        else {
                            sendbutton.setImageResource(R.drawable.ic_send_white_48dp);
                            sendbutton.setClickable(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });


            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                rootView = inflater.inflate(R.layout.activity_chat1to1, container, false);
                final ListView list = (ListView) rootView.findViewById(R.id.listUser);
                ArrayAdapter ad;
                if(adaptersList.containsKey("connectedHostsList"))
                    ad = (ArrayAdapter)adaptersList.get("connectedHostsList");
                else
                {
                    ad = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, DataHandler.knownHostList);
                    adaptersList.put("connectedHostsList", ad);
                }
                list.setAdapter(ad);
            }

            /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                textView.setText("page 1");
            }
            else {
                textView.setText("autre pages");
            }*/
            return rootView;
        }
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        private String pseudo;

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
            Intent intent = getIntent();
            pseudo = intent.getStringExtra("pseudo");
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, pseudo);
        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
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
}


