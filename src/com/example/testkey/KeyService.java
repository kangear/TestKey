package com.example.testkey;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;

public class KeyService {
	private static final String LOG_TAG = "KeyService";
	private static int IS_NEED_REDIRECT = 0;
	private static final int BIT_BT_CONNECTED = (1 << 0);
	private static final int BIT_SCREEN_OFF = (1 << 1);
	private static final int NEED_REDIRECT = BIT_BT_CONNECTED | BIT_SCREEN_OFF;
	private final Context mContext;

	public KeyService(Context context) {
		this.mContext = context;
	}

	@SuppressLint("NewApi")
	public static boolean isNeedRedirect(Context context) {
		if(context == null) {
			return false;
		}
		PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		if(!powerManager.isScreenOn())
			IS_NEED_REDIRECT |= BIT_SCREEN_OFF;
		else
			IS_NEED_REDIRECT &= ~BIT_SCREEN_OFF;

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		IS_NEED_REDIRECT |= parseBtState(mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET));

		Log.d(LOG_TAG, "IS_NEED_REDIRECT:" + IS_NEED_REDIRECT);
		return IS_NEED_REDIRECT == NEED_REDIRECT;
	}

	public static int parseBtState(int state) {
		Log.i(LOG_TAG, "state:" + state);
		int result = 0;
		switch(state) {
		case BluetoothAdapter.STATE_CONNECTED:
			Log.i(LOG_TAG, "STATE_CONNECTED");
			result |= BIT_BT_CONNECTED;
			break;
		case BluetoothAdapter.STATE_DISCONNECTING:
		case BluetoothAdapter.STATE_DISCONNECTED:
			Log.i(LOG_TAG, "STATE_DISCONNECTING");
			result &= ~BIT_BT_CONNECTED;
			break;
		}
		return result;
	}

	public static String parseKeyCode(int keyCode) {
		String ret = "";
		switch (keyCode) {
		case KeyEvent.KEYCODE_POWER:
			// 监控/拦截/屏蔽电源键 这里拦截不了
			ret = "get Key KEYCODE_POWER(KeyCode:" + keyCode + ")";
			break;
		case KeyEvent.KEYCODE_RIGHT_BRACKET:
			// 监控/拦截/屏蔽返回键
			ret = "get Key KEYCODE_RIGHT_BRACKET";
			break;
		case KeyEvent.KEYCODE_MENU:
			// 监控/拦截菜单键
			ret = "get Key KEYCODE_MENU";
			break;
		case KeyEvent.KEYCODE_HOME:
			// 由于Home键为系统键，此处不能捕获
			ret = "get Key KEYCODE_HOME";
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			// 监控/拦截/屏蔽上方向键
			ret = "get Key KEYCODE_DPAD_UP";
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			// 监控/拦截/屏蔽左方向键
			ret = "get Key KEYCODE_DPAD_LEFT";
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			// 监控/拦截/屏蔽右方向键
			ret = "get Key KEYCODE_DPAD_RIGHT";
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			// 监控/拦截/屏蔽下方向键
			ret = "get Key KEYCODE_DPAD_DOWN";
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
			// 监控/拦截/屏蔽中方向键
			ret = "get Key KEYCODE_DPAD_CENTER";
			break;
		case KeyEvent.FLAG_KEEP_TOUCH_MODE:
			// 监控/拦截/屏蔽长按
			ret = "get Key FLAG_KEEP_TOUCH_MODE";
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			// 监控/拦截/屏蔽下方向键
			ret = "get Key KEYCODE_VOLUME_DOWN(KeyCode:" + keyCode + ")";
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			// 监控/拦截/屏蔽中方向键
			ret = "get Key KEYCODE_VOLUME_UP(KeyCode:" + keyCode + ")";
			break;
		case 220:
			// case KeyEvent.KEYCODE_BRIGHTNESS_DOWN:
			// 监控/拦截/屏蔽亮度减键
			ret = "get Key KEYCODE_BRIGHTNESS_DOWN(KeyCode:" + keyCode + ")";
			break;
		case 221:
			// case KeyEvent.KEYCODE_BRIGHTNESS_UP:
			// 监控/拦截/屏蔽亮度加键
			ret = "get Key KEYCODE_BRIGHTNESS_UP(KeyCode:" + keyCode + ")";
			break;
		case KeyEvent.KEYCODE_MEDIA_PLAY:
			ret = "get Key KEYCODE_MEDIA_PLAY(KeyCode:" + keyCode + ")";
			break;
		case KeyEvent.KEYCODE_MEDIA_PAUSE:
			ret = "get Key KEYCODE_MEDIA_PAUSE(KeyCode:" + keyCode + ")";
			break;
		case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
			ret = "get Key KEYCODE_MEDIA_PREVIOUS(KeyCode:" + keyCode + ")";
			break;
		case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
			ret = "get Key KEYCODE_MEDIA_PLAY_PAUSE(KeyCode:" + keyCode + ")";
			break;
		case KeyEvent.KEYCODE_MEDIA_NEXT:
			ret = "get Key KEYCODE_MEDIA_NEXT(KeyCode:" + keyCode + ")";
			break;
		default:
			ret = "keyCode: "
					+ keyCode
					+ " (http://developer.android.com/reference/android/view/KeyEvent.html)";
			break;
		}
		return ret;
	}

	/**
	 * 获得休眠时间 毫秒
	 */
	public int getScreenOffTime() {
		int screenOffTime = 0;
		try {
			screenOffTime = Settings.System.getInt(mContext.getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT);
		} catch (Exception localException) {

		}
		return screenOffTime;
	}

	/**
	 * 设置休眠时间 毫秒
	 */
	public void setScreenOffTime(int paramInt) {
		try {
			Settings.System.putInt(mContext.getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT, paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}
