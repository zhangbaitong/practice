<?php

/**
 * 匿名函数使用样例
 * @autor:zhangtao
 */

$tempFunction = function($input){
    echo 'input is ' . $input;
};

$tempFunction('tempFunction');


function check($value){
    echo "check value is " . $value;
}

$names = array("a","b","c");

array_map('check',$names);

array_map(function($value){
    echo "check value is " . $value;
},$names);