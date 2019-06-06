package croom.konekom.in.testtask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import croom.konekom.in.testtask.fragment.QRFragment;
import croom.konekom.in.testtask.fragment.QRHistory;
import croom.konekom.in.testtask.helper.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private int requestCode = 1;
    String visibleFragment = "qrscan";

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
        toolbar.setTitle("Shop");
        loadFragment(new QRFragment());

    }
    private void checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);

        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");

          if((this.requestCode) == requestCode){

              if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                  Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

              } else {
                    checkAndRequestPermissions();
                  Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

              }
          }
        }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
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

}
