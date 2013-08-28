package com.example.testkey;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView)findViewById(R.id.textView);
		
		printToast("开始测试按键！");
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    protected void onUserLeaveHint() {
            //super.onUserLeaveHint();
            System.out.println("onUserLeaveHint");

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {   
	    switch (keyCode) {
	    
	    	case KeyEvent.KEYCODE_RIGHT_BRACKET :
	    		//监控/拦截/屏蔽返回键
	    		printToast("get Key KEYCODE_RIGHT_BRACKET");
	    		break;
	    		
	    	case KeyEvent.KEYCODE_MENU :
	    		//监控/拦截菜单键
	    		printToast("get Key KEYCODE_MENU");
	    		break;
	    		
	    	case KeyEvent.KEYCODE_HOME:
	    		//由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
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
	    		printToast("get Key KEYCODE_VOLUME_DOWN");
	    		break;
	    		
	    	case KeyEvent.KEYCODE_VOLUME_UP:
	    		//监控/拦截/屏蔽中方向键
	    		printToast("get Key KEYCODE_VOLUME_UP");
	    		break;
	    		
	    	case 220:
	    	//case KeyEvent.KEYCODE_BRIGHTNESS_DOWN:
	    		//监控/拦截/屏蔽下方向键
	    		printToast("get Key KEYCODE_BRIGHTNESS_DOWN");
	    		break;
	    		
	    	case 221:	
	    	//case KeyEvent.KEYCODE_BRIGHTNESS_UP:
	    		//监控/拦截/屏蔽中方向键
	    		printToast("get Key KEYCODE_BRIGHTNESS_UP");
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
	}

}
