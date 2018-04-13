import org.apache.activemq.*;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Created by ruyzhuon 4/13/2018.
 */
public class Test {

  public static void main(String[] args) {

    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);

    try {
      ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
      connection.start();
      ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      ActiveMQQueue queue = (ActiveMQQueue) session.createQueue("test-queue");

      ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue);
      ActiveMQMessage message = (ActiveMQMessage) session.createTextMessage("hello");

      producer.send(message);


      ActiveMQMessageConsumer consumer1 = (ActiveMQMessageConsumer) session.createConsumer(queue);
      ActiveMQMessageConsumer consumer2 = (ActiveMQMessageConsumer) session.createConsumer(queue);

      ActiveMQTextMessage recmessage = (ActiveMQTextMessage) consumer1.receive();
      ActiveMQTextMessage recmessage1 = (ActiveMQTextMessage) consumer2.receive();

      System.out.print(recmessage);
      System.out.print(recmessage1);
      connection.close();

    } catch (JMSException e) {
      e.printStackTrace();
    }

  }
}
