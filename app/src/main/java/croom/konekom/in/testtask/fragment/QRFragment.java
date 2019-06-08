package croom.konekom.in.testtask.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import croom.konekom.in.testtask.R;
import croom.konekom.in.testtask.database.AppDatabase;
import croom.konekom.in.testtask.database.QREntry;

public class QRFragment extends Fragment implements View.OnClickListener {
    private CodeScanner mCodeScanner;
    public FloatingActionButton fab;
    private ArrayList<BarcodeFormat> barcodeFormats;
    private AppDatabase appDatabase;
    private QREntry qrEntry;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.qrscan, container, false);
        CodeScannerView scannerView = rootView.findViewById(R.id.scanner_view);

        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        appDatabase = AppDatabase.getInstance(getActivity());
        barcodeFormats = new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        mCodeScanner.setFormats(barcodeFormats);
        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
                        fab.show();
                        qrEntry = new QREntry();
                        qrEntry.setTimestamp(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        qrEntry.setData(result.getText());
                        appDatabase.userDao().insert(qrEntry);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCodeScanner.releaseResources();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                mCodeScanner.startPreview();
                fab.hide();
        }
    }
}
