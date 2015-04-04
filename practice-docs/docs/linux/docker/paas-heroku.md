paas-heroku

HeroKu（Her-oh-koo）
	期初是基于ruby的快速托管框架
	Heroku后来发展为一个支持多种编程语言的云平台即服务
	Debian的Ubuntu
		https://github.com/heroku
	docs
		https://devcenter.heroku.com/
		https://www.heroku.com/
	架构设计介绍
		http://www.oschina.net/question/54100_31619


Paas产品列表

公司太多了，但是真正要在java上做得好做得大的没几个，首先必须符合两点：支持纯git以及纯maven。纯maven比较容易实现，大部分公司都具备这种条件。但是纯git比较难做到，大部分paas公司就压根没实现好，只是让你单纯上传war。一般人会觉得没什么，但潜在的问题是很大的，假如你只是单纯添加一个页面，难道要在要重新打war吧。再仔细想一想，真正用到java的时候那个war包就是上几百M的，如果只是几个M的应用，那直接用python或者ruby就行了。所以我很强调后期维护的时候git 起到的重要性。

最有名气的就是Google App Engineer跟Amazon推出的Elastic Beanstalk（弹力豆茎）。此外Amazon还有基于Iaas的服务。可见Amazon是相当叼的云公司了。Google 还被墙，amazon在中国网速也一般，还要求信用卡注册，甚至还收费。一谈到钱就伤心，两大好公司就这样把我排除在门外了。

Salesforce跟 微软的azure也很不错，可惜要收费，试用期仅为30天。

VMware Cloud Foundry不收费，但是要安装2g的Vm虚拟客户端，虚拟机什么的最讨厌了。

Heroku 不错，不超过规定的流量是free的。安装Heroku客户端时候会强制安装ruby，但是git的安装可选。

看我下面注册的云计算公司。

124, amazon（没信用卡）

125. cumulogic （不分配空间）

126.heroku （将就）

127, salesforce （试用期30天，要钱）

128.dotcloud （这货完全使用自带客户端dotcloud，不带git，只能上传war，还不如用sae）

129.appfog （这货完全使用自带客户端af，不带git，只能上传war，还不如用sae）

130. http://bitnami.org （这货就跟amazon勾搭的，还要我填写amazon认证）

131. Jelastic  （这货连客户端都没有，不带git，只能网页上传war，还不如用sae）

132.cloudbees（ 独立客户端bees，跟上面的dotcloud以及appfog不同，很多细节没搞明白）

说到底，只有openshift是王道。

之前介绍过openshift，这次介绍下Heroku。

在2010年的时候，heroku这家旧金山公司已经被salesforce收购了。

主页：http://www.heroku.com/

到github 上面下载sample。详细看docs

安装过程：安装 heroku客户端heroku-toolbelt.exe ，安装，记得选择 customer install模式，这样可以去掉git安装，但是这样他还是会在c盘给你安装一个ruby（即使你的电脑上面已经有ruby了，上一篇文章我们安装redhat client的时候已经安装了ruby），我们可以安装之后再卸载他给我们安装的ruby，安装之后他会给我们设置环境变量，我们可以按照自己的需求自己改环境变量。

这样我们就能使用heroku命令了，在任意一个文件夹下面使用heroku create 命令之后输入用户名密码就可以在本地建立起一个ssh连接认证，同时heroku服务器已经建立起一个app了，我们可以到网页上面去改settings。

改完settings之后，我们得到git库的地址，这样就可以在本地git clone。

接下来的事情就是 git的操作了。

git push 之后，使用heroku open 会在浏览器打开一个网页。

最后加一句，国内的paas连maven都没实现。

增加一段，最近试了 cumulogic，效果很差，体验很差，配置超多，只支持上传war文件。