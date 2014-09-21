<?php

/**
 * Traits定义类
 * @autor:zhangtao
 */

namespace MyNamespace\Details;

trait myTrait {

    public $traitName = 'myTrait';

    function getTName(){
        return $this->traitName;
    }
}