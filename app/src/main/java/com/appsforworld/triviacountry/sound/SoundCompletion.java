package com.appsforworld.triviacountry.sound;

import android.media.MediaPlayer;

/**
 * Created by marcelo on 28/09/16.
 */

public class SoundCompletion implements MediaPlayer.OnCompletionListener {
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.reset();
        mediaPlayer.release();
    }
}
