import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSocketServer {

  public static void main(String[] args) {

    start();
//    System.out.println(1 << 2);
/*
    try {
//      InetAddress inetAddress = InetAddress.getByName("192.168.91.137");
      InetAddress inetAddress = InetAddress.getByName("localhost");
      System.out.println(inetAddress.getHostAddress());
      System.out.println(inetAddress.getAddress());
      System.out.println(inetAddress.isReachable(5000));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
*/

  }

  static void start() {

    int port = 1694;

    ServerSocketChannel ssc;
    try {
      ssc = ServerSocketChannel.open();
      ssc.socket().bind(new InetSocketAddress(InetAddress.getByName("192.168.91.137"), port));
      ssc.configureBlocking(false);
      Selector selector = Selector.open();
      ssc.register(selector, SelectionKey.OP_ACCEPT);

      System.out.println("ssc: " + ssc);
      while (true) {
        selector.select();
        Set<SelectionKey> keySet = selector.selectedKeys();

        Iterator<SelectionKey> iterator = keySet.iterator();
        SelectionKey key;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (iterator.hasNext()) {
          key = iterator.next();

          if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
            ServerSocketChannel ssc1 = (ServerSocketChannel) key.channel();
            System.out.println("ssc1: " + ssc1);
            SocketChannel sc = ssc1.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);

            iterator.remove();
          } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {

            SocketChannel sc = (SocketChannel) key.channel();

            System.out.print("port: " + sc.socket().getPort());
            System.out.print("Thread: " + Thread.currentThread());
            buffer.clear();
            while (0 < sc.read(buffer)) {
              buffer.flip();
              sc.write(buffer);
              buffer.clear();
            }
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}