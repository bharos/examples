public class ReaderWriter {

    private int readers = 0;
    private boolean writer;

    boolean readLock() {
       while (writer) {
           try {
               wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
               return false;
           }
       }
        readers++;
        return true;
    }
    boolean readUnlock() {
        if (readers == 0) {
            System.out.println("Error..No lock present.");
            return false;
        }
        readers--;
        return true;
    }

     boolean writeLock() {
        while (readers == 0 && !writer) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        writer = true;
        return true;
    }

    boolean writeUnlock() {
        writer = false;
        return true;
    }

}
