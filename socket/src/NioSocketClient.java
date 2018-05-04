import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

    // 两个线程, 一个监控用户输入并发送到服务器, 一个接收服务器返回

    ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

    try {
      SocketChannel sc = SocketChannel.open();
      sc.configureBlocking(false);
      System.setProperty("socksProxyHost", "191.168.91.11");
      System.setProperty("socksProxyPort", "80");

      sc.connect(new InetSocketAddress(InetAddress.getByName("192.168.91.137"), 1694));
      if (sc.isConnectionPending()) {
        sc.finishConnect();
      }
      Selector selector = Selector.open();
      SelectionKey key = sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT);
      selector.wakeup();
      // 输入缓冲区
      ByteBuffer typeBuffer = ByteBuffer.allocate(1024);
      // 输入线程
      Runnable typein = new Runnable() {
        @Override
        public void run() {
          Scanner scanner = new Scanner(System.in);
          while (scanner.hasNextLine()) {
            synchronized (typeBuffer) {
              typeBuffer.put(scanner.nextLine().getBytes());
              typeBuffer.flip();
              try {
                sc.write(typeBuffer);
              } catch (IOException e) {
                e.printStackTrace();
              }
              if (new String(typeBuffer.array(), 0 , typeBuffer.limit()).equals("exit")) {

                break;
              }
              typeBuffer.clear();
            }
          }
        }
      };
      new Thread(typein).start();

      PrintWriter writer = new PrintWriter(System.out);

      while (true) {

        while (0 < selector.select()) {
          Set<SelectionKey> keySet = selector.selectedKeys();
          Iterator<SelectionKey> iterator = keySet.iterator();
          while (iterator.hasNext()) {
            SelectionKey selectedKey = iterator.next();
            if (SelectionKey.OP_CONNECT == (SelectionKey.OP_CONNECT & selectedKey.readyOps())) {

//            sc.connect(new InetSocketAddress(InetAddress.getByName("192.168.91.137"), 1694));
              if (sc.isConnected()) {
                synchronized (typeBuffer) {
                  typeBuffer.flip();
                  sc.write(typeBuffer);
                  typeBuffer.clear();
                }
              }
            } else if (SelectionKey.OP_WRITE == (SelectionKey.OP_WRITE & selectedKey.readyOps())) {
              synchronized (typeBuffer) {
                typeBuffer.flip();
                if (!typeBuffer.hasRemaining()) {
                  typeBuffer.clear();
                  continue;
                }
                sc.write(typeBuffer);
                typeBuffer.clear();
//                selectedKey.interestOps();
              }
            } else if (SelectionKey.OP_READ == (SelectionKey.OP_READ & selectedKey.readyOps())) {
              receiveBuffer.clear();
              sc.read(receiveBuffer);
              receiveBuffer.flip();
//            writer.print(new String(receiveBuffer.array()));
              System.out.println(new String(receiveBuffer.array(), 0, receiveBuffer.limit()));
              receiveBuffer.clear();
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}