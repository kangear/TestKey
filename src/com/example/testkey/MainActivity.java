package com.example.testkey;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("InlinedApi")
public class MainActivity extends Activity {
	private final static String LOG_TAG = "com.example.testkey";
	Context mContext;
	TextView mTextView;
	private CheckBox mCheckBox = null;
	private boolean mIsChecked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(LOG_TAG, "onCreate");
		mContext = this;
		displayMyself(mContext);
		mTextView = (TextView)findViewById(R.id.textView);

		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
		// Power
		registerReceiver(mBatInfoReceiver, filter);
		// Home
		final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(homePressReceiver, homeFilter);

		printToast("开始测试按键！");

		mCheckBox = (CheckBox) this.findViewById(R.id.is_test_bt_checkbox);
		mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mIsChecked = isChecked;
			}
		});

		// for service
		((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(new ComponentName(
				this,
				MusicIntentReceiver.class));

		// check intent
		Intent intent = getIntent();
		if (intent != null) {
			String act = getIntent().getAction();
			if (act != null) {
				if (act.equals(Intent.ACTION_VOICE_COMMAND)) {
					// voice command
					Log.d(LOG_TAG, "VOICE_COMMAND");
					printToast("get Key VOICE_COMMAND");
				} else if (act.equals("android.intent.action.CALL_PRIVILEGED")
						|| act.equals("android.intent.action.KANGEAR_LASTREDIAL_TO_VR")) {
					printToast("LAST_NUMBER_REDIAL(需要重定向:" + KeyService.isNeedRedirect(this) +")");
					if(KeyService.isNeedRedirect(this)) {
						startVoiceDial();
						displayMyself(mContext);
						finish();
					} else {
						printToast("DIAL_CUSTOM_NUMBER");
						dail(intent);
						displayMyself(mContext);
						finish();
					}
				} else {
					showMyself(mContext);
				}
			}
		}
	}

	private void showMyself(Context context) {
		Window window=((Activity) context).getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.alpha=1.0f;
		window.setAttributes(wl);
	}

	private void displayMyself(Context context) {
		Window window=((Activity) context).getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.alpha=0.0f;
		window.setAttributes(wl);
	}

	private void startVoiceDial() {
		Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// Verify that the intent will resolve to an activity
		if (intent.resolveActivity(getPackageManager()) != null) {
		    startActivity(intent);
		} else {
			Log.e(LOG_TAG, "没有发现语音拨号器！");
		}
	}

	private void dail(Intent intent) {
		Intent intent2 = new Intent(Intent.ACTION_CALL, intent.getData());
		// Verify that the intent will resolve to an activity
		if (intent2.resolveActivity(getPackageManager()) != null) {
		    startActivity(intent2);
		} else {
			Log.e(LOG_TAG, "没有发现拨号器！");
		}
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			displayMyself(mContext);
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

		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		printToast(KeyService.parseKeyCode(keyCode));
		return true;
		//return super.onKeyDown(keyCode, event);
	}

	public void printToast(String str) {
		mTextView.setText(str);
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
				printToast("get Key KEYCODE_POWER(KeyCode:26)-OFF");
			} else if(Intent.ACTION_SCREEN_ON.equals(action)){
				printToast("get Key KEYCODE_POWER(KeyCode:26)-NO");
			}
		}
	};
}
