import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainThreads {

    static Thread thread;

    public static void main(String[] args) {
        /*
        run("Hilo 1");
        run("Hilo 2");
        run("Hilo 3");
         */

        Hilo2 h = new Hilo2();
        Hilo2 h1 = new Hilo2();
        h.setName("deamon");
        h.start();

        try {
            //sincronize
            h.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        h1.start();

        try {
            h1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("fin, fin, fin!!");

        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

        Condition someCondition = lock.newCondition();

        if (7<8){
            try {
                someCondition.await();
                //wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        someCondition.signalAll();
    }

    synchronized void doSomething() throws InterruptedException {
        //without conditions or lock
        // s
        wait();
        notifyAll();
    }


    static void run(String name){
        Runnable runnable = new Hilo(name);
        thread = new Thread(runnable);
        thread.start();
        stop();

        Thread.currentThread().interrupt();
    }

    static void stop(){
        //thread.stop();
        thread.interrupt();
    }

}

class Hilo implements Runnable{

    String name;

    Hilo(String name){
        this.name = name;
    }

    @Override
    public void run() {
        // do something
        int n = 10;

        while (0 < n){
            System.out.println(name + " " + n);
            n--;
        }
    }

    void aaa(){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //this is so important
            Thread.currentThread().interrupt();
        }
    }

}

class Hilo2 extends Thread{

    public void run(){
        for (int i = 0; i < 15; i++)
            System.out.println("Hilo > " + getName());
        // do something else
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
