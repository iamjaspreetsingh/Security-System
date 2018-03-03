package bvpieee.securitysystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        final Switch activate=(Switch)findViewById(R.id.switch1);
        final Switch alarm=(Switch)findViewById(R.id.switch2);

        final Switch sms=(Switch)findViewById(R.id.switch3);

        final ImageView live=(ImageView) findViewById(R.id.imageView);


        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);

            }
        });




        SharedPreferences preference=getSharedPreferences("ieee",MODE_PRIVATE);
        String acce=preference.getString("acti","1");

        if(acce.equals("1")  )
        {activate.setChecked(true);
        startService();
        }
        else activate.setChecked(false);

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activate.isChecked())
                {
                    SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();
                    editor.putString("acti","1");

                    editor.apply();
                    startService();
                }
                else {

                    SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();
                    editor.putString("acti","0");

                    editor.apply();


                }

            }
        });




        String accee=preference.getString("alarm","1");

        if(accee.equals("1")  )alarm.setChecked(true);else alarm.setChecked(false);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(alarm.isChecked())
                {
                    SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();
                    editor.putString("alarm","1");

                    editor.apply();
                }
                else {

                    SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();
                    editor.putString("alarm","0");

                    editor.apply();
                }

            }
        });




         String accte=preference.getString("sms","1");

        if(accte.equals("1")  )sms.setChecked(true);else sms.setChecked(false);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sms.isChecked())
                {
                    SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();
                    editor.putString("sms","1");

                    editor.apply();
                }
                else {

                    SharedPreferences.Editor editor=getSharedPreferences("ieee",MODE_PRIVATE).edit();
                    editor.putString("sms","0");

                    editor.apply();
                }

            }
        });




    }


    public void startService() {
        startService(new Intent(getBaseContext(), MyService.class));



    }
}