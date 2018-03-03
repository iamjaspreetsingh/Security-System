package bvpieee.securitysystem;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainalertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainalert);

        final MediaPlayer alert = MediaPlayer.create(MainalertActivity.this, R.raw.siren);
        alert.start();

}


}
