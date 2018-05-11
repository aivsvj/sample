package sam.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ruyzhu on 5/11/2018
 */
public class DeadLockTest extends Thread {


  public static void main(String[] args) {
    final Object lockA = new Object();
    final Object lockB = new Object();

    synchronized (lockA) {
//      CountDownLatch latch = new CountDownLatch(1);

      System.out.println("main thread locked A");
      new Thread(new Runnable() {
        /**
         * 这种方法不行.可能因为锁是可重入的.
         */
        @Override
        public void run() {
//          latch.countDown();
          synchronized (lockB) {
            System.out.println("sub thread locked B.");
            synchronized (lockA) {
              System.out.println("sub thread locked A.");
            }
          }
        }
      }).run();

      try {
        // wait for sub thread start running.
        // CountDownLatch的await方法会释放锁
//        latch.await();
        // waiting for
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      synchronized (lockB) {
        System.out.println("main thread locked B");

      }
    }


    new DeadLockTest().run()
  }

  @Override
  public void run() {
    super.run();
  }
}
