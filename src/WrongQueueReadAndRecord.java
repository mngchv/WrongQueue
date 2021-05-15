public class WrongQueueReadAndRecord {

    public static void main(String[] args) {
        new WrongQueueReadAndRecord().go();
    }

    void go() {

        MyQ myQ = new MyQ();

        new Producer(myQ);
        new Consumer(myQ);

    }


    class Producer implements Runnable {

        public MyQ queue;

        public Producer(MyQ queue) {
            this.queue = queue;
            new Thread(this, "Producer").start();
        }

        @Override
        public void run() {
            int i = 0;

            while (true) {
                queue.put(i++);
            }
        }
    }

    class Consumer implements Runnable {

        public MyQ queue;

        public Consumer(MyQ queue) {
            this.queue = queue;
            new Thread(this, "Consumer").start();
        }

        @Override
        public void run() {
            while (true) {
                queue.get();
            }
        }
    }
    class MyQ
    {
        volatile int n;
        volatile boolean isSend = false;

        synchronized void put(int n)
        {
            while(isSend)
            {
                try
                {
                    wait();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            this.n = n;
            System.out.println("Send: " + n);
            isSend = true;
            notify();
        }

        synchronized int get()
        {
            while(!isSend)
            {
                try
                {
                    wait();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("Get: " + n);
            isSend = false;
            notify();
            return n;
        }
    }
}