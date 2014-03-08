package me.johnyu.playsimpleaudio.app;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends ActionBarActivity {
    final String TAG = "log";
    boolean playPauseFlag = true;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Play music stored in src/main/assets
        try
        {
            if(mediaPlayer == null)
            {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                AssetFileDescriptor afd = getAssets().openFd("sample.mp3");
                mediaPlayer.setDataSource(afd.getFileDescriptor());
                mediaPlayer.prepare();
            }

            mediaPlayer.start();

            TextView duration = (TextView) findViewById(R.id.durLabel);
            duration.setText(milliToMinSec(mediaPlayer.getDuration()));

            Log.v(TAG, String.valueOf(mediaPlayer.getDuration()));
            playPauseFlag = false;
        }

        catch (Exception e)
        {
            // Display an error toast message
            Toast toast = Toast.makeText(this, "File: not found!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void playFromRaw()
    {
        // Play music stored in src/main/res/raw folder
        mediaPlayer = MediaPlayer.create(this, R.raw.ed);
        mediaPlayer.start();
    }

    public void playPauseSong(View view)
    {
        if(playPauseFlag)
        {
            // Play music stored in src/main/assets
            try
            {
                if(mediaPlayer == null)
                {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    AssetFileDescriptor afd = getAssets().openFd("sample.mp3");
                    mediaPlayer.setDataSource(afd.getFileDescriptor());
                    mediaPlayer.prepare();
                }

                mediaPlayer.start();

                playPauseFlag = false;
            }

            catch (Exception e)
            {
                // Display an error toast message
                Toast toast = Toast.makeText(this, "File: not found!", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        else
        {
            mediaPlayer.pause();
            playPauseFlag = true;
        }
    }

    // Convert a given millisecond integer to a formatted Min:Sec string
    public String milliToMinSec(int milli)
    {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milli);
        milli -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milli);

        String result =  minutes + " : " + seconds;
        return  result;
    }

}
