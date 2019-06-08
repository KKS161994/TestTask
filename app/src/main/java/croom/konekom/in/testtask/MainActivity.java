package croom.konekom.in.testtask;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import croom.konekom.in.testtask.fragment.QRFragment;
import croom.konekom.in.testtask.fragment.QRHistory;
import croom.konekom.in.testtask.helper.BottomNavigationBehavior;
import croom.konekom.in.testtask.model.Photo;
import croom.konekom.in.testtask.rest.ApiClient;
import croom.konekom.in.testtask.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private int requestCode = 1;
    String visibleFragment = "qrscan";
    Fragment fragment;
    private ApiInterface apiInterface;
    private String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        // load the store fragment by default
        if(null==savedInstanceState){
        toolbar.setTitle("Shop");
        loadFragment(new QRFragment());}
        //loadInternetData();

    }

    private void loadInternetData() {
    apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Photo>> getPhotos = apiInterface.getAllPhotos();
        Log.d(TAG,"Api call");

        getPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Toast.makeText(MainActivity.this,response.code()+" "+response.body().size(),Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Api call success");
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG,"Api call fail");
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("QR Scan");
                    fragment = new QRFragment();


                    if(!visibleFragment.equalsIgnoreCase("qrscan")){
                        visibleFragment = "qrscan";
                        loadFragment(fragment);
                    }

                    return true;
                case R.id.navigation_qr_history:
                    toolbar.setTitle("QR History");


                    fragment = new QRHistory();

                    if(!visibleFragment.equalsIgnoreCase("qrhistory")){
                        visibleFragment = "qrhistory";
                        loadFragment(fragment);
                    }
                    return true;

            }

            return false;
        }
    };
//    public static boolean isFragmentVisible(Fragment fragment) {
//        MyFragmentClass test = (MyFragmentClass) getSupportFragmentManager().findFragmentByTag("testID");
//        if (test != null && test.isVisible()) {
//            //DO STUFF
//        }
//        else {
//            //Whatever
//        }
//    }
    /**
     * loading fragment into FrameLayout
     *
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount() - 1; i++) {
            fm.popBackStack();
        }

finish();
    }
}
