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

监听到 末位重拨 按键：
![github](https://github.com/kangear/TestKey/blob/master/apk/last_number_redail.png "github")


###20150122更
介于国产手机ROM进行了CALL_PRIVILEGED进行了恶意屏蔽导致无法正常使用，所以只有通过更改Bluetooth.apk对「末号重拨」进行重定向。

apk/目录下TestKey.apk/Bluetooth.apk是最新的。

替换系统自带Bluetooth.apk后，这次正常方式安装TestKey.apk就可以了。

正常现象是：当按下「末号重拨」键时，不再进行「末号重拨」，而是调起TestKey.apk.

如果实验正常，那么下一步就是转向到「语音拨号」。

###20150123更 重定向
1.不再监听VOICE_COMMAND且将LAST_NUMBER_REDIAL重定向到VOICE_COMMAND

只更新了TestKey.apk只重新安装它就好。

另：如果你的手机支持VOICE_COMMAND(语音拨号)时直接从睡眠(黑屏)中唤醒，那么就能唤醒。

TestKey.apk目前只启动重定向的功能。

2.重定向后自行关闭

###20150124更 选择性重定向
添加判断条件，当且仅当在BT连接／黑屏(锁屏)状态下进行重定向。

下午：更新源码，解决逻辑错误，已经测试。

###20150205更 添加连接蓝牙后自动将黑屏时间设置为10秒

###20150205更 添加连接蓝牙休眠时间设置为10秒断开设置为120秒
并添加了通知，在Miui中验证过了。

![github](https://github.com/kangear/TestKey/blob/master/apk/device-2015-02-07-105356.png "github")

![github](https://github.com/kangear/TestKey/blob/master/apk/device-2015-02-07-105501.png "github") 
