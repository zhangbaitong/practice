<?php

/**
 * 类自动加载使用样例
 * @autor:zhangtao
 */


function __autoload($class) {
    //Need:MyNamespace/User.php
    //Input:MyNamespace\User
    require(str_replace('\\','/',$class) . '.php');
}


$user = new \MyNamespace\Details\User();

echo $user->getName();