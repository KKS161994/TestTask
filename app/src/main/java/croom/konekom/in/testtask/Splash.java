package croom.konekom.in.testtask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    private int requestCode = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        }
        else{
         startActivity();
        }

    }
    public void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
    private void checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);

        }
        else{
            startActivity();
        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");

        if((this.requestCode) == requestCode){

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
startActivity();
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {
                checkAndRequestPermissions();
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }
        }
    }
}
