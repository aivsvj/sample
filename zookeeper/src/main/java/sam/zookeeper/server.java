package sam.zookeeper;

import sam.rmi.service.RemoteObjectFactory;
import sam.rmi.service.impl.RemoteObjectFactoryImpl;

import java.rmi.RemoteException;

/**
 * Created by ruyzhu on 5/3/2018.
 */
public class server {
  public static void main(String[] args) {
    ServiceProvider provider = new ServiceProvider();
    try {
      RemoteObjectFactory factory = new RemoteObjectFactoryImpl();
      provider.publish(factory, "192.168.91.137", 1694);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}
