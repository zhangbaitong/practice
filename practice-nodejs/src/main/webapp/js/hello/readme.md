https://www.npmjs.org/

npm help json

npm init


Package name: (hello)     //模块名字，npm init会自动取当前目录名作为默认名字，这里不需要改，直接确认即可
Description: A example for write a module    //模块说明
Package version: (0.0.0) 0.0.1    //模块版本号，这个大家按自己习惯来定就可以
Project homepage: (none)     //模块的主页，如果有的话可以填在这里，也可以不填
Project git repository: (none)    //模块的git仓库，选填。npm的用户一般都使用github做为自己的git仓库
Author name: Elmer Zhang    //模块作者名字
Author email: (none) freeboy6716@gmail.com     //模块作者邮箱
Author url: (none) http://www.elmerzhang.com    //模块作者URL
Main module/entry point: (none) hello.js     //模块的入口文件，我们这里是hello.js
Test command: (none)    //测试脚本，选填
What versions of node does it run on? (~v0.5.7) *   //依赖的node版本号，我们这个脚本可以运行在任何版本的node上，因此填 *


返回上级目录测试

npm install hello/

node
var Hello = require('hello').Hello;
Hello('worlds');


发布到NPM

使用npm adduser来注册一个

$ npm adduser
Username: zhangbaitong
Password: 12332113212
Email: zhangbaitong@163.com

回到hello根目录进行发布

npm publish

查看结果：
http://search.npmjs.org/

Latest Updates

