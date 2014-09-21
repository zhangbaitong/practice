<?php

/**
 * 异常使用样例
 * @autor:zhangtao
 */



function my_ex_handler(Exception $e){
    echo 'Yes , it is exception : ' . $e;
}

set_exception_handler('my_ex_handler');

function my_error_handler($error_level,$error_message){
    echo 'Yes , it is error!';
}

set_error_handler('my_error_handler',E_ALL);


$testFile = fopen('test.md','r');
