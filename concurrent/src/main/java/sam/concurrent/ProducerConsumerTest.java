package sam.concurrent;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生产者消费者模型
 */
public class ProducerConsumerTest {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    final Basket basket = new Basket();
    Thread consumer = new Thread(() -> {

      while (true) {
        new Consumer().consume(basket);
      }
    });
    consumer.start();

    Thread producer = new Thread(new Runnable() {
      public void run() {

        for (int i = 0; i < 100; i++) {
          new Producer().produce(basket, String.valueOf(ThreadLocalRandom.current().nextInt()));
        }
      }
    });

    producer.start();
  }
}

class Consumer {

  public void consume(Basket basket) {
    System.out.println("consume: " + basket.get());
  }
}

class Producer {

  public void produce(Basket basket, String item) {

    System.out.println("produce: " + item);
    basket.put(item);
  }
}

class Basket {
  private LinkedList<String> queue;
  private int capacity;

  public Basket() {
    this(20);
  }

  public Basket(int capacity) {
    this.queue = new LinkedList<String>();
    this.capacity = capacity;
  }

  public void put(String item) {
    synchronized (queue) {
      if (queue.size() < capacity) {
        queue.add(item);
        if (queue.size() == 1) {
          queue.notifyAll();
        }
      } else {
        try {
          System.out.println("producer waiting...");
          queue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("producer end waiting...");
        put(item);
      }
    }
  }

  public String get() {
    synchronized (queue) {
      if (queue.size() > 0) {
        return queue.removeLast();
      } else {
        try {
          System.out.println("consumer waiting...");
          queue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("consumer end waiting...");
        return get();
      }
    }
  }
}