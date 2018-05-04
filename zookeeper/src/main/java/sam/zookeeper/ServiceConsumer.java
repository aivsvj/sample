package sam.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ruyzhu on 5/3/2018.
 */
public class ServiceConsumer {

  private List<String> urlList = new ArrayList<String>();

  private CountDownLatch latch = new CountDownLatch(1);

  public ServiceConsumer() {
    watchNode(connectZooKeeper());
  }

  private ZooKeeper connectZooKeeper() {
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

  public <T extends Remote> T lookup() {

    T service = null;

    service = lookupService(urlList.get(0));

    return service;
  }

  private void watchNode(final ZooKeeper zk) {

    try {
      List<String> registry = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
        public void process(WatchedEvent event) {

          if (event.getType() == Event.EventType.NodeChildrenChanged) {
            watchNode(zk);
          }
        }
      });
      for (String node: registry) {
        urlList.add(new String(zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null)));
      }
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private <T extends Remote> T lookupService(String name) {

    T remote = null;
    try {
      remote = (T) Naming.lookup(name);
    } catch (NotBoundException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return remote;
  }
}
