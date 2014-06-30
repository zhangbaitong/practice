<?php
   //http://yourdomain/ypx/db/db.php
   class MyDB extends SQLite3{

      function __construct($dbPath){
         $this->open($dbPath);
      }

      function isOK($db){
         if(!$db){
            return $db->lastErrorMsg();
         } else {
            return "OK\n";
         };
      }

      function action($sql){
         $ret = $this->exec($sql);
         $msg = "true";
         if(!$ret){
            $msg =  $this->lastErrorMsg();
         };
         $this->close();
         return $msg;
      }

      function get($sql){
         $ret = $this->query($sql);
         $datas = array();
         while($row= $ret->fetchArray(SQLITE3_ASSOC) ){
            $data = array();//array("a" => "1" , "b" => "2");
            $data['ID'] = $row['ID'];
            $data['NAME'] = $row['NAME'];
            $data['SUM'] = $row['SUM'];
            $data['NUM'] = $row['NUM'];
            $data['DATETIME'] = $row['DATETIME'];
            $datas[] = $data;
         }
         $this->close();
         return $datas;
      }

      function getSpecial($col1,$col2,$sql){
         $ret = $this->query($sql);
         $datas = array();
         while($row= $ret->fetchArray(SQLITE3_ASSOC) ){
            $data = array();//array("a" => "1" , "b" => "2");
            $data[$col1] = $row[$col1];
            $data[$col2] = $row[$col2];
            $datas[] = $data;
         }
         $this->close();
         return $datas;
      }

      function getSpecialMult($sql){
         $ret = $this->query($sql);
         $datas = array();
         while($row= $ret->fetchArray(SQLITE3_ASSOC) ){
            $data = array();//array("a" => "1" , "b" => "2");
            $data['xisha'] = $row['xisha'];
            $data['chashao'] = $row['chashao'];
            $data['huotui'] = $row['huotui'];
            $data['dayday'] = $row['dayday'];
            $datas[] = $data;
         }
         $this->close();
         return $datas;
      }

      //接收json数据格式进行存储
      function insertDB($queryData){
         //echo $queryData;
         $data = json_decode($queryData);
         //eg. [{"name":"00001","num":"11","sum":"111"},{"name":"00002","num":"11","sum":"111"},{"name":"00003","num":"11","sum":"111"}]
         //http://localhost/~zhangtao/ypx/insert?data=json
         //var_dump($data);
         $insertSQL = "";
         foreach($data as $cake){
            //select datetime(CURRENT_TIMESTAMP,'localtime');
            //datetime('now')
            $insertSQL .= "INSERT INTO cake (NAME,NUM,SUM,DATETIME) VALUES (" . $cake->name . "," . $cake->num . "," . $cake->sum . ",datetime(CURRENT_TIMESTAMP,'localtime'));";
            //echo $insertSQL;
         };
         return $this->action($insertSQL);
         // for($i=0;$i<count($data);$i++){
         //    $insertSQL = "INSERT INTO cake (NAME,NUM,SUM,DATETIME) VALUES (" . $data[$i]['name'] . "," . $data[$i]['num'] . "," . $data[$i]['sum'] . ",datetime('now'));";
         //    echo $insertSQL;
         //    $db->action($insertSQL);
         // };
      }

      function getAllDatas(){
         return $this->get('select * from cake');
      }

      function getSumByDay(){
         return $this->getSpecial('sumday','dayday','select sum(sum) as sumday,substr(datetime,1,10) as dayday from cake group by substr(datetime,1,10);');
      }

      function getNumByDay(){
         return $this->getSpecial('numday','dayday','select sum(num) as numday,substr(datetime,1,10) as dayday from cake group by substr(datetime,1,10);');
      }

      function getNumMultByDay(){
         return $this->getSpecialMult("select xisha,chashao,huotui,a.dayday from (select sum(num) as xisha ,substr(datetime,1,10) as dayday from cake where name = '1' group by substr(datetime,1,10)) as a ,(select sum(num) as chashao,substr(datetime,1,10) as dayday from cake where name = '2' group by substr(datetime,1,10)) as b ,(select sum(num) as huotui,substr(datetime,1,10) as dayday from cake where name = '3' group by substr(datetime,1,10)) as c where a.dayday = b.dayday and b.dayday = c.dayday;");
      }

      function getSumMultByDay(){
         return $this->getSpecialMult("select xisha,chashao,huotui,a.dayday from (select sum(sum) as xisha ,substr(datetime,1,10) as dayday from cake where name = '1' group by substr(datetime,1,10)) as a ,(select sum(sum) as chashao,substr(datetime,1,10) as dayday from cake where name = '2' group by substr(datetime,1,10)) as b ,(select sum(sum) as huotui,substr(datetime,1,10) as dayday from cake where name = '3' group by substr(datetime,1,10)) as c where a.dayday = b.dayday and b.dayday = c.dayday;");
      }

      function testInsert(){
         $sql =<<<EOF
      INSERT INTO cake (NAME,NUM,SUM,DATETIME) VALUES ('name',1,15,datetime('now'));
EOF;
         $ret = $this->exec($sql);
         if(!$ret){
            echo $this->lastErrorMsg();
         } else {
            echo "Test Insert OK\n";
         }
         $this->close();
      }

      function insertCake(){
         echo "insert method";
      }

      function testALLData(){
         $ret = $this->query('select * from cake;');
         while($row = $ret->fetchArray(SQLITE3_ASSOC) ){
            echo "ID = ". $row['ID'] . "\n";
            echo "NAME = ". $row['NAME'] ."\n";
            echo "NUM = ". $row['NUM'] ."\n";
            echo "SUM =  ".$row['SUM'] ."\n";
            echo "DATETIME =  ".$row['DATETIME'] ."<br>/";
         }
         echo "Operation done successfully\n";
         $this->close();
      }
   };

   //$db = new MyDB('cakedb.db');

   //$db->testALLData();

   //echo $db->isOK($db);

   //$db->testInsert();


