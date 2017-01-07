package com.laudandjolynn.test.gearman;

import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobResultImpl;
import org.gearman.common.Constants;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.worker.AbstractGearmanFunction;
import org.gearman.worker.GearmanWorker;
import org.gearman.worker.GearmanWorkerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Laud
 * @date: 2013-2-1 下午5:23:03
 */
public class EchoWorker extends AbstractGearmanFunction {
 private final static Logger log = LoggerFactory.getLogger(EchoWorker.class);

 public static void main(String[] args) {
  GearmanWorker worker = new GearmanWorkerImpl();
  GearmanNIOJobServerConnection conn = new GearmanNIOJobServerConnection(
    "192.168.1.123", Constants.GEARMAN_DEFAULT_TCP_PORT);
  worker.addServer(conn);
  worker.registerFunction(EchoWorker.class);
  log.debug("启动服务...");
  worker.work();
 }

 @Override
 public GearmanJobResult executeFunction() {
  log.debug("调用了worker方法！");
  GearmanJobResult jr = new GearmanJobResultImpl("哈哈".getBytes());
  return jr;
 }
}