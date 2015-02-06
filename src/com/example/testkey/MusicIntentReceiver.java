/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.example.testkey;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Receives broadcasted intents. In particular, we are interested in the
 * android.media.AUDIO_BECOMING_NOISY and android.intent.action.MEDIA_BUTTON intents, which is
 * broadcast, for example, when the user disconnects the headphones. This class works because we are
 * declaring it in a &lt;receiver&gt; tag in AndroidManifest.xml.
 */
public class MusicIntentReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "MusicIntentReceiver";
	private Context mContext;
	private KeyService mKeyService;
	private static int mDefaultTimeOut = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
		mContext = context;
		mKeyService = new KeyService(mContext);
        if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            Toast.makeText(context, "Headphones disconnected.", Toast.LENGTH_SHORT).show();

            // send an intent to our MusicService to telling it to pause the audio
            //context.startService(new Intent(MusicService.ACTION_PAUSE));

        } else if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            Log.i(LOG_TAG, "ACTION_MEDIA_BUTTON!");
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    //context.startService(new Intent(MusicService.ACTION_TOGGLE_PLAYBACK));
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                    //context.startService(new Intent(MusicService.ACTION_PLAY));
                    break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    //context.startService(new Intent(MusicService.ACTION_PAUSE));
                    break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    //context.startService(new Intent(MusicService.ACTION_STOP));
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    //context.startService(new Intent(MusicService.ACTION_SKIP));
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    // TODO: ensure that doing this in rapid succession actually plays the
                    // previous song
                    //context.startService(new Intent(MusicService.ACTION_REWIND));
                    break;
            }
        } else if (intent.getAction().equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
        	Log.i(LOG_TAG, "BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED");
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,
                    0);
            if (state == BluetoothAdapter.STATE_CONNECTED) {
                Log.i(LOG_TAG, "STATE_CONNECTED");
                mDefaultTimeOut = mKeyService.getScreenOffTime();
                mKeyService.setScreenOffTime(10*1000); // 10 secs
            } else if (state == BluetoothAdapter.STATE_DISCONNECTING ||
            		   state == BluetoothAdapter.STATE_DISCONNECTED) {
                Log.i(LOG_TAG, "STATE_DISCONNECTING");
                mKeyService.setScreenOffTime(mDefaultTimeOut); // def secs
            } else {
            	Log.e(LOG_TAG, "STATE_UNKOWN:" + state);
            }
        } else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            Log.i(LOG_TAG, "Intent.ACTION_SHUTDOWN");
            if(mDefaultTimeOut != 0)
                mKeyService.setScreenOffTime(mDefaultTimeOut); // def secs
        } else {
            Log.i(LOG_TAG, "other intent");
        }
    }
}
