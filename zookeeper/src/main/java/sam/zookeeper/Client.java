package sam.zookeeper;

import sam.rmi.model.ItemImpl;
import sam.rmi.service.HelloService;
import sam.rmi.service.RemoteObjectFactory;

import java.rmi.RemoteException;

/**
 * Created by ruyzhu on 5/4/2018.
 */
public class Client {

  public static void main(String[] args) {

    ServiceConsumer consumer = new ServiceConsumer();
    RemoteObjectFactory factory = consumer.lookup();
    try {
      HelloService service = factory.create();
      ItemImpl item = service.getItem("1");
      System.out.print(item.getName());
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}