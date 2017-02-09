package epsi.projet.jicdsmdq.murmures.Classes;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import epsi.projet.jicdsmdq.murmures.Classes.DataHandler;
import epsi.projet.jicdsmdq.murmures.Classes.Message;
import epsi.projet.jicdsmdq.murmures.R;

/**
 * Created by C.DUMORTIER on 07/02/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    LinkedList<Message> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolderLeft extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView pseudo;

        public ViewHolderLeft(View vue) {
            super(vue);
            title = (TextView) vue.findViewById(R.id.titleleft);
            pseudo = (TextView) vue.findViewById(R.id.pseudoleft);
        }
    }

    public class ViewHolderright extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView pseudo;

        public ViewHolderright(View vue) {
            super(vue);
            title = (TextView) vue.findViewById(R.id.titleright);
            pseudo = (TextView) vue.findViewById(R.id.pseudoright);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context mContext, LinkedList<Message> messages) {
        this.mContext = mContext;
        this.mDataset = messages;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mDataset.get(position);

        if (message.host.name.equals(DataHandler.localhost.name)) {
            return 0;
        } else if (!message.host.name.equals(DataHandler.localhost.name)) {
            return 1;
        }
        return -1;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.bubblechat_right, parent, false);
                viewHolder = new ViewHolderright(v1);
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.bubblechat_left, parent, false);
                viewHolder = new ViewHolderLeft(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.bubblechat_right, parent, false);
                viewHolder = new ViewHolderLeft(v);
                break;
        }
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolderright vh1 = (ViewHolderright) holder;
                configureViewHolderright(vh1, position);
                break;
            case 1:
                ViewHolderLeft vh2 = (ViewHolderLeft) holder;
                configureViewHolderleft(vh2, position);
                break;
            default:
                ViewHolderLeft vh = (ViewHolderLeft) holder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureDefaultViewHolder(ViewHolderLeft vh, int position) {
        Message message = mDataset.get(position);
        vh.title.setText(message.getMessage());
        vh.pseudo.setText(message.host.name);
    }

    private void configureViewHolderright(ViewHolderright vh1, int position) {
        Message message = mDataset.get(position);
        vh1.title.setText(message.getMessage());
        vh1.pseudo.setText(message.host.name);
    }

    private void configureViewHolderleft(ViewHolderLeft vh2, int position) {
        Message message = mDataset.get(position);
        vh2.title.setText(message.getMessage());
        vh2.pseudo.setText(message.host.name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
