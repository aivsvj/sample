package sam.rmi.client;

import sam.rmi.model.ItemImpl;
import sam.rmi.service.HelloService;
import sam.rmi.service.RemoteObjectFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ruyzhu on 4/30/2018.
 */
public class HelloServiceClient {
  public static void main(String args[]) {
    try {
/*
      Context context = new InitialContext();
      RemoteObjectFactory factory = (RemoteObjectFactory) context.lookup("rmi://192.168.91.137:1694/factory");
*/
      Registry registry = LocateRegistry.getRegistry("192.168.91.137", 1694);
      RemoteObjectFactory factory = (RemoteObjectFactory) registry.lookup("rmi://192.168.91.137:1694/factory");
      factory.testPrint();
      HelloService helloService = factory.create();
      helloService.print("test");
      ItemImpl item = helloService.getItem("1");
      System.out.println(item.getName());
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (NotBoundException e) {
      e.printStackTrace();
    }
  }
}
