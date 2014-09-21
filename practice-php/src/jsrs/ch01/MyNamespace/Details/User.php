<?php

/**
 * Class User一个普通的用户类
 * @autor:zhangtao
 */

namespace MyNamespace\Details;

include_once('MyTraits.php');

class User {
    private $name = 'user';

    function __construct(){
        echo 'I am construct magic method';
    }

    function getName() {
        return $this->name;
    }

    function __get($value){
        return 'sorry,I can not find ' . $value . '!';
    }

    function __call($method,$arguments){
        return 'sorry,I can not find ' . $method . '!';
    }

    use myTrait;
}

/**
 *Test
    $user = new User();
    echo $user->getName();
 */