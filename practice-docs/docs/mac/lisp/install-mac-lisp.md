install-mac-lisp.md

安装shell下的sbcl的repl

代码仓库
/Users/zhangtao/Documents/repository/lisp/src
配置文件
/Users/zhangtao/.config/common-lisp/source-registry.conf.d
quicklisp修改的sbcl配置
/Users/zhangtao/.sbclrc
quicklisp目录
/Users/zhangtao/quicklisp


项目构建工具-ASDF（Another System Definition Facility）
	ASDF Manual
	http://common-lisp.net/project/asdf/asdf.html
	Getting started with ASDF
	http://blog.csdn.net/xiaojianpitt/article/details/7727152
	asdf install
	http://common-lisp.net/project/asdf-install/tutorial/

ASDF用来描述Lisp源文件之间的相互依赖关系，进而保证程序的各个部分能够按照正确的顺序进行编译、加载、测试等等。可以近似地把ASDF看作编写C语言程序时所用到的GNU Make工具和Java的Ant工具。ASDF最新已经发布了ASDF 2，ASDF 2指的是2.000及以后的发布版本；1.656到1.728之间的发布版本是ASDF 2的开发版；ASDF 1指的是1.369之前的版本。

许多Lisp实现都包含了ASDF,可以使用Common Lisp的require函数加载ASDF模块

