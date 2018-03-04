package bvpieee.securitysystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainalertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainalert);

        final MediaPlayer alert = MediaPlayer.create(MainalertActivity.this, R.raw.siren);
        alert.start();
        SharedPreferences preference=getSharedPreferences("ieee",MODE_PRIVATE);
        final String num=preference.getString("no","");

        final String addr=preference.getString("add","");

        FloatingTextButton call=(FloatingTextButton)findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:"+num);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
        FloatingTextButton sms=(FloatingTextButton)findViewById(R.id.sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", num, "")).putExtra("sms_body", "There is an emergency as intruder is detected at my shop.\nAddress::"+addr));
            }
        });
}


}
