/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                                mMediaPlayer.pause();
                                mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                       releaseMediaPlayer();

                    }  else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Initialize the string array
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word ("One","Lutti",
                R.mipmap.number_one,R.raw.number_one));
        words.add(new Word ("Two","Otiiko",
                R.mipmap.number_two,R.raw.number_two));
        words.add(new Word ("Three","tolookosu",
                R.mipmap.number_three,R.raw.number_three));
        words.add(new Word ("Four","Oyyisa",
                R.mipmap.number_four,R.raw.number_four));
        words.add(new Word ("Five","Massokka",
                R.mipmap.number_five,R.raw.number_five));
        words.add(new Word ("Six","Temmokka",
                R.mipmap.number_six,R.raw.number_six));
        words.add(new Word ("Seven","Kenekaku",
                R.mipmap.number_seven,R.raw.number_seven));
        words.add(new Word ("Eight","Kawinta",
                R.mipmap.number_eight,R.raw.number_eight));
        words.add(new Word ("Nine","Wo'e",
                R.mipmap.number_nine,R.raw.number_nine));
        words.add(new Word ("Ten","na'aacha",
                R.mipmap.number_ten,R.raw.number_ten));



        WordAdapter adapter = new WordAdapter(this,words,R.color.category_numbers);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getAudioResourceId());



                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }


            }
        });

        /*
          Clean up the media player by releasing its resources.
         */
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;


            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
