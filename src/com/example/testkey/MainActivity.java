package com.example.testkey;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final static String LOG_TAG = "com.example.testkey";
    TextView tv;
    private MediaPlayer mMediaPlayer = null;
    private CheckBox mCheckBox = null;
    private int state = IDLE;
    private static final int PLAYING = 0;
    private static final int PAUSE = 1;
    private static final int STOP = 2;
    private static final int IDLE = 3;
    public static final int UPDATE = 2;
    private boolean mIsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate");
        tv = (TextView)findViewById(R.id.textView);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        // Power
        registerReceiver(mBatInfoReceiver, filter);
        // Home
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homePressReceiver, homeFilter);

        printToast("开始测试按键！");

        mMediaPlayer = new MediaPlayer();
        mCheckBox = (CheckBox) this.findViewById(R.id.is_test_bt_checkbox);
        mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                mIsChecked = isChecked;
                update();
            }
        });

        // for service
        ((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(new ComponentName(
                this,
                MusicIntentReceiver.class));

        // check intent
        if (getIntent() != null) {
            String act = getIntent().getAction();
            if(act != null) {
                if(act.equals(Intent.ACTION_VOICE_COMMAND)) {
                    // voice command
                    Log.d(LOG_TAG, "VOICE_COMMAND");
                    printToast("get Key VOICE_COMMAND");
                } else if (act.equals("android.intent.action.CALL_PRIVILEGED") || act.equals("android.intent.action.KANGEAR_LASTREDIAL_TO_VR")) {
                    // last number redials command
                    Log.d(LOG_TAG, "ACTION_CALL_PRIVILEGED");
                    printToast("get Key LAST_NUMBER_REDIAL");
                }
            }
        }
    }



    @Override
    public void onDestroy() {

        // 解除注册 Power
        if (mBatInfoReceiver != null) {
            try {
                unregisterReceiver(mBatInfoReceiver);
            } catch (Exception e) {
                Log.e(LOG_TAG, "unregisterReceiver mBatInfoReceiver failure :"
                        + e.getCause());
            }
        };
        // 解除注册 Home
        if (homePressReceiver != null) {
            try {
                unregisterReceiver(homePressReceiver);
            } catch (Exception e) {
                Log.e(LOG_TAG,"unregisterReceiver homePressReceiver failure :"
                                + e.getCause());
            }
        };

        // release mediaplayer
        stop();
        mMediaPlayer.release();
        mMediaPlayer = null;

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_POWER :
                //监控/拦截/屏蔽电源键 这里拦截不了
                printToast("get Key KEYCODE_POWER(KeyCode:"+keyCode+")");
                break;

            case KeyEvent.KEYCODE_RIGHT_BRACKET :
                //监控/拦截/屏蔽返回键
                printToast("get Key KEYCODE_RIGHT_BRACKET");
                break;

            case KeyEvent.KEYCODE_MENU :
                //监控/拦截菜单键
                printToast("get Key KEYCODE_MENU");
                break;

            case KeyEvent.KEYCODE_HOME:
                //由于Home键为系统键，此处不能捕获
                printToast("get Key KEYCODE_HOME");
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                //监控/拦截/屏蔽上方向键
                printToast("get Key KEYCODE_DPAD_UP");
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                //监控/拦截/屏蔽左方向键
                printToast("get Key KEYCODE_DPAD_LEFT");
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                //监控/拦截/屏蔽右方向键
                printToast("get Key KEYCODE_DPAD_RIGHT");
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                //监控/拦截/屏蔽下方向键
                printToast("get Key KEYCODE_DPAD_DOWN");
                break;

            case KeyEvent.KEYCODE_DPAD_CENTER:
                //监控/拦截/屏蔽中方向键
                printToast("get Key KEYCODE_DPAD_CENTER");
                break;

            case KeyEvent.FLAG_KEEP_TOUCH_MODE:
                //监控/拦截/屏蔽长按
                printToast("get Key FLAG_KEEP_TOUCH_MODE");
                break;


            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //监控/拦截/屏蔽下方向键
                printToast("get Key KEYCODE_VOLUME_DOWN(KeyCode:"+keyCode+")");
                break;

            case KeyEvent.KEYCODE_VOLUME_UP:
                //监控/拦截/屏蔽中方向键
                printToast("get Key KEYCODE_VOLUME_UP(KeyCode:"+keyCode+")");
                break;

            case 220:
            //case KeyEvent.KEYCODE_BRIGHTNESS_DOWN:
                //监控/拦截/屏蔽亮度减键
                printToast("get Key KEYCODE_BRIGHTNESS_DOWN(KeyCode:"+keyCode+")");
                break;

            case 221:
            //case KeyEvent.KEYCODE_BRIGHTNESS_UP:
                //监控/拦截/屏蔽亮度加键
                printToast("get Key KEYCODE_BRIGHTNESS_UP(KeyCode:"+keyCode+")");
                break;


            case KeyEvent.KEYCODE_MEDIA_PLAY:
                printToast("get Key KEYCODE_MEDIA_PLAY(KeyCode:"+keyCode+")");
                break;

            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                printToast("get Key KEYCODE_MEDIA_PAUSE(KeyCode:"+keyCode+")");
                break;

            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                printToast("get Key KEYCODE_MEDIA_PREVIOUS(KeyCode:"+keyCode+")");
                break;

            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                printToast("get Key KEYCODE_MEDIA_PLAY_PAUSE(KeyCode:"+keyCode+")");
                break;

            case KeyEvent.KEYCODE_MEDIA_NEXT:
                printToast("get Key KEYCODE_MEDIA_NEXT(KeyCode:"+keyCode+")");
                break;

            default :
                printToast("keyCode: "+keyCode+" (http://developer.android.com/reference/android/view/KeyEvent.html)");
                break;

        }

        return true;
        //return super.onKeyDown(keyCode, event);
    }

    public void printToast(String str) {
        tv.setText(str);
        Log.i(LOG_TAG, str);
    }

    private final BroadcastReceiver homePressReceiver = new BroadcastReceiver() {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null
                        && reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    // 自己随意控制程序，关闭...
                }
            }
        }

    };
    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            final String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {

                printToast("get Key KEYCODE_POWER(KeyCode:26)");;
                // 退出程序...
            }
        }
    };

    @Override
    protected void onResume() {
        update();
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        pause();
        stop();
        super.onPause();
    }

    private void update() {
        if(mIsChecked)
            start();
    }

    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            state = PAUSE;
        }
    }

    private void start() {
        if (state == STOP) {
            play();
        } else if (state == PAUSE) {
            mMediaPlayer.start();
            state = PLAYING;
        }
    }

    private void stop() {
        mMediaPlayer.stop();
        state = STOP;
    }

    // MediaPlayer进入prepared状态开始播放
    private OnPreparedListener preListener = new OnPreparedListener() {
        public void onPrepared(MediaPlayer arg0) {
            mMediaPlayer.start();
            state = PLAYING;
        }

    };

    private void play() {
        try {
            if (mMediaPlayer == null || state == STOP) {
                // 创建MediaPlayer对象并设置Listener
                mMediaPlayer = MediaPlayer.create(this, R.raw.lapple);  //silence10sec
                mMediaPlayer.setOnPreparedListener(preListener);
                mMediaPlayer.setLooping(true);
            } else {
                // 复用MediaPlayer对象
                mMediaPlayer.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