加载asdf模块：
	(require :asdf)
	(require 'asdf)
	(require "asdf")
没有包含默认模块的情况：
	(load "/path/to/asdf.lisp")
查看asdf模块：
	(asdf:asdf-version)
	注：如果引发一个异常，那么要么ASDF没有加载成功，要么正在使用的是ASDF 1的较旧的版本
查看asdf版本是否最新的方法：
	(or #+asdf2 (asdf:asdf-version) #+asdf :old)
	如果返回字符串说明是最新版本的版本号，如果放那会:OLD说明是过时版本，如果返回NIL说明没有安装asdf。
升级asdf的方法（有一种旧风格的升级方法，这里不提）：
     (require "asdf")
     (asdf:load-system :asdf) 
配置asdf
	基本思路是告诉asdf到那个目录去搜寻common lisp软件。
	一般默认用户安装Common Lisp软件的目录是~/.local/share/common-lisp/source/。
    如果要添加一个其它目录的话，需要创建目录~/.config/common-lisp/source-registry.conf.d/，然后在此目录添加配置文件，文件名可以任意，这里我创建文件10-practice-lisp.conf:
    指定目录树结构：
		(:tree "/Users/zhangtao/Documents/repository/lisp/") 
	指定文件夹	
		(:directory "/home/zhuchunlite/Leslie-Chu/Lisp/projects-example/")
	这个配置文件文件名是22-projects-example.conf，习惯把配置文件名以两个数据开头，然后以目录名结尾。
清除并重新读取配置文件：
	(asdf:clear-source-registry) 
创建musicdb.asd
	为这个“软件”musicdb.lisp定义的.asd文件:
	;避免包之间彼此冲突
	(defpackage :musicdb-system
	                (:use :cl :asdf))
	              
	(in-package :musicdb-system)
	;定义musicdb
	(defsystem "musicdb"
	       :description "musicdb: a sample Lisp system."
	       :version "0.2.1"
	       :author "Leslie Chu <pythonisland@gmail.com>"
	       :licence "Public Domain"
	       :components ((:file "musicdb")
	                             (:file "packages" :depends-on ("musicdb"))))
创建packages.lisp文件
	(in-package musicdb)

通过asdf加载软件的方法：
	* (asdf:load-system "musicdb")
	; Loading system definition from
	; /home/zhuchunlite/Leslie-Chu/Lisp/projects-example/musicdb/musicdb.asd into
	; #<PACKAGE "ASDF0">
	; Registering #<SYSTEM "musicdb">
	T 
解释
	先找到对应的asd文件
	然后根据这个文件的定义找到lisp文件
	/Users/zhangtao/.cache/common-lisp/sbcl-1.1.8.0-19cda10-macosx-x64/Users/zhangtao/Documents/repository/lisp/src/musicdb/ASDF-TMP-musicdb.fasl
	/Users/zhangtao/.cache/common-lisp/sbcl-1.1.8.0-19cda10-macosx-x64/Users/zhangtao/Documents/repository/lisp/src/musicdb/ASDF-TMP-packages.fasl
	最后加载packages.lisp
	加载musicdb包后，文件加载完毕，回到cl包
	(in-package musicdb)
	调用就可以了

asdf的编译和加载细节
	编译
		(asdf:compile-system :spider)
	加载
		(asdf:load-system :spider)
	使用asdf的operate对包编译和加载
	编译
		(asdf:oos 'asdf:compile-op :spider)
	加载
		(asdf:oos 'asdf:load-op :spider)
	注：对于一个未经编译完成的包在编译完成会自动加载，同样，未编译完成的包在加载时也会自动先进行编译
asdf包的安装
	使用asdf-install
	http://common-lisp.net/project/asdf-install/tutorial/
加载asdf-install
	(asdf:oos 'asdf:load-op :asdf-install)
通过包的名字进行安装
	(asdf-install:install "iolib")
	这时asdf-install会自动从cliki.net上下载可用的包并安装，http://www.cliki.net/asdf-install这个页面列出了在线可用的软件包的列表。
通过包的url进行安装
	(asdf-install:install "http://weitz.de/files/cl-ppcre.tar.gz")
通过包的本地路径进行安装
	(asdf-install:install “/home/levin/lisp/iolib.tar.gz”)

项目构建工具2-MK-DEFSYSTEM
	http://www.cliki.net/mk-defsystem
	没有asdf流行
	有些cl实现有它们自己的系统构建支持

cl-launch
	http://www.cliki.net/cl-launch

	cl-launch '(+ 3 5)'
	8

	cl-launch --file hello.lisp
	120

	执行一个文件
	把一个lisp源文件转成 shell
	cl-launch --file hello.lisp --output hello.sh



Lisp依赖包管理工具-Quicklisp
	http://www.quicklisp.org/beta/index.html

Quicklisp是Common Lisp的库管理工具。按照其官方介绍:Quicklisp支持数十种主流的CL实现，并且提供超过700个包（库）的下载，安装，管理和加载。个人认为Quickliap类似于ubuntu的apt工具或者java的maven工具。实现（库）包的依赖管理。


下载安装文件
	wget "http://beta.quicklisp.org/quicklisp.lisp"
加载quicklisp
	sbcl
	(load "quicklisp.lisp")
	or
	sbcl --load quicklisp.lisp
安装
	(quicklisp-quickstart:install)
查看安装选项
	(quicklisp-quickstart:help)
添加到sbcl启动文件中自动加载quicklisp
	(ql:add-to-init-file)
	最终修改的是：#P"/Users/zhangtao/.sbclrc"
	#-quicklisp
  	(let ((quicklisp-init (merge-pathnames "quicklisp/setup.lisp"
                                         (user-homedir-pathname))))
    	(when (probe-file quicklisp-init)
      	(load quicklisp-init)))
查看更多信息
	http://www.quicklisp.org/beta/
搜索软件系统
	(ql:system-apropos "term")
安装软件系统
	(ql:quickload "system-name")
卸载软件系统
	(ql-dist:uninstall (ql-dist:release "cl-growl"))
升级所有安装了的lisp库
	(ql:update-all-dists)
升级quicklisp本身
	(ql:update-client)


安装一个库：
(ql:system-apropos "vecto")
(ql:quickload "vecto")

http://www.xach.com/lisp/vecto/

附送一个我的两个小工具宏： loadlib  和 listlib ，可以方便使用 asdf
(require 'asdf)

(defmacro loadlib (name)
  `(asdf:oos 'asdf:load-op ,name))

(defmacro listlib (name)
  `(do-external-symbols (s ,name) (print s)))
(defun load-modules (&rest modules)
  (dolist (m modules) (loadlib m)))



【6】https://news.ycombinator.com/item?id=3809098

      6、安装图形开发库ltk：(ql:quickload "ltk")

------------------------------------------------------------------------------------------

####Fedora 15环境下安装Common Lisp

*sbcl，比较流行的一种common lisp实现；
*emacs，用作开发环境，虽然比较复杂，但是比原来的命令行好用多了；
*slime，emas下的一个交互式lisp开发环境；
*quicklisp，common lisp包管理系统。

1. 先用yum安装emacs和sbcl。
2. 然后按如下步骤安装quicklisp。
下载quicklisp安装脚本：
wget http://beta.quicklisp.org/quicklisp.lisp
启动sbcl：
sbcl –load quicklisp.lisp
安装quicklisp：
(quicklisp-quickstart:install)
添加quicklisp到启动文件，以后启动sbcl会自动加载quicklisp
(ql:add-to-init-file)
3. 用quicklisp安装slime
(ql:quickload "quicklisp-slime-helper")
安装脚本执行完之后会给出一段elisp代码，按照说明把代码加入.emacs文件。
代码示例：
(load (expand-file-name "~/quicklisp/slime-helper.el"))
;; Replace "sbcl" with the path to your implementation
(setq inferior-lisp-program "sbcl")
改为==>
(setq inferior-lisp-program "/usr/bin/sbcl --noinform")
("quicklisp-slime-helper")
4. 启动emacs，M-x slime启动slime，进入交互式cl编程环境，大功告成！
如果还嫌麻烦，直接用Lispbox。



