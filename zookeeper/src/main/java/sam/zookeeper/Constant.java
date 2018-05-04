package sam.zookeeper;

/**
 * Created by ruyzhu on 5/3/2018.
 */
public class Constant {
  public static final String ZK_CONNECTION_URL = "localhost:2181";
  public static final int ZK_SESSION_TIMEOUT = 5000;
  public static final String ZK_REGISTRY_PATH = "/registry";
  public static final String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
}
