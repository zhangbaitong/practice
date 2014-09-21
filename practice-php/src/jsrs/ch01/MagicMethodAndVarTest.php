<?php

/**
 * 魔法函数和魔法变量使用样例
 * @autor:zhangtao
 */


require('MyNamespace/Details/User.php');

$user = new \MyNamespace\Details\User();

echo __NAMESPACE__;

echo $user->age;

echo $user->hello();