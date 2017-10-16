package com.example.tommy.stop_watch_real;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {
    private Cursor myCursor;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecordListAdapter(Cursor myCursor) {
        this.myCursor = myCursor;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView)  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ImageView imageView = (ImageView) holder.cardView.findViewById(R.id.image_field);

        TextView textView1 = (TextView) holder.cardView.findViewById(R.id.date_field);
        TextView textView2 = (TextView) holder.cardView.findViewById(R.id.time_field);
        myCursor.moveToPosition(position);
        String date =  myCursor.getString(0);
        int minutes =  myCursor.getInt(1);
        textView1.setText(date);
        textView1.setText(Integer.toString(minutes));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myCursor.getCount();
    }
}