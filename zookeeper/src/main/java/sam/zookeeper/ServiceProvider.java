package sam.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ruyzhu on 5/3/2018.
 */
public class ServiceProvider {

  private CountDownLatch latch = new CountDownLatch(1);
  public void publish(Remote remote, String host, int port) {
    createNode(connectZooKeeper(), publishSevice(remote, host, port));
  }

  private String publishSevice(Remote remote, String host, int port) {
    String url = null;
    try {
      url = String.format("rmi://%s:%d/%s", host, port, remote.getClass().getName());
      Registry registry = LocateRegistry.createRegistry(port);
      Naming.rebind(url, remote);
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }

  private ZooKeeper connectZooKeeper(){
    ZooKeeper zk = null;
    try {
      zk = new ZooKeeper(Constant.ZK_CONNECTION_URL, Constant.ZK_SESSION_TIMEOUT,
          new Watcher() {
            public void process(WatchedEvent event) {
              if (event.getState() == Event.KeeperState.SyncConnected) {
                latch.countDown();
              }
            }
          });
      latch.await();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return zk;
  }

  private void createNode(ZooKeeper zk, String url) {

    try {
      zk.create(Constant.ZK_PROVIDER_PATH, url.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
