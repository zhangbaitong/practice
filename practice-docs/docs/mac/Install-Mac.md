##日常软件

Evernote
	http://www.yinxiang.com/evernote/

QQ
	http://im.qq.com/download/pc.shtml

Flash
	http://get.adobe.com/cn/flashplayer/

xmind
	http://www.xmind.net/download/mac/
	

chrome
	http://www.google.cn/intl/zh-CN/chrome/browser/
	导入书签(使用google账号)
	
Betterzip
	RAR解压缩（Mac自己可以解决tar，zip等格式）
	
iTunes
	设置一下不要拷贝音乐文件进入自己的目录


##开发软件

xcode
	从appstore安装（Git等工具被同时安装）
	
git命令补全
	curl https://raw.github.com/git/git/master/contrib/completion/git-completion.bash -o ~/.git-completion.bash
	vi .bash_profile
	最后一行添加source ~/.git-completion.bash
	
SVN－eclipse
	在线安装https://app.cloudforge.com/subscriptions/new?product=Free

javahl
	http://subclipse.tigris.org/wiki/JavaHL
	通过Homebrew安装
	
homebrew
	http://brew.sh/
	
Eclipse
	http://www.eclipse.org/downloads/
	
	
JDK
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	
Mac里Java几个命令的来源
	java home命令具体目录：/usr/libexec/java_home 
	jdk默认安装目录：/Library/Java/JavaVirtualMachines/jdk1.7.0_40.jdk/Contents/Home
	终端里的java命令(通过ls -la /usr/bin/java查看)
	/System/Library/Frameworks/JavaVM.framework/Versions/Current/Commands
	/System/Library/Frameworks/JavaVM.framework/Versions/A/Commands
	升级后jdk链接位置：/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home

eclipse不能启动的问题
	eclipse的info.plist：/System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Commands/java
	eclipse的config.ini
	同时要修改很多链接
最简单的方式是安装JavaForOSX官方支持
	http://support.apple.com/kb/DL1572

JavaHome设置
	export JAVA_HOME=`/usr/libexec/java_home -v 1.6`
	export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
	
VIM
	vim-pathogen:https://github.com/tpope/vim-pathogen
	NERD Tree：https://github.com/scrooloose/nerdtree

##环境设置

几个常用的环境配置文件
/etc/profile（用户全局）
/etc/bashrc(系统bash全局)
~/.bash_profile（用户bash）
	
.bash_profile
	ll等别名设置
		用户目录下建立文件“.bash_profile”
		alias ll='ls -alF'
		alias la='ls -A'
		alias l='ls -CF’
		source .bash_profile
		
##一些命令的存储位置

	vi:/usr/bin/vi
	rm:/bin/rm
	
##一些零散记录

活动监视器：在application的工具目录下
向后删除键:fn + delete
Boot Camp：Apple Wireless Keyboard 键盘在 Windows 中的映射
http://support.apple.com/kb/HT1171?viewlocale=zh_CN&locale=zh_CN