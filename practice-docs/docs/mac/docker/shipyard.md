shipyard
		https://github.com/shipyard/shipyard
	依赖
		https://github.com/citadel/citadel
	docs
		http://shipyard-project.com/
	中文文档
		http://www.dockerpool.com/static/books/shipyard_doc/index.html


快速入门
	启动一个RethinkDB数据卷实例
		docker run -it -d --name shipyard-rethinkdb-data --entrypoint /bin/bash shipyard/rethinkdb -l
	启动RethinkDB使用钢材的数据卷容器
		docker run -it -P -d --name shipyard-rethinkdb --volumes-from shipyard-rethinkdb-data shipyard/rethinkdb
	启动Shipyard的controller
		docker run -it -p 8089:8080 -d --name shipyard --link shipyard-rethinkdb:rethinkdb shipyard/shipyard
	使用Shipyard的CLI进行交互
		docker run --rm -it shipyard/shipyard-cli
		然后自动进入shell
		查看命令使用
			shipyard
		查看帮助
			shipyard help
		登陆
			web
				http://192.168.0.30:8089
				admin / shipyard
			cli
				shipyard login
				http://192.168.0.30:8080

注：这里需要先查看容器（运行controller的container）的IP，然后才能访问cli的login

Components
	Controller
		使用RethinkDB进行数据存储
		提供API和web接口
		使用Citadel进行各个host的通信并管理cluster的events
	API
		跟shipyard交互的所有接口定义
		比如启动，停止，查看容器状态，添加，删除引擎等
		RESTful JSON based API
	CLI
		Shipyard cluster和docker command line接口
	UI
		Controller提供的基于AngularJS的web接口
		它使用Shipyard API提供的交互能力