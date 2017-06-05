public class JobStopper {

    private Integer permisses;

    public JobStopper(Integer threadsWaiting) {
        this.permisses = threadsWaiting;
    }

    public synchronized void decrease() {
        this.permisses--;
        notifyAll();
    }

    public synchronized void waitToFinalize() throws InterruptedException {
        while(permisses > 0)
            wait();
    }
}
