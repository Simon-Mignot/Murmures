package epsi.projet.jicdsmdq.murmures.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.HashMap;

import epsi.projet.jicdsmdq.murmures.R;

/**
 * Created by C.DUMORTIER on 09/02/2017.
 */

public class PlaceholderFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static HashMap<String, Object> adaptersList;

    public PlaceholderFragment()
    {
    }

    public static PlaceholderFragment newInstance(int sectionNumber)
    {
        PlaceholderFragment fragment = new PlaceholderFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);

        adaptersList = new HashMap<>();

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = null;

        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1)
        {
            rootView = inflater.inflate(R.layout.activity_chatall, container, false);

            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.textchatall);
            final MyAdapter adapter;

            if(adaptersList.containsKey("globalChat"))
                adapter = (MyAdapter)adaptersList.get("globalChat");
            else
            {
                adapter = new MyAdapter(this.getContext(), DataHandler.globalMessage);
                adaptersList.put("globalChat", adapter);
            }

            final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);

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
                        DataHandler.networkSend(new Message(DataHandler.localhost, message.getText().toString()));
                        message.setText("");
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
            DataHandler.setList(ad);
        }
        return rootView;
    }
}
