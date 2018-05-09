package sam.rmi.service.impl;

import sam.rmi.model.ItemImpl;
import sam.rmi.service.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by ruyzhu on 4/30/2018.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

  public HelloServiceImpl() throws RemoteException {
  }

  public void print(String arg) {

    System.out.println("print remote arg: " + arg);
  }

  public ItemImpl getItem(String arg) throws RemoteException {
    ItemImpl item = new ItemImpl();
    item.setId("1");
    item.setName("1");
    return item;
  }
}
