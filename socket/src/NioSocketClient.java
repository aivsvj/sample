import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioSocketClient {

  public static void start() {

    //

  }

  public static void main(String[] args) {

    // 两个线程, 一个监控用户输入, 一个与服务器通信

    // 输入缓冲区
    ByteBuffer typeBuffer = ByteBuffer.allocate(1024);
    // 输入线程
    Runnable typein = new Runnable(){
      @Override
      public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
          synchronized (typeBuffer) {
            typeBuffer.put(scanner.nextLine().getBytes());
          }
        }
      }
    };
    new Thread(typein).start();

    try {

      ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

      SocketChannel sc = SocketChannel.open();
      sc.configureBlocking(false);
      sc.connect(new InetSocketAddress(InetAddress.getByName("localhost"),1694));

      Selector selector = Selector.open();
      sc.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//      sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

      PrintWriter writer = new PrintWriter(System.out);
      while (true) {


        selector.select();
        Set<SelectionKey> keySet = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keySet.iterator();
        while (iterator.hasNext()) {
          SelectionKey key = iterator.next();
          if (SelectionKey.OP_CONNECT == (SelectionKey.OP_CONNECT & key.readyOps())) {


            sc.
            if (sc.isConnected()) {
              synchronized (typeBuffer) {
                typeBuffer.flip();
                sc.write(typeBuffer);
                typeBuffer.clear();
              }
            }
          } else if (SelectionKey.OP_WRITE == (SelectionKey.OP_WRITE & key.readyOps())) {
            synchronized (typeBuffer) {
              typeBuffer.flip();
              sc.write(typeBuffer);
              typeBuffer.clear();
            }
          } else if (SelectionKey.OP_READ == (SelectionKey.OP_WRITE & key.readyOps())) {
            receiveBuffer.clear();
            sc.write(receiveBuffer);
            receiveBuffer.flip();
            writer.print(receiveBuffer.array());
          }
        }

      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}