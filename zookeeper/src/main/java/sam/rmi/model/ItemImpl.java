package sam.rmi.model;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by ruyzhu on 5/3/2018.
 */
public class ItemImpl implements Item, Serializable {
  private String id;
  private String name;
  private String price;
  private String amount;

  public ItemImpl() throws RemoteException {
  }

  public String getId() throws RemoteException {
    return id;
  }

  public void setId(String id) throws RemoteException {
    this.id = id;
  }

  public String getName() throws RemoteException {
    return name;
  }

  public void setName(String name) throws RemoteException {
    this.name = name;
  }

  public String getPrice() throws RemoteException {
    return price;
  }

  public void setPrice(String price) throws RemoteException {
    this.price = price;
  }

  public String getAmount() throws RemoteException {
    return amount;
  }

  public void setAmount(String amount) throws RemoteException {
    this.amount = amount;
  }
}
