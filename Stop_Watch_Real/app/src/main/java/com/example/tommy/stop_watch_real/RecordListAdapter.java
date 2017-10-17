package com.example.tommy.stop_watch_real;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.sip.SipAudioCall;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {

    Context mycontext;
    private RecordListAdapter mAdapter;
    public void setterForAdapter (RecordListAdapter adapter) {
        this.mAdapter = adapter;
        return;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }


    public RecordListAdapter (Context context) {
        this.mycontext = context;
    }

    @Override
    public RecordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView)  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ImageView imageView = (ImageView) holder.cardView.findViewById(R.id.image_field);
        imageView.setImageResource(R.drawable.ic_launcher_round);
        TextView textView1 = (TextView) holder.cardView.findViewById(R.id.date_field);
        TextView textView2 = (TextView) holder.cardView.findViewById(R.id.time_field);
        String date = null;
        int minutes = 0;
        try {
            SQLiteOpenHelper dataBaseHelper = new DataBaseHelper(mycontext);
            SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
            Cursor myCursor = db.query("RECORD", new String[] {"DATE", "MINUTES"}, null, null,null,null,null);
            myCursor.moveToFirst();
            myCursor.moveToPosition(position);
            date = myCursor.getString(0);
            minutes = myCursor.getInt(1);
            myCursor.close();
            db.close();
        }catch (SQLiteException e)
        {e.printStackTrace();}
        textView1.setText(date);
        textView2.setText(Integer.toString(minutes));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mycontext);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure you want to delete this? ");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteOpenHelper dataBaseHelper = new DataBaseHelper(mycontext);
                        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
                        TextView temp = (TextView) v.findViewById(R.id.date_field);
                        db.delete("RECORD", "DATE=?", new String[] {temp.getText().toString()});
                        dialog.dismiss();
                        db.close();
                        mAdapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        SQLiteOpenHelper dataBaseHelper = new DataBaseHelper(mycontext);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor temp = db.query("RECORD", new String[] {"DATE", "MINUTES"}, null, null,null,null,null);
        int tempInt = temp.getCount();
        temp.close();
        db.close();
        return tempInt;
    }
}