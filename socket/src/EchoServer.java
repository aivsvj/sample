import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by ruyzhu on 9/25/2017.
 */
public class EchoServer {

    public EchoServer(){}

    public static void main(String[] args) {

        new EchoServer().start();
    }

    public void start() {
        int port = 1695;
        try {
            ServerSocket servletSocket = new ServerSocket(port, 100);

            while (true) {
                Socket socket = servletSocket.accept();

                BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                OutputStream out = socket.getOutputStream();
                byte[] bytes = new byte[4096];
                StringBuffer content = new StringBuffer();
                while (in.read(bytes) != -1) {
                    content.append(Arrays.toString(bytes));
                }
                System.out.println(content.toString());
                out.write(content.toString().getBytes());
                out.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
