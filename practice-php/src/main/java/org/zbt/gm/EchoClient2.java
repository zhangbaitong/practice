package com.laudandjolynn.test.gearman;

import java.io.IOException;
import java.util.concurrent.Future;

import org.gearman.client.GearmanClientImpl;
import org.gearman.client.GearmanJob;
import org.gearman.client.GearmanJobImpl;
import org.gearman.client.GearmanJobResult;
import org.gearman.common.Constants;
import org.gearman.common.GearmanJobServerConnection;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.util.ByteUtils;

/**
 * @author: Laud
 * @date: 2013-2-1 下午5:12:35
 */
public class EchoClient {
 private final GearmanClientImpl client;

 public EchoClient(String host, int port) throws IOException {
  GearmanJobServerConnection connection = new GearmanNIOJobServerConnection(
    host, port);
  client = new GearmanClientImpl();
  client.addJobServer(connection);
 }

 public String echo(String input) throws IOException {
  byte[] data = ByteUtils.toUTF8Bytes(input);
  byte[] respBytes = client.echo(data);
  return ByteUtils.fromUTF8Bytes(respBytes);
 }

 public String hello() throws IOException {
  String uniqueId = null;
  GearmanJob job = GearmanJobImpl.createJob(
    EchoWorker.class.getCanonicalName(), null, uniqueId);
  Future<GearmanJobResult> f = client.submit(job);
  GearmanJobResult jr = null;
  try {
   jr = f.get();
  } catch (Exception e) {
   e.printStackTrace(); // NOPMD
  }
  return new String(jr.getResults());
 }

 public void shutdown() throws IllegalStateException {
  if (client == null) {
   throw new IllegalStateException("No client to shutdown");
  }
  client.shutdown();
 }

 public static void main(String[] args) {
  String host = "192.168.1.123";
  int port = Constants.GEARMAN_DEFAULT_TCP_PORT;

  try {
   EchoClient ec = new EchoClient(host, port);
//   String value = ec.echo("hello!");
//   System.out.println(value); // NOPMD
   System.out.println(ec.hello());
   ec.shutdown();
  } catch (IOException ioe) {
   ioe.printStackTrace(); // NOPMD
  }
 }
}