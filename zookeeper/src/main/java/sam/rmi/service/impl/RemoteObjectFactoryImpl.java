package sam.rmi.service.impl;

import sam.rmi.service.HelloService;
import sam.rmi.service.RemoteObjectFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by ruyzhu on 4/30/2018.
 */
public class RemoteObjectFactoryImpl extends UnicastRemoteObject implements RemoteObjectFactory {

  public RemoteObjectFactoryImpl() throws RemoteException {
  }

  public HelloService create() throws RemoteException {
//  public String create() throws RemoteException {
    HelloService service = new HelloServiceImpl();
    return service;
//    return "service";
  }

  public void testPrint() throws RemoteException {
    System.out.println("test");
//    return "test";
  }
}
