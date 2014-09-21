<?php

/**
 * Traits使用样例
 * @autor:zhangtao
 */

require('MyNamespace/Details/User.php');

$user = new \MyNamespace\Details\User();

echo $user->getTName();

require('MyNamespace/Details/Manager.php');

$manager = new \MyNamespace\Details\Manager();

echo $manager->getTName();