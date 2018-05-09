package sam.rmi.server;


import sam.rmi.service.RemoteObjectFactory;
import sam.rmi.service.impl.RemoteObjectFactoryImpl;

import javax.naming.NamingException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) throws NamingException, RemoteException, NotBoundException, AlreadyBoundException {
    System.out.println("Hello World!");

//    Context context = null;
    Registry registry = LocateRegistry.createRegistry(1694);
    RemoteObjectFactory factory = new RemoteObjectFactoryImpl();
//    registry.bind("rmi://192.168.91.137:1694/factory", factory);
    registry.bind("rmi://192.168.91.137:1694/factory", new RemoteObjectFactoryImpl());

/*
      context = new InitialContext();
      context.bind("rmi://192.168.91.137/factory", new RemoteObjectFactoryImpl());
      context.close();
*/
  }
}
