package bvpieee.securitysystem;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final FloatingTextButton fb = (FloatingTextButton) findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                adddialog();


            }
        });
        final Switch activate = (Switch) findViewById(R.id.switch1);
        final Switch alarm = (Switch) findViewById(R.id.switch2);

        final Switch sms = (Switch) findViewById(R.id.switch3);

        final ImageView live = (ImageView) findViewById(R.id.imageView);


        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);

            }
        });


        SharedPreferences preference = getSharedPreferences("ieee", MODE_PRIVATE);
        String acce = preference.getString("acti", "1");

        if (acce.equals("1")) {
            activate.setChecked(true);
            startService();
        } else activate.setChecked(false);

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activate.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("ieee", MODE_PRIVATE).edit();
                    editor.putString("acti", "1");

                    editor.apply();
                    startService();
                } else {

                    SharedPreferences.Editor editor = getSharedPreferences("ieee", MODE_PRIVATE).edit();
                    editor.putString("acti", "0");

                    editor.apply();


                }

            }
        });


        String accee = preference.getString("alarm", "1");

        if (accee.equals("1")) alarm.setChecked(true);
        else alarm.setChecked(false);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (alarm.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("ieee", MODE_PRIVATE).edit();
                    editor.putString("alarm", "1");

                    editor.apply();
                } else {

                    SharedPreferences.Editor editor = getSharedPreferences("ieee", MODE_PRIVATE).edit();
                    editor.putString("alarm", "0");

                    editor.apply();
                }

            }
        });


        String accte = preference.getString("sms", "1");

        if (accte.equals("1")) sms.setChecked(true);
        else sms.setChecked(false);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sms.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("ieee", MODE_PRIVATE).edit();
                    editor.putString("sms", "1");

                    editor.apply();
                } else {

                    SharedPreferences.Editor editor = getSharedPreferences("ieee", MODE_PRIVATE).edit();
                    editor.putString("sms", "0");

                    editor.apply();
                }

            }
        });


    }

    private void adddialog() {


        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout, null);
        final EditText num = (EditText) alertLayout.findViewById(R.id.no);

        final EditText add = (EditText) alertLayout.findViewById(R.id.editText2);

        ImageView loc = (ImageView) alertLayout.findViewById(R.id.loc);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // get the last know location from your location manager.
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // now get the lat/lon from the location and do something with it.
        String lat= String.valueOf(location.getLatitude());

        String longi= String.valueOf(location.getLongitude());


String address="http://maps.google.com/maps?q=loc:" + lat+","+longi;



        SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();

        editor.putString("add",add.getText().toString());

        editor.apply();

        add.setText(address);
    }
});
loc.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(getApplicationContext(),"Get Current location of shop",Toast.LENGTH_SHORT).show();
        return false;
    }
});
        SharedPreferences preference=getSharedPreferences("ieee",MODE_PRIVATE);
        String acce=preference.getString("no","");

        num.setText(acce);

        String accee=preference.getString("add","");

       add.setText(accee);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setTitle("Details ");
        alert.setIcon(R.drawable.ic_perm_contact_calendar_black_24dp);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();
                editor.putString("no",num.getText().toString());

                editor.apply();

                editor.putString("add",add.getText().toString());

                editor.apply();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();






    }


    public void startService() {
        startService(new Intent(this, MyService.class));



    }
}
