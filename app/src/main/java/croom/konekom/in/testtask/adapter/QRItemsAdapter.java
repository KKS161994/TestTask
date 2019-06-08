package croom.konekom.in.testtask.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import croom.konekom.in.testtask.R;
import croom.konekom.in.testtask.database.QREntry;


public class QRItemsAdapter extends RecyclerView.Adapter<QRItemsAdapter.ViewHolder> {
    private Context context;
    private List<QREntry> qrEntries;

    public QRItemsAdapter(Context context, List<QREntry> qrEntries) {
        this.context = context;
        this.qrEntries = qrEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.qrhistory_item, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i == 0) {
            viewHolder.qrData.setText("Data");
            viewHolder.qrData.setTypeface(null, Typeface.BOLD);
            viewHolder.qrTimeStamp.setText("Time");
            viewHolder.qrTimeStamp.setTypeface(null, Typeface.BOLD);
        } else {
            QREntry qrEntry = qrEntries.get(i - 1);
            viewHolder.qrData.setText(qrEntry.getData());
            viewHolder.qrTimeStamp.setText(convertTimestamp(qrEntry.getTimestamp()));
            viewHolder.qrData.setTypeface(null, Typeface.NORMAL);
            viewHolder.qrTimeStamp.setTypeface(null, Typeface.NORMAL);
        }
    }

    public String convertTimestamp(Long timestamp) {

        String value = new java.text.SimpleDateFormat("hh:mm:ss  a   dd/MM/yyyy").
                format(new java.util.Date(timestamp * 1000));
        return value;
    }

    @Override
    public int getItemCount() {
        return qrEntries.size() + 1;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView qrData;
        public TextView qrTimeStamp;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            qrData = itemView.findViewById(R.id.qr_data);
            qrTimeStamp = itemView.findViewById(R.id.qr_timestamp);
        }
    }
}