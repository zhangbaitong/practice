##Node实现Websocket

##准备工作

	版本检查

	node -v

	npm -v

##node-websocket-server

	github: https://github.com/miksago/node-websocket-server

	注：基于Node底层API,不兼容性强，长期没有更新.不支持websocket的draft-10

	npm install websocket-server

	appserver1.js

	appclient.html

##WebSocket-Node

	github: https://github.com/Worlize/WebSocket-Node

	注：WebSocket-Node，简单库，支持draft-10和之前版本

	npm install websocket

	appserver2.js

	appclient.html

	appserver21.js

	appserver21-client.js


##faye-websocket-node

	github: https://github.com/faye/faye-websocket-node

	注：扩展faye项目的websocket实现

	npm install faye-websocket

	appserver3.js

	appclient.html

##socket.io

	github: https://github.com/LearnBoost/socket.io

	express -e nodejs-socketio

	cd nodejs-socketio && npm install

	npm install socket.io

	appserver3.js

	appserver3-client.html(直接通过浏览器访问根目录即可)

##ws

	github: http://einaros.github.io/ws/

	note:a node.js websocket implementation

	npm install ws

	appserver4.js

	appserver4-client.js

##websocket-server(另外一个例子)

	package.json

	npm install -d

	server.js

	client.html

	注：由于这个库长久没有更新，已经不适用现有的node版本，所以不能运行，仅供参考

##一些测试结果（仅供参考）


websocket(HTML5)
	三星手机的微信浏览器：no
	三星手机的UC浏览器：yes（1s）
	三星手机的互联网浏览器：no
	三星手机的欧朋浏览器：no
	三星手机的QQ浏览器：yes(150ms)

	HTC手机的UC浏览器：no

	华为手机的QQ浏览器：yes(1s)
	华为手机的浏览器：no
	华为手机的baidu浏览器：no
	华为手机的360浏览器：no
	华为手机的xunlei浏览器：no

	微信浏览器：no

socketio(1.0.6)
	三星手机的微信浏览器：yes（1s）
	三星手机的UC浏览器：no
	三星手机的互联网浏览器：yes（900ms）
	三星手机的欧朋浏览器：yes（300ms）
	三星手机的QQ浏览器：yes(1s)


	HTC手机的UC浏览器：yes（1s）


	华为手机的QQ浏览器：yes(1.7s)
	华为手机的浏览器：yes(1s)
	华为手机的baidu浏览器：yes(800ms)
	华为手机的360浏览器：yes(1s)
	华为手机的xunlei浏览器：yes(1s)


	微信浏览器：yes
