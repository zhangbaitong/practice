<?php

/**
 * 命名空间使用样例
 * @autor:zhangtao
 */

require('MyNamespace/Details/User.php');

$user = new \MyNamespace\Details\User();
echo $user->getName();