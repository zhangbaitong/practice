<?php

/**
 * SPL使用样例
 * @autor:zhangtao
 */


function class_loader($class){
    require(str_replace('\\','/',$class) . '.php');
}

spl_autoload_register('class_loader');


$user = new \MyNamespace\Details\User();

echo $user->getName();