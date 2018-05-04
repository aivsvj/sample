package sam.rmi.service;

import sam.rmi.service.HelloService;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ruyzhu on 4/30/2018.
 */
public interface RemoteObjectFactory extends Remote {
  HelloService create() throws RemoteException;
//  String create() throws RemoteException;
  void testPrint() throws RemoteException;
}
