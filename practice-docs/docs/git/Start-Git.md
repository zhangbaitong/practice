## 使用Git
 
### 使用Git从github开始
 
github:https://github.com/
 
设置自己的github博客：http://zhangbaitong.github.io/
 
建立自己的github代码库：https://github.com/zhangbaitong/practice
 
### 本地使用Git与github交互
 
安装Git(Mac默认安装)

设置Git用户和邮件：

    git config --global user.name "yourname"
    
    git config --global user.email yourname@xx.com

本地使用代码库：

获取仓库代码到本地：git clone https://github.com/zhangbaitong/practice.git
 
更新仓库代码到本地：git pull origin master
 
提交本地代码到仓库：

    git add .
    
    git commit -m "your message"
    
    git push origin master
 
本地代码重命名提交：git add . -A

EGit学习资料：

http://eclipsesource.com/blogs/tutorials/egit-tutorial/

http://wiki.eclipse.org/EGit/User_Guide

http://www.vogella.com/tutorials/EclipseGit/article.html

http://eclipsesource.com/blogs/2012/08/28/tips-and-tricks-using-eclipse-with-github/
