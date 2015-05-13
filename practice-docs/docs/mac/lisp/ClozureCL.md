Clozure CL

http://ccl.clozure.com/


http://ccl.clozure.com/docs/ccl.html

http://trac.clozure.com/ccl/

http://ccl.clozure.com/manual/

http://ccl.clozure.com/docs/ccl.html#using-clozure-cl

http://trac.clozure.com/ccl/wiki/FrequentlyAskedQuestions

Common Lisp实现
http://www.cliki.net/Common+Lisp+Implementation

wiki
http://en.wikipedia.org/wiki/Common_Lisp

两本语言介绍的书籍和规范
http://www.lispworks.com/documentation/common-lisp.html

http://common-lisp.net/

http://zh.wikipedia.org/zh/Common_Lisp


文档收集

http://clojure.org/lisps

SLIME or ILISP

hemlock editor
http://www.cons.org/cmucl/hemlock/index-print.html

http://www.clisp.org/impnotes/


在线书籍
http://www.gigamonkeys.com/book/practical-a-simple-database.html


##使用ST2作为CL的IDE

	http://marcelkornblum.com/2013/05/24/setting-up-the-lisp-repl-in-sublime-text-2-on-os-x/

sbcl解释器安装
	http://www.sbcl.org/platform-table.html
选择平台
	http://www.sbcl.org/getting.html


安装下载的对应平台二进制文件
	bzip2 -cd sbcl-1.1.8-x86-64-darwin-binary.tar.bz2 | tar xvf -
	cd sbcl-1.1.8-x86-64-darwin
	sh install.sh 

	SBCL has been installed:
 		binary /usr/local/bin/sbcl
 		core and contribs in /usr/local/lib/sbcl/
	Documentation:
 		man /usr/local/share/man/man1/sbcl.1

进入
	sbcl
退出
	(quit)

安装SublimeREPL
	https://github.com/wuub/SublimeREPL
	Preferences | Package Control | Package Control: Install Package
	Choose SublimeREPL
SublimeREPL Lisp support
	Preferences/Browse Packages, go to Lisp

	Lisp.sublime-settings

	{
	    "cmd": ["sbcl", "--script", "$file"],
	    "working_dir": "${project_path:${folder}}",
	    "selector": "source.lisp",
	    "osx":
	    {
	        "cmd": ["/opt/local/bin/sbcl", "--script", "$file"]
	    },
	    // exemple in windows with CLISP
	    "windows":
	    {
	        "cmd": ["clisp", "$file"]
	    },
	    // exemple in windows with SBCL
	    // "windows":
	    // {
	    //  "cmd": ["sbcl", "--script", "$file"]
	    // }

		"extensions": ["lisp", "scm", "ss", "cl"]
	}

	Default.sublime-commands

	{
	    "caption": "SublimeREPL: Lisp",
	    "command": "run_existing_window_command", "args":
	    {
	        "id": "repl_lisp",
	        "file": "config/Lisp/Main.sublime-menu"
	    }
	}

	Main.sublime-menu

	[
	     {
	        "id": "tools",
	        "children":
	        [{
	            "caption": "SublimeREPL",
	            "mnemonic": "r",
	            "id": "SublimeREPL",
	            "children":
	            [
	                {"command": "repl_open", 
	                 "caption": "Lisp",
	                 "id": "repl_lisp",
	                 "mnemonic": "q",
	                 "args": {
	                    "type": "subprocess",
	                    "encoding": "utf8",
	                    "cmd": ["sbcl", "-i"],
	                    "cwd": "$file_path",
	                    "external_id": "lisp",
	                    "syntax": "Packages/Lisp/Lisp.tmLanguage"
	                    }
	                }
	            ]   
	        }]
	    }
	]