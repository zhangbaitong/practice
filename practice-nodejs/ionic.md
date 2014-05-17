##资料

http://ionicframework.com/

http://ionicframework.com/docs/guide/

https://angularjs.org/


##安装

1.安装Node

2.安装ionic：npm install -g cordova ionic

3.生成项目(默认为tabs)：ionic start myApp blank｜tabs｜sidemenu

4.运行测试（ios can be android）：

	进入项目目录：
		cd myApp
	设置平台：
		ionic platform ios / ionic platform add ios
	测试：
		ionic build ios(ios默认通过，android需要sdk相关，没做测试)
		ionic emulate ios(提示错误ios-sim was not found)
			安装：npm install -g ios-sim
			再次执行：ionic emulate ios
			出现错误提示：Session could not be started，但模拟器启动，可以看到效果，暂时不做深入
5.开发自己的应用：

	清除默认设置：
		rm www/index.html
		rm www/js/*
		rm www/css/style.css
	配置Cordova（ios才需要）：实际测试，已经对config.xml修改过了，不需要修改
	创建内容：
		创建wwww/index页面
		创建wwww/js/app.js文件
	本地测试：
		cd www
		python -m SimpleHTTPServer 8000
		http://localhost:8000/
	模拟器测试：
		cd myapp
		ionic build ios
		ionic emulate ios
	本地IDE调试（XCode or Android eclipse）
		如果本地文件修改则执行：ionic prepare ios
	安装到手机测试
		ios:platforms/ios/
		android:cordova run android
6.实现具体内容
7.发布
	关闭日志：cordova plugin rm org.apache.cordova.console
	发布到android：
		关闭调试信息：修改platforms/android/AndroidManifest.xml的debuggable="false"
		生成release：cordova build --release android
	具体发布到google paly参考官网即可