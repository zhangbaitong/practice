<?php
require 'flight/Flight.php';
include 'db/db.php';

Flight::set('db', new MyDB('db/cakedb.db'));
Flight::set('sys_title', 'YPX销售系统');
//Global $db;
//$db = new MyDB('db/cakedb.db');

Flight::route('/index', function(){
	//Global $db;
	$db = Flight::get('db');
    showViewsPage('sale',$db->isOK($db));
});


Flight::route('GET /insert', function(){
	$queryData = Flight::request()->query->data;
	//echo $queryData;
	$db = Flight::get('db');
	echo $db->insertDB($queryData);
});


Flight::route('/list', function(){
	$db = Flight::get('db');
    showViewsPage('list',$db->getAllDatas());
});


Flight::route('/template', function(){
    showViewsPage('template','template');
});

Flight::route('/report_sum_day', function(){
	$db = Flight::get('db');
    showViewsPage('report',json_encode($db->getSumByDay()));
});

Flight::route('/report_num_day', function(){
	$db = Flight::get('db');
    showViewsPage('report',json_encode($db->getNumByDay()));
});


Flight::route('/report_num_mult_day', function(){
	$db = Flight::get('db');
    showViewsPage('reportMult',json_encode($db->getNumMultByDay()));
});

Flight::route('/report_sum_mult_day', function(){
	$db = Flight::get('db');
    showViewsPage('reportMult',json_encode($db->getSumMultByDay()));
});

Flight::route('/about', function(){
    showViewsPage('about','关于页面');
});

Flight::route('/admin', function(){
    showViewsPage('admin','系统管理页面');
});

Flight::route('/test', function(){
	 //include 'about.html';
    //include 'info.php';
	//showViews('ddwww');
	showViewsPage('b','bbb');
});

//layout
function showViews($content){
    Flight::render('header', array('header' => Flight::get('sys_title')), 'header_content');
	Flight::render('body', array('body' => $content), 'body_content');
	Flight::render('footer', array('footer' => 'Copyright &copy; 2013 Baitong Zhang<br>Powered by Flight'), 'footer_content');
	Flight::render('layout', array('title' => Flight::get('sys_title')));
};

function showViewsPage($page,$content){
    Flight::render('header', array('header' => Flight::get('sys_title')), 'header_content');
	Flight::render($page, array('body' => $content), 'body_content');
	Flight::render('footer', array('footer' => 'Copyright &copy; 2013 Baitong Zhang<br>Powered by Flight'), 'footer_content');
	Flight::render('layout', array('title' => Flight::get('sys_title')));
};



Flight::start();
?>
