import java.util.concurrent.Semaphore;

public class SemaphoreReadersWriters {

    // Fair semaphores (FIFO)
    static final Semaphore serviceQueue = new Semaphore(1, true); // turnstile
    static final Semaphore rmutex       = new Semaphore(1, true); // protects readCount
    static final Semaphore resource     = new Semaphore(1, true); // writers' exclusive / readers' shared via readCount
    static int readCount = 0;

    static class Reader implements Runnable {
        private final int id;
        Reader(int id) { this.id = id; }

        @Override
        public void run() {
            try {
                // Line up in arrival order with writers
                serviceQueue.acquire();
                try {
                    rmutex.acquire();
                    try {
                        readCount++;
                        if (readCount == 1) {
                            // first reader locks resource (writers excluded)
                            resource.acquire();
                        }
                    } finally {
                        rmutex.release();
                    }
                } finally {
                    serviceQueue.release(); // let next thread (reader or writer) step up
                }

                // ---- critical read section (shared among readers) ----
                System.out.println("Reader " + id + " is READING");
                Thread.sleep(500);
                System.out.println("Reader " + id + " has FINISHED READING");
                // ------------------------------------------------------

                rmutex.acquire();
                try {
                    readCount--;
                    if (readCount == 0) {
                        resource.release(); // last reader releases resource
                    }
                } finally {
                    rmutex.release();
                }

            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Writer implements Runnable {
        private final int id;
        Writer(int id) { this.id = id; }

        @Override
        public void run() {
            try {
                // Line up in arrival order with readers
                serviceQueue.acquire();
                try {
                    resource.acquire(); // exclusive access
                } finally {
                    serviceQueue.release();
                }

                // ---- critical write section (exclusive) ----
                System.out.println("Writer " + id + " is WRITING");
                Thread.sleep(1000);
                System.out.println("Writer " + id + " has FINISHED WRITING");
                // --------------------------------------------

                resource.release();

            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread r1 = new Thread(new Reader(1));
        Thread r2 = new Thread(new Reader(2));
        Thread r3 = new Thread(new Reader(3));
        Thread w1 = new Thread(new Writer(1));
        Thread w2 = new Thread(new Writer(2));

        r1.start();
        w1.start();
        r2.start();
        w2.start();
        r3.start();
    }
}
