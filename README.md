Android按键测试程序
---
###物理按键测试
正常安装启动后就可以检测

###蓝牙耳机播放音乐相关按键测试
需要额外播放音乐才能监听

###蓝牙耳机语音通话相关按键测试
其中：

		     VOICE_COMMAND : 语音拨号键
		LAST_NUMBER_REDIAL : 末位重拨
		               ... : ...

说明：

		VOICE_COMMAND 正常安装后就可以监听到
		LAST_NUMBER_REDIAL 需要将该APP放置到system/app目录下放可正常监听到
		比如，当LAST_NUMBER_REDIAL被触发后会出现PHONE应用和TESTKEY争抢启动。
![github](https://github.com/kangear/TestKey/blob/master/apk/device-2015-01-21-152934.png "github") 
监听到 末位重拨
![github](https://github.com/kangear/TestKey/blob/master/apk/last_number_redail.png "github") 
