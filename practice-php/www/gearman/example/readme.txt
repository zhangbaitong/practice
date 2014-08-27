##install to mac os

安装
brew install gearman

patching file libgearman-1.0/gearman.h
==> ./configure --prefix=/usr/local/Cellar/gearman/1.1.9 --without-mysql
==> make install
==> Caveats
To have launchd start gearman at login:
    ln -sfv /usr/local/opt/gearman/*.plist ~/Library/LaunchAgents
Then to load gearman now:
    launchctl load ~/Library/LaunchAgents/homebrew.mxcl.gearman.plist
Or, if you don't want/need launchctl, you can just run:
    gearmand -d
==> Summary
🍺  /usr/local/Cellar/gearman/1.1.9: 208 files, 2.1M, built in 7.7 minutes


启动
/usr/local/Cellar/gearman/1.1.9/sbin/gearmand -d


查看版本
/usr/local/Cellar/gearman/1.1.9/sbin/gearmand -V


运行时出错，需要日志文件，手动创建
vi /usr/local/Cellar/gearman/1.1.9/var/log/gearmand.log


测试

gearman -w -f myfun -- ls -lh

gearman -f myfun < /dev/null


查看gearman扩展

$ php --info | grep "gearman support"

brew install wget

查看具体的扩展内容

http://pecl.php.net/package/gearman（git://github.com/php/pecl-gearman.git）

wget http://pecl.php.net/get/gearman-1.1.2.tgz

tar xzf gearman-1.1.2.tgz

cd gearman-1.1.2.tgz

phpize

./configure

make

sudo make install

Installing shared extensions:     /usr/lib/php/extensions/no-debug-non-zts-20100525/

sudo vi /etc/php.ini

$ sudo cp /etc/php.ini.default /etc/php.ini
$ sudo vi /etc/php.ini

# Set extension directory
extension_dir = "/usr/lib/php/extensions/no-debug-non-zts-20060613/"
# Add Gearman extension
extension="gearman.so"

$ php --info | egrep -i "(configuration|gearman)"


测试：
$ php worker.php &
$ php client.php
All The World's A Stage
$ jobs
[3]+  Running                 php worker.php &

备选方案：MAMP

资料：
https://github.com/Homebrew/homebrew-php/issues/106
http://ronaldbradford.com/blog/gearman-examples-under-mac-os-x-2010-03-12/
http://stackoverflow.com/questions/9705925/how-to-install-gearman-extension-on-mamp