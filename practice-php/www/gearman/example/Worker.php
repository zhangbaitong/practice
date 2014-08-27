<?php
$worker= new GearmanWorker();
$worker->addServer();
## $worker->addServer("127.0.0.1", 4730);
$worker->addFunction("title", "title_function");
while (true){
    $worker->work();
    if ($worker->returnCode() != GEARMAN_SUCCESS) {
        //Gearman 状态错误 需要做日志或异常处理
    }
}
function title_function($job)
{
    return ucwords(strtolower($job->workload()));
}
?>