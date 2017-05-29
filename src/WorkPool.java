import java.util.ArrayList;

public class WorkPool {

    private ArrayList<RangeOfWork> rangeOfWorks;

    public WorkPool() {
        this.rangeOfWorks = new ArrayList<RangeOfWork>();
    }

    public Boolean isEmpty() {
        return this.rangeOfWorks.isEmpty();
    }

    public synchronized void push(RangeOfWork rangeOfWork) {
        rangeOfWorks.add(rangeOfWork);
        notifyAll();
    }

    public synchronized RangeOfWork pop() throws InterruptedException {
        while(rangeOfWorks.isEmpty())
            wait();
        RangeOfWork rangeOfWork = this.rangeOfWorks.get(this.rangeOfWorks.size() - 1);
        this.rangeOfWorks.remove(this.rangeOfWorks.size() - 1);
        notifyAll();
        return rangeOfWork;
    }

}