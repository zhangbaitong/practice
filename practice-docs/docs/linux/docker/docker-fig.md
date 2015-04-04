docker-fig
	https://github.com/docker/fig
	简单配置多容器组合使用

	使用 CoreOS 和 Fig 构建基于 Docker 的复杂应用
	http://www.21ops.com/linux/18349.html

	https://github.com/zhangbaitong/docker_practice/blob/master/fig/install.md

	https://github.com/zhangbaitong/docker_practice/blob/master/fig/intro.md
安装
	curl -L https://github.com/docker/fig/releases/download/1.0.1/fig-`uname -s`-`uname -m` > /usr/local/bin/fig; chmod +x /usr/local/bin/fig
	or
	Python
	sudo pip install -U fig
使用
	查看版本
		fig --version
	启动
		fig up
		fig up -d(后端启动)
		fig ps(查看进程)
	停止
		fig stop
	其他使用
		fig --help
	查看环境变量
		fig run web env


注意事项：
fig ps必须在包含有fig.yml的目录




