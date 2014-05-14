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



##外部模块安装（统一安装到index.js同级的node_modules目录下）

npm介绍

https://www.npmjs.org/doc/cli/npm-install.html

npm install formidable（文件上传模块）（index.js目录下）

	https://www.npmjs.org/package/formidable

npm install express(index.js目录下4.2版本)

	package.json为配置文件，设置依赖，可以在当前目录创建json文件并运行npm install之后当前目录则包含了node_modules目录。

	这里我们只是生成json并没有安装，而是在index.js同级目录安装express然后手动创建app.js进行使用。

	http://expressjs.com/

	https://github.com/visionmedia/express

	http://expressjs.com/guide.html

 npm -g install supervisor

 	npm config set prefix "路径"

 	supervisor app.js