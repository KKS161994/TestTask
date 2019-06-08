package croom.konekom.in.testtask.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import croom.konekom.in.testtask.R;
import croom.konekom.in.testtask.adapter.QRItemsAdapter;
import croom.konekom.in.testtask.database.AppDatabase;
import croom.konekom.in.testtask.database.QREntry;

public class QRHistory extends Fragment {
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private final static String TAG = QRHistory.class.getSimpleName();
    private List<QREntry> qrEntries;
    private QRItemsAdapter qrItemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.qrhistory, container, false);
        appDatabase = AppDatabase.getInstance(getActivity());
        qrEntries = new ArrayList<>();
        qrEntries = appDatabase.userDao().getAll();

        recyclerView = rootView.findViewById(R.id.qrHistory);
        if(qrEntries.size()==0||qrEntries==null){
            rootView.findViewById(R.id.noContent).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            rootView.findViewById(R.id.noContent).setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            setAdapter();
        }
        return rootView;
    }

    private void setAdapter() {
        qrItemsAdapter = new QRItemsAdapter(getActivity(), qrEntries);
        recyclerView.setAdapter(qrItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
