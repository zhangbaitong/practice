##资料

http://nodejs.org/

https://github.com/joyent/node

The Node Beginner Book

http://www.nodebeginner.org/

中文社区wiki（学习资源）

https://github.com/cnodejs/nodeclub/wiki

node资源介绍

http://www.nodecloud.org/

nodejs中文学习资料导航

http://blog.lovedan.cn/?p=146

nodejs api中文版

http://nodeapi.ucdok.com/#/api/

infoq对nodejs的介绍

http://www.infoq.com/cn/articles/what-is-nodejs

nodejs中文网（中文api）

http://nodejs.cn/

rainweb前端开发nodejs

http://www.rainweb.cn/article/category/Nodejs

https://github.com/manuelkiessling/NodeBeginnerBook

http://codehenge.net/blog/tag/node-js/

http://blog.osbutler.com/categories/node-by-example/

https://github.com/nswbmw/N-chat

版本号概念

http://semver.org/lang/zh-CN/

图片处理
GraphicsMagick for node
https://github.com/aheckmann/gm

https://github.com/nickewing/line-reader

让浏览器运行node
https://github.com/substack/browserify-website

时间处理
https://github.com/moment/moment

http://substack.net/browserify
    https://github.com/substack/node-browserify
    https://github.com/jmars/jquery-browserify
    https://github.com/visionmedia/jade
    前端的MVC框架
    http://backbonejs.org/
    https://github.com/jashkenas/backbone

    https://github.com/creationix/step
    https://github.com/substack/dnode
    https://github.com/substack/node-seq
    https://github.com/substack/js-traverse


http://www.docker.com/

https://github.com/dotcloud/docker

npm install jsdom

htmlpaser，jsdom,node-jquery

https://github.com/tmpvar/jsdom

https://github.com/coolaj86/node-jquery

npm install request；
jsdom
jQuery
xmlhttprequest
htmlparser

request模块的request(url， callback（error, response, body))
error（错误信息）， response（返回信息）, body（返回的页面内容）；response的statusCode属性的值等于200时表示该网站成功返回
var $ = require(‘jQuery’);
$(body).find(‘#nv’).html();

##install for mac

brew install nodejs

	Downloading http://nodejs.org/dist/v0.10.26/node-v0.10.26.tar.gz
	######################################################################## 100.0%
	==> ./configure --prefix=/usr/local/Cellar/node/0.10.26
	==> make install
	==> Caveats
	Bash completion has been installed to:
	  /usr/local/etc/bash_completion.d
	==> Summary
	🍺  /usr/local/Cellar/node/0.10.26: 1121 files, 16M, built in 13.5 minutes

##install for centOS6

wget "http://nodejs.org/dist/v0.10.28/node-v0.10.28.pkg"

rpm -i node-v0.10.28.pkg

yum repolist

yum repolist | grep epel

rpm -Uvh http://download-i2.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm

sudo yum install nodejs npm --enablerepo=epe

node

node -v

##practice

helloWord:node helloworld.js

http server:node index.js

文件上传：http://localhost:8888/ 或者 http://localhost:8888/start

显示上传文件：http://localhost:8888/show

Post提交（支持中文）：http://localhost:8888/post

http server2:node httpserver/http.js

访问：http://127.0.0.1:3000

express:node app/app.js

访问：http://localhost:3000/

socket io test:node nio/app.js

chatroom : cd chatroom & DEBUG=chatroom ./bin/www

访问：http://localhost:3000/



##外部模块安装（统一安装到index.js同级的node_modules目录下）

npm介绍

https://www.npmjs.org/doc/cli/npm-install.html

https://github.com/npm/npm

查看npm安装列表

	npm ls

	npm install/uninstall(安装过程很容易出现网络问题，实践)

npm install formidable（文件上传模块）（index.js目录下）

	https://www.npmjs.org/package/formidable

npm install express(index.js目录下4.2版本)

	package.json为配置文件，设置依赖，可以在当前目录创建json文件并运行npm install之后当前目录则包含了node_modules目录。

	这里我们只是生成json并没有安装，而是在index.js同级目录安装express然后手动创建app.js进行使用。

	http://expressjs.com/

	https://github.com/visionmedia/express

	http://expressjs.com/guide.html

npm install -g express-generator(安装express代码生成器)

	/usr/local/bin/express -> /usr/local/lib/node_modules/express-generator/bin/express
	express-generator@4.2.0 /usr/local/lib/node_modules/express-generator
	├── mkdirp@0.3.5
	└── commander@1.3.2 (keypress@0.1.0)

 npm -g install supervisor

 	npm config set prefix "路径"

 	supervisor app.js

##使用express创建项目

	express projectName
		install dependencies:
     	$ cd projectName && npm install

   		run the app:
     	$ DEBUG=projectName ./bin/www

 ##socket io

 	http://socket.io/

 	https://github.com/LearnBoost/socket.io

 	https://github.com/LearnBoost/socket.io/wiki/Rooms

 	https://github.com/Wisembly/elephant.io

##node-inspector(debug)

	https://github.com/node-inspector/node-inspector

##async(线程管理工具，支持多模式)

	https://github.com/caolan/async

##node-webkit（windows的exe打包工具）

 	https://github.com/rogerwang/node-webkit

##coffee-script

    安装：
    npm install coffee-script

    运行.coffee文件：
    coffee hello.coffee

    将hello.coffee编译成js：
    coffee -c hello.coffee

    资料：
    http://coffeescript.org/（英文网）
    http://coffee-script.org/（中文网）
    https://github.com/jashkenas/coffeescript

##log

    https://github.com/flatiron/winston/

