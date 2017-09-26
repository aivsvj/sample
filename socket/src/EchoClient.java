import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ruyzhu on 9/25/2017.
 */
public class EchoClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputStirng = new StringBuilder();
        String line;
        while(true) {
            if("".equals(line = scanner.nextLine())) {
                break;
            }
            inputStirng.append(line);
        }

        try {
            Socket socket = new Socket("192.168.91.137", 1695);
            OutputStream out = socket.getOutputStream();
            out.write(inputStirng.toString().getBytes());
            out.flush();
            socket.shutdownOutput();

            InputStream in = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            int i;
            char[] buffer = new char[4096];
            StringBuffer content = new StringBuffer();
            i = in.read();
            i = reader.read(buffer);
            while (-1 != (i = reader.read(buffer))) {
                content.append(buffer, 0, i);
            }
            System.out.print(content);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
