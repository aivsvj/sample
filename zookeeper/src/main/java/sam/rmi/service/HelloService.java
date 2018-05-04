package sam.rmi.service;

import sam.rmi.model.ItemImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ruyzhu on 4/30/2018.
 */
public interface HelloService extends Remote {

  void print(String arg) throws RemoteException;
  ItemImpl getItem(String arg) throws RemoteException;
}
